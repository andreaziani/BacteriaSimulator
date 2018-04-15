package controller;

import java.io.File;
import java.io.IOException;

import view.View;
import view.ViewImpl;

/**
 * Controller implementation.
 *
 */
public class ControllerImpl extends EnvironmentControllerImpl implements Controller {
    private final FileController fileController;
    private View view;

    /**
     * Create a controller implementation.
     */
    public ControllerImpl() {
        super();
        fileController = new FileControllerImpl();
    }

    @Override
    protected void simulationLoop() {
        this.view.update(this.getState());
    }

    @Override
    public synchronized void setView(final ViewImpl view) {
        this.view = view;
    }

    @Override
    public synchronized void loadInitialState(final String path) throws IOException {
        this.updateCurrentState(SimulationState.REPLAY);
        this.setInitialState(this.fileController.loadInitialState(path));
    }

    @Override
    public synchronized void saveInitialState(final String path) throws IOException {
        this.fileController.saveInitialState(path, this.getInitialState());
    }

    @Override
    public synchronized void loadReplay(final String path) throws IOException {
        this.startReplay(this.fileController.loadReplay(path));
    }

    @Override
    public synchronized void saveReplay(final String path) throws IOException {
        this.fileController.saveReplay(path, this.getReplay());
    }

    @Override
    public synchronized void saveAnalisys(final String path) throws IOException {
        this.fileController.saveAnalysis(path, this.getAnalysis());
    }
    @Override
    public void updateCurrentState(final SimulationState state) {
        super.updateCurrentState(state);
        this.view.updateSimulationState(state);
    }
}
