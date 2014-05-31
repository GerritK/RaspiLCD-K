/*
 * RaspiLCD-K
 * Module: Core
 *
 * Button.java
 *
 * Author: Gerrit Kaul
 * Date: 31.05.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.interfaces;

import net.gerritk.raspberry.lcd.input.ButtonListener;
import net.gerritk.raspberry.lcd.input.Buttons;

import java.util.ArrayList;

/**
 * The button class represents a hardware button.
 */
public abstract class Button {
    protected ArrayList<ButtonListener> listeners;
    private final int code;

    /**
     * Constructs a new button with the code.
     *
     * @param code the code of the button
     */
    public Button(int code) {
        this.listeners = new ArrayList<>();
        this.code = code;

        Buttons.addButton(this);
    }

    /**
     * Returns the code of the button.
     *
     * @return the code of the button
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns whether the button is pressed.
     *
     * @return <code>true</code> if the button is pressed; <code>false</code> otherwise
     */
    public abstract boolean isPressed();

    /**
     * Adds the listener to the list.
     *
     * @param listener the listener to add
     * @return <code>true</code> if the listener could be added; <code>false</code> otherwise
     */
    public synchronized boolean addListener(ButtonListener listener) {
        if(listeners.contains(listener)) {
            throw new IllegalArgumentException("listener already registered!");
        }

        return listeners.add(listener);
    }

    /**
     * Removes the listener from the list.
     *
     * @param listener the listener to remove
     * @return <code>true</code> if the list contains the listener; <code>false</code> otherwise
     */
    public synchronized boolean removeListener(ButtonListener listener) {
        return listeners.remove(listener);
    }
}
