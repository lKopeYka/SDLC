package by.bsuir.hero;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame implements HeroModel.ModelListener {
    private final HeroController controller;
    private final HeroModel model;
    private final JLabel lblRank = new JLabel("Статус: Данные не введены", SwingConstants.CENTER);
    private final JLabel lblDetails = new JLabel("Введите параметры для расчета", SwingConstants.CENTER);

    public MainView(HeroController controller, HeroModel model) {
        this.controller = controller;
        this.model = model;
        this.model.addListener(this);
        initView();
    }

    private void initView() {
        setTitle("Superhero Rank Calculator (MVC Active Model)");
        setSize(480, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 10, 10));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        lblRank.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblRank.setForeground(new Color(0, 102, 204));
        lblDetails.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panelInfo.add(lblRank);
        panelInfo.add(lblDetails);
        add(panelInfo, BorderLayout.CENTER);

        JButton btnOpenInput = new JButton("Ввести данные");
        btnOpenInput.setPreferredSize(new Dimension(160, 40));
        btnOpenInput.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel panelButton = new JPanel();
        panelButton.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelButton.add(btnOpenInput);
        add(panelButton, BorderLayout.SOUTH);

        btnOpenInput.addActionListener(e -> controller.openInputDialog(this));
    }

    @Override
    public void onModelChanged() {
        lblRank.setText("Уровень супергероя: " + model.getRank());
        lblDetails.setText(String.format("Рост: %.1f см | Вес: %.1f кг | Подтягивания: %d | Сон: %.1f ч",
                model.getHeight(), model.getWeight(), model.getPullUps(), model.getSleepHours()));
    }
}