/*
 * RaspiLCD-K
 * Module: Core
 *
 * Font.java
 *
 * Author: Gerrit Kaul
 * Date: 30.05.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.interfaces;

/**
 * The font interface represents a font to draw on the screen.
 */
public interface Font {
    /**
     * Draws the string on the screen at the coordinates (x/y).
     *
     * @param s the string to draw
     * @param x the x coordinate of the upper-left corner
     * @param y the y coordinate of the upper-left corner
     */
    public void drawString(String s, int x, int y);

    /**
     * Returns the height of this font.
     *
     * @return the height of this font
     */
    public int getHeight();

    /**
     * Returns the width of the string with this font.
     *
     * @param s the string to get the width from
     * @return the width of the string
     */
    public int getWidth(String s);
}
