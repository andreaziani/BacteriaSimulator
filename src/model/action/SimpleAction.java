package model.action;

import java.util.Objects;

class SimpleAction implements Action {

    private final ActionType type;

    SimpleAction(final ActionType type) {
        this.type = type;
    }

    @Override
    public final ActionType getType() {
        return this.type;
    }

    /**
     * Hash the Action based only on its type. A subclass should consider using the
     * superclass hash and ignore the type.
     */
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    /**
     * Compare if the runtime class of the objects are equal. Then compare if their
     * types are equal.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final SimpleAction other = (SimpleAction) obj;
        return Objects.equals(type, other.type);
    }

}
