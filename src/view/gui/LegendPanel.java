package view.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.SimulationCondition;
import controller.SimulationState;
import model.bacteria.species.SpeciesOptions;
import view.SimulationStateUpdatable;
import view.controller.ViewController;
import view.model.food.ViewFood;
import view.model.food.ViewProvision;

/**
 * Panel that assign colors to species.
 */
public final class LegendPanel extends JPanel implements ColorAssigner, SimulationStateUpdatable {

    private static final int MIN_COLOR_STEP = 64;
    private static final int MAX_COLOR_VALUE = 255;
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -8989135061384289871L;
    private static final String UNNAMED_FOOD_NAME = "Unnamed Food";
    private final ViewController viewController;
    private Optional<JPanel> legendContainer;
    private final List<Color> candidateFoodsColors;
    private final List<Color> candidateSpeciesColors;
    private final JLabel foodLabel;
    private final JLabel speciesLabel;

    private Color unnamedColor = Color.BLACK;
    private SimulationCondition simulationState;
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
        for (int i = 0; i < MAX_COLOR_VALUE; i += MIN_COLOR_STEP) {
            for (int j = MIN_COLOR_STEP; j < MAX_COLOR_VALUE; j += MIN_COLOR_STEP) {
                candidateFoodsColors.add(new Color(MAX_COLOR_VALUE, i, j));
                candidateSpeciesColors.add(new Color(j, i, MAX_COLOR_VALUE));
            }
        }
        simulationState = SimulationCondition.NOT_READY;
        this.viewController = viewController;
        foodLabel = new JLabel("Food colors:");
        speciesLabel = new JLabel("Species colors:");
        init();
        this.foodLabel.setFont(GuiUtils.FONT);
        this.speciesLabel.setFont(GuiUtils.FONT);
    }

    private void init() {
        foodColors = new HashMap<>();
        speciesColors = new HashMap<>();
        foodColorIterator = getColorIterator(candidateFoodsColors);
        speciesColorIterator = getColorIterator(candidateSpeciesColors);
        legendContainer = Optional.empty();
        update();
    }

    private void resetContainer() {
        if (this.legendContainer.isPresent()) {
            this.remove(legendContainer.get());
        }
    }

    /**
     * Reset all associations of colors and hide the legend.
     */
    public void reset() {
        SwingUtilities.invokeLater(() -> {
            resetContainer();
            legendContainer = Optional.empty();
            init();
            //TODO forced refresh of the panel because revalidate() doesn't work. Is a better way
            this.setVisible(false);
            this.setVisible(true);
        });
    }

    @Override
    public Color getColorFromFood(final ViewProvision food) {
        return getColorFromColorable(food, foodColors);
    }

    @Override
    public Color getColorFromSpecies(final SpeciesOptions species) {
        return getColorFromColorable(species, speciesColors);
    }

    @Override
    public void updateSimulationState(final SimulationState state) {
        this.simulationState = state.getCurrentCondition();
        if (simulationState == SimulationCondition.NOT_READY) {
            this.reset();
        }
    }

    /**
     * Update foods and species in the legend and show the legend if the simulation
     * state currently registered by this panel is not NOT_READY or ENDED. This
     * method is not thread safe.
     */
    public void update() {
        final Set<ViewFood> foods = viewController.getFoodTypes().stream().collect(Collectors.toSet());
        final Set<SpeciesOptions> species = viewController.getController().getSpecies();
        JPanel legendPanel;
        resetContainer();
        legendPanel = new JPanel(new GridLayout(foods.size() + species.size() + 3, 1));
        legendContainer = Optional.of(legendPanel);
        legendPanel.add(foodLabel);
        legendPanel.add(buildLegendEntryPanel(UNNAMED_FOOD_NAME, unnamedColor, (l, c) -> {
            unnamedColor = c;
            l.setForeground(unnamedColor);
        }));

        fillPanelsOfColorables(foods, foodColors, this::nextRandomFoodColor);
        legendPanel.add(speciesLabel);
        fillPanelsOfColorables(species, speciesColors, this::nextRandomSpeciesColor);
        this.add(legendPanel);
        this.revalidate();
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
            legendContainer.get().add(buildLegendEntryPanel(el.getName(), color, (l, c) -> updateColor(l, map, l.getText(), c)));
        }
    }

    private JPanel buildLegendEntryPanel(final String name, final Color color, final BiConsumer<JLabel, Color> colorChanger) {
        final JPanel legendEntryPanel = new JPanel(new GridLayout(1, 2));
        final JLabel nameLabel = new JLabel(name);
        colorChanger.accept(nameLabel, color);
        legendEntryPanel.add(nameLabel);
        final JButton btn = new JButton("Change");
        btn.setFont(GuiUtils.FONT);
        nameLabel.setFont(GuiUtils.FONT);
        btn.addActionListener(ev -> {
            final Color nextColor = JColorChooser.showDialog(LegendPanel.this, "Choose new color", color);
            colorChanger.accept(nameLabel, nextColor);
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
        return map.getOrDefault(colorable.getName(), unnamedColor);
    }
}
