/*
 * RaspiLCD-K
 * Module: Core
 *
 * ButtonListener.java
 *
 * Author: Gerrit Kaul
 * Date: 31.05.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.input;

public interface ButtonListener {
    /**
     * Called when a button is pressed.
     *
     * @param e the button event
     */
    public void onButtonPressed(ButtonEvent e);

    /**
     * Called when a button is released.
     *
     * @param e the button event
     */
    public void onButtonReleased(ButtonEvent e);

    /**
     * Called when the button is changed.
     *
     * @param e the button event
     */
    public void onButtonChanged(ButtonEvent e);
}
