package view.gui;

import java.awt.Font;
import java.awt.Toolkit;

/**
 * Utility class for GUI.
 *
 */
public final class GuiUtils {
    private static final int SCREEN_RES = Toolkit.getDefaultToolkit().getScreenResolution();
    private static final int FONT_SIZE = (int) Math.round(12.0 * SCREEN_RES / 100.0);
    /**
     * Font to use in the project.
     */
    public static final Font FONT = new Font("Arial", Font.PLAIN, FONT_SIZE);

    private GuiUtils() { }
}