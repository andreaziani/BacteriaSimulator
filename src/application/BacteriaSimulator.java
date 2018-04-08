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
    private final MainFrame mainFrame;

    private BacteriaSimulator() {
        this.controller = new ControllerImpl();
        this.mainFrame = new MainFrame(new ViewImpl(this.controller));
    }

    private void start() {
//        this.controller.init();
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
