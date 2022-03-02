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
    private FieldMenu fieldMenu;

    public Field(View view){
        this.view = view;
        KeyHandler keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        setFocusable(true);

        setLayout(new BorderLayout());
        fieldMenu = new FieldMenu(view);
        JPanel fieldMain = new FieldMain(view);
        add(fieldMain, BorderLayout.CENTER);
        add(fieldMenu, BorderLayout.NORTH);

    }

    public void paint(Graphics g){
        super.paint(g);
        fieldMenu.updateUndoButtonState();
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
                case VK_ESCAPE: view.showMenu(); break;
                case VK_R: eventListener.restart();
            }
        }
    }
    public class FieldMenu extends JPanel {
        private final View view;
        private JButton buttonUndo;

        public FieldMenu(View view){
            this.view = view;
            setBackground(new Color(50,50,50));
            setLayout(new BorderLayout());
            buttonUndo = new JButton("Отменить действие");
            buttonUndo.setFocusPainted(false);
            buttonUndo.setFont(new Font("Segoe Print", Font.BOLD,12 ));
            buttonUndo.addActionListener(e -> {
                eventListener.undoMove();
                view.repaint();
            });
            add(buttonUndo, BorderLayout.WEST);
        }

        public void updateUndoButtonState(){
            buttonUndo.setEnabled(view.canMakeUndo());
        }
    }
    public class FieldMain extends JPanel {
        private final View view;


        public FieldMain(View view){

            this.view = view;
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
    }
}
