package com.zenika.ui;

import com.zenika.logic.BricksLogic;
import com.zenika.logic.EndGameException;
import com.zenika.logic.GameLogic;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.awt.Rectangle;

public class GamePanel extends JPanel implements ActionListener {
    private final GameLogic gameLogic;
    private final BricksLogic bricksLogic;

    public GamePanel() {
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        bricksLogic = new BricksLogic(800);
        gameLogic = new GameLogic(800, 600, bricksLogic);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    gameLogic.movePaddleLeft();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    gameLogic.movePaddleRight();
                }
            }
        });
        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillOval(gameLogic.getBallX(), gameLogic.getBallY(), 20, 20);
        g2d.fillRect(gameLogic.getPaddleX(), gameLogic.getPaddleY(), gameLogic.getPaddleWidth(), gameLogic.getPaddleHeight());

        g2d.setColor(Color.RED);
        for (Rectangle brick : bricksLogic.getBricks()) {
            g2d.fillRect(brick.x, brick.y, brick.width, brick.height);
        }

        g2d.setColor(Color.WHITE);
        g2d.drawString("Score: " + gameLogic.getScore(), 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            gameLogic.update();
        } catch (EndGameException ex) {
            System.exit(0);
        }
        repaint();
    }
}