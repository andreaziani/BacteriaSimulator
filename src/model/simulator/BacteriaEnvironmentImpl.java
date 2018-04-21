package model.simulator;

import java.util.Map.Entry;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.bacteria.Bacteria;
import model.state.Position;

/**
 * Implementation of BacteriaEnvironment.
 *
 */
public class BacteriaEnvironmentImpl implements BacteriaEnvironment {
    private final Position maxPosition;
    private final Map<Position, Bacteria> bacteria = new HashMap<>();
    private BitSet occupiedPosition = new BitSet();

    private int positionToBitSetIndex(final Position pos) {
        return (int) (pos.getX() * this.maxPosition.getX() + pos.getY());
    }

    private void setPosition(final Position bacteriaPos, final Bacteria bact, final boolean value) {
        EnvironmentUtil.positionStream((int) Math.ceil(bact.getRadius()), bacteriaPos, this.maxPosition)
                .forEach(pos -> this.occupiedPosition.set(this.positionToBitSetIndex(pos), value));
    }

    /**
     * Constructor for a Bacteria environment.
     * 
     * @param maxPosition
     *            the maximumPosition in the simulation used as limit for the
     *            positions
     */
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
        positions.stream().forEach(pos -> {
            final Bacteria bacteria = this.bacteria.get(pos);
            this.clearPosition(pos, bacteria);
            this.bacteria.remove(pos);
        });
    }

    @Override
    public void insertBacteria(final Position position, final Bacteria bacteria) {
        this.markPosition(position, bacteria);
        this.bacteria.put(position, bacteria);
    }

    @Override
    public Map<Position, Bacteria> getBacteriaState() {
        return Collections.unmodifiableMap(this.bacteria);
    }

    @Override
    public void updateOccupiedPositions() {
        this.occupiedPosition = new BitSet();
        this.bacteria.entrySet().stream().forEach(e -> this.markPosition(e.getKey(), e.getValue()));
    }

    @Override
    public boolean isPositionOccupied(final Position pos) {
        return this.occupiedPosition.get(this.positionToBitSetIndex(pos));
    }

    @Override
    public void clearPosition(final Position position, final Bacteria bacteria) {
        this.setPosition(position, bacteria, false);
    }

    @Override
    public void markPosition(final Position position, final Bacteria bacteria) {
        this.setPosition(position, bacteria, true);
    }

    @Override
    public int getNumberOfBacteria() {
        return this.bacteria.size();
    }

    @Override
    public Position getMaxPosition() {
        return this.maxPosition;
    }
}
