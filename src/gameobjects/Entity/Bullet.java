package gameobjects.Entity;
import java.awt.Graphics2D;

import gameobjects.Gamemanager.GameWorld;
import gameobjects.Gamemanager.ParticularObjectManager;

public abstract class Bullet extends ParticularObject {

    public Bullet(double posX, double posY, GameWorld gameWorld, double width, double height, double mass, int damage) {
        super(posX, posY, gameWorld, width, height, mass, 1);
        setDamage(damage);
        setIsTimeLimit(true);
    }

    public abstract void draw(Graphics2D g2);

    public void Update() {
        super.Update();
        setPosX(getPosX()+getSpeedX());
        setPosY(getPosY()+getSpeedY());
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
        if(object != null && object.getState()==ALIVE) {
            setBlood(0);
            object.beHurt(getDamage());
        }
    }
}
