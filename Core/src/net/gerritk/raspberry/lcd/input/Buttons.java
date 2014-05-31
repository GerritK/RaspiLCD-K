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

    public static final Buttons instance = new Buttons();

    private static ArrayList<Button> buttons = new ArrayList<>();
    private static ArrayList<ButtonListener> listeners = new ArrayList<>();

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

        return buttons.add(button);
    }

    /**
     * Removes the button from the list.
     *
     * @param button the button to remove
     * @return <code>true</code> if the button exists in the list; <code>false</code> otherwise
     */
    public static boolean removeButton(Button button) {
        return buttons.remove(button);
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
        for(Button button : buttons) {
            if(button.getCode() == code) {
                return button;
            }
        }

        return null;
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
