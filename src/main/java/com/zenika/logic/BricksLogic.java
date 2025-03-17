package com.zenika.logic;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class BricksLogic {
    private final List<Rectangle> bricks;
    private final int panelWidth;

    public BricksLogic(int panelWidth) {
        this.panelWidth = panelWidth;
        this.bricks = new ArrayList<>();
        initializeBricks();
    }

    private void initializeBricks() {
        int brickWidth = 60;
        int brickHeight = 20;
        for (int i = 0; i < panelWidth / brickWidth; i += 2) {
            bricks.add(new Rectangle(i * brickWidth, 50, brickWidth, brickHeight));
        }
    }

    public List<Rectangle> getBricks() {
        return bricks;
    }

    public boolean checkCollision(Rectangle ball) {
        for (int i = 0; i < bricks.size(); i++) {
            if (ball.intersects(bricks.get(i))) {
                bricks.remove(i);
                return true;
            }
        }
        return false;
    }
}