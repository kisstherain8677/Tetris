package Tetris;

import javax.swing.*;

public class Tetris extends JFrame {


    Tetris() {
        setSize(405, 550);
        setTitle("俄罗斯方块");
        WorldMap worldMap = new WorldMap();
        add(worldMap);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        addKeyListener(worldMap.new MyKeyListener());
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
        new Tetris();

    }
}
