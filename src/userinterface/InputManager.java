package userinterface;

import java.awt.event.KeyEvent;

import gameobjects.Gamemanager.*;
import gameobjects.Entity.Naruto;

public class InputManager {
    private GameWorld gameWorld;

    public InputManager(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    public void setPressedButton(int code) {
        switch (code) {
            case KeyEvent.VK_T:
                if (gameWorld.state == GameWorld.GAMEPLAY) {
                    if (GameWorld.Level == 1)
                        GameWorld.Level = 2;
                    else {
                        GameWorld.Level = 1;
                        GameWorld.isInitEnemy = false;
                    }
                }
                break;

            case KeyEvent.VK_S:
                if (!gameWorld.naruto.isSuperAttack && gameWorld.naruto.getSpeedX() == 0
                        && gameWorld.state == GameWorld.GAMEPLAY)
                    gameWorld.naruto.dick();
                break;
            case KeyEvent.VK_DOWN:
                if (!gameWorld.gaara.isSuperAttack && gameWorld.gaara.getSpeedX() == 0)
                    gameWorld.gaara.dick();
                break;
            case KeyEvent.VK_D:
                if (!gameWorld.naruto.isSuperAttack && !gameWorld.naruto.getIsDicking() && !gameWorld.naruto.isRunning
                        && gameWorld.state == GameWorld.GAMEPLAY) {
                    gameWorld.naruto.setDirection(Naruto.RIGHT_DIR);
                    gameWorld.naruto.walk();
                }
                if (gameWorld.state == GameWorld.PAUSEGAME) {
                    if (gameWorld.getMenu().getMenuState() == 1)
                        gameWorld.getMenu().setMenu(Menu.MENU_INTERMEDIATE);
                    else if (gameWorld.getMenu().getMenuState() == 2)
                        gameWorld.getMenu().setMenu(Menu.MENU_YES);
                    else if (gameWorld.getMenu().getMenuState() == 3)
                        gameWorld.getMenu().setMenu(Menu.MENU_NO);
                    else if (gameWorld.getMenu().getMenuState() == 4) {
                        if (gameWorld.isSound == "mute")
                            gameWorld.isSound = "unmute";
                        else
                            gameWorld.isSound = "mute";
                    }
                }
                if (gameWorld.state == GameWorld.GAMEOVER) {
                    if (gameWorld.getMenu().getMenuState() == 1)
                        gameWorld.getMenu().setMenu(Menu.MENU_NO);
                    else if (gameWorld.getMenu().getMenuState() == 2)
                        gameWorld.getMenu().setMenu(Menu.MENU_YES);
                }

                break;
            case KeyEvent.VK_RIGHT:
                if (!gameWorld.gaara.isSuperAttack && !gameWorld.gaara.getIsDicking() && !gameWorld.gaara.isRunning) {
                    gameWorld.gaara.setDirection(Naruto.RIGHT_DIR);
                    gameWorld.gaara.walk();
                }
                break;
            case KeyEvent.VK_A:
                if (!gameWorld.naruto.isSuperAttack && !gameWorld.naruto.getIsDicking() && !gameWorld.naruto.isRunning
                        && gameWorld.state == GameWorld.GAMEPLAY) {
                    gameWorld.naruto.setDirection(Naruto.LEFT_DIR);
                    gameWorld.naruto.walk();
                }
                if (gameWorld.state == GameWorld.PAUSEGAME) {
                    if (gameWorld.getMenu().getMenuState() == 1)
                        gameWorld.getMenu().setMenu(Menu.MENU_NO);
                    else if (gameWorld.getMenu().getMenuState() == 2)
                        gameWorld.getMenu().setMenu(Menu.MENU_INTERMEDIATE);
                    else if (gameWorld.getMenu().getMenuState() == 3)
                        gameWorld.getMenu().setMenu(Menu.MENU_YES);
                    else if (gameWorld.getMenu().getMenuState() == 4) {
                        if (gameWorld.isSound == "mute")
                            gameWorld.isSound = "unmute";
                        else
                            gameWorld.isSound = "mute";
                    }
                }

                // if (gameWorld.state == GameWorld.GAMEOVER) {
                //     // if (gameWorld.menu == 1) gameWorld.menu =2;
                //     // else if (gameWorld.menu == 2) gameWorld.menu =1;
                // }
                break;
            case KeyEvent.VK_LEFT:
                if (!gameWorld.gaara.isSuperAttack && !gameWorld.gaara.getIsDicking() && !gameWorld.gaara.isRunning) {
                    gameWorld.gaara.setDirection(Naruto.LEFT_DIR);
                    gameWorld.gaara.walk();
                }

                break;
            case KeyEvent.VK_W:
                if (!gameWorld.naruto.isSuperAttack && gameWorld.state == GameWorld.GAMEPLAY)
                    gameWorld.naruto.jump();
                break;
            case KeyEvent.VK_UP:
                if (!gameWorld.gaara.isSuperAttack)
                    gameWorld.gaara.jump();
                break;
            case KeyEvent.VK_3:
                if (gameWorld.naruto.permitToRun && !gameWorld.naruto.isSuperAttack)
                    gameWorld.naruto.run();
                break;
            case KeyEvent.VK_J:
                if (gameWorld.gaara.permitToRun && !gameWorld.gaara.isSuperAttack)
                    gameWorld.gaara.run();
                break;
            case KeyEvent.VK_4:
                if (!gameWorld.naruto.isRunning && !gameWorld.naruto.isSuperAttack && !gameWorld.naruto.getIsJumping())
                    gameWorld.naruto.attack();
                break;
            case KeyEvent.VK_K:
                if (!gameWorld.gaara.isRunning && !gameWorld.gaara.isSuperAttack && !gameWorld.gaara.getIsJumping())
                    gameWorld.gaara.attack();
                break;
            case KeyEvent.VK_5:
                if (!gameWorld.naruto.isRunning && !gameWorld.naruto.getIsTimeLimit()
                        && !gameWorld.naruto.getIsJumping())
                    gameWorld.naruto.superAttack();
                break;
            case KeyEvent.VK_L:
                if (!gameWorld.gaara.isRunning && !gameWorld.gaara.getIsTimeLimit() && !gameWorld.gaara.getIsJumping())
                    gameWorld.gaara.superAttack();
                break;
            case KeyEvent.VK_ESCAPE:
                if (gameWorld.state == GameWorld.GAMEPLAY) {
                    gameWorld.k = 0;
                    gameWorld.switchState(GameWorld.PAUSEGAME);
                    if(gameWorld.previousState==GameWorld.PAUSEGAME) {
                        if (gameWorld.gaara.getSound().getClip() != null)
                            gameWorld.gaara.getSound().play();
                        if (gameWorld.naruto.getSound().getClip() != null)
                            gameWorld.naruto.getSound().play();
                    }
                } else if (gameWorld.state == GameWorld.PAUSEGAME) {
                    gameWorld.switchState(GameWorld.GAMEPLAY);
                    gameWorld.getMenu().setMenu(Menu.MENU_YES);
                }
                if (gameWorld.state == GameWorld.PAUSEGAME) {
                    if (gameWorld.gaara.getSound().getClip() != null)
                        gameWorld.gaara.getSound().stop();
                    if (gameWorld.naruto.getSound().getClip() != null)
                        gameWorld.naruto.getSound().stop();
                }

                break;
            case KeyEvent.VK_ENTER:
                if (gameWorld.state == GameWorld.INIT_GAME) {
                    gameWorld.switchState(GameWorld.GAMESTART);
                    if (GameWorld.isSound == "unmute") {
                        gameWorld.bgsound.play();
                        gameWorld.bgsound.loop();
                    }
                    gameWorld.getMenu().setMenu(Menu.MENU_INTERMEDIATE);
                }
                // gameWorld.bgMusic.loop();
                // gameWorld.bgMusic.play();

                if (gameWorld.state == GameWorld.TUTORIAL && gameWorld.storyTutorial >= 1) {
                    if (gameWorld.storyTutorial <= 3) {
                        if (gameWorld.storyTutorial != 3)
                            gameWorld.storyTutorial++;
                        else if (GameWorld.Level != 0)
                            gameWorld.storyTutorial++;
                        gameWorld.currentSize = 1;
                        gameWorld.textTutorial = gameWorld.texts1[gameWorld.storyTutorial - 1];
                    } else {
                        gameWorld.switchState(GameWorld.GAMEPLAY);
                    }
                }

                if (gameWorld.state == GameWorld.PAUSEGAME) {
                    if (gameWorld.getMenu().getMenuState() == 1) {

                        gameWorld.switchState(GameWorld.GAMEPLAY);
                        if(gameWorld.previousState==GameWorld.PAUSEGAME) {
                        if (gameWorld.gaara.getSound().getClip() != null)
                            gameWorld.gaara.getSound().play();
                        if (gameWorld.naruto.getSound().getClip() != null)
                            gameWorld.naruto.getSound().play();
                        }
                    }
                    if (gameWorld.getMenu().getMenuState() == 3)
                        gameWorld.getMenu().setMenu(Menu.MENU_EXTEND);
                    else if (gameWorld.getMenu().getMenuState() == 4)
                        gameWorld.getMenu().setMenu(Menu.MENU_INTERMEDIATE);
                    if (gameWorld.getMenu().getMenuState() == 2) {
                        gameWorld.state = GameWorld.BACK;
                        gameWorld.battlesound.stop();
                        gameWorld.getMenu().setMenu(Menu.MENU_INTERMEDIATE);
                    }
                }

        }
    }

    public void setReleasedButton(int code) {
        switch (code) {
            case KeyEvent.VK_UP:
                break;
            case KeyEvent.VK_S:
                gameWorld.naruto.standUp();
                break;
            case KeyEvent.VK_DOWN:
                gameWorld.gaara.standUp();
                break;
            case KeyEvent.VK_D:
                if (gameWorld.naruto.getSpeedX() > 0) {
                    gameWorld.naruto.stopRun();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (gameWorld.gaara.getSpeedX() > 0) {
                    gameWorld.gaara.stopRun();
                }
                break;
            case KeyEvent.VK_A:
                if (gameWorld.naruto.getSpeedX() < 0) {
                    gameWorld.naruto.stopRun();
                }
                break;
            case KeyEvent.VK_LEFT:
                if (gameWorld.gaara.getSpeedX() < 0) {
                    gameWorld.gaara.stopRun();
                }
                break;
        }
    }
}