package model.simulator;

import java.util.Map.Entry;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.Position;
import model.bacteria.Bacteria;

/**
 * Implementation of BacteriaEnvironment.
 *
 */
public class BacteriaEnvironmentImpl implements BacteriaEnvironment {
    private final Map<Position, Bacteria> bacteria = new HashMap<>();

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

}
