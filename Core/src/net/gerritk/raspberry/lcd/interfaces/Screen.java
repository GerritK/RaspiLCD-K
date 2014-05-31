/*
 * RaspiLCD-K
 * Module: Core
 *
 * Screen.java
 *
 * Author: Gerrit Kaul
 * Date: 30.05.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.interfaces;

import net.gerritk.raspberry.lcd.resources.Color;

/**
 * The screen interface represents the screen of the module.
 */
public interface Screen {
    /**
     * The maximum width of the screen.
     */
    public static final int WIDTH = 128;

    /**
     * The maximum height of the screen.
     */
    public static final int HEIGHT = 64;

    /**
     * Initializes the screen.
     */
    public void init();

    /**
     * Toggles the backlight of this screen.
     */
    public void toggleBacklight();

    /**
     * Sets the state of the backlight.
     *
     * @param enabled <code>true</code> if you want to activate the backlight; <code>false</code> otherwise
     */
    public void setBacklightEnabled(boolean enabled);

    /**
     * Returns whether the backlight is enabled.
     *
     * @return <code>true</code> if the backlight is enabled; <code>false</code> otherwise
     */
    public boolean isBacklightEnabled();

    /**
     * Increases the contrast by 1.
     */
    public void increaseContrast();

    /**
     * Decreases the contrast by 1.
     */
    public void decreaseContrast();

    /**
     * Sets the contrast of this screen.
     *
     * @param contrast the contrast to set (0..63)
     */
    public void setContrast(int contrast);

    /**
     * Returns the contrast of this screen.
     *
     * @return the contrast (0..63)
     */
    public int getContrast();

    /**
     * Sets the color of the pixel at the coordinates.
     *
     * @param x the x coordinate of the pixel; must be greater than or equals 0 and less than or equals WIDTH
     * @param y the y coordinate of the pixel; must be greater than or equals 0 and less than or equals HEIGHT
     * @param color the color to set
     */
    public void setPixel(int x, int y, Color color);

    /**
     * Clears the screen completely.
     */
    public void clear();

    /**
     * Flushes the buffer to the hardware.
     */
    public void flush();
}
