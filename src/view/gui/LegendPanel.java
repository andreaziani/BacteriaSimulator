package view.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.SimulationState;
import view.ViewController;
import view.model.bacteria.ViewSpecies;
import view.model.food.ViewFood;
import view.model.food.ViewProvision;

/**
 * Panel that assign colors to species.
 */
public final class LegendPanel extends JPanel implements ColorAssigner, SimulationStateUpdatable {

    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -8989135061384289871L;
    private static final String UNNAMED_FOOD = "Unnamed Food";

    private final ViewController viewController;
    private Optional<JPanel> legendContainer;
    private final List<Color> candidateFoodsColors;
    private final List<Color> candidateSpeciesColors;
    private final JLabel foodLabel;
    private final JLabel speciesLabel;

    private SimulationState simulationState;
    private Map<String, Color> foodColors;
    private Map<String, Color> speciesColors;
    private Iterator<Color> foodColorIterator;
    private Iterator<Color> speciesColorIterator;

    /**
     * Create a new LegendPanel specifying the ViewController of the application.
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

        simulationState = SimulationState.NOT_READY;
        this.viewController = viewController;
        init();
        foodLabel = new JLabel("Food colors:");
        speciesLabel = new JLabel("Species colors:");
    }

    private void init() {
        foodColors = new HashMap<>();
        speciesColors = new HashMap<>();
        foodColorIterator = getColorIterator(candidateFoodsColors);
        speciesColorIterator = getColorIterator(candidateSpeciesColors);
        legendContainer = Optional.empty();
        this.setVisible(false);
    }

    /**
     * Reset all associations of colors and hide the legend.
     */
    public void reset() {
        SwingUtilities.invokeLater(() -> {
            if (legendContainer.isPresent()) {
                this.remove(legendContainer.get());
                legendContainer = Optional.empty();
            }
            init();
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

    @Override
    public void updateSimulationState(final SimulationState state) {
        this.simulationState = state;
        if (simulationState == SimulationState.NOT_READY) {
            this.reset();
        }
    }

    /**
     * Update foods and species in the legend and show the legend if the simulation
     * state currently registered by this panel is not NOT_READY or ENDED. This
     * method is not thread safe.
     */
    public void update() {
        if (simulationState != SimulationState.NOT_READY) {
            final Set<ViewFood> foods = viewController.getFoodsType().stream().collect(Collectors.toSet());
            final Set<ViewSpecies> species = viewController.getSpecies();
            JPanel legendPanel;
            if (this.legendContainer.isPresent()) {
                this.remove(legendContainer.get());
            }
            legendPanel = new JPanel(new GridLayout(foods.size() + species.size() + 3, 1));
            legendContainer = Optional.of(legendPanel);
            legendPanel.add(foodLabel);
            legendPanel.add(buildLegendEntryPanel(UNNAMED_FOOD, Color.BLACK, foodColors));
            fillPanelsOfColorables(foods, foodColors, this::nextRandomFoodColor);
            legendPanel.add(speciesLabel);
            fillPanelsOfColorables(species, speciesColors, this::nextRandomSpeciesColor);
            this.add(legendPanel);
            this.setVisible(true);
        }
    }

    private void fillPanelsOfColorables(final Set<? extends Colorable> set, final Map<String, Color> map,
            final Supplier<Color> colorSupplier) {
        for (final Colorable el : set) {
            final Color color;
            if (map.containsKey(el.getName())) {
                color = map.get(el.getName());
            } else {
                color = colorSupplier.get();
            }
            legendContainer.get().add(buildLegendEntryPanel(el.getName(), color, map));
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

    private Color nextRandomFoodColor() {
        if (!foodColorIterator.hasNext()) {
            foodColorIterator = getColorIterator(candidateFoodsColors);
        }
        return foodColorIterator.next();
    }

    private Color nextRandomSpeciesColor() {
        if (!speciesColorIterator.hasNext()) {
            speciesColorIterator = getColorIterator(candidateSpeciesColors);
        }
        return speciesColorIterator.next();
    }

    private Iterator<Color> getColorIterator(final List<Color> list) {
        return new Random().ints(0, list.size()).distinct().limit(list.size()).mapToObj(i -> list.get(i)).iterator();
    }

    private Color getColorFromColorable(final Colorable colorable, final Map<String, Color> map) {
        try {
            return map.getOrDefault(colorable.getName(), Color.BLACK);
        } catch (NoSuchElementException e) {
            return map.get(UNNAMED_FOOD);
        }
    }
}
