package view.gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
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
public class LegendPanel extends JPanel implements SimulationStateUpdatable, ColorAssigner {

    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -8989135061384289871L;

    private final ViewController viewController;
    private final JPanel legendContainer;
    private final List<Color> candidateFoodsColors;
    private final List<Color> candidateSpeciesColors;
    private final JLabel foodLabel;
    private final JLabel speciesLabel;

    private Set<ViewFood> foods;
    private Set<ViewSpecies> species;
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
        candidateFoodsColors.add(Color.RED.brighter());
        candidateFoodsColors.add(Color.RED.darker());
        candidateFoodsColors.add(Color.PINK);
        candidateFoodsColors.add(Color.PINK.brighter());
        candidateFoodsColors.add(Color.PINK.darker());
        candidateFoodsColors.add(Color.YELLOW);
        candidateFoodsColors.add(Color.YELLOW.brighter());

        candidateSpeciesColors.add(Color.BLUE);
        candidateSpeciesColors.add(Color.BLUE.darker());
        candidateSpeciesColors.add(Color.BLUE.brighter());
        candidateSpeciesColors.add(Color.CYAN.darker());
        candidateSpeciesColors.add(Color.CYAN.brighter());
        candidateSpeciesColors.add(Color.CYAN.darker());
        candidateSpeciesColors.add(Color.GREEN);
        candidateSpeciesColors.add(Color.GREEN.brighter());
        candidateSpeciesColors.add(Color.GREEN.darker());
        candidateSpeciesColors.add(Color.MAGENTA);
        candidateSpeciesColors.add(Color.MAGENTA.darker());

        this.viewController = viewController;
        foods = new HashSet<>();
        species = new HashSet<>();
        foodColors = new HashMap<>();
        speciesColors = new HashMap<>();
        legendContainer = new JPanel(new GridLayout(1, 2));
        legendContainer.setVisible(false);
        foodLabel = new JLabel("Foods");
        speciesLabel = new JLabel("Species");
        this.add(legendContainer);
    }

    @Override
    public void updateSimulationState(final SimulationState state) {
        if (state == SimulationState.RUNNING || state == SimulationState.REPLAY) { // TODO need a way to know if was
                                                                                   // resumed or a new simulation.
            SwingUtilities.invokeLater((() -> {
                foods = viewController.getFoodsType().stream().collect(Collectors.toSet());
                species = viewController.getSpecies();
                foodColors = new HashMap<>();
                speciesColors = new HashMap<>();
                final JPanel namesPanel = new JPanel(new GridLayout(foods.size() + species.size(), 1));
                final JPanel buttonsPanel = new JPanel(new GridLayout(foods.size() + species.size(), 1));
                namesPanel.add(foodLabel);
                fillPanelsOfColorables(foods, foodColors, candidateFoodsColors, namesPanel, buttonsPanel);
                namesPanel.add(speciesLabel);
                fillPanelsOfColorables(species, speciesColors, candidateSpeciesColors, namesPanel, buttonsPanel);
                legendContainer.add(namesPanel);
                legendContainer.add(buttonsPanel);
                legendContainer.setVisible(true);
            }));
        }
    }

    private void fillPanelsOfColorables(final Set<? extends Colorable> set, final Map<String, Color> map,
            final List<Color> candidateColors, final JPanel namesPanel, final JPanel buttonsPanel) {
        final Iterator<Integer> colorIterator;
        if (set.size() > candidateColors.size()) {
            colorIterator = new Random().ints(0, candidateColors.size()).iterator();
        } else {
            colorIterator = new Random().ints(0, candidateColors.size()).distinct().iterator();
        }
        for (final Colorable el : set) {
            final Color color = candidateColors.get(colorIterator.next());
            final JLabel nameLabel = new JLabel(el.getName());
            updateColor(nameLabel, map, el.getName(), color);
            namesPanel.add(nameLabel);
            final JButton btn = new JButton("Change");
            btn.addActionListener(ev -> {
                final Color nextColor = JColorChooser.showDialog(LegendPanel.this, "Choose new color", color);
                updateColor(nameLabel, map, el.getName(), nextColor);
            });
            buttonsPanel.add(btn);
        }
    }

    private void updateColor(final JLabel label, final Map<String, Color> map, final String key, final Color color) {
        label.setForeground(color);
        map.put(key, color);
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
        return map.getOrDefault(colorable.getName(), Color.BLACK); // TODO better to find black in a more general way.
    }
}
