package view.gui;

import java.awt.BorderLayout;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.InvalidSpeciesExeption;
import model.action.ActionType;
import model.bacteria.species.AlreadyExistingSpeciesExeption;
import view.ViewController;

/**
 * Frame that enable creation of Species composing different attributes.
 */
public class SpeciesCreationDialog extends JDialog {
    private static final int INITIAL_TXT_SIZE = 20;
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -3946173214672360528L;
    private final JTextField txtName;
    private final Map<ActionType, JComboBox<String>> comboBoxes;
    private final List<JCheckBox> checkBoxList;
    private final ViewController view;

    /**
     * Create a new SpeciesCreationDialog.
     * 
     * @param view
     *            the View with which this frame interacts.
     * @param main
     *            the JFrame that will be blocked by this dialog.
     */
    public SpeciesCreationDialog(final ViewController view, final UserInterface main) {
        super(main, true);
        this.setTitle("Create a Species");
        this.view = view;
        this.setLayout(new BorderLayout());
        final JLabel txtLabel = new JLabel("Set the name of the Species");
        txtLabel.setFont(GuiUtils.FONT);
        txtName = new JTextField(INITIAL_TXT_SIZE);
        txtName.setFont(GuiUtils.FONT);

        comboBoxes = new EnumMap<>(ActionType.class);
        Arrays.asList(ActionType.values()).stream().forEach(a -> {
            if (a != ActionType.NOTHING) {
                final JComboBox<String> comboBox = new JComboBox<>();
                comboBox.setFont(GuiUtils.FONT);
                comboBoxes.put(a, comboBox);
            }
        });
        view.getDecisionOptions().entrySet().stream()
                .forEach(x -> x.getValue().stream().forEach(s -> comboBoxes.get(x.getKey()).addItem(s)));
        checkBoxList = new ArrayList<>();
        view.getDecoratorOptions().stream().map(x -> new JCheckBox(x))
                .peek(checkBox -> checkBox.setFont(GuiUtils.FONT))
                .forEach(checkBoxList::add);

        final JButton btnCreate = new JButton("create Species");
        btnCreate.setFont(GuiUtils.FONT);
        btnCreate.addActionListener(e -> createSpecies(main));

        final JPanel comboPanel = new JPanel(new GridLayout(comboBoxes.size() * 2, 1));
        for (final ActionType type : ActionType.values()) {
            if (type != ActionType.NOTHING) {
                final JLabel label = new JLabel(type.toString().toLowerCase(Locale.ENGLISH));
                label.setFont(GuiUtils.FONT);
                comboPanel.add(label);
                comboPanel.add(comboBoxes.get(type));
            }
        }

        final JPanel checkPanel = new JPanel(new GridLayout(checkBoxList.size(), 1));
        checkPanel.setFont(GuiUtils.FONT);
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
        centerPanelContainer.setFont(GuiUtils.FONT);
        centerPanelContainer.add(centerPanel);
        this.add(centerPanelContainer, BorderLayout.CENTER);
        this.add(createPanel, BorderLayout.SOUTH);
        this.pack();
        this.setVisible(true);
        this.txtName.setFont(GuiUtils.FONT);
    }

    private void createSpecies(final UserInterface main) {
        try {
            if (txtName.getText().isEmpty()) {
                final JLabel label = new JLabel("Name not valid");
                label.setFont(GuiUtils.FONT);
                JOptionPane.showMessageDialog(this, label);
            } else {
                view.createSpecies(txtName.getText(),
                        comboBoxes.entrySet().stream()
                                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue().getSelectedIndex())),
                        checkBoxList.stream().map(x -> x.isSelected()).collect(Collectors.toList()));
                final JLabel label = new JLabel("Species " + txtName.getText() + " added succesfully");
                label.setFont(GuiUtils.FONT);
                JOptionPane.showMessageDialog(this, label);
                main.notifyUpdate();
                this.setVisible(false);
                this.dispose();
            }
        } catch (InvalidSpeciesExeption e) {
            final JLabel label = new JLabel("Can't create the Species correctly");
            label.setFont(GuiUtils.FONT);
            JOptionPane.showMessageDialog(this, label);
        } catch (AlreadyExistingSpeciesExeption e) {
            final JLabel label = new JLabel("A species with that name already exists");
            label.setFont(GuiUtils.FONT);
            JOptionPane.showMessageDialog(this, label);
        }
    }
}
