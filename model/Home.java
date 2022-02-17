package model;



import java.awt.*;

public class Home extends GameObject {

    public Home(int x, int y) {
        super(x, y, 20, 20);
    }

    @Override
    public void draw(Graphics graphics) {
//        graphics.setColor(Color.YELLOW);
        graphics.setColor(new Color(247,247, 185, 100));
        graphics.fillOval(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }


}
