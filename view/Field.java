package view;

import controller.EventListener;
import model.Direction;
import model.GameObjects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

public class Field extends JPanel {

    private final View view;
    private EventListener eventListener;

    public Field (View view){
        this.view = view;
        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void paint(Graphics g){
        g.setColor(new Color(50,50,50));
        g.fillRect(0,0, this.getWidth(), this.getHeight());
        GameObjects gameObjects = view.getGameObjects();
        gameObjects.getHomes().forEach(go->go.draw(g));
        gameObjects.getPlayer().draw(g);
        gameObjects.getBoxes().forEach(go->go.draw(g));
        gameObjects.getWalls().forEach(go->go.draw(g));
    }

    public void setEventListener(EventListener eventListener){
        this.eventListener = eventListener;
    }

    public class KeyHandler extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case VK_LEFT: eventListener.move(Direction.LEFT); break;
                case VK_RIGHT: eventListener.move(Direction.RIGHT); break;
                case VK_UP: eventListener.move(Direction.UP); break;
                case VK_DOWN: eventListener.move(Direction.DOWN); break;
                case VK_R: eventListener.restart();
            }
        }
    }



}
