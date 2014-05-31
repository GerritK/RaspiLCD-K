/*
 * RaspiLCD-K
 * Module: Core
 *
 * ButtonEvent.java
 *
 * Author: Gerrit Kaul
 * Date: 31.05.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.input;

import net.gerritk.raspberry.lcd.interfaces.Button;

import java.util.EventObject;

/**
 * The button event class represents the event object of a button activity.
 */
public class ButtonEvent extends EventObject {
    private int buttonCode;
    private boolean pressed;

    /**
     * Constructs a new button event with the source, button code and pressed state.
     *
     * @param source the source of the event
     * @param buttonCode the code of the button
     * @param pressed <code>true</code> if the button is pressed; <code>false</code> otherwise
     */
    public ButtonEvent(Button source, int buttonCode, boolean pressed) {
        super(source);

        this.buttonCode = buttonCode;
        this.pressed = pressed;
    }

    /**
     * Returns the button code of this event.
     *
     * @return the button code
     */
    public int getButtonCode() {
        return buttonCode;
    }

    /**
     * Returns whether the button is pressed.
     *
     * @return <code>true</code> if the button is pressed; <code>false</code> otherwise
     */
    public boolean isPressed() {
        return pressed;
    }
}
