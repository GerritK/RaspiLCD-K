/*
 * RaspiLCD-K
 * Module: Simulator
 *
 * RaspiLCDSimulator.java
 *
 * Author: Gerrit Kaul
 * Date: 08.06.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.simulator;

import net.gerritk.raspberry.lcd.RaspiLCD;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RaspiLCDSimulator {
    private final RaspiLCD raspiLCD;

    private JFrame frame;

    /**
     * Creates a new raspi lcd simulator.
     */
    public RaspiLCDSimulator() {
        this(new RaspiLCD(SimScreen.getInstance()));
    }

    /**
     * Creates a new raspi lcd simulator with the raspi lcd instance.
     *
     * @param raspiLCD the instance of raspi lcd to use
     */
    public RaspiLCDSimulator(RaspiLCD raspiLCD) {
        if(!(raspiLCD.getScreen() instanceof SimScreen)) {
            throw new IllegalArgumentException("screen of raspi lcd must be instance of sim screen!");
        }

        this.raspiLCD = raspiLCD;

        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(500, 300));
        this.frame.setSize(frame.getPreferredSize());
        this.frame.setResizable(false);

        this.frame.add(new JLabel(new ImageIcon(SimScreen.getInstance().getScreen())));

        this.frame.setVisible(true);
    }

    /**
     * Returns the raspi lcd instance used by the simulator.
     *
     * @return the raspi lcd instance
     */
    public RaspiLCD getRaspiLCD() {
        return raspiLCD;
    }

    public static void main(String[] args) {
        RaspiLCDSimulator simulator = new RaspiLCDSimulator();

        RaspiLCD raspiLCD = simulator.getRaspiLCD();
        raspiLCD.drawRect(20, 20, 50, 50);
        raspiLCD.getScreen().flush();
    }
}
