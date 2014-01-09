package com.homework.snake.view;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SnakeMenuBar extends JMenuBar {

    private JMenuItem newGame;
    private JMenuItem showTopList;
    private JMenuItem exitGame;
    private JMenuItem setHardMode;
    private JMenuItem setNormalMode;
    private JMenuItem setEasyMode;
    private JMenuItem showControls;
    private JMenuItem showCredits;

    public SnakeMenuBar() {
        super();
        initGameMenu();
        initSettingsMenu();
        initHelpMenu();
    }

    private void initGameMenu() {
        JMenu menu = new JMenu("Játék");
        newGame = createMenuItem(menu, "Új Játék (F2)");
        showTopList = createMenuItem(menu, "Toplista");
        exitGame = createMenuItem(menu, "Kilépés (ALT+F4)");
        this.add(menu);
    }

    private void initSettingsMenu() {
        JMenu menu = new JMenu("Beállítások");
        setHardMode = createMenuItem(menu, "Nehéz");
        setNormalMode = createMenuItem(menu, "Normál");
        setEasyMode = createMenuItem(menu, "Könnyű");
        this.add(menu);
    }

    private void initHelpMenu() {
        JMenu menu = new JMenu("Segítség");
        showControls = createMenuItem(menu, "Itányítás");
        showCredits = createMenuItem(menu, "Készítő");
        this.add(menu);
    }

    private JMenuItem createMenuItem(JMenu menu, String title) {
        JMenuItem menuItem = new JMenuItem(title);
        menu.add(menuItem);
        return menuItem;
    }

    public void addListenerToNewGame(ActionListener listener) {
        newGame.addActionListener(listener);
    }

    public void addListenerToShowTopList(ActionListener listener) {
        showTopList.addActionListener(listener);
    }

    public void addListenerToExitGame(ActionListener listener) {
        exitGame.addActionListener(listener);
    }

    public void addListenerToSetHardMode(ActionListener listener) {
        setHardMode.addActionListener(listener);
    }

    public void addListenerToSetNormalMode(ActionListener listener) {
        setNormalMode.addActionListener(listener);
    }

    public void addListenerToSetEasyMode(ActionListener listener) {
        setEasyMode.addActionListener(listener);
    }

    public void addListenerToShowControls(ActionListener listener) {
        showControls.addActionListener(listener);
    }

    public void addListenerToShowCredits(ActionListener listener) {
        showCredits.addActionListener(listener);
    }
}

