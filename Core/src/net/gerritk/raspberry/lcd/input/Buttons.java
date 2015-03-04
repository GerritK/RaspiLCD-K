/*
 * RaspiLCD-K
 * Module: Core
 *
 * Buttons.java
 *
 * Author: Gerrit Kaul
 * Date: 31.05.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.input;

import net.gerritk.raspberry.lcd.interfaces.Button;

import java.util.ArrayList;

/**
 * The button class keeps the button codes associated.
 */
public class Buttons implements ButtonListener {
    /**
     * The first button from the left.
     */
    public static final int A = 0;

    /**
     * The second button from the left.
     */
    public static final int B = 1;

    /**
     * The third button from the left.
     */
    public static final int C = 2;

    /**
     * The fourth button from the left.
     */
    public static final int D = 3;

    /**
     * The upper button right of the screen.
     */
    public static final int UP = 5;

    /**
     * The bottom button right of the screen.
     */
    public static final int DOWN = 4;

    private static final Buttons instance = new Buttons();

    private ArrayList<Button> buttons = new ArrayList<>();
    private ArrayList<ButtonListener> listeners = new ArrayList<>();

    /**
     * Adds the button to the list.
     *
     * @param button the button to add
     * @return <code>true</code> if the button could be added; <code>false</code> otherwise
     */
    public static boolean addButton(Button button) {
        if(getButton(button.getCode()) != null) {
            throw new IllegalArgumentException("button code already registered!");
        }

        boolean result = instance.buttons.add(button);
        if(result) {
            button.addListener(instance);
        }

        return result;
    }

    /**
     * Removes the button from the list.
     *
     * @param button the button to remove
     * @return <code>true</code> if the button exists in the list; <code>false</code> otherwise
     */
    public static boolean removeButton(Button button) {
        boolean result = instance.buttons.remove(button);
        if(result) {
            button.removeListener(instance);
        }

        return result;
    }

    /**
     * Removes the button with the code from the list.
     * @param code the code of the button to remove
     * @return <code>true</code> if the button exists in the list; <code>false</code> otherwise
     */
    public static boolean removeButton(int code) {
        Button button = getButton(code);

        return button != null && removeButton(button);
    }

    /**
     * Returns the button with the code.
     *
     * @param code the code of the button
     * @return the button with the code
     */
    public static Button getButton(int code) {
        for(Button button : instance.buttons) {
            if(button.getCode() == code) {
                return button;
            }
        }

        return null;
    }

    /**
     * Adds the button listener to the list of listeners.
     *
     * @param listener the listener to add
     * @return <code>true</code> if the button could be added and was not registered before; <code>false</code> otherwise
     */
    public static boolean addListener(ButtonListener listener) {
        return !instance.listeners.contains(listener) && instance.listeners.add(listener);
    }

    /**
     * Removes the listener from the list of listeneres.
     *
     * @param listener the listener to remove
     * @return <code>true</code> if the button could be removed; <code>false</code> otherwise
     */
    public static boolean removeListener(ButtonListener listener) {
        return instance.listeners.remove(listener);
    }

    @Override
    public void onButtonPressed(ButtonEvent e) {
        for(ButtonListener listener : listeners) {
            listener.onButtonPressed(e);
        }
    }

    @Override
    public void onButtonReleased(ButtonEvent e) {
        for(ButtonListener listener : listeners) {
            listener.onButtonReleased(e);
        }
    }

    @Override
    public void onButtonChanged(ButtonEvent e) {
        for(ButtonListener listener : listeners) {
            listener.onButtonChanged(e);
        }
    }
}
