package com.homework.snake.view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.homework.snake.domain.SnakeController;
import com.homework.snake.domain.SnakeGame;


public class SnakeWindow extends JFrame {
    private static final int SCOREBAR_HEIGHT = 30;
    private static final int WINDOW_WIDTH = 506;
    private static final int WINDOW_HEIGHT = 380;

    private static final int BLOCK_SIZE = 10;

    private JFrame mainWindow;
    private GameBoard gameBoard;
    private ScoreBar scoreBar;
    private TopList topList;
    private SnakeMenuBar menuBar;
    private SnakeGame game;
    private SnakeController controller;


    public SnakeWindow(SnakeGame game, SnakeController controller) {
        this.game = game;
        this.controller = controller;
        initWindow();
    }

    private void initWindow() {
        mainWindow = new JFrame("Snake v0.8");
        initMenuBar();
        initGameBoard();
        initScoreBar();
        initTopList();
        initMainWindow();
    }

    private void initTopList() {
        topList = new TopList(mainWindow, new Point(0, 0), (game.getWidth() + 2) * BLOCK_SIZE, (game.getHeight() + 2) * BLOCK_SIZE);
        mainWindow.add(topList);
    }

    private void initMainWindow() {
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void keyPressed(KeyEvent e) {
                controller.handleKeyPress(e.getKeyCode());
            }
        });
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
    }

    private void initGameBoard() {
        gameBoard = new GameBoard(game, new Point(BLOCK_SIZE, BLOCK_SIZE), game.getWidth() * BLOCK_SIZE, game.getHeight() * BLOCK_SIZE);
        mainWindow.add(gameBoard);
    }

    private void initScoreBar() {
        scoreBar = new ScoreBar(new Point(0, (game.getHeight() + 2) * BLOCK_SIZE), WINDOW_WIDTH, SCOREBAR_HEIGHT);
        mainWindow.add(scoreBar);
    }

    public void initMenuBar() {
        menuBar = new SnakeMenuBar();
        menuBar.addListenerToShowTopList(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameBoard, topList.getScoreBoard());
            }
        });
        menuBar.addListenerToExitGame(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuBar.addListenerToShowCredits(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameBoard, "Készítõ: Kérlek Refaktorálj\n" + "Programnév: Snake\n" + "Verziószám: v0.8");
            }
        });
        menuBar.addListenerToShowControls(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameBoard, "Irányítás a kurzor segítségével:\n" + "-Fel nyíl: a kígyó felfele mozog\n"
                        + "-Le nyíl: a kígyó lefele mozog\n" + "-Jobbra nyíl: a kígyó jobbra mozog\n" + "-Balra nyíl: a kígyó balra mozog\n");
            }
        });

        mainWindow.setJMenuBar(menuBar);
    }

    public void addListenerToNewGame(ActionListener listener) {
        menuBar.addListenerToNewGame(listener);
    }
    
    public void addListenerToSetHardMode(ActionListener listener) {
        menuBar.addListenerToSetHardMode(listener);
    }
    
    public void addListenerToSetNormalMode(ActionListener listener) {
        menuBar.addListenerToSetNormalMode(listener);
    }

    public void addListenerToSetEasyMode(ActionListener listener) {
        menuBar.addListenerToSetEasyMode(listener);
    }

    public void drawGameBoard() {
        gameBoard.drawPoints();
    }

    public void init() {
        gameBoard.init();
        scoreBar.setScore(game.getScore());
        topList.setVisible(false);
        gameBoard.setVisible(true);
    }

    public void setScore(int score) {
        scoreBar.setScore(score);
    }

    public void showScoreBoard() {
        gameBoard.setVisible(false);
        topList.addScore(game.getScore());
        topList.setVisible(true);
    }
}
