package model;

/**
 * Representation of an amount of energy. Bacteria acquire it by eating foods
 * and use it doing actions.
 */
public interface Energy extends Comparable<Energy> {
    /**
     * @return a double representing the amount of energy this object has.
     */
    double getAmount();

    /**
     * Get the addition of this energy an other.
     * 
     * @param other
     *            an other Energy.
     * @return an new Energy which amount is the sum of the two amounts.
     */
    Energy add(Energy other);

    /**
     * Get the subtraction of an Energy to this.
     * 
     * @param other
     *            the Energy to be subtracted.
     * @return a new Energy which amount is the subtraction of the two amounts.
     */
    Energy subtract(Energy other);

    /**
     * Get the multiplication of this Energy and a double.
     * 
     * @param amount
     *            a double to multiply.
     * @return a new Energy which amount is multiply by the parameter.
     */
    Energy multiply(double amount);

    /**
     * @return a new Energy which amount is equals to the opposite of this
     *         object's amount.
     */
    Energy opposite();
}
