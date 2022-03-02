package view;

import controller.EventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.jar.JarEntry;

public class Menu extends JPanel {
    private EventListener eventListener;
    private final View view;
    private final JPanel menuPanel = new JPanel();;

    public Menu(View view){

        this.view = view;
        drawMenu();
    }

    private void drawMenu(){
        menuPanel.setLayout(new GridLayout(0,1,10,10));

        JButton startButton = getMenuButton("Новая игра");
        startButton.addActionListener(e -> eventListener.newGame());

        JButton continueButton = getMenuButton("Продолжить");
        continueButton.setEnabled(false);
        continueButton.addActionListener(e -> view.showGame());

        menuPanel.add(startButton);
        menuPanel.add("Continue", continueButton);
        add(menuPanel);
    }

    private JButton getMenuButton(String lable){
        JButton button = new JButton(lable);
        button.setPreferredSize(new Dimension(200, 50));
        button.setFont(new Font("Segoe Print", Font.BOLD,18 ));
        button.setFocusPainted(false);
        return button;
    }

    public void setEventListener(EventListener eventListener){
        this.eventListener = eventListener;
    }

    public void setContinueEnable(){
        menuPanel.getComponent(1).setEnabled(true);
    }
}
