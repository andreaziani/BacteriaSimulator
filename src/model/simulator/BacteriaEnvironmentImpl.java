package model.simulator;

import java.util.Map.Entry;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.Position;
import model.bacteria.Bacteria;
import utils.EnvironmentUtil;

/**
 * Implementation of BacteriaEnvironment.
 *
 */
public class BacteriaEnvironmentImpl implements BacteriaEnvironment {
    private final Position maxPosition;
    private final Map<Position, Bacteria> bacteria = new HashMap<>();
    private BitSet occupiedPosition;

    private int positionToBitSetIndex(final Position pos) {
        return (int) (pos.getX() * this.maxPosition.getX() + pos.getY());
    }

    public BacteriaEnvironmentImpl(final Position maxPosition) {
        this.maxPosition = maxPosition;
    }
    @Override
    public boolean containBacteriaInPosition(final Position pos) {
        return this.bacteria.containsKey(pos);
    }

    @Override
    public void changeBacteriaPosition(final Position oldPos, final Position newPos) {
        if (this.bacteria.containsKey(oldPos)) {
            final Bacteria bacterium = this.bacteria.get(oldPos);
            this.bacteria.remove(oldPos);
            this.bacteria.put(newPos, bacterium);
        }
    }

    @Override
    public Bacteria getBacteria(final Position pos) {
        return this.bacteria.get(pos);
    }

    @Override
    public Set<Entry<Position, Bacteria>> entrySet() {
        return this.bacteria.entrySet();
    }
    
    @Override
    public Set<Position> activePosition() {
        return this.bacteria.keySet();
    }

    @Override
    public void removeFromPositions(final Set<Position> positions) {
        this.bacteria.keySet().removeAll(positions);
    }

    @Override
    public void insertBacteria(final Position position, final Bacteria bacteria) {
        this.bacteria.put(position, bacteria);
    }

    @Override
    public Map<Position, Bacteria> getBacteriaState() {
        return Collections.unmodifiableMap(this.bacteria);
    }

    @Override
    public void updateOccupiedPositions() {
        this.occupiedPosition = new BitSet();
        this.bacteria.entrySet().stream().forEach(e -> this.setPosition(e.getKey()));
    }

    @Override
    public boolean isPositionOccupied(final Position pos) {
        return this.occupiedPosition.get(this.positionToBitSetIndex(pos));
    }

    @Override
    public void clearPosition(final Position position) {
        final Bacteria bact = this.bacteria.get(position);
        EnvironmentUtil.positionStream((int) Math.ceil(bact.getRadius()), position, this.maxPosition)
                       .filter(pos -> EnvironmentUtil.distance(position, pos) <= bact.getRadius())
                       .forEach(pos -> this.occupiedPosition.clear(this.positionToBitSetIndex(pos)));
    }

    @Override
    public void setPosition(final Position position) {
        final Bacteria bact = this.bacteria.get(position);
        EnvironmentUtil.positionStream((int) Math.ceil(bact.getRadius()), position, this.maxPosition)
                       .filter(pos -> EnvironmentUtil.distance(position, pos) <= bact.getRadius())
                       .forEach(pos -> this.occupiedPosition.set(this.positionToBitSetIndex(pos)));
    }

}
