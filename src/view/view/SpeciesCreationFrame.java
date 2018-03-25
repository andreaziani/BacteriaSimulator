package view.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.action.ActionType;
import utils.exceptions.AlreadyExistingSpeciesExeption;
import utils.exceptions.InvalidSpeciesExeption;
import view.View;

/**
 * Frame that enable creation of Species composing different attributes.
 */
public class SpeciesCreationFrame extends JFrame {
    private static final int INITIAL_TXT_SIZE = 20;
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -3946173214672360528L;
    private final JTextField txtName;
    // private final JColorChooser colorChooser;
    private final Map<ActionType, JComboBox<String>> comboBoxes;
    private final List<JCheckBox> checkBoxList;
    private final View view;

    /**
     * Create a new SpeciesCreationFrame.
     * 
     * @param view
     *            the View with which this frame interacts.
     */
    public SpeciesCreationFrame(final View view) {
        super("Create a Species");
        this.view = view;
        this.setLayout(new BorderLayout());

        final JLabel txtLabel = new JLabel("Set the name of the Species");
        txtName = new JTextField(INITIAL_TXT_SIZE);

        // colorChooser = new JColorChooser();
        comboBoxes = new EnumMap<>(ActionType.class);
        Arrays.asList(ActionType.values()).stream().forEach(a -> {
            if (a != ActionType.NOTHING) {
                comboBoxes.put(a, new JComboBox<>());
            }
        });
        view.getDecisionOptions().entrySet().stream()
                .forEach(x -> x.getValue().stream().forEach(s -> comboBoxes.get(x.getKey()).addItem(s)));
        checkBoxList = new ArrayList<>();
        view.getDecoratorOptions().stream().map(x -> new JCheckBox(x)).forEach(checkBoxList::add);
        final JButton btnCreate = new JButton("create Species");
        btnCreate.addActionListener(e -> createSpecies());

        // this.add(colorChooser, BorderLayout.WEST);
        final JPanel comboPanel = new JPanel(new GridLayout(comboBoxes.size() * 2, 1));
        for (final ActionType type : ActionType.values()) {
            if (type != ActionType.NOTHING) {
                comboPanel.add(new JLabel(type.toString().toLowerCase(Locale.ENGLISH)));
                comboPanel.add(comboBoxes.get(type));
            }
        }

        final JPanel checkPanel = new JPanel(new GridLayout(checkBoxList.size(), 1));
        checkBoxList.forEach(checkPanel::add);
        final JPanel behaviorPanel = new JPanel();
        behaviorPanel.add(comboPanel);
        behaviorPanel.add(checkPanel);

        final JPanel txtPanel = new JPanel(new GridLayout(3, 1));
        txtPanel.add(txtLabel);
        txtPanel.add(txtName);
        final JPanel createPanel = new JPanel(new FlowLayout());
        createPanel.add(btnCreate);
        final JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.add(behaviorPanel);
        centerPanel.add(txtPanel);
        final JPanel centerPanelContainer = new JPanel(new FlowLayout());
        centerPanelContainer.add(centerPanel);
        this.add(centerPanelContainer, BorderLayout.CENTER);
        this.add(createPanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
    }

    private void createSpecies() {
        try {
            if (txtName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name not valid");
            } else {
                view.createSpecies(txtName.getText(),
                        JColorChooser.showDialog(this, "Choose Species visualization color", Color.BLACK),
                        comboBoxes.entrySet().stream()
                                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue().getSelectedIndex())),
                        checkBoxList.stream().map(x -> x.isSelected()).collect(Collectors.toList()));
                JOptionPane.showMessageDialog(this, "Species " + txtName.getText() + " added succesfully");
                this.setVisible(false);
                this.dispose();
            }
        } catch (InvalidSpeciesExeption e) {
            JOptionPane.showMessageDialog(this, "Can't create the Species correctly");
        } catch (AlreadyExistingSpeciesExeption e) {
            JOptionPane.showMessageDialog(this, "A species with that name already exists");
        }
    }
}
