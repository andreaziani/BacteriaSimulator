package controller;

import model.Environment;
import model.state.State;
import utils.Logger;

public class SimulationLoop implements Runnable {
    private static final long PERIOD = 125L;

    private final EnvironmentController controller;
    private final Environment environment;
    private SimulationState state = SimulationState.NOT_READY;

    private volatile boolean isPaused;

    public SimulationLoop(final EnvironmentController controller, final Environment environment) {
        this.controller = controller;
        this.environment = environment;
        this.isPaused = false;
    }

    @Override
    public void run() {
        this.updateState(SimulationState.RUNNING);
        // FOR DEBUGGING
        State currentState;
        while (this.state == SimulationState.RUNNING) {
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
            final long elapsed = System.currentTimeMillis() - start;
            // DUBUGGING INFO
            Logger.getInstance().info("GameLoop",
                    "Elapsed: " + elapsed + " ms\t" + "Bact size: " + currentState.getBacteriaState().size()
                            + "\tFood size: " + currentState.getFoodsState().size() + "\n");

            if (elapsed < PERIOD) {
                try {
                    Thread.sleep(PERIOD - elapsed);
                    synchronized (this) {
                        while (isPaused) {
                            wait();
                        }
                    }
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

    public synchronized void stop() {
        this.updateState(SimulationState.ENDED);
        if (this.isPaused) {
            notifyAll();
        }
    }

    public synchronized void pause() {
        if (!this.isPaused) {
            this.updateState(SimulationState.PAUSED);
            this.isPaused = true;
        }
    }

    public synchronized void resume() {
        if (this.isPaused) {
            this.updateState(SimulationState.RUNNING);
            this.isPaused = false;
            notifyAll();
        }
    }
}
