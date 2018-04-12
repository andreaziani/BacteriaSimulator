package view.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import view.ViewController;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFood;
import view.model.food.ViewProvision;

/**
 * Panel that assign colors to species.
 */
public class LegendPanel extends JPanel implements ColorAssigner {

    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -8989135061384289871L;
    private static final String UNNAMED_FOOD = "Unnamed Food";

    private final ViewController viewController;
    private JPanel legendContainer;
    private final List<Color> candidateFoodsColors;
    private final List<Color> candidateSpeciesColors;
    private final JLabel foodLabel;
    private final JLabel speciesLabel;

    private Map<String, Color> foodColors;
    private Map<String, Color> speciesColors;

    /**
     * Create a new LegendPanel specifing the ViewController of the application.
     * 
     * @param viewController
     *            the view controller.
     */
    public LegendPanel(final ViewController viewController) {
        super();
        candidateFoodsColors = new ArrayList<>();
        candidateSpeciesColors = new ArrayList<>();
        candidateFoodsColors.add(Color.RED);
        candidateFoodsColors.add(Color.ORANGE);
        candidateFoodsColors.add(Color.PINK);
        candidateFoodsColors.add(Color.YELLOW);

        candidateSpeciesColors.add(Color.BLUE);
        candidateSpeciesColors.add(Color.CYAN);
        candidateSpeciesColors.add(Color.GREEN);
        candidateSpeciesColors.add(Color.MAGENTA);

        this.viewController = viewController;
        foodColors = new HashMap<>();
        speciesColors = new HashMap<>();
        legendContainer = new JPanel();
        legendContainer.setVisible(false);
        foodLabel = new JLabel("Food colors:");
        speciesLabel = new JLabel("Species colors:");
        this.add(legendContainer);
    }

    /**
     * Update foods and species in the legend. This method is not thread safe.
     */
    public void update() {
        final Set<ViewFood> foods = viewController.getFoodsType().stream().collect(Collectors.toSet());
        final Set<ViewSpecies> species = viewController.getSpecies();
        this.remove(legendContainer);
        legendContainer = new JPanel(new GridLayout(foods.size() + species.size() + 3, 1));
        legendContainer.add(foodLabel);
        legendContainer.add(buildLegendEntryPanel(UNNAMED_FOOD, Color.BLACK, foodColors));
        fillPanelsOfColorables(foods, foodColors, candidateFoodsColors);
        legendContainer.add(speciesLabel);
        fillPanelsOfColorables(species, speciesColors, candidateSpeciesColors);
        legendContainer.setVisible(true);
        this.add(legendContainer);
    }

    private void fillPanelsOfColorables(final Set<? extends Colorable> set, final Map<String, Color> map,
            final List<Color> candidateColors) {
        final Iterator<Integer> colorIterator;
        if (set.size() > candidateColors.size()) {
            colorIterator = new Random().ints(0, candidateColors.size()).iterator();
        } else {
            colorIterator = new Random().ints(0, candidateColors.size()).distinct().iterator();
        }
        for (final Colorable el : set) {
            final Color color = map.getOrDefault(el.getName(), candidateColors.get(colorIterator.next()));
            legendContainer.add(buildLegendEntryPanel(el.getName(), color, map));
        }
    }

    private JPanel buildLegendEntryPanel(final String name, final Color color, final Map<String, Color> map) {
        final JPanel legendEntryPanel = new JPanel(new GridLayout(1, 2));
        final JLabel nameLabel = new JLabel(name);
        updateColor(nameLabel, map, name, color);
        legendEntryPanel.add(nameLabel);
        final JButton btn = new JButton("Change");
        btn.addActionListener(ev -> {
            final Color nextColor = JColorChooser.showDialog(LegendPanel.this, "Choose new color", color);
            updateColor(nameLabel, map, name, nextColor);
        });
        legendEntryPanel.add(btn);
        return legendEntryPanel;
    }

    private void updateColor(final JLabel label, final Map<String, Color> map, final String key, final Color color) {
        label.setForeground(color);
        map.put(key, color);
    }

    /**
     * Reset all associations of colors and hide the legend.
     */
    public void reset() {
        SwingUtilities.invokeLater(() -> {
            foodColors = new HashMap<>();
            speciesColors = new HashMap<>();
            legendContainer = new JPanel();
            this.remove(legendContainer);
            legendContainer.setVisible(false);
            this.add(legendContainer);
        });
    }

    @Override
    public Color getColorFromFood(final ViewProvision food) {
        return getColorFromColorable(food, foodColors);
    }

    @Override
    public Color getColorFromSpecies(final ViewSpecies species) {
        return getColorFromColorable(species, speciesColors);
    }

    private Color getColorFromColorable(final Colorable colorable, final Map<String, Color> map) {
        try {
            return map.getOrDefault(colorable.getName(), Color.BLACK);
        } catch (NoSuchElementException e) {
            return map.get(UNNAMED_FOOD);
        }
    }
}
