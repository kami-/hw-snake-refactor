package com.homework.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.homework.snake.domain.Snake;
import com.homework.snake.domain.TopListRepository;
import com.homework.snake.exceptions.SnakeAteItselfException;
import com.homework.snake.view.SnakeMenuBar;

public class SnakeOriginal extends JFrame implements KeyListener, Runnable {
    /**
     * 
     */
    private static final int BOARD_WIDTH = 48;
    private static final int BOARD_HEIGHT = 28;

    private static final int SCOREBAR_HEIGHT = 30;
    private static final int WINDOW_WIDTH = 506;
    private static final int WINDOW_HEIGHT = 380;

    private static final int BLOCK_SIZE = 10;

    private Snake snake;
    private Point foodPoint;
    private JButton foodButton;
    private boolean canChangeDirection;
    private List<JButton> snakeComponent = new ArrayList<>();
    private SnakeMenuBar menuBar;
    private JPanel gameBoard;
    private int score;
    
    private static final long serialVersionUID = 1L;
    private int updateDelay;
    private boolean isRunning;
    private boolean isGameOver;

    Random r = new Random();

    private JFrame mainWindow;
    private JPanel scoreBar;
    private JLabel scoreBarLabel;
    private JPanel topList;

    private JScrollPane topListScrollPane;

    private TopListRepository topListRepo;

    SnakeOriginal() {
        initWindow();
        initGame();
        topListRepo = new TopListRepository();
        startGame();
        (new Thread(this)).start();
    }

    public void initGame() {
        updateDelay = 70;
        score = 0;
        isRunning = false;
        isGameOver = false;
        addFood();
        initSnake();
    }

