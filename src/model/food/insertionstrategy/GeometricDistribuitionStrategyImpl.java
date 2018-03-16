package model.food.insertionstrategy;

import org.apache.commons.math3.distribution.GeometricDistribution;

import model.Position;
import model.PositionImpl;

/**
 * Implementation of the random adding strategies. 
 *
 */
public class GeometricDistribuitionStrategyImpl implements RandomPositionStrategy {
    @Override
    public Position getPosition() {
        final GeometricDistribution dist = new GeometricDistribution(0.01);
        return new PositionImpl(dist.sample(), dist.sample());
    }
}
