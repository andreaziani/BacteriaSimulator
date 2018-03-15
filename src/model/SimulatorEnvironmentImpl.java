package model;

import java.util.HashMap;
import java.util.Map;
import java.util.EnumMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import model.bacteria.Bacteria;
import model.food.ExistingFoodManager;
import model.food.ExistingFoodManagerImpl;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodEnvironmentImpl;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.perception.Perception;
import model.perception.PerceptionImpl;
import utils.EnvGeometry;

/**
 * implementation of SimulatorEnvironment.
 *
 */
public class SimulatorEnvironmentImpl implements SimulatorEnvironment {
    private final int foodPerRound = 15;
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final FoodEnvironment foodEnv = new FoodEnvironmentImpl(manager);
    private final FoodFactory factory = new FoodFactoryImpl();
    private final Map<Position, Bacteria> bacteria = new HashMap<>();
    private final State state = new StateImpl(this.foodEnv);

    @Override
    public void addFood(final Food food, final Position position) {
        this.foodEnv.addFood(food, position);
    }

    @Override
    public ExistingFoodManager getExistingFoods() {
        return this.manager;
    }

    @Override
    public State getState() {
        return this.state;
    }

    private void updateDeadBacteria() {
        final Set<Position> toBeRemoved = this.bacteria.entrySet().stream()
                                                            .filter(e -> e.getValue().isDead())
                                                            .peek(e -> this.foodEnv.addFood(e.getValue().getInternalFood(this.factory), e.getKey()))
                                                            .map(e -> e.getKey())
                                                            .collect(Collectors.toSet());
        this.bacteria.keySet().removeAll(toBeRemoved);
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final double radius = this.bacteria.get(bacteriaPos).getPerceptionRadius();
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        for (double y = -Math.ceil(radius); y <= Math.ceil(radius); y += EnvGeometry.SPACE_UNIT) {
            for (double x = -Math.ceil(radius); x <= Math.ceil(radius); x += EnvGeometry.SPACE_UNIT) {
                final Position currentPos = new PositionImpl(bacteriaPos.getX() + x, bacteriaPos.getY() + y);
                final double distanceToPos = EnvGeometry.distance(currentPos, bacteriaPos);
                if (distanceToPos <= radius && foodsState.containsKey(currentPos)) {
                    final Direction closestDir = EnvGeometry.directionFromAngle(EnvGeometry.angle(bacteriaPos, currentPos));
                    if (!distsToFood.containsKey(closestDir) || distanceToPos < distsToFood.get(closestDir)) {
                        distsToFood.put(closestDir, distanceToPos);
                    }
                }
            }
        }
        return distsToFood;
    }

    private Perception createPerception(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final Optional<Food> foodInPosition = foodsState.containsKey(bacteriaPos) ? Optional.of(foodsState.get(bacteriaPos)) : Optional.empty();
        final Map<Direction, Double> distsToFood = closestFoodDistances(bacteriaPos, foodsState);
        return new PerceptionImpl(foodInPosition, distsToFood);
    }

    private void performAction(final Position bacteriaPos, final Bacteria bacteria) {
        switch (bacteria.getAction().getType()) {
        case MOVE: 
            break;
        case EAT:
            break;
        case REPLICATE: 
            break;
        default: 
            break;
        }
    }

    private void updateLivingBacteria() {
        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();
        this.bacteria.entrySet().stream()
                                .peek(e -> e.getValue().setPerception(this.createPerception(e.getKey(), foodsState)))
                                .forEach(e -> this.performAction(e.getKey(), e.getValue()));
    }

    private void updateFood() {
        IntStream.range(0, foodPerRound).forEach(x -> this.foodEnv.addRandomFood());
    }

    @Override
    public void update() {
        this.updateDeadBacteria();
        this.updateLivingBacteria();
        this.updateFood();
    }

    @Override
    public Analisys getAnalisys() {
        // TODO Auto-generated method stub
        return null;
    }
}