    public void startGame() {
        isRunning = true;

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
        topList = new JPanel();
        topList.setBounds(0, 0, (BOARD_WIDTH + 2) * BLOCK_SIZE, (BOARD_HEIGHT + 2) * BLOCK_SIZE);
        topList.setBackground(Color.LIGHT_GRAY);
        topList.setVisible(false);
        mainWindow.add(topList);
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

    private void initGameBoard() {
        gameBoard = new JPanel();
        gameBoard.setLayout(null);
        gameBoard.setBounds(BLOCK_SIZE, BLOCK_SIZE, BOARD_WIDTH * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE);
        gameBoard.setBackground(Color.LIGHT_GRAY);
        mainWindow.add(gameBoard);
    }

    public void initMenuBar() {
        menuBar = new SnakeMenuBar();
        menuBar.addListenerToNewGame(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        menuBar.addListenerToShowTopList(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameBoard, topListScrollPane);
            }
        });
        menuBar.addListenerToExitGame(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuBar.addListenerToSetHardMode(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDelay = 50;
            }
        });
        menuBar.addListenerToSetNormalMode(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDelay = 70;
            }
        });
        menuBar.addListenerToSetEasyMode(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDelay = 90;
            }
        });
        menuBar.addListenerToShowCredits(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameBoard, "Készítõ: Kérlek Refaktorálj\n" + "Programnév: Snake\n" + "Verziószám: v0.7");
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

    private void initScoreBar() {
        scoreBar = new JPanel();
        scoreBar.setBounds(0, (BOARD_HEIGHT + 2) * BLOCK_SIZE, WINDOW_WIDTH, SCOREBAR_HEIGHT);
        scoreBar.setBackground(Color.GRAY);
        scoreBarLabel = new JLabel();
        scoreBarLabel.setForeground(Color.BLACK);
        scoreBarLabel.setText("Pontszám: " + score);
        scoreBar.add(scoreBarLabel);
        mainWindow.add(scoreBar);
    }

    void reset() {
        clearFood();
        clearSnakeComponent();
        if (isGameOver == true) {
            topList.setVisible(false);
        }
        initGame();
        gameBoard.setVisible(true);
        scoreBarLabel.setText("Pontszám: " + score);
        startGame();
    }

    void initSnake() {
        snake = new Snake(3, new Point(24, 14), new Point(1, 0));
        drawSnake();
    }

    void clearSnakeComponent() {
        for (JButton snakeButton : snakeComponent) {
            snakeButton.setVisible(false);
        }
        snakeComponent.clear();
    }

    void clearFood() {
        foodButton.setVisible(false);
    }

    private void drawSnake() {
        List<Point> snakeParts = snake.getParts();
        extendKocka(snakeParts.size());
        for (int i = 0; i < snakeParts.size(); i++) {
            updateButton(snakeComponent.get(i), snakeParts.get(i), Color.BLACK);
            //System.out.println("size: " + snakeParts.size() + ", index: " + i);
        }
    }

    private void updateButton(JButton button, Point point, Color color) {
        button.setEnabled(false);
        button.setVisible(true);
        button.setBounds(point.x * BLOCK_SIZE, point.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        button.setBackground(color);
    }

    private void extendKocka(int snakeSize) {
        while (snakeComponent.size() < snakeSize) {
            JButton button = new JButton();
            snakeComponent.add(button);
            gameBoard.add(button);
        }
    }

    void addFood() {
        if (foodButton == null) {
            foodButton = new JButton();
            gameBoard.add(foodButton);
        }
        foodButton.setVisible(true);
        foodPoint = new Point(r.nextInt(BOARD_WIDTH), r.nextInt(BOARD_HEIGHT));
        updateButton(foodButton, foodPoint, Color.BLACK);
    }

    void toplistabatesz() {
        gameBoard.setVisible(false);
        topList.setVisible(true);

        if (topListRepo.canAddScore(score)) {
            addLabelsToToplist("A játéknak vége!", "Gratulálok! Felkerültél a toplistára. Kérlek add meg a neved (max 10 betû):");
            final JTextField playerName = new JTextField(10);
            topList.add(playerName);

            playerName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    topListRepo.addNewEntry(new TopListEntry(playerName.getText(), score));
                    topListRepo.writeFile();
                    drawTopListScrollPanel();
                    showTopListScrollPane();
                    mainWindow.setVisible(true);
                    mainWindow.repaint();
                }
            });
        } else {
            addLabelsToToplist("A játéknak vége!", "Sajnos nem került be az eredményed a legjobb 10-be. Próbálkozz újra (F2).");
            drawTopListScrollPanel();
            mainWindow.add(topList, BorderLayout.CENTER);
        }
    }

    private void showTopListScrollPane() {
        topList.removeAll();
        topList.add(topListScrollPane);
        mainWindow.add(topList, BorderLayout.CENTER);
    }

    private void addLabelsToToplist(String title, String description) {
        JLabel titleLabel = new JLabel(title);
        JLabel descriptionLabel = new JLabel(description);
        titleLabel.setForeground(Color.BLACK);
        descriptionLabel.setForeground(Color.BLACK);
        topList.removeAll();
        topList.add(titleLabel);
        topList.add(descriptionLabel);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    void drawTopListScrollPanel() {
        Vector columnHeaders = new Vector();
        columnHeaders.add("Név");
        columnHeaders.add("Pont");

        DefaultTableModel entryTable = new DefaultTableModel(columnHeaders, 0);
        JTable tablazat = new JTable(entryTable);
        topListScrollPane = new JScrollPane(tablazat);

        for (TopListEntry entry : topListRepo.getTopList()) {
            String[] row = {entry.getPlayerName(), Integer.toString(entry.getScore())};
            entryTable.addRow(row);
        }
    }

    void mozgat() {
        try {
            snake.move();
            if (hasLeftBorad()) {
                endGame();
            } else {
                if (hasFoundFood()) {
                    snake.eat();
                    updatePoints();
                    addFood();
                }
                drawSnake();
            }
            canChangeDirection = true;
        } catch (SnakeAteItselfException e) {
            endGame();
        }

        gameBoard.repaint();
        mainWindow.setVisible(true);
    }

    private boolean hasFoundFood() {
        return foodPoint.equals(snake.getHead());
    }

    private void updatePoints() {
        score = score + 5;
        scoreBarLabel.setText("Pontszám: " + score);
    }

    private void endGame() {
        isRunning = false;
        isGameOver = true;
        toplistabatesz();
    }

    private boolean hasLeftBorad() {
        return snake.getHead().x >= BOARD_WIDTH || snake.getHead().x < 0 || snake.getHead().y >= BOARD_HEIGHT || snake.getHead().y < 0;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 113) {
            reset();
            return;
        }
        if (canChangeDirection) {
            int x = 0;
            int y = 0;
            if (e.getKeyCode() == 37) {
                x = -1;
            } else if (e.getKeyCode() == 38) {
                y = -1;
            } else if (e.getKeyCode() == 39) {
                x = 1;
            } else if (e.getKeyCode() == 40) {
                y = 1;
            }
            if (!isOppositeDirection(x, y)) {
                setSnakeDirection(x, y);
            }
            canChangeDirection = false;
        }
    }

    private boolean isOppositeDirection(int x, int y) {
        return snake.getDirection().x == -x && snake.getDirection().y == -y;
    }

    private void setSnakeDirection(int x, int y) {
        snake.setDirection(new Point(x, y));
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /*
    * A run metódus hivja meg megadott idõközönként a mozgató függvényt
    */
    @Override
    public void run() {
        while (true) {
            while (isRunning) {
                mozgat();
                try {
                    Thread.sleep(updateDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
