package by.bsuir.hero;

import javax.swing.*;

public class HeroController {
    private final HeroModel model;
    private InputDialog inputDialog;

    public HeroController(HeroModel model) {
        this.model = model;
    }

    public void openInputDialog(JFrame parent) {
        if (inputDialog == null || !inputDialog.isDisplayable()) {
            inputDialog = new InputDialog(parent, this);
        }
        inputDialog.setValues(model.getHeight(), model.getWeight(), model.getAge(),
                model.getPullUps(), model.getSleepHours());
        inputDialog.setVisible(true);
    }

    public void processInput(String hStr, String wStr, String aStr, String pStr, String sStr) {
        try {
            double h = Double.parseDouble(hStr.trim());
            double w = Double.parseDouble(wStr.trim());
            int a = Integer.parseInt(aStr.trim());
            int p = Integer.parseInt(pStr.trim());
            double s = Double.parseDouble(sStr.trim());

            model.setData(h, w, a, p, s);

            if (inputDialog != null) {
                inputDialog.dispose();
            }
        } catch (NumberFormatException e) {
            showError("Ошибка: Пожалуйста, введите корректные числовые значения!");
        } catch (IllegalArgumentException e) {
            showError("Ошибка: " + e.getMessage());
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
    }
}