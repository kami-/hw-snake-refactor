package com.homework.snake.view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class SnakeWindow extends JFrame implements KeyListener {
    private static final int SCOREBAR_HEIGHT = 30;
    private static final int WINDOW_WIDTH = 506;
    private static final int WINDOW_HEIGHT = 380;
    private static final int BLOCK_SIZE = 10;

    private JFrame mainWindow;
    private JPanel gameBoard;
    private JPanel scoreBar;
    private JLabel scoreBarLabel;
    private JPanel topList;



    int[] pozx = new int[125];
    int[] pozy = new int[125];
    Point[] p = new Point[125];
    Random r = new Random();

    

    JScrollPane scrollpane;


    public SnakeWindow() {
        initMainWindow();
        initGameBoard();
        initScoreBar();
        initMenuBar();
        initTopList();
    }

    private void initMainWindow() {
        mainWindow = new JFrame("Snake v0.8");
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
        mainWindow.addKeyListener(this);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(null);
    }

    private void initMenuBar() {

    }

    private void initGameBoard() {
        gameBoard = new JPanel();
        gameBoard.setLayout(null);
        gameBoard.setBounds(BLOCK_SIZE, BLOCK_SIZE, WINDOW_WIDTH, WINDOW_HEIGHT);
        gameBoard.setBackground(Color.LIGHT_GRAY);
    }

    private void initScoreBar() {
        scoreBar = new JPanel();
        scoreBar.setBounds(0, WINDOW_HEIGHT, WINDOW_WIDTH, SCOREBAR_HEIGHT);
        scoreBar.setBackground(Color.GRAY);
        scoreBarLabel = new JLabel();
        scoreBarLabel.setForeground(Color.BLACK);
        scoreBar.add(scoreBarLabel);
    }

    private void initTopList() {
        topList = new JPanel();
        topList.setBounds(0, 0, WINDOW_WIDTH, SCOREBAR_HEIGHT);
        topList.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
