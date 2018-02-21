package model.food;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Implementation of the strategies for adding foods randomly.
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
