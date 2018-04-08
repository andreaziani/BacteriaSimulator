package controller;

import java.util.Optional;
import java.util.Set;
import controller.food.FoodController;
import controller.food.FoodControllerImpl;
import model.Analysis;
import model.Environment;
import model.bacteria.SpeciesBuilder;
import model.simulator.SimulatorEnvironment;
import utils.ConversionsUtil;
import utils.Log;
import utils.exceptions.InvalidSpeciesExeption;
import utils.exceptions.SimulationAlreadyStartedExeption;
import view.model.ViewPosition;
import view.model.ViewState;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFood;
import view.model.food.ViewFoodImpl;

/**
 * Implementation of EnvironmentController.
 *
 */
public class EnvironmentControllerImpl implements EnvironmentController {
    private static final long PERIOD = 100L;
    private Environment env;
    private FoodController foodController;
    private Optional<ViewPosition> maxViewPosition = Optional.empty();
    private InitialState initialState;
    private boolean isStarted;
    private final SimulationLoop loop;

    /**
     * Constructor that builds the EnvironmentController by passing the Environment
     * as an argument.
     */
    public EnvironmentControllerImpl() {
        resetSimulation();

        this.loop = new SimulationLoop() {
            @Override
            public void run() {
                // TODO different condition
                while (!env.getState().getBacteriaState().isEmpty()) {
                    final long start = System.currentTimeMillis();
                    env.update();
                    simulationLoop();
                    final long elapsed = System.currentTimeMillis() - start;
                    
                    //final State state = env.getState();
                    //Log.getLog().info("Bacteria " + state.getBacteriaState().toString());
                    //Log.getLog().info("Food " + state.getFoodsState().toString());

                    try {
                        if(elapsed < PERIOD) {
                            System.out.println(elapsed);
                            Thread.sleep(PERIOD - elapsed);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private void resetSimulation() {
        isStarted = false;
        this.env = new SimulatorEnvironment();
        this.foodController = new FoodControllerImpl(this.env);
        initialState = new InitialState(env.getMaxPosition().getX(), env.getMaxPosition().getY());
    }


    /**
     * Restore the simulation to a state defined by an InitialState object.
     * 
     * @param initialState
     *            the representation of the initial state.
     */
    protected void setInitialState(final InitialState initialState) {
        resetSimulation();
        this.initialState = initialState;
    }

    /**
     * Start the simulation from the initialState saved in this controller.
     */
    protected void startFromInitialState() {
        // TODO resettare env, reinserire tutto da InitialState e fare start
        resetSimulation();
        isStarted = true;
    }

    /**
     * @return the initial state of the simulation.
     */
    protected InitialState getInitialState() {
        return initialState;
    }
    
    /**
     * @return a replay representing the simulation.
     */
    protected Replay getReplay() {
        throw new UnsupportedOperationException();
    }
    
    /**
     * @return the analysis of the simulation.
     */
    protected Analysis getAnalysis() {
        return env.getAnalisys();
    }

    @Override
    public void addFoodFromView(final ViewFood food, final ViewPosition position) {
        this.foodController.addFoodFromViewToModel(food, ConversionsUtil.conversionFromViewPositionToPosition(position,
                env.getMaxPosition(), maxViewPosition.get()));
//         System.out.println(ConversionsUtil.conversionFromViewPositionToPosition(position,
//         env.getMaxPosition(), maxViewPosition.get()).getX() + " " +
//         ConversionsUtil.conversionFromViewPositionToPosition(position,
//         env.getMaxPosition(), maxViewPosition.get()).getY());
        
    }

    protected void simulationLoop() {
    }

    @Override
    public void start() {
        // TODO start
        // TODO complete InitialState
        Log.getLog().info("Application started");
        this.env.init();
        isStarted = true;
        // TODO reorganize logic
        final Thread mainThread = new Thread(this.loop);
        mainThread.start();
    }

    @Override
    public void addNewTypeOfFood(final ViewFood food) {
        this.foodController.addNewTypeOfFood(food);
        initialState.addFood((ViewFoodImpl) food);
    }

    @Override
    public Set<ViewFood> getExistingViewFoods() {
        return this.foodController.getExistingViewFoods();
    }

    @Override
    public ViewState getState() {
        return ConversionsUtil.conversionFromStateToViewState(this.env.getState(), foodController,
                this.env.getMaxPosition(), this.maxViewPosition.get(), initialState);
    }

    @Override
    public void addSpecies(final ViewSpecies species) {
        if (isSimulationStarted()) {
            throw new SimulationAlreadyStartedExeption();
        }
        try {
            final SpeciesBuilder builder = new SpeciesBuilder(species.getName());
            species.getDecisionOptions().forEach(builder::addDecisionMaker);
            species.getDecoratorOptions().forEach(builder::addDecisionBehaiorDecorator);
            env.addSpecies(builder.build());
            initialState.addSpecies(species);
        } catch (IllegalStateException e) {
            throw new InvalidSpeciesExeption();
        }
    }

    @Override
    public void setMaxViewDimension(final ViewPosition position) {
        this.maxViewPosition = Optional.of(position);
    }

    /**
     * @return a boolean indicating if the simulation is already started.
     */
    public boolean isSimulationStarted() {
        return isStarted;
    }

    public boolean isSpeciesEmpty() {
        return this.initialState.getSpecies().isEmpty();
    }
}
