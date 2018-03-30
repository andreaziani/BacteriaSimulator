package model.food.insertionstrategy;

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
public class RandomFoodStrategyImpl implements RandomFoodStrategy {

    @Override
    public Food getFood(final ExistingFoodManager manager) {
        final Random rand = new Random();
        final List<Food> list = manager.getExistingFoodsSet().stream().collect(Collectors.toList());
        return list.get(rand.nextInt(list.size()));
    }

}
