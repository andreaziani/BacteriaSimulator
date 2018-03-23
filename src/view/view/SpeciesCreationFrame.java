package view.view;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -3946173214672360528L;
    private final JTextField txtName;
    private final JColorChooser colorChooser;
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
        this.setLayout(new FlowLayout());
        final JPanel panel = new JPanel();
        txtName = new JTextField();
        colorChooser = new JColorChooser();
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

        panel.add(txtName);
        panel.add(colorChooser);
        comboBoxes.values().forEach(panel::add);
        checkBoxList.forEach(panel::add);
        panel.add(btnCreate);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }

    private void createSpecies() {
        try {
            view.createSpecies(txtName.getText(), colorChooser.getColor(),
                    comboBoxes.entrySet().stream()
                            .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue().getSelectedIndex())),
                    checkBoxList.stream().map(x -> x.isSelected()).collect(Collectors.toList()));
            this.setVisible(false);
            this.dispose();
        } catch (InvalidSpeciesExeption e) {
            JOptionPane.showMessageDialog(this, "Can't create the Species correctly");
        } catch (AlreadyExistingSpeciesExeption e) {
            JOptionPane.showMessageDialog(this, "A species with that name already exists");
        }
    }
}
