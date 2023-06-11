package gameobjects.Entity;

import gameobjects.Gamemanager.GameWorld;

public abstract class GameObject {
    private double posX;
    private double posY;
    private GameWorld gameWorld;
    public GameObject(double posX, double posY, GameWorld gameWorld) {
        this.posX = posX;
        this.posY = posY;
        this.gameWorld = gameWorld;
    }
    public double getPosX() {
        return posX;
    }
    public void setPosX(double posX) {
        this.posX = posX;
    }
    public double getPosY() {
        return posY;
    }
    public void setPosY(double posY) {
        this.posY = posY;
    }
    public GameWorld getGameWorld() {
        return gameWorld;
    }
    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
    public abstract void Update();
}
