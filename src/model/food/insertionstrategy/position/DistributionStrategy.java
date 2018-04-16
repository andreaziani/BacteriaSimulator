package model.food.insertionstrategy.position;
/**
 * 
 * A strategy that a user can chose.
 *
 */
public enum DistributionStrategy {
    /**
     * Uniform distribution strategy.
     */
    UNIFORM_DISTRIBUTION("Uniform distribution"),
    /**
     * Geometric distribution strategy.
     */
    GEOMETRIC_DISTRIBUTION("Geometric distribution");
    private final String name;
    DistributionStrategy(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
