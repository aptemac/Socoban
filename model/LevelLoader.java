package model;



import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

public class LevelLoader {
    private final Path levels;
    public LevelLoader(Path levelsFile) {
        levels = levelsFile;
    }

    public GameObjects getLevel(int level){

        while (level>60){
            level-=60;
        }

        ArrayList<String> allLevelData = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(levels))));
            String curLine;

            boolean neededLevel = false;
            while (br.ready() ){
                curLine=br.readLine();
                if (curLine.equalsIgnoreCase("Maze: " + level)){
                    neededLevel = true;
                }
                if (curLine.equalsIgnoreCase("*************************************") && neededLevel){
                    break;
                }
                if (neededLevel){
                    allLevelData.add(curLine);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int sizeX = Integer.parseInt(allLevelData.get(2).substring("Size X:".length()).trim());
        int sizeY = Integer.parseInt(allLevelData.get(3).substring("Size Y:".length()).trim());

        ArrayList<String> levelWriteData = new ArrayList<>();
        for (int i = 7; i < 7 + sizeY; i++) {
            levelWriteData.add(allLevelData.get(i));
        }

        int x0 = Model.FIELD_CELL_SIZE / 2;
        int y0 = x0;
        HashSet<Wall> walls = new HashSet<>();
        HashSet<Box> box = new HashSet<>();
        HashSet<Home> home = new HashSet<>();
        Player player = new Player(0,0);
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                int xCoordinate = x0+x* Model.FIELD_CELL_SIZE;
                int yCoordinate = y0+y* Model.FIELD_CELL_SIZE;
                switch (levelWriteData.get(y).charAt(x)){
                    case ' ': break;
                    case 'X': walls.add(new Wall(xCoordinate, yCoordinate)); break;
                    case '*': box.add(new Box(xCoordinate, yCoordinate)); break;
                    case '.': home.add(new Home(xCoordinate, yCoordinate)); break;
                    case '&': home.add(new Home(xCoordinate, yCoordinate)); box.add(new Box(xCoordinate, yCoordinate)); break;
                    case '@': player = new Player(xCoordinate, yCoordinate); break;
                }
            }
        }

        return new GameObjects(walls,box,home,player);
    }
}
