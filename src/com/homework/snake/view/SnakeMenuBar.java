package com.homework.snake.view;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SnakeMenuBar extends JMenuBar {

    public SnakeMenuBar(ActionListener[] gameListeners, ActionListener[] settingsListeners, ActionListener[] helpListeners) {
        super();
        initGameMenu(gameListeners);
        initSettingsMenu(settingsListeners);
        initHelpMenu(helpListeners);
    }

    private void initGameMenu(ActionListener[] gameListeners) {
        String[] titles = {"�j J�t�k (F2)", "Toplista", "Kil�p�s (ALT+F4)"};
        JMenu menu = new JMenu("J�t�k");
        addMenu(menu, titles, gameListeners);
    }

    private void initSettingsMenu(ActionListener[] settingsListeners) {
        String[] titles = {"Neh�z", "Norm�l", "K�nny�"};
        JMenu menu = new JMenu("Be�ll�t�sok");
        addMenu(menu, titles, settingsListeners);
    }

    private void initHelpMenu(ActionListener[] helpListeners) {
        String[] titles = {"Ir�ny�t�s", "K�sz�t�"};
        JMenu menu = new JMenu("Seg�ts�g");
        addMenu(menu, titles, helpListeners);
    }

    private void addMenu(JMenu menu, String[] titles, ActionListener[] listeners) {
        for (int i = 0; i < listeners.length; i++) {
            createMenuItem(menu, titles[i], listeners[i]);
            if (i < listeners.length) {
                menu.addSeparator();
            }
        }
        add(menu);
    }

    private void createMenuItem(JMenu menu, String title, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(listener);
        menu.add(menuItem);
    }
}

