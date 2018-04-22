package model.action;

import java.util.EnumMap;
import java.util.Map;

import model.Direction;

/**
 * Simple factory to create objects that implement the Action interface.
 */
public final class ActionFactory {
    private static final Map<ActionType, Action> SIMPLE_ACTIONS = new EnumMap<>(ActionType.class);
    static {
        SIMPLE_ACTIONS.put(ActionType.NOTHING, new SimpleAction(ActionType.NOTHING));
        SIMPLE_ACTIONS.put(ActionType.EAT, new SimpleAction(ActionType.EAT));
        SIMPLE_ACTIONS.put(ActionType.REPLICATE, new SimpleAction(ActionType.REPLICATE));
    }

    private ActionFactory() {
    }

    /**
     * Create an Action only from the type of the action.
     * 
     * @param type
     *            the type of the action to create.
     * @return a new action corresponding to the given type, if that type can
     *         represent an action without other information.
     * @throws IllegalArgumentExeption
     *             if type is not NOTHING, EAT or REPLICATE.
     */
    public static Action createAction(final ActionType type) {
        if (type != ActionType.NOTHING && type != ActionType.EAT && type != ActionType.REPLICATE) {
            throw new IllegalArgumentException("Wrong action type");
        }
        return SIMPLE_ACTIONS.get(type);
    }

    /**
     * Create an Action from the type of the action, a direction and the distance to
     * move in that direction.
     * 
     * @param type
     *            the type of the action to create.
     * @param dir
     *            the direction in which to move to.
     * @param distance
     *            the distance to move.
     * @return a new action corresponding to the given type, direction and distance,
     *         if that type can represent an action with these informations.
     * @throws IllegalArgumentExeption
     *             if type is not MOVE.
     */
    public static Action createAction(final ActionType type, final Direction dir, final double distance) {
        if (type != ActionType.MOVE) {
            throw new IllegalArgumentException("Wrong action type");
        }
        return new DirectionalActionImpl(type, dir, distance);
    }
}
