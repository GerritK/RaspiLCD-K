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

public class RaspiLCDDemo implements Runnable, ButtonListener {
    private final RaspiLCD raspiLCD;

    private Point point = new Point(100, 50);
    private boolean running;

    public RaspiLCDDemo(RaspiLCD raspiLCD) {
        this.raspiLCD = raspiLCD;

        Buttons.addListener(this);

        this.running = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        long last;

        while(running) {
            last = System.currentTimeMillis();

            raspiLCD.getScreen().clear();
            raspiLCD.getScreen().setPixel(point.x, point.y, Color.BLACK);
            raspiLCD.getScreen().flush();

            while(System.currentTimeMillis() - last < 250) {
                Thread.yield();
            }
        }
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

    @Override
    public void onButtonPressed(ButtonEvent e) {
        if(e.getButtonCode() == Buttons.UP) {
            point.y -= 1;
        } else if(e.getButtonCode() == Buttons.DOWN) {
            point.y += 1;
        }
    }

    @Override
    public void onButtonReleased(ButtonEvent e) {

    }

    @Override
    public void onButtonChanged(ButtonEvent e) {

    }
}
