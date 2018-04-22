package application;

import controller.Controller;
import controller.ControllerImpl;
import view.controller.ViewImpl;
import view.gui.UserInterface;

/**
 *test main class.
 */
public final class BacteriaSimulator {

    private final ViewImpl view;

    private BacteriaSimulator() {
        final Controller controller = new ControllerImpl();
        this.view = new ViewImpl(controller);
        controller.setView(view);
    }

    private void start() {
        final UserInterface frame = new UserInterface(view);
        this.view.setUserInterface(frame);
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
