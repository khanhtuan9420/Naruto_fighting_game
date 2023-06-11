package gameobjects.Entity;

import java.awt.Graphics2D;
import effect.CacheDataLoader;
import gameobjects.Gamemanager.GameWorld;

public class FastItem extends Item {
    public static final double speedUp =2;
    public static final long limitSpeedUp=3000;
    public FastItem(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("fast").getImage(), (int)(getPosX()-getWidth()/2),(int)(getPosY()-getHeight()/2-10 ), null);
    }
    
    @Override
    public void Update() {
        super.Update();
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
        if (object != null && object.getState() == ALIVE) {
            setBlood(0);
            setState(ParticularObject.BEHURT);
            object.setSpeedX(0);
            object.startSpeedUp=System.currentTimeMillis();
        }
    }
}
