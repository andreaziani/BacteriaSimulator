package model;

/**
 * Implementation of Energy as a wrapper of a double.
 */
public class EnergyImpl implements Energy {
    /**
     * The Zero Energy.
     */
    public static final Energy ZERO = new EnergyImpl(0);

    private final double amount;

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
    public Energy multiply(final double amount) {
        return new EnergyImpl(this.amount * amount);
    }

    @Override
    public Energy invert() {
        return new EnergyImpl(this.amount * -1);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(amount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EnergyImpl other = (EnergyImpl) obj;
        return Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount);
    }

}
