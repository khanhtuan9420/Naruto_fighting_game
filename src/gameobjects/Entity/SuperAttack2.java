package gameobjects.Entity;

import effect.Animation;
import effect.CacheDataLoader;
import gameobjects.Gamemanager.GameWorld;

import java.awt.Rectangle;
import java.awt.Graphics2D;

public class SuperAttack2 extends Bullet {
    private Animation forwardBulletAnim;

    public SuperAttack2(double posX, double posY, GameWorld gameWorld) {
        super(posX , posY-20, gameWorld, 210, 175, 1, 10);

        forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("SandBurial");
        setIsTimeLimit(false);
        setIsDistanceLimit(false);
        setFrameLimit(false);
        if(gameWorld.gaara.getDirection()==LEFT_DIR) {
            setPosX(posX-300);
        } else setPosX(posX+300);
    }


    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        forwardBulletAnim.Update(System.nanoTime());
        forwardBulletAnim.draw((int) (getPosX()), (int) getPosY(), g2);
        // drawBoundForCollisionWithMap(g2);
    }
    @Override
    public void Update() {
        if(forwardBulletAnim.getCurrentFrame()==18) setFrameLimit(true);
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
