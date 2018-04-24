package model.simulator;

import java.util.BitSet;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.bacteria.Bacteria;
import model.state.Position;

/**
 * Implementation of BacteriaEnvironment.
 *
 */
public final class BacteriaEnvironmentImpl implements BacteriaEnvironment {
    private static final int EDGE = 15;
    private final Position maxPosition;
    private final Map<Quadrant, Map<Position, Bacteria>> bacteria;
    private final int limitLeftRight;
    private final int limitTopBottom;
    private BitSet occupiedPosition;

    private enum Quadrant {
        TOP_RIGHT, TOP_LEFT, BOTTOM_RIGHT, BOTTOM_LEFT
    }

    private int positionToBitSetIndex(final Position pos) {
        return (int) (pos.getX() * this.maxPosition.getX() + pos.getY());
    }

    private void setPosition(final Position bacteriaPos, final Bacteria bact, final boolean value) {
        EnvironmentUtil.positionStream((int) Math.ceil(bact.getRadius()), bacteriaPos, this.maxPosition)
                .forEach(pos -> this.occupiedPosition.set(this.positionToBitSetIndex(pos), value));
    }

    private Quadrant positionToQuadrant(final Position pos) {
        if (pos.getX() > this.limitLeftRight) {
            if (pos.getY() > this.limitTopBottom) {
                return Quadrant.TOP_RIGHT;
            } else {
                return Quadrant.BOTTOM_RIGHT;
            }
        } else {
            if (pos.getY() > this.limitTopBottom) {
                return Quadrant.TOP_LEFT;
            } else {
                return Quadrant.BOTTOM_LEFT;
            }
        }
    }

    private Map<Position, Bacteria> getMap(final Position pos) {
        final Quadrant quad = this.positionToQuadrant(pos);
        return this.bacteria.get(quad);
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
        this.bacteria = new EnumMap<>(Quadrant.class);
        for (final Quadrant q : Quadrant.values()) {
            this.bacteria.put(q, new HashMap<>());
        }
        this.limitLeftRight = (int) maxPosition.getX() / 2;
        this.limitTopBottom = (int) maxPosition.getY() / 2;
        this.occupiedPosition = new BitSet((int) Math.ceil(maxPosition.getX() * maxPosition.getY()));
    }


    @Override
    public boolean containBacteriaInPosition(final Position pos) {
        return this.getMap(pos).containsKey(pos);
    }

    @Override
    public void changeBacteriaPosition(final Position oldPos, final Position newPos) {
        if (this.getMap(oldPos).containsKey(oldPos)) {
            final Bacteria bacterium = this.getMap(oldPos).get(oldPos);
            this.getMap(oldPos).remove(oldPos);
            this.getMap(newPos).put(newPos, bacterium);
        }
    }

    @Override
    public Bacteria getBacteria(final Position pos) {
        return this.getMap(pos).get(pos);
    }


    @Override
    public void removeFromPositions(final List<Position> positions) {
        positions.stream().forEach(pos -> {
            final Bacteria bacteria = this.getMap(pos).get(pos);
            this.clearPosition(pos, bacteria);
            this.getMap(pos).remove(pos);
        });
    }

    @Override
    public void insertBacteria(final Position position, final Bacteria bacteria) {
        this.markPosition(position, bacteria);
        this.getMap(position).put(position, bacteria);
    }

    @Override
    public Map<Position, Bacteria> getBacteriaState() {
        return this.bacteria.values().stream().flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    @Override
    public void updateOccupiedPositions() {
        this.occupiedPosition = new BitSet();
        this.bacteria.keySet().forEach(quad -> this.bacteria.get(quad).entrySet().stream()
                .forEach(e -> this.markPosition(e.getKey(), e.getValue())));
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
        return this.bacteria.keySet().stream().mapToInt(quad -> this.bacteria.get(quad).size()).sum();
    }

    @Override
    public boolean isSafe(final Position pos) {
        return Math.abs(pos.getX() - this.limitLeftRight) > EDGE && Math.abs(pos.getY() - this.limitTopBottom) > EDGE;
    }

    @Override
    public int getQuadrant(final Position pos) {
        return this.positionToQuadrant(pos).ordinal();
    }

    @Override
    public int getNumberOfQuadrants() {
        return Quadrant.values().length;
    }
}
