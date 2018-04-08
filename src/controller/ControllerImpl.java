package controller;

import java.io.File;
import java.io.IOException;

import view.ViewImpl;

/**
 * Controller implementation.
 *
 */
public class ControllerImpl extends EnvironmentControllerImpl implements Controller {
    private final FileController fileController;
    private ViewImpl view;

    /**
     * Create a controller implementation.
     */
    public ControllerImpl() {
        super();
        fileController = new FileControllerImpl();
    }

    @Override
    protected void simulationLoop() {
        super.simulationLoop();
        this.view.update(this.getState());
    }

    @Override
    public void setView(final ViewImpl view) {
        this.view = view;
    }

    @Override
    public void loadInitialState(final File file) throws IOException {
        this.setInitialState(this.fileController.loadInitialState(file));
    }

    @Override
    public void saveInitialState(final File file) throws IOException {
        this.fileController.saveInitialState(file, this.getInitialState());
    }

    @Override
    public void loadReplay(final File file) {
        this.fileController.loadReplay(file);
    }

    @Override
    public void saveReplay(final File file) {
        this.fileController.saveReplay(file, this.getReplay());
    }

    @Override
    public void saveAnalisys(final File file) throws IOException {
        this.fileController.saveAnalysis(file, this.getAnalysis());
    }
}
