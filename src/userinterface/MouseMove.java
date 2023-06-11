package userinterface;
import java.awt.event.*;

import gameobjects.Gamemanager.GameWorld;
import gameobjects.Gamemanager.Menu;

public class MouseMove implements MouseMotionListener {
    private GameWorld gameWorld;
    public MouseMove(GameWorld gameWorld) {
        this.gameWorld=gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld=gameWorld;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        
        
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        if(gameWorld.state==GameWorld.GAMEOVER) {
            if(e.getX()>=300 && e.getX()<=480 && e.getY()>=540 && e.getY()<=650) {
                gameWorld.getMenu().setMenu(Menu.MENU_YES);
            } else if(e.getX()>=830 && e.getX()<=970 && e.getY()>=540 && e.getY()<=650) {
                gameWorld.getMenu().setMenu(Menu.MENU_NO);
            } else gameWorld.getMenu().setMenu(Menu.MENU_INTERMEDIATE);
        }

        if(gameWorld.state==GameWorld.GAMESTART) {
            if(e.getX()>=164 && e.getX()<=366 && e.getY()>=212 && e.getY()<=302) {
                gameWorld.getMenu().setMenu(Menu.MENU_YES);
            } else if(e.getX()>=957 && e.getX()<=1148 && e.getY()>=212 && e.getY()<=302) {
                gameWorld.getMenu().setMenu(Menu.MENU_NO);
            } else if(e.getX()>=1000 && e.getX()<=1100 && e.getY()>=540 && e.getY()<=640) {
                gameWorld.getMenu().setMenu(Menu.MENU_EXTEND);
            } else gameWorld.getMenu().setMenu(Menu.MENU_INTERMEDIATE);
        }
    }
}
