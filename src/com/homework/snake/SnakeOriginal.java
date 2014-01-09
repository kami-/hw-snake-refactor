package com.homework.snake;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
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
import com.homework.snake.exceptions.SnakeAteItselfException;
import com.homework.snake.view.SnakeMenuBar;

public class SnakeOriginal extends JFrame implements KeyListener, Runnable {
    /**
     * 
     */
    private static final int SCOREBAR_HEIGHT = 30;
    private static final int WINDOW_WIDTH = 506;
    private static final int WINDOW_HEIGHT = 380;
    private static final int BOARD_WIDTH = 48;
    private static final int BOARD_HEIGHT = 28;
    private static final int BLOCK_SIZE = 10;

    private Snake snake;
    private Point foodPoint;
    private JButton foodButton;
    private boolean canChangeDirection;
    private List<JButton> snakeComponent = new ArrayList<>();
    private SnakeMenuBar menuBar;
    private JPanel gameBoard;
    
    private static final long serialVersionUID = 1L;
    int palyasz = 50 * BLOCK_SIZE, palyam = 30 * BLOCK_SIZE;
    int sebesseg, pontok;
    boolean fut, gameover;

    Random r = new Random();


    private JFrame mainWindow;
    private JPanel scoreBar;
    private JLabel scoreBarLabel;
    JPanel top;

    JScrollPane scrollpane;

    ArrayList<Toplist> lista = new ArrayList<Toplist>();
    {
        for (int i = 0; i < 10; i++) {
            lista.add(new Toplist("", 0));
        }
    }

    /*
    * Az értékek alaphelyzetbe állítása és a toplistát tartalmazó fájl
    * megnyitása
    */
    public void initGame() {
        sebesseg = 70;
        pontok = 0;
        fut = false;
        gameover = false;
        addFood();
        initSnake();
    }

    /*
    * A mozgatás elindításának függvénye.
    */
    public void startGame() {
        fut = true;
        (new Thread(this)).start();
    }

