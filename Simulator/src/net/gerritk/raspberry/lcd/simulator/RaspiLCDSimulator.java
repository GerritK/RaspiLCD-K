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
import net.gerritk.raspberry.lcd.simulator.input.SimButton;

import javax.swing.*;

public class RaspiLCDSimulator {
    private final RaspiLCD raspiLCD;

    private JFrame frame;
    private JPanel panel;
    private JButton btnA;
    private JButton btnB;
    private JButton btnC;
    private JButton btnD;
    private JButton btnDown;
    private JButton btnUp;
    private JLabel screen;

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

        System.out.print("Launching Simulator...  ");

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        this.raspiLCD = raspiLCD;

        this.frame = new JFrame();
        ((SimScreen) raspiLCD.getScreen()).setFrame(frame);

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setResizable(false);
        this.frame.add(panel);

        screen.setIcon(new ImageIcon(SimScreen.getInstance().getScreen()));
		screen.setBorder(BorderFactory.createEtchedBorder());

        SimButton simBtn = new SimButton(Buttons.A);
		btnA.setFocusPainted(false);
        btnA.addMouseListener(simBtn);

        simBtn = new SimButton(Buttons.B);
		btnB.setFocusPainted(false);
        btnB.addMouseListener(simBtn);

        simBtn = new SimButton(Buttons.C);
		btnC.setFocusPainted(false);
        btnC.addMouseListener(simBtn);

        simBtn = new SimButton(Buttons.D);
		btnD.setFocusPainted(false);
        btnD.addMouseListener(simBtn);

        simBtn = new SimButton(Buttons.UP);
		btnUp.setFocusPainted(false);
        btnUp.addMouseListener(simBtn);

        simBtn = new SimButton(Buttons.DOWN);
		btnDown.setFocusPainted(false);
        btnDown.addMouseListener(simBtn);

        this.frame.pack();
        this.frame.setVisible(true);

        System.out.println("[OK]");
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
}
