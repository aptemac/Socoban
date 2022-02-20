package view;

import controller.Controller;
import controller.EventListener;
import model.GameObjects;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private final Controller controller;
    private Field field;
    private Menu menu;

    private CardLayout cardLayout;
    private final JPanel jPanels = new JPanel();
    final static String GAME = "Game";
    final static String MENU = "Menu";

    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {

        field = new Field(this);
        menu = new Menu(this);

        cardLayout = new CardLayout();
        jPanels.setLayout(cardLayout);
        jPanels.add(field, GAME);
        jPanels.add(menu, MENU);
        getContentPane().add(jPanels);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Сокобан");
        setVisible(true);
    }

    public void showMenu(){
        cardLayout.show(jPanels, MENU);
    }

    public void showGame(){
        cardLayout.show(jPanels, GAME);
        field.requestFocus();
        menu.setContinueEnable();
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
        menu.setEventListener(eventListener);
    }
}
