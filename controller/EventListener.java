package controller;


import model.Direction;

public interface EventListener {

    void move(Direction direction);

    void restart();

    void startNextLevel();

    void levelCompleted(int level);

    void newGame();

    void undoMove();

    boolean saveGame(String saveName);
}
