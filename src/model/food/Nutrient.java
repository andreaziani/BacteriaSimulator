package model.food;

/**
 * All possible type of nutrients.
 * 
 */
public enum Nutrient {
    /**
     * Water.
     */
    WATER("Water"),
    /**
     * Carbohydrates.
     */
    CARBOHYDRATES("Carbohydrates"),
    /**
     * Peptones.
     */
    PEPTONES("Peptones"),
    /**
     * Hydrolysates.
     */
    HYDROLYSATES("Hydrolysates"),
    /**
     * Inorganic salt.
     */
    INORGANIC_SALT("Inorganic salt");
    private final String name;
    Nutrient(final String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
