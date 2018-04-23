package model.simulator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import model.PositionAlreadyOccupiedException;
import model.food.ExistingFoodManager;
import model.food.Food;
import model.food.insertionstrategy.position.GeometricDistributionStrategy;
import model.food.insertionstrategy.position.PoissonDistributionStrategy;
import model.food.insertionstrategy.foodinsertion.SelectionStrategy;
import model.food.insertionstrategy.foodinsertion.RandomSelectionStrategy;
import model.food.insertionstrategy.position.DistributionStrategy;
import model.food.insertionstrategy.position.PositionStrategy;
import model.food.insertionstrategy.position.RandomPositionStrategy;
import model.state.Position;

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
    private DistributionStrategy strategy = DistributionStrategy.UNIFORM_DISTRIBUTION;

    /**
     * Construct the FoodEnvironment from an ExistingFoodManager with which to know
     * the types of food already created and the maximum dimension of the
     * environment.
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
        if (!food.getNutrients().isEmpty()) {
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
        final SelectionStrategy foodStrategy = new RandomSelectionStrategy();
        PositionStrategy positionStrategy;
        switch (strategy) {
        case GEOMETRIC_DISTRIBUTION:
            positionStrategy = new GeometricDistributionStrategy(this.maxDim);
            break;
        case UNIFORM_DISTRIBUTION:
            positionStrategy = new RandomPositionStrategy(maxDim);
            break;
        case POISSON_DISTRIBUTION:
            positionStrategy = new PoissonDistributionStrategy(maxDim);
            break;
        default:
            positionStrategy = new RandomPositionStrategy(maxDim);
            break;
        }
        for (int i = MAXATTEMPS; (i > 0 && check); i--) {
            try {
                addFood(foodStrategy.getFood(manager), positionStrategy.getPosition());
                check = false;
            } catch (PositionAlreadyOccupiedException e) {
                check = true;
            }
        }
    }
}
