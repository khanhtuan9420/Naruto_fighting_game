package gameobjects.Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import effect.CacheDataLoader;
import gameobjects.Gamemanager.GameWorld;

public abstract class Item extends ParticularObject {
    Random rd = new Random();
    public Item(GameWorld gameWorld) {
        super(0, 300, gameWorld, 30, 43, 3, 10);
        setTeamType(ParticularObject.ITEM_TEAM);
        setIsTimeLimit(false);
        setIsDistanceLimit(false);
        setFrameLimit(false);
        this.setPosX(rd.nextInt(1230)+30);
    }

    @Override
    public void attack() {

    }

    @Override
    public void superAttack() {

    }

    public void draw(Graphics2D g2) {
        
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {

        return getBoundForCollisionWithMap();
    }

    @Override
    public void Update() {
        super.Update();
        if(getPosY()+ getMass()<=607) setPosY(getPosY()+ getMass());
    }
}
