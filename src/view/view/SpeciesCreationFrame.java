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
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.action.ActionType;
import view.View;

/**
 * Frame that enable creation of Species composing different attributes.
 */
public class SpeciesCreationFrame extends JFrame {
    /**
     * Automatically generated.
     */
    private static final long serialVersionUID = -3946173214672360528L;

    /**
     * Create a new SpeciesCreationFrame.
     * 
     * @param view
     *            the View with which this frame interacts.
     */
    public SpeciesCreationFrame(final View view) {
        super("Create a Species");
        this.setLayout(new FlowLayout());
        final JPanel panel = new JPanel();
        final JTextField name = new JTextField();
        final JColorChooser color = new JColorChooser();
        final Map<ActionType, JComboBox<String>> groups = new EnumMap<>(ActionType.class);
        Arrays.asList(ActionType.values()).stream().forEach(a -> {
            if (a != ActionType.NOTHING) {
                groups.put(a, new JComboBox<>());
            }
        });
        view.getDecisionOptions().entrySet().stream()
                .forEach(x -> x.getValue().stream().forEach(s -> groups.get(x.getKey()).addItem(s)));
        final List<JCheckBox> decoratorsGroup = new ArrayList<>();
        view.getDecoratorOptions().stream().map(x -> new JCheckBox(x)).forEach(decoratorsGroup::add);
        final JButton create = new JButton("create Species");
        create.addActionListener(e -> view.createSpecies(name.getText(), color.getColor(),
                groups.entrySet().stream()
                        .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue().getSelectedIndex())),
                decoratorsGroup.stream().map(x -> x.isSelected()).collect(Collectors.toList())));

        panel.add(name);
        panel.add(color);
        groups.values().forEach(panel::add);
        decoratorsGroup.forEach(panel::add);
        panel.add(create);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
}
