package model;
/**
 *Representation of an amount of energy. Bacteria acquire it by eating foods and use it doing actions. 
 */
@FunctionalInterface
public interface Energy {
    /**
     * @return a double representing the amount of energy this object has.
     */
    double getAmount();
}
