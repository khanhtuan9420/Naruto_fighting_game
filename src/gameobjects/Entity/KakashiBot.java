
package gameobjects.Entity;

import effect.Animation;
import effect.CacheDataLoader;
import gameobjects.Gamemanager.GameWorld;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class KakashiBot extends ParticularObject{

    private Animation forwardAnim, backAnim;
    
    private long startTimeToShoot;
    private float x1, x2;
    private int turn=0;
    
    public KakashiBot(float x, float y, GameWorld gameWorld) {
        super(x, y, gameWorld, 127, 89, 0, 100);
        backAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
        forwardAnim = CacheDataLoader.getInstance().getAnimation("darkraise");
        forwardAnim.flipAllImage();
        startTimeToShoot = 0;
        setTimeNotBeHurt(300000000);
        
        x1 = x - 100;
        x2 = x + 100;
        setSpeedX(1);
        
        setDamage(10);
    }

    @Override
    public void attack() {
    
        double narutoX = getGameWorld().naruto.getPosX();
        double narutoY = getGameWorld().naruto.getPosY();
        
        double deltaX = narutoX - getPosX();
        double deltaY = narutoY - getPosY();

        double gaaraX = getGameWorld().gaara.getPosX();
        double gaaraY = getGameWorld().gaara.getPosY();
        
        double deltaX1 = gaaraX - getPosX();
        double deltaY1 = gaaraY - getPosY();
        
        double speed = 3;
        double a=0;
        if(turn==0) {
            a= Math.abs(deltaX/deltaY);
        } else {
            a= Math.abs(deltaX1/deltaY1);
        }
        
        double speedX = (double) Math.sqrt(speed*speed*a*a/(a*a + 1));
        double speedY = (double) Math.sqrt(speed*speed/(a*a + 1));
        
        
        
        Bullet bullet = new Dart(getPosX(), getPosY(), getGameWorld());
        
        if(turn ==0) {
            if(deltaX < 0)
                bullet.setSpeedX(-speedX);
            else bullet.setSpeedX(speedX);
            turn=1;
        } else {
            if(deltaX1 < 0)
                bullet.setSpeedX(-speedX);
            else bullet.setSpeedX(speedX);
            turn=0;
        }
        bullet.setSpeedY(speedY);
        bullet.setTeamType(getTeamType());
        getGameWorld().bulletManager.addObject(bullet);

    }

    
    public void Update(){
        super.Update();
        if(getPosX() < x1)
            setSpeedX(1);
        else if(getPosX() > x2)
            setSpeedX(-1);
        setPosX(getPosX() + getSpeedX());
        
        if(System.nanoTime() - startTimeToShoot > 1000*10000000*1.5){
            attack();
            startTimeToShoot = System.nanoTime();
        }
    }
    
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x += 20;
        rect.width -= 40;
        
        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
            
                if(getDirection() == LEFT_DIR){
                    backAnim.Update(System.nanoTime());
                    backAnim.draw((int) (getPosX()), 
                            (int)(getPosY()), g2);
                }else{
                    forwardAnim.Update(System.nanoTime());
                    forwardAnim.draw((int) (getPosX()), 
                            (int)(getPosY()), g2);
                }
            
        //drawBoundForCollisionWithEnemy(g2);
    }
    
    @Override
    public void superAttack() {
        
    }
}
