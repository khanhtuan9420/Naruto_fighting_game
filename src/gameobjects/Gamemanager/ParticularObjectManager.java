package gameobjects.Gamemanager;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import gameobjects.Entity.ParticularObject;

import java.awt.Graphics2D;

public class ParticularObjectManager {
    protected List<ParticularObject> particularObjects;
    private GameWorld gameWorld;

    public ParticularObjectManager(GameWorld gameWorld) {
        particularObjects = Collections.synchronizedList(new LinkedList<ParticularObject>());
        this.gameWorld = gameWorld;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void addObject(ParticularObject particularObject) {
        synchronized (particularObjects) {
            particularObjects.add(particularObject);
        }
    }

    public void removeObject(ParticularObject particularObject) {
        synchronized (particularObjects) {
            for (int id = 0; id < particularObjects.size(); id++) {
                ParticularObject object = particularObjects.get(id);
                if (object == particularObject) {
                    particularObjects.remove(id);
                }
            }
        }
    }

    public ParticularObject getCollisionWithEnemyObject(ParticularObject object) {
        synchronized (particularObjects) {
            for (int id = 0; id < particularObjects.size(); id++) {
                ParticularObject objectInList = particularObjects.get(id);
                if (object.getTeamType() != objectInList.getTeamType() && object.getBoundForCollisionWithEnemy()
                        .intersects(objectInList.getBoundForCollisionWithEnemy())) {
                    return objectInList;
                }
            }
        }
        return null;
    }

    public void UpdateObject() {
        synchronized(particularObjects) {
            for(int id=0; id< particularObjects.size(); id++) {
                ParticularObject object = particularObjects.get(id);
                if(object.getTeamType()==ParticularObject.BOT_TEAM && !GameWorld.isInitEnemy) {
                    particularObjects.remove(id);
                }
                object.Update();
                if((object.getBlood()<=0 || System.currentTimeMillis()- getGameWorld().itemTime>5000) && object.getTeamType()==ParticularObject.ITEM_TEAM) 
                    particularObjects.remove(id);
            }
        }
    }

    public void draw(Graphics2D g2) {
        synchronized(particularObjects) {
            for(ParticularObject object : particularObjects) {
                    object.draw(g2);
            }
        }
    }
}
