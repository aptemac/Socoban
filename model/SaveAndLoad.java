package model;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SaveAndLoad {
    private final Path saveFile;
    private final ArrayList<SaveData> saveData;
    public SaveAndLoad(Path saveFile) {
        this.saveFile = saveFile;
        this.saveData = this.readSaves();
    }

    private ArrayList<SaveData> readSaves(){
        ArrayList<SaveData> arrayList = new ArrayList<>();
        String saveDataName = "";
        int level = 0;
        Set<Wall> walls = new HashSet<>();
        Set<Box> boxes = new HashSet<>();
        Set<Home> homes = new HashSet<>();
        Player player = new Player(-1,-1);
        String[] temp;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(String.valueOf(saveFile))));
            String curLine;
            while (br.ready() ){
                curLine=br.readLine();
                if (curLine.startsWith("Save name: ")){
                    saveDataName = curLine.substring("Save name: ".length());
                }
                if (curLine.startsWith("Level: ")){
                    level = Integer.parseInt(curLine.substring("Level: ".length()));
                }
                if (curLine.startsWith("Walls: ")){
                    temp = curLine.substring("Walls: ".length()).split(" ");
                    for (String value : temp) {
                        String[] s = value.split(";");
                        int x = Integer.parseInt(s[0]);
                        int y = Integer.parseInt(s[1]);
                        walls.add(new Wall(x, y));
                    }
                }
                if (curLine.startsWith("Boxes: ")){
                    temp = curLine.substring("Boxes: ".length()).split(" ");
                    for (String value : temp) {
                        String[] s = value.split(";");
                        int x = Integer.parseInt(s[0]);
                        int y = Integer.parseInt(s[1]);
                        boxes.add(new Box(x, y));
                    }
                }
                if (curLine.startsWith("Homes: ")){
                    temp = curLine.substring("Homes: ".length()).split(" ");
                    for (String value : temp) {
                        String[] s = value.split(";");
                        int x = Integer.parseInt(s[0]);
                        int y = Integer.parseInt(s[1]);
                        homes.add(new Home(x, y));
                    }
                }
                if (curLine.startsWith("Player: ")){
                    String[] s = curLine.substring("Player: ".length()).split(";");
                    int x = Integer.parseInt(s[0]);
                    int y = Integer.parseInt(s[1]);
                    player = new Player(x,y);
                }
                if (curLine.startsWith("**********")){
                    arrayList.add(new SaveData(saveDataName, new GameObjects(walls, boxes, homes, player), level));
                    walls = new HashSet<>();
                    boxes = new HashSet<>();
                    homes = new HashSet<>();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private void writeSaves(){

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(String.valueOf(saveFile)));
            saveData.forEach(sd->{
                try {
                    bw.write("Save name: "+sd.name);
                    bw.newLine();
                    bw.write("Level: "+sd.level);
                    bw.newLine();
                    bw.write("Walls:");
                    for (Wall w : sd.gameObjects.getWalls()) {
                        bw.write(" "+w.getX()+";"+w.getY());
                    }
                    bw.newLine();
                    bw.write("Boxes:");
                    for (Box w : sd.gameObjects.getBoxes()) {
                        bw.write(" "+w.getX()+";"+w.getY());
                    }
                    bw.newLine();
                    bw.write("Homes:");
                    for (Home w : sd.gameObjects.getHomes()) {
                        bw.write(" "+w.getX()+";"+w.getY());
                    }
                    bw.newLine();
                    bw.write("Player: "+sd.gameObjects.getPlayer().getX()+";"+sd.gameObjects.getPlayer().getY());
                    bw.newLine();
                    bw.write("**********");
                    bw.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<String> getSavesName(){
        ArrayList<String> names = new ArrayList<>();
        saveData.forEach(sd->names.add(sd.name));
        return names;
    }

    public boolean saveGame(String name){

        return false;
    }

    public void removeSave(String name){
        saveData.removeIf(sd -> sd.name.equalsIgnoreCase(name));
        writeSaves();
    }

    private class SaveData{
        String name;
        GameObjects gameObjects;
        int level;

        public SaveData(String name, GameObjects gameObjects, int level) {
            this.name = name;
            this.gameObjects = gameObjects;
            this.level = level;
        }
    }
}
