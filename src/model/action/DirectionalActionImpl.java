package model.action;

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

}