    /*
    * A Snake() függvény. Ez a program lelke. Itt történik az ablak
    * létrehozása, az ablak minden elemények hozzáadása, az értékek
    * inicializálása, az elsõ snake létrehozása, valamint itt híodik meg a
    * "mozgató" függvény is
    */
    SnakeOriginal() {
        initWindow();
        initGame();
        startGame();
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
        top = new JPanel();
        top.setBounds(0, 0, palyasz, palyam);
        top.setBackground(Color.LIGHT_GRAY);
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

    /*
    * Ez a menüt létrehozõ függvény. Létrehozza a menüket, hozzáadja a
    * funkcióikat, és a képernyõre viszi azokat
    */
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
                JOptionPane.showMessageDialog(gameBoard, scrollpane);
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
                sebesseg = 50;
            }
        });
        menuBar.addListenerToSetNormalMode(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sebesseg = 70;
            }
        });
        menuBar.addListenerToSetEasyMode(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sebesseg = 90;
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
        scoreBar.add(scoreBarLabel);
        mainWindow.add(scoreBar);
    }

    /*
    * Az újraindító függvény. Ennek meghívásakor az érték újra alapállapotba
    * kerülnek, ami eddig az ablakon volt az eltûnik, a mozgatás megáll, a
    * keret, az elsõ snake és a pontszám újra kirajzoldik, és meghívódik a
    * mozgató függvény
    */
    void reset() {
        // Az értékek kezdeti helyzetbe állítása
        initGame();

        // Ha az elõzõ játékban meghalt a kígyó, akkor a játék vége kijelzõ
        // törlése az ablakból
        if (gameover == true) {
            mainWindow.remove(top);
        }

        initSnake();

        // A pálya hozzáadása az ablakhoz, annak újrarajzolása és a pontszám
        // kiírása
        mainWindow.repaint();
        mainWindow.setVisible(true);
        scoreBarLabel.setText("Pontszám: " + pontok);

        // A mozgatás elindítása
        startGame();
    }

    /*
    * Az elsõ snake létrehozása és a pályára rajzolása.
    */
    void initSnake() {
        snake = new Snake(3, new Point(24, 14), new Point(1, 0));
        drawSnake();
    }

    private void drawSnake() {
        List<Point> snakeParts = snake.getParts();
        extendKocka(snakeParts.size());
        for (int i = 0; i < snakeParts.size(); i++) {
            updateButton(snakeComponent.get(i), snakeParts.get(i), Color.BLACK);
        }
    }

    private void updateButton(JButton button, Point point, Color color) {
        button.setEnabled(false);
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
        foodPoint = new Point(r.nextInt(BOARD_WIDTH), r.nextInt(BOARD_HEIGHT));
        updateButton(foodButton, foodPoint, Color.BLACK);
    }

    /*
    * A fájlmegnyitó függvény megnyitja a "toplist.ser" nevû fájlt, mely a
    * toplista szereplõit tartalmazza és ezeket a lista nevû ArrayListben
    * eltárolja (deszerializálás)
    */
    @SuppressWarnings("unchecked")
    void fajlmegnyitas() {
        // A fájl megnyitása
        try {
            InputStream file = new FileInputStream("toplista.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput in;
            in = new ObjectInputStream(buffer);

            // A fájl tartalmának bemásolása a lista ArrayListbe
            lista = (ArrayList<Toplist>) in.readObject();

            // A fájl bezárása
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * A fájlbaíró függvény a "toplista.ser" nevû fájlba beírja a legfrissebb
    * toplista szereplõit (szerializálás)
    */
    void fajlbairas() {
        // A fájl megnyitása
        try {
            OutputStream file = new FileOutputStream("toplista.ser");

            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput out;
            out = new ObjectOutputStream(buffer);

            // A lista ArrayList fájlba írása
            out.writeObject(lista);

            // A fájl bezárása
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * Ez a függvény a játék végét vizsgálja. Megnézi, hogy a kígyó halála után
    * felkerül-e a toplistára a játékos az elért eredményével. Ha igen akkor
    * bekéri a nevét, és frissíti a toplistát. Ha nem akkor egy játék vége
    * képernyõt rajzol ki. A végén pedig szerializál.
    */
    void toplistabatesz() {
        // A pálya törlése a képernyõrõl.
        mainWindow.remove(gameBoard);

        // Ha az elért eredmény jobb az eddigi legkisebb eredménynél
        if (pontok > lista.get(9).getpont()) {
            // Egy ArrayList létrehozása, mely a megadott nevet tárolja
            final ArrayList<String> holder = new ArrayList<String>();

            // A kiírások és a szövegmezõ létrehozása
            JLabel nyert1 = new JLabel("A játéknak vége!");
            JLabel nyert2 = new JLabel("Gratulálok! Felkerültél a toplistára. Kérlek add meg a neved (max 10 betû):");
            final JTextField newnev = new JTextField(10);

            // Ezek hozzáadása a top panelhez
            top.removeAll();
            top.add(nyert1);
            top.add(nyert2);
            top.add(newnev);

            // A szövegmezõ tartalmának hozzásadása a holderhez
            newnev.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    synchronized (holder) {
                        holder.add(newnev.getText());
                        holder.notify();
                    }
                    mainWindow.dispose();
                }
            });

            // A top panel hozzáadása az ablakhoz, és az ablak újrarajzolása
            mainWindow.add(top, BorderLayout.CENTER);
            mainWindow.setVisible(true);
            mainWindow.repaint();

            // Várakozás a szövegezõ kitöltéséig
            synchronized (holder) {
                while (holder.isEmpty())
                    try {
                        holder.wait();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
            }

            // A lista utolsó elemének kicserélése az új listaelemmel és a lista
            // sorbarendezése
            Comp comp = new Comp();
            lista.remove(9);
            lista.add(new Toplist(holder.remove(0), pontok));
            Collections.sort(lista, comp);

            // A toplista frissítése, és kirajzolása az ablakra
            toplistafrissites();
            top.removeAll();
            top.add(scrollpane);
            mainWindow.repaint();
            // Ha az eredmény nincs bent a legjobb 10-be
        } else {
            // A kiirások létrehozása és hozzáadása az ablakhoz
            JLabel nemnyert1 = new JLabel("A játéknak vége!");
            JLabel nemnyert2 = new JLabel("Sajnos nem került be az eredményed a legjobb 10-be. Próbálkozz újra (F2).");
            nemnyert1.setForeground(Color.BLACK);
            nemnyert2.setForeground(Color.BLACK);
            top.removeAll();
            top.add(nemnyert1);
            top.add(nemnyert2);

            // A toplista frissítése és a top panel hozzáadása az ablakhoz
            toplistafrissites();
            mainWindow.add(top, BorderLayout.CENTER);
            mainWindow.setVisible(true);
            mainWindow.repaint();
        }
        // Szerializálás
        fajlbairas();
    }

    /*
    * Ez a függvény a toplistát egy táblázatba rakja
    */
    @SuppressWarnings({"rawtypes", "unchecked"})
    void toplistafrissites() {
        // A táblázat fejlécének létrehozása
        Vector colnames = new Vector();
        colnames.add("Név");
        colnames.add("Pont");

        // A táblázat létrehozása egy ScrollPane-ben
        DefaultTableModel tablazatmodell = new DefaultTableModel(colnames, 0);
        JTable tablazat = new JTable(tablazatmodell);
        scrollpane = new JScrollPane(tablazat);

        // A táblázat feltöltése a lista elemeivel
        for (Toplist i : lista) {
            String[] row = {i.getnev(), i.getstrpont()};
            tablazatmodell.addRow(row);
        }

    }

    /*
    * A mozgató függvény megváltoztatja a kígyó pozícióját a megadott irányba,
    * és közben vizsgálja, hogy a kígyó nem ütközött-e falnak vagy magának,
    * illetve azt, hogy evett-e
    */
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
        pontok = pontok + 5;
        scoreBarLabel.setText("Pontszám: " + pontok);
    }

    private void endGame() {
        fut = false;
        gameover = true;
        toplistabatesz();
    }

    private boolean hasLeftBorad() {
        return snake.getHead().x >= BOARD_WIDTH || snake.getHead().x < 0 || snake.getHead().y >= BOARD_HEIGHT || snake.getHead().y < 0;
    }

    /*
    * A billentyû lenyomását érzékelõ függvény, mely megfelelõ gomb lenyomására
    * a megfelelõ mûveletet hajtja végre
    */
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
            if (!isOppositeHeading(x, y)) {
                setSnakeHeading(x, y);
            }
            canChangeDirection = false;
        }
    }

    private boolean isOppositeHeading(int x, int y) {
        return snake.getHeading().x == -x && snake.getHeading().y == -y;
    }

    private void setSnakeHeading(int x, int y) {
        snake.setHeading(new Point(x, y));
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
        while (fut) {
            mozgat();
            try {
                Thread.sleep(sebesseg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
