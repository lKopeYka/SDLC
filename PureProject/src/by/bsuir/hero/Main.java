package by.bsuir.hero;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HeroModel model = new HeroModel();
            HeroController controller = new HeroController(model);
            MainView view = new MainView(controller, model);
            view.setVisible(true);
        });
    }
}