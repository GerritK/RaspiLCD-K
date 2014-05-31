/*
 * RaspiLCD-K
 * Module: Core
 *
 * Color.java
 *
 * Author: Gerrit Kaul
 * Date: 30.05.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.resources;

/**
 * The color enum represents the valid color statements to pass to the screen.
 */
public enum Color {
    WHITE(0), BLACK(1), TRANSPARENT(-1);

    private final int value;

    Color(int value) {
        this.value = value;
    }

    /**
     * Returns the value to pass to the screen.
     *
     * @return the value of this color
     */
    public int getValue() {
        return value;
    }
}
