package application;

import controller.Controller;
import controller.ControllerImpl;
import view.ViewImpl;
import view.gui.MainFrame;

/**
 *test main class.
 */
public final class BacteriaSimulator {

    private final Controller controller;

    private BacteriaSimulator() {
        this.controller = new ControllerImpl();
    }

    private void start() {
        final ViewImpl view = new ViewImpl(this.controller);
        this.controller.setView(view);
        final MainFrame mainFrame = new MainFrame(view);
    }

    /**
     * Entry point of the application.
     * @param args additional argument
     */
    public static void main(final String[] args) {
        final BacteriaSimulator application = new BacteriaSimulator();
        application.start();
    }
}
