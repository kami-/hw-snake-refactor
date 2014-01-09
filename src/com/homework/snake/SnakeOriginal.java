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
    * Az �rt�kek alaphelyzetbe �ll�t�sa �s a toplist�t tartalmaz� f�jl
    * megnyit�sa
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
    * A mozgat�s elind�t�s�nak f�ggv�nye.
    */
    public void startGame() {
        fut = true;
        (new Thread(this)).start();
    }

    /*
    * A Snake() f�ggv�ny. Ez a program lelke. Itt t�rt�nik az ablak
    * l�trehoz�sa, az ablak minden elem�nyek hozz�ad�sa, az �rt�kek
    * inicializ�l�sa, az els� snake l�trehoz�sa, valamint itt h�odik meg a
    * "mozgat�" f�ggv�ny is
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
    * Ez a men�t l�trehoz� f�ggv�ny. L�trehozza a men�ket, hozz�adja a
    * funkci�ikat, �s a k�perny�re viszi azokat
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
                JOptionPane.showMessageDialog(gameBoard, "K�sz�t�: K�rlek Refaktor�lj\n" + "Programn�v: Snake\n" + "Verzi�sz�m: v0.7");
            }
        });
        menuBar.addListenerToShowControls(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(gameBoard, "Ir�ny�t�s a kurzor seg�ts�g�vel:\n" + "-Fel ny�l: a k�gy� felfele mozog\n"
                        + "-Le ny�l: a k�gy� lefele mozog\n" + "-Jobbra ny�l: a k�gy� jobbra mozog\n" + "-Balra ny�l: a k�gy� balra mozog\n");
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
    * Az �jraind�t� f�ggv�ny. Ennek megh�v�sakor az �rt�k �jra alap�llapotba
    * ker�lnek, ami eddig az ablakon volt az elt�nik, a mozgat�s meg�ll, a
    * keret, az els� snake �s a pontsz�m �jra kirajzoldik, �s megh�v�dik a
    * mozgat� f�ggv�ny
    */
    void reset() {
        // Az �rt�kek kezdeti helyzetbe �ll�t�sa
        initGame();

        // Ha az el�z� j�t�kban meghalt a k�gy�, akkor a j�t�k v�ge kijelz�
        // t�rl�se az ablakb�l
        if (gameover == true) {
            mainWindow.remove(top);
        }

        initSnake();

        // A p�lya hozz�ad�sa az ablakhoz, annak �jrarajzol�sa �s a pontsz�m
        // ki�r�sa
        mainWindow.repaint();
        mainWindow.setVisible(true);
        scoreBarLabel.setText("Pontsz�m: " + pontok);

        // A mozgat�s elind�t�sa
        startGame();
    }

    /*
    * Az els� snake l�trehoz�sa �s a p�ly�ra rajzol�sa.
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
    * A f�jlmegnyit� f�ggv�ny megnyitja a "toplist.ser" nev� f�jlt, mely a
    * toplista szerepl�it tartalmazza �s ezeket a lista nev� ArrayListben
    * elt�rolja (deszerializ�l�s)
    */
    @SuppressWarnings("unchecked")
    void fajlmegnyitas() {
        // A f�jl megnyit�sa
        try {
            InputStream file = new FileInputStream("toplista.ser");
            InputStream buffer = new BufferedInputStream(file);
            ObjectInput in;
            in = new ObjectInputStream(buffer);

            // A f�jl tartalm�nak bem�sol�sa a lista ArrayListbe
            lista = (ArrayList<Toplist>) in.readObject();

            // A f�jl bez�r�sa
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
    * A f�jlba�r� f�ggv�ny a "toplista.ser" nev� f�jlba be�rja a legfrissebb
    * toplista szerepl�it (szerializ�l�s)
    */
    void fajlbairas() {
        // A f�jl megnyit�sa
        try {
            OutputStream file = new FileOutputStream("toplista.ser");

            OutputStream buffer = new BufferedOutputStream(file);
            ObjectOutput out;
            out = new ObjectOutputStream(buffer);

            // A lista ArrayList f�jlba �r�sa
            out.writeObject(lista);

            // A f�jl bez�r�sa
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * Ez a f�ggv�ny a j�t�k v�g�t vizsg�lja. Megn�zi, hogy a k�gy� hal�la ut�n
    * felker�l-e a toplist�ra a j�t�kos az el�rt eredm�ny�vel. Ha igen akkor
    * bek�ri a nev�t, �s friss�ti a toplist�t. Ha nem akkor egy j�t�k v�ge
    * k�perny�t rajzol ki. A v�g�n pedig szerializ�l.
    */
    void toplistabatesz() {
        // A p�lya t�rl�se a k�perny�r�l.
        mainWindow.remove(gameBoard);

        // Ha az el�rt eredm�ny jobb az eddigi legkisebb eredm�nyn�l
        if (pontok > lista.get(9).getpont()) {
            // Egy ArrayList l�trehoz�sa, mely a megadott nevet t�rolja
            final ArrayList<String> holder = new ArrayList<String>();

            // A ki�r�sok �s a sz�vegmez� l�trehoz�sa
            JLabel nyert1 = new JLabel("A j�t�knak v�ge!");
            JLabel nyert2 = new JLabel("Gratul�lok! Felker�lt�l a toplist�ra. K�rlek add meg a neved (max 10 bet�):");
            final JTextField newnev = new JTextField(10);

            // Ezek hozz�ad�sa a top panelhez
            top.removeAll();
            top.add(nyert1);
            top.add(nyert2);
            top.add(newnev);

            // A sz�vegmez� tartalm�nak hozz�sad�sa a holderhez
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

            // A top panel hozz�ad�sa az ablakhoz, �s az ablak �jrarajzol�sa
            mainWindow.add(top, BorderLayout.CENTER);
            mainWindow.setVisible(true);
            mainWindow.repaint();

            // V�rakoz�s a sz�vegez� kit�lt�s�ig
            synchronized (holder) {
                while (holder.isEmpty())
                    try {
                        holder.wait();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
            }

            // A lista utols� elem�nek kicser�l�se az �j listaelemmel �s a lista
            // sorbarendez�se
            Comp comp = new Comp();
            lista.remove(9);
            lista.add(new Toplist(holder.remove(0), pontok));
            Collections.sort(lista, comp);

            // A toplista friss�t�se, �s kirajzol�sa az ablakra
            toplistafrissites();
            top.removeAll();
            top.add(scrollpane);
            mainWindow.repaint();
            // Ha az eredm�ny nincs bent a legjobb 10-be
        } else {
            // A kiir�sok l�trehoz�sa �s hozz�ad�sa az ablakhoz
            JLabel nemnyert1 = new JLabel("A j�t�knak v�ge!");
            JLabel nemnyert2 = new JLabel("Sajnos nem ker�lt be az eredm�nyed a legjobb 10-be. Pr�b�lkozz �jra (F2).");
            nemnyert1.setForeground(Color.BLACK);
            nemnyert2.setForeground(Color.BLACK);
            top.removeAll();
            top.add(nemnyert1);
            top.add(nemnyert2);

            // A toplista friss�t�se �s a top panel hozz�ad�sa az ablakhoz
            toplistafrissites();
            mainWindow.add(top, BorderLayout.CENTER);
            mainWindow.setVisible(true);
            mainWindow.repaint();
        }
        // Szerializ�l�s
        fajlbairas();
    }

    /*
    * Ez a f�ggv�ny a toplist�t egy t�bl�zatba rakja
    */
    @SuppressWarnings({"rawtypes", "unchecked"})
    void toplistafrissites() {
        // A t�bl�zat fejl�c�nek l�trehoz�sa
        Vector colnames = new Vector();
        colnames.add("N�v");
        colnames.add("Pont");

        // A t�bl�zat l�trehoz�sa egy ScrollPane-ben
        DefaultTableModel tablazatmodell = new DefaultTableModel(colnames, 0);
        JTable tablazat = new JTable(tablazatmodell);
        scrollpane = new JScrollPane(tablazat);

        // A t�bl�zat felt�lt�se a lista elemeivel
        for (Toplist i : lista) {
            String[] row = {i.getnev(), i.getstrpont()};
            tablazatmodell.addRow(row);
        }

    }

    /*
    * A mozgat� f�ggv�ny megv�ltoztatja a k�gy� poz�ci�j�t a megadott ir�nyba,
    * �s k�zben vizsg�lja, hogy a k�gy� nem �tk�z�tt-e falnak vagy mag�nak,
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
        scoreBarLabel.setText("Pontsz�m: " + pontok);
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
    * A billenty� lenyom�s�t �rz�kel� f�ggv�ny, mely megfelel� gomb lenyom�s�ra
    * a megfelel� m�veletet hajtja v�gre
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
    * A run met�dus hivja meg megadott id�k�z�nk�nt a mozgat� f�ggv�nyt
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
