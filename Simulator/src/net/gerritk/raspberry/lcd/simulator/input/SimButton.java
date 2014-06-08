/*
 * RaspiLCD-K
 * Module: Simulator
 *
 * SimButton.java
 *
 * Author: Gerrit Kaul
 * Date: 08.06.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.simulator.input;

import net.gerritk.raspberry.lcd.input.ButtonEvent;
import net.gerritk.raspberry.lcd.input.ButtonListener;
import net.gerritk.raspberry.lcd.interfaces.Button;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SimButton extends Button implements MouseListener {
    protected boolean pressed;

    /**
     * Constructs a new button with the code.
     *
     * @param code the code of the button
     */
    public SimButton(int code) {
        super(code);
    }

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        ButtonEvent buttonEvent = new ButtonEvent(this, getCode(), isPressed());

        for(ButtonListener listener : listeners) {
            listener.onButtonChanged(buttonEvent);
            listener.onButtonPressed(buttonEvent);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
        ButtonEvent buttonEvent = new ButtonEvent(this, getCode(), isPressed());

        for(ButtonListener listener : listeners) {
            listener.onButtonChanged(buttonEvent);
            listener.onButtonReleased(buttonEvent);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
