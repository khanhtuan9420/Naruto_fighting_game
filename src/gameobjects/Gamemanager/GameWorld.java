package gameobjects.Gamemanager;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import effect.CacheDataLoader;
import userinterface.GameFrame;
import effect.FrameImage;
import effect.Sound;
import gameobjects.Entity.*;


import java.awt.Color;
import java.awt.Font;
import java.awt.image.*;

public class GameWorld {
    public Sound bgsound;
    public Sound battlesound;
    private Sound sounds;
    private BufferedImage bufferedImage;
    private long blinkTime = 0;
    private boolean checkSoundWin = false;
    private boolean checkSoundLost = false;
    Color color = new Color(224, 60, 60, 255);
    public ParticularObjectManager particularObjectManager;
    public BulletManager bulletManager;
    public ItemManager itemManager;
    public Naruto naruto;
    public Gaara gaara;
    public static boolean sound = true;
    public PhysicalMap physicalMap;
    public static boolean isGameOver = false;
    public static final int INIT_GAME = 0;
    public static final int GAMESTART = 1;
    public static final int TUTORIAL = 2;
    public static final int GAMEPLAY = 3;
    public static final int GAMEOVER = 4;
    public static final int PAUSEGAME = 5;
    public static final int REGAME = 6;
    public static final int BACK = 7;
    public static int Level = 0;
    public static final int INTROGAME = 0;
    public static boolean isInitEnemy = false;
    public boolean isResetMenu = false;
    private Menu menu;
    public int openIntroGameY = 0;
    public int state = INIT_GAME;
    public int previousState = state;
    public int tutorialState = INTROGAME;
    public String easyButtonState = "";
    public String hardButtonState = "";
    public int storyTutorial = 0;
    public String[] texts1 = new String[4];
    public String textTutorial;
    public int currentSize = 1;
    public long itemTime = 0;
    private Random rd;
    private int boundOfRandom;

    FrameImage avatar = CacheDataLoader.getInstance().getFrameImage("Avatar");

    public static int k = 0;
    private long delayRender;
    public String continueGame = "continue";
    public String settingGame = "setDisable";
    public String quitGame = "quitDisable";
    // public static int menu;

    public String selected = "undecor";
    public String gameStart = "playGame";
    public static String isSound = "unmute";
    public static String soundPlay = "soundON";

    public GameWorld() {
        rd = new Random();
        texts1[0] = "Chào mừng các bạn đến với game Wibu\nĐẠI CHIẾN";
        texts1[1] = "Đây là trận chiến giữa các wibu dưới đáy xh\nmột trận chiến quyết định ai sẽ vào hang ở... ";
        texts1[2] = "Trước hết mời các\nwibu chọn chế độ";
        texts1[3] = "Nào bắt đầu thôi! GÉC GÔ...";
        textTutorial = texts1[0];
        Level = 0;
        menu = new Menu(this);
        menu.setMenu(Menu.MENU_INTERMEDIATE);
        isInitEnemy = false;
        isGameOver = false;
        bufferedImage = new BufferedImage(GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        naruto = new Naruto(400, 400, this);
        gaara = new Gaara(800, 400, this);
        physicalMap = new PhysicalMap(0, 0, this);
        bulletManager = new BulletManager(this);
        itemManager = new ItemManager(this);
        particularObjectManager = new ParticularObjectManager(this);
        particularObjectManager.addObject(naruto);
        particularObjectManager.addObject(gaara);

        bgsound = new Sound();
        bgsound.setFile("bgmusic");
        sounds = new Sound();
        battlesound = new Sound();
        battlesound.setFile("battlesound");
    }

    public Menu getMenu() {
        return menu;
    }

    private void initEnemies() {

        ParticularObject kakashi = new KakashiBot(656, 210, this);
        kakashi.setTeamType(ParticularObject.BOT_TEAM);
        particularObjectManager.addObject(kakashi);

    }

    public void switchState(int state) {
        previousState = this.state;
        this.state = state;
    }

    private void TutorialUpdate() {
        switch (tutorialState) {
            case INTROGAME:

                if (storyTutorial == 0) {
                    if (openIntroGameY < 450) {
                        openIntroGameY += 4;
                    } else
                        storyTutorial++;

                } else {

                    if (currentSize < textTutorial.length())
                        currentSize++;
                }
                break;

        }
    }

    private void drawString(Graphics2D g2, String text, int x, int y) {
        for (String str : text.split("\n"))
            g2.drawString(str, x, y += g2.getFontMetrics().getHeight());
    }

    private void TutorialRender(Graphics2D g2) {
        switch (tutorialState) {
            case INTROGAME:
                int yMid = GameFrame.SCREEN_HEIGHT / 2 - 15;
                int y1 = yMid - GameFrame.SCREEN_HEIGHT / 2 - openIntroGameY / 2;
                int y2 = yMid + openIntroGameY / 2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);

                if (storyTutorial >= 1) {
                    g2.drawImage(avatar.getImage(), 700, 379, null);
                    g2.setColor(Color.white);
                    g2.fillRect(380, 450, 350, 80);
                    g2.setColor(Color.BLACK);
                    String text = textTutorial.substring(0, currentSize - 1);
                    drawString(g2, text, 390, 475);
                }

                if (storyTutorial == 3) {
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage(easyButtonState).getImage(), 510, 465,
                            null);
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage(hardButtonState).getImage(), 620, 465,
                            null);
                }

