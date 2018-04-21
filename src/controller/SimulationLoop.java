package controller;

import model.Environment;
import model.state.State;
import utils.Logger;

/**
 * SimulationLoop is responsible for running the simulation.
 *
 */
public class SimulationLoop implements Runnable {
    private static final long PERIOD = 150L;

    private final EnvironmentController controller;
    private final Environment environment;
    private SimulationState state = SimulationState.NOT_READY;

    private volatile boolean setPaused;

    /**
     * Constructor for SimulationLoop.
     * @param controller the controller that the simulation will use to update the application
     * @param environment the environment on which execute the updates
     */
    public SimulationLoop(final EnvironmentController controller, final Environment environment) {
        this.controller = controller;
        this.environment = environment;
        this.setPaused = false;
    }

    @Override
    public void run() {
        this.updateState(SimulationState.RUNNING);
        // FOR DEBUGGING
        State currentState;
        while (this.state != SimulationState.ENDED) {
            final long start = System.currentTimeMillis();
            synchronized (this.controller) {
                environment.update();
                // FOR DEBUGGING
                currentState = environment.getState();
                this.controller.addReplayState(currentState);
                this.controller.simulationLoop();

                if (environment.isSimulationOver()) {
                    this.updateState(SimulationState.ENDED);
                }
            }

            try {
                synchronized (this) {
                    while (this.setPaused) {
                        if (this.state != SimulationState.PAUSED) {
                            this.updateState(SimulationState.PAUSED);
                        }
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final long elapsed = System.currentTimeMillis() - start;
            // DUBUGGING INFO
            Logger.getInstance().info("GameLoop",
                    "Elapsed: " + elapsed + " ms\t" + "Bact size: " + currentState.getBacteriaState().size()
                            + "\tFood size: " + currentState.getFoodsState().size() + "\n");

            if (elapsed < PERIOD) {
                try {
                    Thread.sleep(PERIOD - elapsed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateState(final SimulationState state) {
        this.state = state;
        this.controller.updateCurrentState(state);
    }

    /**
     * Stop the simulation.
     */
    public synchronized void stop() {
        this.updateState(SimulationState.ENDED);
        if (this.setPaused) {
            notifyAll();
        }
    }

    /**
     * Pause the simulation.
     */
    public synchronized void pause() {
        if (!this.setPaused) {
            this.setPaused = true;
            this.updateState(SimulationState.PAUSED);
        }
    }

    /**
     * Resume the simulation.
     */
    public synchronized void resume() {
        if (this.setPaused) {
            this.setPaused = false;
            this.updateState(SimulationState.RUNNING);
            notifyAll();
        }
    }
}
