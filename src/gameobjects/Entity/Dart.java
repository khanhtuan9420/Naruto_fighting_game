package gameobjects.Entity;

import effect.Animation;
import effect.CacheDataLoader;
import gameobjects.Gamemanager.GameWorld;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Dart extends Bullet{
	
    private Animation forwardBulletAnim, backBulletAnim;
    
    public Dart(double x, double y, GameWorld gameWorld) {
            super(x, y, gameWorld ,30, 30, 1.0f, 10);
            forwardBulletAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
            backBulletAnim = CacheDataLoader.getInstance().getAnimation("darkraisebullet");
            backBulletAnim.flipAllImage();
            setIsTimeLimit(false);
    }

    
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
            
            return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
            
        if(getSpeedX() > 0){          
            forwardBulletAnim.Update(System.nanoTime());
            forwardBulletAnim.draw((int) (getPosX()), (int) getPosY(), g2);
        }else{
            backBulletAnim.Update(System.nanoTime());
            backBulletAnim.draw((int) (getPosX()), (int) getPosY(), g2);
        }
        //drawBoundForCollisionWithEnemy(g2);
    }

    @Override
    public void Update() {
            
        super.Update();
    }

    @Override
    public void attack() {}
    @Override
    public void superAttack() {}
}
