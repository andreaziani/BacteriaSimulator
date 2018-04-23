package view.gui;

import java.awt.Component;
import java.awt.Container;
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

    private GuiUtils() {
    }

    /**
     * Set font of components.
     * 
     * @param comps
     *            the components in which to set font.
     */
    public static void setFontOfComponents(final Component... comps) {
        for (final Component c : comps) {
            if (c instanceof Container) {
                setFontOfComponents(((Container) c).getComponents());
            }
            c.setFont(FONT);
        }
    }
}
