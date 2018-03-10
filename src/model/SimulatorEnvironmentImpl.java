package model;

import java.util.HashMap;
import java.util.Map;
import java.util.EnumMap;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final ExistingFoodManager manager = new ExistingFoodManagerImpl();
    private final FoodEnvironment foodEnv = new FoodEnvironmentImpl(manager);
    private final FoodFactory factory = new FoodFactoryImpl();
    private final Map<Position, Bacteria> bacteria = new HashMap<>();
    
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
        // TODO Auto-generated method stub
        return null;
    }
  
    private void updateDeadBacteria() {
        Set<Position> toBeRemoved = this.bacteria.entrySet().stream()
                                                            .filter(e -> e.getValue().isDead())
                                                            .peek(e -> this.foodEnv.addFood(e.getValue().getInternalFood(this.factory), e.getKey()))
                                                            .map(e -> e.getKey())
                                                            .collect(Collectors.toSet());
        
        this.bacteria.keySet().removeAll(toBeRemoved);
    }

    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final double radius = this.bacteria.get(bacteriaPos).getPerceptionRadius();
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        for (int y = (int) -Math.ceil(radius); y <= (int) Math.ceil(radius); ++y) {
            for (int x = (int) -Math.ceil(radius); x <= (int) Math.ceil(radius); ++x) {
                final Position currentPos = new PositionImpl(bacteriaPos.getX() + x, bacteriaPos.getY() + y);
                final double distanceToPos = EnvGeometry.distance(currentPos, bacteriaPos);
                if (distanceToPos <= radius && foodsState.containsKey(currentPos)) {
                    Direction closestDir = EnvGeometry.directionFromAngle(EnvGeometry.angle(bacteriaPos, currentPos));
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
        default:    // NOTHING
            break;
        }
    }

    private void updateLivingBacteria() {
        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();
        this.bacteria.entrySet().stream()
                                .peek(e -> e.getValue().setPerception(this.createPerception(e.getKey(), foodsState)))
                                .forEach(e -> this.performAction(e.getKey(), e.getValue()));
    }
    
    @Override
    public void update() {
        updateDeadBacteria();
        updateLivingBacteria();
    }

    @Override
    public Analisys getAnalisys() {
        // TODO Auto-generated method stub
        return null;
    }

}
