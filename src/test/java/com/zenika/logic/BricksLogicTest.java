package com.zenika.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class BricksLogicTest {

    private final int panelWidth = 800;
    private final BricksLogic bricksLogic = new BricksLogic(panelWidth);

    @Test
    @DisplayName("Should initialize bricks correctly")
    void should_initialize_bricks_correctly() {
        // Arrange
        int expectedBrickCount = 7;

        // Act
        int actualBrickCount = bricksLogic.getBricks().size();

        // Assert
        assertEquals(expectedBrickCount, actualBrickCount);
    }

    @Test
    @DisplayName("Should initialize bricks with correct dimensions")
    void should_initialize_bricks_with_correct_dimensions() {
        // Given
        int expectedBrickWidth = 60;
        int expectedBrickHeight = 20;

        // When
        Rectangle firstBrick = bricksLogic.getBricks().getFirst();

        // Then
        assertEquals(expectedBrickWidth, firstBrick.width);
        assertEquals(expectedBrickHeight, firstBrick.height);
    }

    @Test
    @DisplayName("Should detect collision and remove brick when ball hits")
    void should_detect_collision_and_remove_brick_when_ball_hits() {
        // Given
        Rectangle ball = new Rectangle(0, 50, 20, 20);
        int initialBrickCount = bricksLogic.getBricks().size();

        // When
        boolean collisionDetected = bricksLogic.checkCollision(ball);

        // Then TODO: Use assertCollision method to factorize this test
        assertTrue(collisionDetected);
        assertEquals(initialBrickCount - 1, bricksLogic.getBricks().size());
    }

    @Test
    @DisplayName("Should not detect collision when ball does not hit any brick")
    void should_not_detect_collision_when_ball_does_not_hit_any_brick() {
        // Given
        Rectangle ball = new Rectangle(0, 0, 20, 20);
        int initialBrickCount = bricksLogic.getBricks().size();

        // When
        boolean collisionDetected = bricksLogic.checkCollision(ball);

        // Then TODO: Use assertCollision method to factorize this test
        assertFalse(collisionDetected);
        assertEquals(initialBrickCount, bricksLogic.getBricks().size());
    }

    private void assertCollision(Rectangle ball, boolean expectedCollision, int expectedBrickCount) {
        int initialBrickCount = bricksLogic.getBricks().size();

        // When
        boolean collisionDetected = bricksLogic.checkCollision(ball);

        // Then
        assertEquals(expectedCollision, collisionDetected);
        assertEquals(expectedBrickCount, bricksLogic.getBricks().size());
    }
}
