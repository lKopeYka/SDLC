package by.bsuir.hero;

import java.util.ArrayList;
import java.util.List;

public class HeroModel {
    private double height;
    private double weight;
    private int age;
    private int pullUps;
    private double sleepHours;
    private String rank = "Не определён";

    private final List<ModelListener> listeners = new ArrayList<>();

    public interface ModelListener {
        void onModelChanged();
    }

    public void addListener(ModelListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (ModelListener listener : listeners) {
            listener.onModelChanged();
        }
    }

    public void setData(double height, double weight, int age, int pullUps, double sleepHours) throws IllegalArgumentException {
        if (height < 50 || height > 250) {
            throw new IllegalArgumentException("Некорректный рост! Значение должно быть от 50 до 250 см.");
        }
        if (weight < 20 || weight > 300) {
            throw new IllegalArgumentException("Некорректный вес! Значение должно быть от 20 до 300 кг.");
        }
        if (age < 1 || age > 120) {
            throw new IllegalArgumentException("Некорректный возраст! Значение должно быть от 1 до 120 лет.");
        }
        if (pullUps < 0) {
            throw new IllegalArgumentException("Количество подтягиваний не может быть отрицательным!");
        }
        if (sleepHours < 0 || sleepHours > 24) {
            throw new IllegalArgumentException("Часы сна должны быть в пределах от 0 до 24!");
        }

        this.height = height;
        this.weight = weight;
        this.age = age;
        this.pullUps = pullUps;
        this.sleepHours = sleepHours;

        calculateRank();
        notifyListeners();
    }

    private void calculateRank() {
        if (sleepHours < 5) {
            rank = "Нужно срочно в отпуск";
        } else if (pullUps >= 20 && sleepHours >= 7) {
            rank = "Легенда";
        } else if (pullUps >= 8) {
            rank = "Герой";
        } else {
            rank = "Стажёр";
        }
    }

    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public int getAge() { return age; }
    public int getPullUps() { return pullUps; }
    public double getSleepHours() { return sleepHours; }
    public String getRank() { return rank; }
}