                break;

        }
    }

    public void Update() {
        if (state != GameWorld.GAMESTART)
            bgsound.stop();
        if (isSound == "mute") {
            bgsound.stop();
            sound = false;
        } else if (!sound) {
            if (state == GameWorld.GAMESTART) {
                bgsound.play();
                bgsound.loop();
            }
            sound = true;
        }
        if (isSound == "unmute" && sound && state != GameWorld.GAMESTART && state != GameWorld.INIT_GAME) {
            battlesound.play();
            battlesound.loop();
            sound = false;
        } else {
            battlesound.stop();
        }
        if (System.currentTimeMillis() - blinkTime > 500 && System.currentTimeMillis() - blinkTime < 1000) {
            color = new Color(0, 0, 0, 255);
        } else if (System.currentTimeMillis() - blinkTime < 500) {
            color = new Color(224, 60, 60, 255);
        } else {
            blinkTime = System.currentTimeMillis();
        }
        if (Level != 1) {
            easyButtonState = "EasyDisabled";
        } else
            easyButtonState = "Easy";
        if (Level != 2) {
            hardButtonState = "HardDisabled";
        } else
            hardButtonState = "Hard";

        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
        switch (state) {
            case INIT_GAME:
                g2.drawImage(CacheDataLoader.getInstance().getFrameImage("Intro").getImage(), 0, 0, null);
                break;

            case GAMESTART:
                menu.MenuUpdate();
                break;

            case PAUSEGAME:
                if (k <= 10) {
                    float[] blurKernel = new float[3 * 3];
                    for (int j = 0; j < blurKernel.length; j++)
                        blurKernel[j] = 1f / (3 * 3);
                    BufferedImageOp blur = new ConvolveOp(new Kernel(3, 3, blurKernel));
                    BufferedImage bluri = blur.filter(bufferedImage, new BufferedImage(bufferedImage.getWidth(),
                            bufferedImage.getHeight(), bufferedImage.getType()));
                    k++;
                    g2.drawImage(bluri, 0, 0, null);
                }

                menu.MenuUpdate();
                break;
            case TUTORIAL:
                TutorialUpdate();
                menu.setMenu(Menu.MENU_YES);
                break;
            case GAMEPLAY:
                if (Level == 1)
                    boundOfRandom = 2;
                if (Level == 2)
                    boundOfRandom = 8;
                if (System.currentTimeMillis() - itemTime > 10000) {
                    int a = rd.nextInt(boundOfRandom);
                    switch (a) {
                        case 0:
                            HPitem hpItem = new HPitem(this);
                            itemManager.addObject(hpItem);
                            break;
                        case 1:
                            ManaItem manaItem = new ManaItem(this);
                            itemManager.addObject(manaItem);
                            break;
                        case 2:
                        case 3:
                            ToxicItem toxicItem = new ToxicItem(this);
                            itemManager.addObject(toxicItem);
                            break;
                        case 4:
                        case 5:
                            SlowItem slowItem = new SlowItem(this);
                            itemManager.addObject(slowItem);
                            break;
                        case 6:
                        case 7:
                            FastItem fastItem = new FastItem(this);
                            itemManager.addObject(fastItem);
                            break;
                    }
                    ;
                    itemTime = System.currentTimeMillis();
                }
                if (!isInitEnemy && Level == 2) {
                    isInitEnemy = true;
                    initEnemies();
                }
                particularObjectManager.UpdateObject();
                bulletManager.UpdateObject();
                itemManager.UpdateObject();
                physicalMap.Update();

                if (naruto.getState() == ParticularObject.DEATH || gaara.getState() == ParticularObject.DEATH) {
                    if (naruto.getState() == ParticularObject.DEATH && soundPlay == "soundON") {
                        if (!checkSoundLost) {
                            playSound("narutodie");
                            checkSoundLost = true;
                        }
                        if (System.currentTimeMillis() - naruto.timeDeath > 2000 && !checkSoundWin) {
                            playSound("gaarawin");
                            checkSoundWin = true;
                        }
                    }
                    if (gaara.getState() == ParticularObject.DEATH && soundPlay == "soundON") {
                        if (!checkSoundLost) {
                            playSound("gaaradie");
                            checkSoundLost = true;
                        }
                        if (System.currentTimeMillis() - gaara.timeDeath > 2000 && !checkSoundWin) {
                            playSound("narutowin");
                            checkSoundWin = true;
                        }
                    }
                    if (isGameOver && (System.currentTimeMillis()
                            - ((naruto.timeDeath > gaara.timeDeath) ? naruto.timeDeath : gaara.timeDeath) > 5000)) {
                        switchState(GAMEOVER);
                        ;
                    }
                }

                break;
            case GAMEOVER:
                if (!isResetMenu) {
                    menu.setMenu(Menu.MENU_INTERMEDIATE);
                    ;
                    isResetMenu = true;
                }
                menu.MenuUpdate();
                break;
        }

    }

    public void Render() {

        Graphics2D g2 = (Graphics2D) bufferedImage.getGraphics();
        g2.setColor(color);
        if (g2 != null) {

            switch (state) {
                case INIT_GAME:
                    g2.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
                    g2.drawString("PRESS ENTER TO CONTINUE", 520, 350);
                    break;

                case GAMESTART:
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage(gameStart).getImage(), 0, 0, null);
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage(isSound).getImage(), 1000, 540, null);
                    break;

                case PAUSEGAME:
                    if (System.currentTimeMillis() - delayRender > 200) {
                        menu.MenuRender(g2);
                        delayRender = System.currentTimeMillis();

                    }

                    break;
                case TUTORIAL:
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("Map").getImage(), 0, 0, null);
                    TutorialRender(g2);

                    break;
                case GAMEPLAY:
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("Map").getImage(), 0, 0, null);
                    particularObjectManager.draw(g2);
                    bulletManager.draw(g2);
                    itemManager.draw(g2);
                    g2.setColor(Color.red);
                    g2.fillRect(58, 60, (int) (naruto.getBlood() * 3), 20);

                    g2.setColor(Color.red);
                    g2.fillRect(1218 - (int) (gaara.getBlood() * 3), 60, (int) (gaara.getBlood() * 3), 20);

                    g2.setColor(Color.blue);
                    g2.fillRect(58, 90, (int) (naruto.getMana() * 3), 20);

                    g2.setColor(Color.blue);
                    g2.fillRect(1218 - (int) (gaara.getMana() * 3), 90, (int) (gaara.getMana() * 3), 20);

                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hpbar").getImage(), 0, 35, null);

                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("hpbar_2").getImage(), 876, 35, null);

                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage(easyButtonState).getImage(), 585, 20,
                            null);
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage(hardButtonState).getImage(), 585, 77,
                            null);
                    g2.setColor(Color.BLACK);
                    drawString(g2, "PRESS T TO CHANGE THE LEVEL", 545, 132);

                    g2.setColor(Color.white);
                    drawString(g2, "HP: " + Integer.toString(naruto.getBlood()), 70, 58);
                    drawString(g2, "MP: " + Integer.toString(naruto.getMana()), 70, 88);
                    drawString(g2, Integer.toString(gaara.getBlood()) + ":HP", 1165, 58);
                    drawString(g2, Integer.toString(gaara.getMana()) + " :MP", 1165, 88);

                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("Logo1").getImage(), 390, 65, null);
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage("Logo2").getImage(), 700, 65, null);

                    g2.setColor(color);
                    g2.setFont(new Font("SANS_SERIF", Font.BOLD, 100));
                    if (naruto.getState() == ParticularObject.DEATH || gaara.getState() == ParticularObject.DEATH) {
                        if (naruto.timeDeath > gaara.timeDeath)
                            g2.drawString("Gaara WIN", 380, 350);
                        else
                            g2.drawString("Naruto WIN", 380, 350);
                    }
                    break;
                case GAMEOVER:
                    g2.drawImage(CacheDataLoader.getInstance().getFrameImage(selected).getImage(), 0, 0, null);
                    break;

            }

        }

    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void playSound(String str) {
        sounds.setFile(str);
        sounds.play();
    }

}