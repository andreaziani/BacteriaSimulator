package model.action;

import java.util.Objects;

import model.Direction;

/**
 * An implementation of action that has a direction associated. Can only be used
 * with Move action types.
 */
public class DirectionalActionImpl extends AbstractAction implements DirectionalAction {

    private final Direction dir;

    /**
     * Create a directional action given an ActionType and a Direction.
     * 
     * @param type
     *            an ActionType.
     * @param dir
     *            a Direction.
     * @throws IllegalArgumentExeption
     *             if type is not Move
     */
    public DirectionalActionImpl(final ActionType type, final Direction dir) {
        super(type);
        if (type != ActionType.MOVE) {
            throw new IllegalArgumentException("Wrong action type");
        }
        this.dir = dir;
    }

    @Override
    public Direction getDirection() {
        return this.dir;
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
