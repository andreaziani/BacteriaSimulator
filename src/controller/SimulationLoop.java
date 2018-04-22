package controller;

import model.Environment;
import utils.Logger;

/**
 * SimulationLoop is responsible for running the simulation.
 *
 */
public class SimulationLoop implements Runnable {
    private static final long PERIOD = 150L;

    private final EnvironmentController controller;
    private final Environment environment;
    private SimulationState state = new SimulationState();

    private volatile boolean setPaused;

    /**
     * Constructor for SimulationLoop.
     * @param controller the controller that the simulation will use to update the application
     * @param environment the environment on which execute the updates
     */
    public SimulationLoop(final EnvironmentController controller, final Environment environment, final SimulationState currentState) {
        this.controller = controller;
        this.environment = environment;
        this.setPaused = false;
        this.state = currentState;
    }

    @Override
    public void run() {
        this.updateState(SimulationCondition.RUNNING);

        while (this.state.getCurrentCondition() != SimulationCondition.ENDED) {
            final long start = System.currentTimeMillis();
            synchronized (this.controller) {
                environment.update();

                this.controller.addReplayState(environment.getState());
                this.controller.simulationLoop();

                if (environment.isSimulationOver()) {
                    this.updateState(SimulationCondition.ENDED);
                }
            }

            try {
                synchronized (this) {
                    while (this.setPaused) {
                        if (this.state.getCurrentCondition() != SimulationCondition.PAUSED) {
                            this.updateState(SimulationCondition.PAUSED);
                        }
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final long elapsed = System.currentTimeMillis() - start;
            // DUBUGGING INFO
            Logger.getInstance().info("GameLoop", "Elapsed = " + elapsed);

            if (elapsed < PERIOD) {
                try {
                    Thread.sleep(PERIOD - elapsed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateState(final SimulationCondition condition) {
        this.state.setSimulationCondition(condition);
        this.controller.updateCurrentState(condition, state.getCurrentMode());
    }

    /**
     * Stop the simulation.
     */
    public synchronized void stop() {
        this.updateState(SimulationCondition.ENDED);
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
            this.updateState(SimulationCondition.PAUSED);
        }
    }

    /**
     * Resume the simulation.
     */
    public synchronized void resume() {
        if (this.setPaused) {
            this.setPaused = false;
            this.updateState(SimulationCondition.RUNNING);
            notifyAll();
        }
    }
}
