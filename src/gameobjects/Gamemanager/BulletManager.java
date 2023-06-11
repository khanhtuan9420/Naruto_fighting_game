package gameobjects.Gamemanager;

import gameobjects.Entity.ParticularObject;

public class BulletManager extends ParticularObjectManager {
    public BulletManager(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void UpdateObject() {
        super.UpdateObject();
        synchronized (particularObjects) {
            for (int id = 0; id < particularObjects.size(); id++) {
                ParticularObject object = particularObjects.get(id);
                if(object.getTeamType()==ParticularObject.ENEMY_TEAM && object.getIsFrameLimit()) {
                    particularObjects.remove(id);
                }

                if(object.getIsTimeLimit()) {
                    if(System.nanoTime()- getGameWorld().naruto.lastPunchTime > 5000*1000000)
                        particularObjects.remove(id);
                }
                if(object.getIsDistanceLimit()) {
                    if(Math.abs(object.getPosX()-getGameWorld().naruto.getPosNow())>600) {
                        particularObjects.remove(id);
                    } 
                }
                if(getGameWorld().isGameOver) {
                    particularObjects.remove(id);
                }
            }
        }
    }
}
