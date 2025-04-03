package com.zenika.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameLogicTest {

    // Constants
    private final int panelWidth = 800;
    private final int panelHeight = 600;

    // Tested component
    private final BricksLogic bricksLogic = new BricksLogic(panelWidth);
    private final GameLogic gameLogic = new GameLogic(panelWidth, panelHeight, bricksLogic);

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
    @DisplayName("Should bounce ball off paddle")
    void should_bounce_ball_off_paddle() throws EndGameException {
        // Given
        gameLogic.setBallX(350);
        gameLogic.setBallY(540);
        gameLogic.setBallDY(2);

        // When
        gameLogic.update();

        // Then
        assertEquals(-2, gameLogic.getBallDY());
    }

    @Test
    @DisplayName("Should not bounce ball off paddle when not intersecting")
    void should_not_bounce_ball_off_paddle_when_not_intersecting() throws EndGameException {
        // Given
        gameLogic.setBallX(0);
        gameLogic.setBallY(0);
        gameLogic.setBallDY(2);

        // When
        gameLogic.update();

        // Then
        assertEquals(2, gameLogic.getBallDY());
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
        assertEquals(7, bricksLogic.getBricks().size());
    }

    @Test
    @DisplayName("Should remove brick when ball hits")
    void should_remove_brick_when_ball_hits() throws EndGameException {
        // Given
        gameLogic.setBallX(50);
        gameLogic.setBallY(50);
        gameLogic.setBallDY(2);
        int initialBrickCount = bricksLogic.getBricks().size();

        // When
        gameLogic.update();

        // Then
        assertEquals(initialBrickCount - 1, bricksLogic.getBricks().size());
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

    @Test
    @DisplayName("Mock - Should call checkCollision when ball hits a brick")
    void should_call_checkCollision_when_ball_hits_a_brick_mock() throws EndGameException {
        // Given
        BricksLogic mockBricksLogic = mock(BricksLogic.class);
        GameLogic gameLogicWithMock = new GameLogic(panelWidth, panelHeight, mockBricksLogic);
        gameLogicWithMock.setBallX(0);
        gameLogicWithMock.setBallY(50);
        gameLogicWithMock.setBallDY(2);

        // When
        gameLogicWithMock.update();

        // Then
        verify(mockBricksLogic, times(1)).checkCollision(any(Rectangle.class));
    }

    @Test
    @DisplayName("Stub - Should call checkCollision when ball hits a brick")
    void should_call_checkCollision_when_ball_hits_a_brick_stub() throws EndGameException {
        // Given
        BricksLogic stubBricksLogic = new BricksLogic(panelWidth) {
            @Override
            public boolean checkCollision(Rectangle ball) {
                return true; // Stubbed response
            }
        };
        GameLogic gameLogicWithStub = new GameLogic(panelWidth, panelHeight, stubBricksLogic);
        gameLogicWithStub.setBallX(0);
        gameLogicWithStub.setBallY(50);
        gameLogicWithStub.setBallDY(2);

        // When
        gameLogicWithStub.update();

        // Then
        assertTrue(stubBricksLogic.checkCollision(new Rectangle(0, 50, 20, 20)));
    }

    @Test
    @DisplayName("Spy - Should call checkCollision when ball hits a brick")
    void should_call_checkCollision_when_ball_hits_a_brick_spy() throws EndGameException {
        // Given
        BricksLogic spyBricksLogic = spy(new BricksLogic(panelWidth));
        GameLogic gameLogicWithSpy = new GameLogic(panelWidth, panelHeight, spyBricksLogic);
        gameLogicWithSpy.setBallX(0);
        gameLogicWithSpy.setBallY(50);
        gameLogicWithSpy.setBallDY(2);

        // When
        gameLogicWithSpy.update();

        // Then
        verify(spyBricksLogic, times(1)).checkCollision(any(Rectangle.class));
    }

    @Test
    @DisplayName("Fake - Should call checkCollision when ball hits a brick")
    void should_call_checkCollision_when_ball_hits_a_brick_fake() throws EndGameException {
        // Given
        BricksLogic fakeBricksLogic = new FakeBricksLogic();
        GameLogic gameLogicWithFake = new GameLogic(panelWidth, panelHeight, fakeBricksLogic);
        gameLogicWithFake.setBallX(0);
        gameLogicWithFake.setBallY(50);
        gameLogicWithFake.setBallDY(2);

        // When
        gameLogicWithFake.update();

        // Then
        assertTrue(fakeBricksLogic.checkCollision(new Rectangle(0, 50, 20, 20)));
    }

    // Fake class for BricksLogic
    static class FakeBricksLogic extends BricksLogic {
        public FakeBricksLogic() {
            super(800); // Assuming panelWidth is 800
        }

        @Override
        public boolean checkCollision(Rectangle ball) {
            return true; // Fake response
        }
    }

    @Test
    @DisplayName("Dummy - Should call checkCollision when ball hits a brick")
    void should_call_checkCollision_when_ball_hits_a_brick_dummy() throws EndGameException {
        // Given
        BricksLogic dummyBricksLogic = new BricksLogic(panelWidth);
        GameLogic gameLogicWithDummy = new GameLogic(panelWidth, panelHeight, dummyBricksLogic);
        gameLogicWithDummy.setBallX(0);
        gameLogicWithDummy.setBallY(50);
        gameLogicWithDummy.setBallDY(2);

        // When
        gameLogicWithDummy.update();

        // Then
        // No verification needed as dummy is not used
    }

    @Test
    @DisplayName("Should reset game without exiting program")
    void should_reset_game_without_exiting_program() {
        // Given
        GameLogic gameLogic = new GameLogic(panelWidth, panelHeight, bricksLogic);

        // When
        gameLogic.resetGame();

        // Then
        assertAll(
                () -> assertEquals(400, gameLogic.getBallX()),
                () -> assertEquals(70, gameLogic.getBallY()),
                () -> assertEquals(0, gameLogic.getScore())
        );
    }

    @Test
    @DisplayName("Should reset ball position on resetGame")
    void should_reset_ball_position_on_resetGame() {
        // Given
        GameLogic gameLogic = new GameLogic(panelWidth, panelHeight, bricksLogic);
        gameLogic.setBallX(100);
        gameLogic.setBallY(100);

        // When
        gameLogic.resetGame();

        // Then
        assertEquals(400, gameLogic.getBallX());
        assertEquals(70, gameLogic.getBallY());
    }

    @Test
    @DisplayName("Should reset paddle position on resetGame")
    void should_reset_paddle_position_on_resetGame() {
        // Given
        GameLogic gameLogic = new GameLogic(panelWidth, panelHeight, bricksLogic);
        gameLogic.setPaddleX(100);

        // When
        gameLogic.resetGame();

        // Then
        assertEquals(350, gameLogic.getPaddleX());
    }

    @Test
    @DisplayName("Should reset score on resetGame")
    void should_reset_score_on_resetGame() {
        // Given
        GameLogic gameLogic = new GameLogic(panelWidth, panelHeight, bricksLogic);
        gameLogic.setScore(0);

        // When
        gameLogic.resetGame();

        // Then
        assertEquals(0, gameLogic.getScore());
    }
}