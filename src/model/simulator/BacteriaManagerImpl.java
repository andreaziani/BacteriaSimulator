package model.simulator;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import model.Direction;
import model.EnergyImpl;
import model.Position;
import model.bacteria.Bacteria;
import model.food.Food;
import model.food.FoodEnvironment;
import model.food.FoodFactory;
import model.food.FoodFactoryImpl;
import model.perception.Perception;
import model.perception.PerceptionImpl;
import utils.EnvGeometry;

/**
 * Implementation of BacteriaManager.
 *
 */
public class BacteriaManagerImpl implements BacteriaManager {
    private final double ENV_COST_OF_LIVING;
    private final FoodFactory factory = new FoodFactoryImpl();
    private final FoodEnvironment foodEnv;
    private final Map<Position, Bacteria> bacteria = new HashMap<>();
    private final ActionPerformer actionPerf = new ActionPerformer();

    /**
     * Constructor.
     * @param foodEnv used to update food env according to bacteria actions
     * @param COST_OF_LIVING amount of energy that a Bacteria spend just to stay alive
     */
    public BacteriaManagerImpl(final FoodEnvironment foodEnv, final double COST_OF_LIVING) {
        this.foodEnv = foodEnv;
        this.ENV_COST_OF_LIVING = COST_OF_LIVING;
    }

    // TODO separate this method whit IntPairStream()
    private Map<Direction, Double> closestFoodDistances(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final double radius = this.bacteria.get(bacteriaPos).getPerceptionRadius();
        final int start = (int) -Math.ceil(radius);
        final int end = (int) Math.ceil(radius);
        final Map<Direction, Double> distsToFood = new EnumMap<Direction, Double>(Direction.class);

        EnvGeometry.positionStream(start, end, start, end, bacteriaPos).forEach(position -> {
            final double distanceToPos = EnvGeometry.distance(position, bacteriaPos);
            if (distanceToPos <= radius && foodsState.containsKey(position)) {
                final Direction closestDir = EnvGeometry.directionFromAngle(EnvGeometry.angle(bacteriaPos, position));
                if (!distsToFood.containsKey(closestDir) || distanceToPos < distsToFood.get(closestDir)) {
                    distsToFood.put(closestDir, distanceToPos);
                }
            }
        });
        return distsToFood;
    }

    private Perception createPerception(final Position bacteriaPos, final Map<Position, Food> foodsState) {
        final Optional<Food> foodInPosition = foodsState.containsKey(bacteriaPos) ? Optional.of(foodsState.get(bacteriaPos)) : Optional.empty();
        final Map<Direction, Double> distsToFood = closestFoodDistances(bacteriaPos, foodsState);
        return new PerceptionImpl(foodInPosition, distsToFood);
    }

    private void performAction(final Position bacteriaPos, final Bacteria bacteria) {
        switch (bacteria.getAction().getType()) {
        case MOVE: actionPerf.move();
            break;
        case EAT: actionPerf.eat();
            break;
        case REPLICATE: actionPerf.replicate();
            break;
        default: actionPerf.doNothing();
            break;
        }
    }

    private void costOfLiving(final Bacteria bacteria) {
        bacteria.spendEnergy(new EnergyImpl(this.ENV_COST_OF_LIVING));
    }

    private void updateLivingBacteria() {
        final Map<Position, Food> foodsState = this.foodEnv.getFoodsState();
        this.bacteria.entrySet().stream()
                                .peek(e -> e.getValue().setPerception(this.createPerception(e.getKey(), foodsState)))
                                .peek(e -> this.performAction(e.getKey(), e.getValue()))
                                .forEach(e -> this.costOfLiving(e.getValue()));
    }

    private void updateDeadBacteria() {
        final Set<Position> toBeRemoved = this.bacteria.entrySet().stream()
                                                            .filter(e -> e.getValue().isDead())
                                                            .peek(e -> this.foodEnv.addFood(e.getValue().getInternalFood(this.factory), e.getKey()))
                                                            .map(e -> e.getKey())
                                                            .collect(Collectors.toSet());
        this.bacteria.keySet().removeAll(toBeRemoved);
    }

    @Override
    public void updateBacteria() {
        this.updateLivingBacteria();
        this.updateDeadBacteria();
    }

    /**
     * Inner class whose sole task is to perform Baacteria's actions.
     */
    private class ActionPerformer {
        private void move() {
            //
        }

        private void eat() {
            //
        }

        private void replicate() {
            //
        }

        private void doNothing() {
            //
        }
    }
}
