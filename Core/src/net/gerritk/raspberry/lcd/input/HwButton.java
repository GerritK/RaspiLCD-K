/*
 * RaspiLCD-K
 * Module: Core
 *
 * HwButton.java
 *
 * Author: Gerrit Kaul
 * Date: 31.05.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.input;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import net.gerritk.raspberry.lcd.interfaces.Button;

public class HwButton extends Button implements GpioPinListenerDigital {
    private final GpioPinDigitalInput pin;

    /**
     * Constructs a new button with the gpio pin and the code.
     *
     * @param pin the pin of the button
     * @param code the code of the button
     */
    public HwButton(Pin pin, int code) {
        super(code);

        GpioController gpio = GpioFactory.getInstance();
        this.pin = gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_UP);
        this.pin.addListener(this);
    }

    @Override
    public boolean isPressed() {
        return pin.isLow();
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        ButtonEvent buttonEvent = new ButtonEvent(this, getCode(), isPressed());

        for(ButtonListener listener : listeners) {
            listener.onButtonChanged(buttonEvent);

            if(buttonEvent.isPressed()) {
                listener.onButtonPressed(buttonEvent);
            } else {
                listener.onButtonReleased(buttonEvent);
            }
        }
    }
}
