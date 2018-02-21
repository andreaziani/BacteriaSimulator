package model;

/**
 * Implementation of Energy as a wrapper of a double.
 */
public class EnergyImpl implements Energy {
    /**
     * The Zero Energy.
     */
    public static final Energy ZERO = new EnergyImpl(0);

    private double amount;

    /**
     * Create an Energy from a given amount.
     * 
     * @param amount
     *            a double representing the amount of energy.
     */
    public EnergyImpl(final double amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(final Energy other) {
        return Double.compare(this.getAmount(), other.getAmount());
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public Energy add(final Energy other) {
        return new EnergyImpl(this.getAmount() + other.getAmount());
    }

    @Override
    public Energy subtract(final Energy other) {
        return this.add(other.invert());
    }

    @Override
    public Energy invert() {
        return new EnergyImpl(this.amount * -1);
    }

}
