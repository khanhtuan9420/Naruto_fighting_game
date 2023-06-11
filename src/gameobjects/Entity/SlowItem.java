package gameobjects.Entity;

import java.awt.Graphics2D;
import effect.CacheDataLoader;
import gameobjects.Gamemanager.GameWorld;

public class SlowItem extends Item {
    public static final long limitSpeedDown=3000;
    public SlowItem(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("slow").getImage(), (int)(getPosX()-getWidth()/2),(int)(getPosY()-getHeight()/2-20 ), null);
    }
    
    @Override
    public void Update() {
        super.Update();
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
        if (object != null && object.getState() == ALIVE) {
            setBlood(0);
            setState(ParticularObject.BEHURT);
            // object.setState(ParticularObject.BEHURT);
            object.setSpeedX(0);
            object.startSpeedDown=System.currentTimeMillis();
        }
    }
}
