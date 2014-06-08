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
import net.gerritk.raspberry.lcd.input.Buttons;
import net.gerritk.raspberry.lcd.interfaces.Screen;
import net.gerritk.raspberry.lcd.simulator.input.SimButton;

import javax.swing.*;
import java.awt.*;

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
        ((SimScreen) raspiLCD.getScreen()).setFrame(frame);

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(500, 300));
        this.frame.setSize(frame.getPreferredSize());
        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        this.frame.add(new JLabel(new ImageIcon(SimScreen.getInstance().getScreen())));

        SimButton simBtn = new SimButton(Buttons.A);
        JButton btn = new JButton("A");
        btn.addMouseListener(simBtn);
        this.frame.add(btn);

        simBtn = new SimButton(Buttons.B);
        btn = new JButton("B");
        btn.addMouseListener(simBtn);
        this.frame.add(btn);

        simBtn = new SimButton(Buttons.C);
        btn = new JButton("C");
        btn.addMouseListener(simBtn);
        this.frame.add(btn);

        simBtn = new SimButton(Buttons.D);
        btn = new JButton("D");
        btn.addMouseListener(simBtn);
        this.frame.add(btn);

        simBtn = new SimButton(Buttons.UP);
        btn = new JButton("UP");
        btn.addMouseListener(simBtn);
        this.frame.add(btn);

        simBtn = new SimButton(Buttons.DOWN);
        btn = new JButton("DOWN");
        btn.addMouseListener(simBtn);
        this.frame.add(btn);

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

    /**
     * Returns the frame of the simulator.
     *
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        RaspiLCDSimulator simulator = new RaspiLCDSimulator();

        RaspiLCD raspiLCD = simulator.getRaspiLCD();
        raspiLCD.drawRect(20, 20, 50, 50);
        raspiLCD.drawLine(0, 0, Screen.WIDTH, Screen.HEIGHT);
        raspiLCD.getScreen().flush();
    }
}
