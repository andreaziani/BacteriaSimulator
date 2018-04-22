package model.food.insertionstrategy.foodinsertion;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import model.food.ExistingFoodManager;
import model.food.Food;

/**
 * Implementation of the strategy that uses a uniform distribution for the
 * random choice.
 *
 */
public final class RandomSelectionStrategyImpl implements SelectionFoodStrategy {

    @Override
    public Food getFood(final ExistingFoodManager manager) {
        final Random rand = new Random();
        final List<Food> list = manager.getExistingFoods().stream().collect(Collectors.toList());
        return list.get(rand.nextInt(list.size()));
    }

}
