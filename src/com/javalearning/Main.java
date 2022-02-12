package com.javalearning;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame window = new JFrame();
        //add x button to close the game
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set to unable resize window
        window.setResizable(false);
        window.setTitle("2D Adventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // this can let window to be sized to fit the preferred size and layouts of its subcomponents(GamePanel)
        window.pack();

        // Not specify the location of the window, the window will be displayed at the center of the screen
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}
