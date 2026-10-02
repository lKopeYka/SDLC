package by.bsuir.hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InputDialog extends JDialog {
    private final JTextField txtHeight = new JTextField(7);
    private final JTextField txtWeight = new JTextField(7);
    private final JTextField txtAge = new JTextField(7);
    private final JTextField txtPullUps = new JTextField(7);
    private final JTextField txtSleep = new JTextField(7);

    public InputDialog(JFrame parent, HeroController controller) {
        super(parent, "Ввод параметров супергероя", true);
        setSize(380, 280);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(new JLabel(" Рост (см):")); add(txtHeight);
        add(new JLabel(" Вес (кг):")); add(txtWeight);
        add(new JLabel(" Возраст:")); add(txtAge);
        add(new JLabel(" Подтягивания:")); add(txtPullUps);
        add(new JLabel(" Сон (часов/сутки):")); add(txtSleep);

        JButton btnSubmit = new JButton("OK");
        add(new JLabel());
        add(btnSubmit);

        ActionListener submitAction = e -> controller.processInput(
                txtHeight.getText(), txtWeight.getText(), txtAge.getText(),
                txtPullUps.getText(), txtSleep.getText()
        );

        btnSubmit.addActionListener(submitAction);
        txtHeight.addActionListener(submitAction);
        txtWeight.addActionListener(submitAction);
        txtAge.addActionListener(submitAction);
        txtPullUps.addActionListener(submitAction);
        txtSleep.addActionListener(submitAction);
    }

    public void setValues(double height, double weight, int age, int pullUps, double sleep) {
        if (height > 0) {
            txtHeight.setText(String.valueOf(height));
            txtWeight.setText(String.valueOf(weight));
            txtAge.setText(String.valueOf(age));
            txtPullUps.setText(String.valueOf(pullUps));
            txtSleep.setText(String.valueOf(sleep));
        }
    }
}