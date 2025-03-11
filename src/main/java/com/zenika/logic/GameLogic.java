package com.zenika.logic;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class GameLogic {
    private int ballX = 400, ballY = 70, ballDX = 2, ballDY = 2;
    private int paddleX = 350;
    private final int paddleY = 550;
    private final int paddleWidth = 100;
    private final int paddleHeight = 10;
    private final int panelWidth;
    private final int panelHeight;
    private final List<Rectangle> bricks;
    private int score;

    public GameLogic(int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.bricks = new ArrayList<>();
        this.score = 0;
        initializeBricks();
    }

    private void initializeBricks() {
        int brickWidth = 60;
        int brickHeight = 20;
        for (int i = 0; i < panelWidth / brickWidth; i += 2) {
            bricks.add(new Rectangle(i * brickWidth, 50, brickWidth, brickHeight));
        }
    }

    public void update() throws EndGameException {
        ballX += ballDX;
        ballY += ballDY;

        if (ballX <= 0 || ballX >= panelWidth - 20) {
            ballDX = -ballDX;
        }
        if (ballY <= 0) {
            ballDY = -ballDY;
        }

        if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(paddleX, paddleY, paddleWidth, paddleHeight))) {
            ballDY = -ballDY;
        }

        for (int i = 0; i < bricks.size(); i++) {
            if (new Rectangle(ballX, ballY, 20, 20).intersects(bricks.get(i))) {
                bricks.remove(i);
                ballDY = -ballDY;
                score += 10;
                break;
            }
        }

        if (ballY >= panelHeight - 20) {
            looseGame();
        }
    }

    public List<Rectangle> getBricks() {
        return bricks;
    }

    public int getScore() {
        return score;
    }

    public void movePaddleLeft() {
        if (paddleX > 0) {
            paddleX -= 20;
        }
    }

    public void movePaddleRight() {
        if (paddleX < panelWidth - paddleWidth) {
            paddleX += 20;
        }
    }

    public void looseGame() throws EndGameException {
        throw new EndGameException();
    }

    public int getBallX() {
        return ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public int getPaddleX() {
        return paddleX;
    }

    public int getPaddleY() {
        return paddleY;
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    public int getPaddleHeight() {
        return paddleHeight;
    }

    public void setPaddleX(int i) {
        this.paddleX = i;
    }

    public void setBallY(int i) {
        this.ballY = i;
    }

    public void setBallDX(int i) {
        this.ballDX = i;
    }

    public void setBallDY(int i) {
        this.ballDY = i;
    }

    public void setBallX(int i) {
        this.ballX = i;
    }

    public int getBallDY() {
        return this.ballDY;
    }
}