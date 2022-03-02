package model;

import controller.EventListener;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

public class Model {

    public static final int FIELD_CELL_SIZE = 20;
    private EventListener eventListener;
    private GameObjects gameObjects;
    private int currentLevel = 0;
    private LevelLoader levelLoader;
    private final Stack<GameObjects> userActions = new Stack<>();
    private SaveAndLoad saveAndLoad;

    public Model(){


        try {
            saveAndLoad = new SaveAndLoad(Paths.get(getClass().getResource("../res/saves.txt").toURI()));
            levelLoader = new LevelLoader(Paths.get(getClass().getResource("../res/levels.txt").toURI()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void move(Direction direction){
        GameObjects tempGameObjects = gameObjects.clone();

        if (checkWallCollision(gameObjects.getPlayer(), direction)){
            return;
        }
        
        if (checkBoxCollisionAndMoveIfAvailable(direction)){
            return;
        }

        userActions.push(tempGameObjects.clone());
        switch (direction){
            case UP: gameObjects.getPlayer().move(0, -FIELD_CELL_SIZE); break;
            case DOWN: gameObjects.getPlayer().move(0, FIELD_CELL_SIZE); break;
            case LEFT: gameObjects.getPlayer().move(-FIELD_CELL_SIZE, 0); break;
            case RIGHT: gameObjects.getPlayer().move(FIELD_CELL_SIZE,0); break;
        }
        checkCompletion();
    }

    public void setEventListener(EventListener eventListener){
        this.eventListener = eventListener;
    }

    public GameObjects getGameObjects(){
        return this.gameObjects;
    }

    public void restartLevel(int level){
       this.gameObjects =  levelLoader.getLevel(level);
       if (userActions!=null)
           userActions.clear();
    }

    public void restart(){
        restartLevel(this.currentLevel);
    }

    public void startNextLevel(){
        currentLevel++;
        restart();
    }

    public boolean checkWallCollision(CollisionObject gameObject, Direction direction) {
        for (Wall wall : gameObjects.getWalls()) {
            if (gameObject.isCollision(wall, direction)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkBoxCollisionAndMoveIfAvailable(Direction direction) {
        Box boxInDirection = null;
        for (Box go: gameObjects.getBoxes()){
            if (gameObjects.getPlayer().isCollision(go, direction)) {
                boxInDirection = go;
                for (Box bx: gameObjects.getBoxes()){
                    if (go.isCollision(bx,direction))
                        return true;
                }
                if (checkWallCollision(go,direction))
                    return true;
                }
            }

        if (boxInDirection!=null){
            switch (direction){
                case UP: boxInDirection.move(0, -FIELD_CELL_SIZE); break;
                case DOWN: boxInDirection.move(0, FIELD_CELL_SIZE); break;
                case LEFT: boxInDirection.move(-FIELD_CELL_SIZE, 0); break;
                case RIGHT: boxInDirection.move(FIELD_CELL_SIZE,0); break;
            }
        }
        return false;
    }

    public void checkCompletion(){
        int boxesOnHomes=0;
        for (Box box: gameObjects.getBoxes()) {
            for (Home home: gameObjects.getHomes())
                if (box.getY()== home.getY()&&box.getX()== home.getX())
                    boxesOnHomes++;
        }
        if (boxesOnHomes==gameObjects.getHomes().size())
            eventListener.levelCompleted(currentLevel);
    }

    public boolean canMakeUndo(){
        return !userActions.empty();
    }

    public void undoMove(){
        if (!userActions.empty()){
            gameObjects = userActions.pop();

        }
    }

    public boolean saveGame(String saveName){
        return saveAndLoad.saveGame(saveName);
    }

    public void removeSave(String saveName){
        saveAndLoad.removeSave(saveName);
    }

    public ArrayList<String> getSaves(){
        return saveAndLoad.getSavesName();
    }




}
