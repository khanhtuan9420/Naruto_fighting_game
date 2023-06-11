package userinterface;

import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;

import effect.CacheDataLoader;

import java.awt.Dimension;

public class GameFrame extends JFrame {
    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 740;
    GamePanel gamePanel;
    public static Mouse mouse;
    public static MouseMove mouseMove;

    public GameFrame() {
        Toolkit toolkit = this.getToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds((dimension.width - SCREEN_WIDTH) / 2, (dimension.height - SCREEN_HEIGHT) / 2, SCREEN_WIDTH,
                SCREEN_HEIGHT);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            CacheDataLoader.getInstance().LoadData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        gamePanel = new GamePanel();
        mouse=new Mouse(gamePanel.gameWorld);
        mouseMove= new MouseMove(gamePanel.gameWorld);
        add(gamePanel);
        addKeyListener(gamePanel);
        addMouseListener(mouse); 
        addMouseMotionListener(mouseMove);
    }

    public void startGame() {
        gamePanel.startGame();
    }

    public static void main(String[] args) {
        GameFrame test = new GameFrame();
        test.setVisible(true);
        test.setTitle("Naruto_fighting_game");
        test.startGame();
    }
}
