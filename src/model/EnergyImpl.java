package model;

import java.util.Objects;

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
        return Objects.hash(amount);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final EnergyImpl other = (EnergyImpl) obj;
        return amount == other.amount;
    }

    @Override
    public String toString() {
       return String.valueOf(this.amount);
    }
}
