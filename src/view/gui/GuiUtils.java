package view.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Collections;

import javax.swing.UIManager;

/**
 * Utility class for GUI.
 *
 */
public final class GuiUtils {
    private static final int SCREEN_RES = Toolkit.getDefaultToolkit().getScreenResolution();
    private static final int FONT_SIZE = (int) Math.round(13.0 * SCREEN_RES / 100.0);
    private static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int SH = (int) SCREEN.getHeight();
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

    /**
     * Resize the buttons in JDialog.
     */
    public static void resizeButtonsInDialogs() {
        for (final Object key : Collections.list(UIManager.getDefaults().keys())) {
            final Object value = UIManager.get(key);
            if (value instanceof Font) {
                final Font origin = (Font) value;
                 final float originalSize = origin.getSize();
                 final float newSize = originalSize * SH / 1000;
                final Font desired = origin.deriveFont(newSize);
                UIManager.put(key, desired);
            }
        }
    }
}
