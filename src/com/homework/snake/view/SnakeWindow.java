package com.homework.snake.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.homework.snake.SnakeGame;


public class SnakeWindow extends JFrame implements KeyListener {
    private static final int SCOREBAR_HEIGHT = 30;
    private static final int WINDOW_WIDTH = 506;
    private static final int WINDOW_HEIGHT = 380;

    private static final int BLOCK_SIZE = 10;

    private SnakeGame game;
    private JFrame mainWindow;
    private JPanel gameBoard;
    private JPanel scoreBar;
    private JLabel scoreBarLabel;
    private JPanel topList;
    private JButton foodButton;
    private JScrollPane scrollpane;


    JMenuBar menubar;
    JMenu jatek, beallitasok, segitseg;

    public SnakeWindow(SnakeGame game) {
        this.game = game;
        mainWindow = new JFrame("Snake v0.8");
        initMenuBar();
        initGameBoard();
        initScoreBar();
        //initTopList();
        initMainWindow();
    }

    private void initMainWindow() {
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainWindow.setResizable(false);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.addKeyListener(this);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setLayout(null);
        mainWindow.setVisible(true);
    }

    private void initMenuBar() {
        SnakeMenuBar menuBar = new SnakeMenuBar();
        menuBar.addListenerToNewGame(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //reset();
            }
        });
        menuBar.addListenerToShowTopList(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameBoard, scrollpane);
            }
        });
        menuBar.addListenerToExitGame(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mainWindow.setJMenuBar(menuBar);
    }

    private void initGameBoard() {
        gameBoard = new JPanel();
        gameBoard.setLayout(null);
        gameBoard.setBounds(BLOCK_SIZE, BLOCK_SIZE, game.getBoardWidth() * BLOCK_SIZE, game.getBoardHeight() * BLOCK_SIZE);
        gameBoard.setBackground(Color.LIGHT_GRAY);
        mainWindow.add(gameBoard);
        //createGameBoardFrame();
    }

    /*
    private void createGameBoardFrame() {
        int[][] frameSettings = {{0, 0, game.getBoardWidth() * BLOCK_SIZE + FRAME_SIZE * BLOCK_SIZE, BLOCK_SIZE}, {}, {}, {}};
        for (int[] setting : frameSettings) {
            JPanel panel = new JPanel();
            panel.setBounds(setting[0], setting[1], setting[2], setting[3]);
        }
        keret[0] = new JPanel();
        keret[0].setBounds(0, 0, palyasz, BLOCK_SIZE);
        keret[1] = new JPanel();
        keret[1].setBounds(0, 0, BLOCK_SIZE, palyam);
        keret[2] = new JPanel();
        keret[2].setBounds(0, palyam - BLOCK_SIZE, palyasz, BLOCK_SIZE);
        keret[3] = new JPanel();
        keret[3].setBounds(palyasz - BLOCK_SIZE, 0, BLOCK_SIZE, palyam);
    }
    */

    private void initScoreBar() {
        scoreBar = new JPanel();
        scoreBar.setBounds(0, (game.getBoardHeight() + 2) * BLOCK_SIZE, WINDOW_WIDTH, SCOREBAR_HEIGHT);
        scoreBar.setBackground(Color.GRAY);
        scoreBarLabel = new JLabel();
        scoreBarLabel.setForeground(Color.BLACK);
        scoreBar.add(scoreBarLabel);
        mainWindow.add(scoreBar);
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

    public static void main(String[] args) {
        new SnakeWindow(new SnakeGame(48, 28));
    }

}
