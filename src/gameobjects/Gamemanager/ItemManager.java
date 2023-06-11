package gameobjects.Gamemanager;

public class ItemManager extends ParticularObjectManager {
    public ItemManager(GameWorld gameWorld) {
        super(gameWorld);
    }

    @Override
    public void UpdateObject() {
        super.UpdateObject();
        synchronized(particularObjects) {
            
        }
    }
}
