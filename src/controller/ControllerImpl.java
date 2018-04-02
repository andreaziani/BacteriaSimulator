package controller;

import java.io.IOException;


/**
 * Controller implementation.
 *
 */
public class ControllerImpl extends EnvironmentControllerImpl implements Controller {

    private final FileController fileController;

    /**
     * Create a controller implementation.
     */
    public ControllerImpl() {
        super();
        fileController = new FileControllerImpl();
    }

    @Override
    public void loadInitialState(final String path) throws IOException {
        this.fileController.loadInitialState(path);
    }

    @Override
    public void saveInitialState(final String path) throws IOException {
        this.fileController.saveInitialState(path, this.getInitialState());
    }

    @Override
    public void loadReplay(final String path) {
        this.fileController.loadReplay(path);
    }

    @Override
    public void saveReplay(final String path) {
        this.fileController.saveReplay(path, this.getReplay());
    }

    @Override
    public void saveAnalisys(final String path) throws IOException {
        this.fileController.saveAnalisys(path, this.getAnalysis());
    }

}
