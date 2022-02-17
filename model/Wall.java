package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class Wall extends CollisionObject {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
//        graphics.setColor(new Color(150,75,0));
//        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        Image img = null;
        try {
            File input = new File(String.valueOf(Paths.get(getClass().getResource("../res/socoban_wall_20x20.png").toURI())));
            img = ImageIO.read(input);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
//        graphics.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        graphics.drawImage(img, this.getX(), this.getY(), null);
    }
}
