package com.zenika.logic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class BricksLogicTest {

    private final int panelWidth = 800;
    private final BricksLogic bricksLogic = new BricksLogic(panelWidth);

    @Test
    @DisplayName("Should detect collision and remove brick when ball hits")
    void should_detect_collision_and_remove_brick_when_ball_hits() {
        // Given
        Rectangle ball = new Rectangle(0, 50, 20, 20);
        int initialBrickCount = bricksLogic.getBricks().size();

        // When
        boolean collisionDetected = bricksLogic.checkCollision(ball);

        // Then
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

        // Then
        assertFalse(collisionDetected);
        assertEquals(initialBrickCount, bricksLogic.getBricks().size());
    }
}
