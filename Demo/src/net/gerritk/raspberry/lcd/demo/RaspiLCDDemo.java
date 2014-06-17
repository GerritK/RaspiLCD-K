/*
 * RaspiLCD-K
 * Module: Demo
 *
 * RaspiLCDDemo.java
 *
 * Author: Gerrit Kaul
 * Date: 08.06.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.demo;

import net.gerritk.raspberry.lcd.HwScreen;
import net.gerritk.raspberry.lcd.RaspiLCD;
import net.gerritk.raspberry.lcd.input.ButtonEvent;
import net.gerritk.raspberry.lcd.input.ButtonListener;
import net.gerritk.raspberry.lcd.input.Buttons;
import net.gerritk.raspberry.lcd.interfaces.Screen;
import net.gerritk.raspberry.lcd.resources.Color;
import net.gerritk.raspberry.lcd.simulator.RaspiLCDSimulator;
import net.gerritk.raspberry.lcd.simulator.SimScreen;

import java.awt.*;

public class RaspiLCDDemo implements Runnable {
    private final RaspiLCD raspiLCD;

    private Point point = new Point(100, 50);

    public RaspiLCDDemo(RaspiLCD raspiLCD) {
        this.raspiLCD = raspiLCD;

        new Thread(this).start();
    }

    @Override
    public void run() {

    }

    public RaspiLCD getRaspiLCD() {
        return raspiLCD;
    }

    public static void main(String[] args) {
        Screen screen;
        RaspiLCD raspiLCD;

        if(args[0].equals("simulator")) {
            screen = SimScreen.getInstance();
            raspiLCD = new RaspiLCD(screen);
            new RaspiLCDSimulator(raspiLCD);
        } else {
            screen = HwScreen.getInstance();
            raspiLCD = new RaspiLCD(screen);
        }

        new RaspiLCDDemo(raspiLCD);
    }
}
