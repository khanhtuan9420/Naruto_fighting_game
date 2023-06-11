package gameobjects.Entity;

import java.awt.Graphics2D;

import effect.CacheDataLoader;
import gameobjects.Gamemanager.GameWorld;

public class HPitem extends Item {
    public HPitem(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hpitem").getImage(), (int)(getPosX()-getWidth()/2),(int)(getPosY()-getHeight()/2 ), null);
    }
    
    @Override
    public void Update() {
        super.Update();
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWithEnemyObject(this);
        if (object != null && object.getState() == ALIVE) {
            setBlood(0);
            setState(ParticularObject.BEHURT);
            // object.setState(ParticularObject.BEHURT);
            object.setBlood(object.getBlood() + 20);
            if(object.getBlood()>100) object.setBlood(100);
        }
    }
}
