package gameobjects.Entity;

import effect.Animation;
import effect.CacheDataLoader;
import gameobjects.Gamemanager.GameWorld;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class SuperAttack extends Bullet {
    private Animation forwardBulletAnim, backBulletAnim;

    public SuperAttack(double posX, double posY, GameWorld gameWorld) {
        super(posX+50, posY-75, gameWorld, 390, 272, 1, 10);

        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("Rasengan");
        backBulletAnim = CacheDataLoader.getInstance().getAnimation("Rasengan");
        backBulletAnim.flipAllImage();
        setIsTimeLimit(false);
        setIsDistanceLimit(true);
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getSpeedX() > 0) {

            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX()), (int) getPosY(), g2);
        } else {
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX()), (int) getPosY(), g2);
        }
        // drawBoundForCollisionWithMap(g2);
    }
    @Override
    public void Update() {
        setPosX(getPosX()+getSpeedX());
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
        if(object != null && object.getState()==ALIVE) {
            setBlood(0);
            object.setBlood(object.getBlood() - getDamage());
            object.setState(BEHURT);
        }
    }
    @Override
    public void attack() {
        
    }
    @Override
    public void superAttack() {

    }
}
