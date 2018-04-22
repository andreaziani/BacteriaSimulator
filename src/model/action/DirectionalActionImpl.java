package model.action;

import java.util.Objects;

import model.Direction;

final class DirectionalActionImpl extends SimpleAction implements DirectionalAction {

    private final Direction dir;
    private final double distance;

    DirectionalActionImpl(final ActionType type, final Direction dir, final double distance) {
        super(type);
        this.dir = dir;
        this.distance = distance;
    }

    @Override
    public Direction getDirection() {
        return this.dir;
    }

    @Override
    public double getDistance() {
        return this.distance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dir, super.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DirectionalActionImpl other = (DirectionalActionImpl) obj;
        return Objects.equals(dir, other.dir);
    }

}
