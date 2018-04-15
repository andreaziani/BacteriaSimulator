package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import controller.SimulationState;
import view.ViewController;

/**
 * Top Panel of MainFrame.
 *
 */
public class TopPanel extends JPanel implements SimulationStateUpdatable {
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu fileMenu = new JMenu("File");
    private final JMenu helpMenu = new JMenu("Help");
    private final JMenuItem loadSimulation = new JMenuItem("Load Simulation");
    private final JMenuItem saveSimulation = new JMenuItem("Save Simulation");
    private final JMenuItem loadReplay = new JMenuItem("Load Replay");
    private final JMenuItem saveReplay = new JMenuItem("Save Replay");
    private final JMenuItem help = new JMenuItem("How to use");
    private final SpeciesAndFoodPanel speciesAndFood;

    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = 2643418239937346321L;
    private final ChoicesPanel choicesPanel;

    /**
     * Constructor.
     * 
     * @param view
     *            the View with which to interact.
     * @param main
     *            frame that's call this panel.
     */
    public TopPanel(final ViewController view, final MainFrame main) {
        super(new BorderLayout());
        this.help.addActionListener(e -> {
            new HelpDialog(main);
        });
        this.speciesAndFood = new SpeciesAndFoodPanel(view, main);
        this.add(menuBar, BorderLayout.NORTH);
        choicesPanel = new ChoicesPanel(view, main);
        this.add(choicesPanel, BorderLayout.SOUTH);
        this.add(this.speciesAndFood, BorderLayout.CENTER);
        this.componentsSettings();

        final JFileChooser simulationChooser = new JFileChooser();
        simulationChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        simulationChooser.setDialogTitle("Choose a file");

        loadSimulation.addActionListener(e -> {
            if (simulationChooser.showOpenDialog(main) == JFileChooser.APPROVE_OPTION) {
                try {
                    view.loadSimulation(simulationChooser.getSelectedFile().getPath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred trying to load the simulation");
                }
            }
        });
        saveSimulation.addActionListener(e -> {
            if (simulationChooser.showSaveDialog(main) == JFileChooser.APPROVE_OPTION) {
                try {
                    view.saveSimulation(simulationChooser.getSelectedFile().getPath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred trying to save the simulation");
                }
            }
        });

        final JFileChooser replayChooser = new JFileChooser();
        simulationChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        simulationChooser.setDialogTitle("Choose a file");

        loadReplay.addActionListener(e -> {
            if (replayChooser.showOpenDialog(main) == JFileChooser.APPROVE_OPTION) {
                try {
                    view.loadReplay(replayChooser.getSelectedFile().getPath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred trying to load the replay");
                }
            }
        });
        saveReplay.addActionListener(e -> {
            if (replayChooser.showSaveDialog(main) == JFileChooser.APPROVE_OPTION) {
                try {
                    view.saveReplay(replayChooser.getSelectedFile().getPath());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred trying to load the replay");
                }
            }
        });
        this.saveReplay.setEnabled(false);
    }

    /**
     * 
     * @return the name of the selected food.
     */
    public int getSelectedFood() {
        return this.speciesAndFood.getSelectedFood();
    }
    /**
     * Update food's type.
     */
    public void updateFoods() {
        this.speciesAndFood.updateFoods();
    }

    private void componentsSettings() {
        this.fileMenu.setActionCommand("File");
        this.fileMenu.add(loadSimulation);
        this.fileMenu.add(this.saveSimulation);
        this.fileMenu.add(loadReplay);
        this.fileMenu.add(saveReplay);
        this.helpMenu.add(help);
        this.menuBar.add(this.fileMenu);
        this.menuBar.add(helpMenu);
        this.speciesAndFood.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
        this.choicesPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
    }

    @Override
    public final void updateSimulationState(final SimulationState state) {
        this.speciesAndFood.updateSimulationState(state);
        this.choicesPanel.updateSimulationState(state);
        if (state == SimulationState.ENDED) {
            this.saveReplay.setEnabled(true);
        } else {
            this.saveReplay.setEnabled(false);
        }
    }
}
