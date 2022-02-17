package view;

import controller.Controller;
import controller.EventListener;
import model.GameObjects;

import javax.swing.*;

public class View extends JFrame {
    private final Controller controller;
    private Field field;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        field = new Field(this);
        add(field);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Сокобан");
        setVisible(true);


    }

    public void update(){
        field.repaint();
    }

    public void completed(int level){
        update();
        JOptionPane.showMessageDialog(null,"Level finished!","title",JOptionPane.PLAIN_MESSAGE);
        controller.startNextLevel();
    }

    public GameObjects getGameObjects(){
        return controller.getGameObjects();
    }

    public void setEventListener(EventListener eventListener){
        field.setEventListener(eventListener);
    }
}
