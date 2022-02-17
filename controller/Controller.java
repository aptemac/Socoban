package controller;

import model.Direction;
import model.GameObjects;
import model.Model;
import view.View;

import java.nio.file.Path;
import java.nio.file.Paths;


public class Controller implements EventListener {

    private final View view;
    private final Model model;

    public Controller() {
        view = new View(this);
        model = new Model();
        view.init();
        model.restart();
        view.setEventListener(this);
        model.setEventListener(this);
    }

    public GameObjects getGameObjects(){
        return model.getGameObjects();
    }

    @Override
    public void move(Direction direction) {
        model.move(direction);
        view.update();
    }

    @Override
    public void restart() {
        model.restart();
        view.update();
    }

    @Override
    public void startNextLevel() {
        model.startNextLevel();
        view.update();
    }

    @Override
    public void levelCompleted(int level) {
        view.completed(level);
    }

    public static void main(String[] args) {

        Controller controller = new Controller();


        Path f = Paths.get(controller.getClass().getName().replace(".","\\")+"\\..\\..\\res\\levels.txt").normalize().toAbsolutePath();

    }
}