package gameobjects.Gamemanager;

import java.awt.Graphics2D;

import effect.CacheDataLoader;
import userinterface.GameFrame;

public class Menu {
    public static final int MENU_YES=1;
    public static final int MENU_INTERMEDIATE=3;
    public static final int MENU_NO=2;
    public static final int MENU_EXTEND=4;
    private GameWorld gameWorld;
    private int menuState;
    public Menu(GameWorld gameWorld) {
        this.gameWorld=gameWorld;
        menuState=MENU_INTERMEDIATE;
    }
    public void setMenu(int i) {
        this.menuState=i;
    }

    public int getMenuState() {
        return menuState;
    }

    public void MenuUpdate() {
        if (menuState == 1) {
            gameWorld.continueGame = "continue";
            gameWorld.settingGame = "setDisable";
            gameWorld.quitGame = "quitDisable";
            gameWorld.selected = "yes";
            gameWorld.gameStart = "playGame";
        } else if (menuState == 2) {
            gameWorld.continueGame = "continueDisable";
            gameWorld.settingGame = "setDisable";
            gameWorld.quitGame = "quit";
            gameWorld.selected = "no";
            gameWorld.gameStart = "quitGame";
        } else if (menuState == 3) {
            gameWorld.continueGame = "continueDisable";
            gameWorld.quitGame = "quitDisable";
            gameWorld.settingGame = "set";
            gameWorld.selected = "undecor";
            gameWorld.gameStart = "defaultGame";
        } else if (menuState == 4 ) {
            if (gameWorld.isSound=="unmute") gameWorld.soundPlay = "soundON";
            else gameWorld.soundPlay = "soundOFF";
        }
    }

    public void MenuRender(Graphics2D g2) {
        g2.drawImage(CacheDataLoader.getInstance().getFrameImage("menuGame").getImage(),
                (GameFrame.SCREEN_WIDTH - CacheDataLoader.getInstance().getFrameImage("menuGame").getImage().getWidth())
                        / 2,
                (GameFrame.SCREEN_HEIGHT
                        - CacheDataLoader.getInstance().getFrameImage("menuGame").getImage().getHeight()) / 2,
                null);
        if (menuState != 4) {
            g2.drawImage(CacheDataLoader.getInstance().getFrameImage(gameWorld.continueGame).getImage(), 480, 240, null);
            g2.drawImage(CacheDataLoader.getInstance().getFrameImage(gameWorld.settingGame).getImage(), 620, 240, null);
            g2.drawImage(CacheDataLoader.getInstance().getFrameImage(gameWorld.quitGame).getImage(), 760, 240, null);
        } else {
            g2.drawImage(CacheDataLoader.getInstance().getFrameImage(gameWorld.soundPlay).getImage(), 480, 240, null);
        }
    }
}
