package userinterface;

import java.awt.event.*;

import gameobjects.Gamemanager.GameWorld;
import gameobjects.Gamemanager.Menu;

public class Mouse implements MouseListener {
    GameWorld gameWorld;

    Mouse(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void mouseClicked(MouseEvent e) {
        if (gameWorld.state == GameWorld.TUTORIAL) {
            if (e.getX() > 510 && e.getX() < 634 && e.getY() > 465 && e.getY() < 544)
                GameWorld.Level = 1;
            if (e.getX() > 614 && e.getX() < 740 && e.getY() > 465 && e.getY() < 544)
                GameWorld.Level = 2;
        }

        if (gameWorld.state == GameWorld.GAMEOVER) {
            if (gameWorld.getMenu().getMenuState() == 1) {
                gameWorld.battlesound.stop();
                gameWorld.state = GameWorld.REGAME;
            }
            else if (gameWorld.getMenu().getMenuState() == 2) {
                gameWorld.state = GameWorld.BACK;
                gameWorld.battlesound.stop();
                gameWorld.getMenu().setMenu(Menu.MENU_INTERMEDIATE);
            }
        }

        if (gameWorld.state == GameWorld.GAMESTART) {
            if (gameWorld.getMenu().getMenuState() == 1)
                gameWorld.switchState(GameWorld.TUTORIAL);
            else if (gameWorld.getMenu().getMenuState() == 2)
                System.exit(0);
            else if (gameWorld.getMenu().getMenuState() == 4) {
                if (GameWorld.isSound == "unmute")
                    GameWorld.isSound = "mute";
                else
                    GameWorld.isSound = "unmute";
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }
}
