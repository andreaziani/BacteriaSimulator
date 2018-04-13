package model.food;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import model.Position;
import model.food.insertionstrategy.RandomFoodStrategy;
import model.food.insertionstrategy.RandomFoodStrategyImpl;
import model.food.insertionstrategy.position.GeometricDistribuitionStrategyImpl;
import model.food.insertionstrategy.position.DistributionStrategy;
import model.food.insertionstrategy.position.RandomPositionStrategy;
import model.food.insertionstrategy.position.RandomPositionStrategyImpl;
import utils.EnvironmentUtil;
import utils.exceptions.PositionAlreadyOccupiedException;

/**
 * Implementation of FoodEnvironment, it contains information about foods.
 *
 *
 */
public final class FoodEnvironmentImpl implements FoodEnvironment {
    private static final int MAXATTEMPS = 10;
    private final Position maxDim;
    private final Map<Position, Food> foods = new HashMap<>();
    private final ExistingFoodManager manager;
    private DistributionStrategy strategy = DistributionStrategy.UNIFORM_DISTRIBUTION; //Uniform distribution by default.

    /**
     * Construct the FoodEnvironment from an ExistingFoodManager with which to know
     * the types of food already created and the maximum dimension of the environment.
     * 
     * @param manager
     *            that contains all existing foods.
     * @param maximumDimension
     *            the maximum position in the environment.
     */
    public FoodEnvironmentImpl(final ExistingFoodManager manager, final Position maximumDimension) {
        this.manager = manager;
        this.maxDim = maximumDimension;
    }

    @Override
    public void addFood(final Food food, final Position position) {
        if (!food.getNutrients().isEmpty()) { // Can't add a food with no nutrients.
            // a food can be added only if it don't collide with other foo
            if (!this.foods.entrySet().stream().anyMatch(
                    e -> EnvironmentUtil.isCollision(Pair.of(position, food), Pair.of(e.getKey(), e.getValue())))) {
                this.foods.put(position, food);
            } else {
                throw new PositionAlreadyOccupiedException();
            }
        }
    }

    @Override
    public void removeFood(final Food food, final Position position) {
        if (this.foods.containsKey(position) && this.foods.get(position).equals(food)) {
            this.foods.remove(position);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Map<Position, Food> getFoodsState() {
        return Collections.unmodifiableMap(this.foods);
    }

    @Override
    public void changeFoodPosition(final Position oldPosition, final Position newPosition, final Food food) {
        removeFood(food, oldPosition);
        addFood(food, newPosition);
    }

    @Override
    public void setDistributionStrategy(final DistributionStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void addRandomFood() {
        boolean check = true;
        final RandomFoodStrategy foodStrategy = new RandomFoodStrategyImpl();
        RandomPositionStrategy positionStrategy;
        if (this.strategy == DistributionStrategy.GEOMETRIC_DISTRIBUTION) {
            positionStrategy = new GeometricDistribuitionStrategyImpl(this.maxDim, strategy);
        } else {
            positionStrategy = new RandomPositionStrategyImpl(maxDim, strategy);
        }
        for (int i = MAXATTEMPS; (i > 0 && check); i--) { // try to re-insert in another position if the precedent was occupied.
            try {
                addFood(foodStrategy.getFood(manager), positionStrategy.getPosition());
                check = false;
            } catch (PositionAlreadyOccupiedException e) {
                check = true;
            }
        }
    }
}
