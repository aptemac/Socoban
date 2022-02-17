package tests;

import model.GameObjects;
import model.LevelLoader;
import org.junit.Test;


import java.net.URISyntaxException;
import java.nio.file.Paths;

public class SocobanTest {
    @Test
    public void levelLoaderTest(){
        LevelLoader levelLoader = null;
        try {
            levelLoader = new LevelLoader(Paths.get(getClass().getResource("../res/levels.txt").toURI()));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        GameObjects go = levelLoader.getLevel(8);
//        go.getHomes().forEach(o-> System.out.println(o.toString()));
    }
}
