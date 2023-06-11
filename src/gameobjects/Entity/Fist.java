package gameobjects.Entity;

import java.awt.Rectangle;

import gameobjects.Gamemanager.GameWorld;

import java.awt.Graphics2D;

public class Fist extends Bullet {

    public Fist(double posX, double posY, GameWorld gameWorld) {
        super(posX, posY, gameWorld, 35, 30, 1, 10);
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        // drawBoundForCollisionWithMap(g2);
    }
    @Override
    public void Update() {
        super.Update();
    }
    @Override
    public void attack() {
        
    }

    @Override
    public void superAttack() {
        
    }
}
