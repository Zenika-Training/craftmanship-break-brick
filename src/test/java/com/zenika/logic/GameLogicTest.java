package com.zenika.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameLogicTest {

    // Constants
    private final int panelWidth = 800;
    private final int panelHeight = 600;

    // Tested component
    private final GameLogic gameLogic = new GameLogic(panelWidth, panelHeight);

    @Test
    @DisplayName("Should move paddle left")
    void should_move_paddle_left() {
        // Given
        var paddleX = gameLogic.getPaddleX();

        // When
        gameLogic.movePaddleLeft();

        // Then
        assert (gameLogic.getPaddleX() < paddleX);
    }

    @Test
    @DisplayName("Should move paddle right")
    void should_move_paddle_right() {
        // Given
        var paddleX = gameLogic.getPaddleX();

        // When
        gameLogic.movePaddleRight();

        // Then
        assert (gameLogic.getPaddleX() > paddleX);
    }

    @Test
    @DisplayName("Should stop when paddle hits the left wall")
    void should_stop_when_paddle_hits_the_left_wall() {
        // Given
        gameLogic.setPaddleX(0);

        // When
        gameLogic.movePaddleLeft();

        // Then
        assert (gameLogic.getPaddleX() == 0);
    }

    @Test
    @DisplayName("Should stop when paddle hits the right wall")
    void should_stop_when_paddle_hits_the_right_wall() {
        // Given
        gameLogic.setPaddleX(panelWidth);

        // When
        gameLogic.movePaddleRight();

        // Then
        assert (gameLogic.getPaddleX() == panelWidth);
    }

    @Test
    @DisplayName("Should stop program when ball hits the bottom wall")
    void should_stop_program_when_ball_hits_the_bottom_wall() {
        // Given
        gameLogic.setBallY(panelHeight);
        gameLogic.setBallDY(2);

        // When & Then
        assertThrows(EndGameException.class, gameLogic::update);
    }

    @Test
    @DisplayName("Should initialize bricks correctly")
    void should_initialize_bricks_correctly() {
        // Given & When & Then
        assertEquals(7, gameLogic.getBricks().size());
    }

    @Test
    @DisplayName("Should remove brick when ball hits")
    void should_remove_brick_when_ball_hits() throws EndGameException {
        // Given
        gameLogic.setBallX(0);
        gameLogic.setBallY(50);
        gameLogic.setBallDY(2);
        int initialBrickCount = gameLogic.getBricks().size();

        // When
        gameLogic.update();

        // Then
        assertEquals(initialBrickCount - 1, gameLogic.getBricks().size());
    }

    @Test
    @DisplayName("Should bounce ball off brick")
    void should_bounce_ball_off_brick() throws EndGameException {
        // Given
        gameLogic.setBallX(0);
        gameLogic.setBallY(50);
        gameLogic.setBallDY(2);

        // When
        gameLogic.update();

        // Then
        assertEquals(-2, gameLogic.getBallDY());
    }

    @Test
    @DisplayName("Should increase score when brick is destroyed")
    void should_increase_score_when_brick_is_destroyed() throws EndGameException {
        // Given
        gameLogic.setBallX(0);
        gameLogic.setBallY(50);
        gameLogic.setBallDY(2);
        int initialScore = gameLogic.getScore();

        // When
        gameLogic.update();

        // Then
        assertEquals(initialScore + 10, gameLogic.getScore());
    }
}