/*
 * RaspiLCD-K
 * Module: Simulator
 *
 * SimScreen.java
 *
 * Author: Gerrit Kaul
 * Date: 08.06.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.simulator;

import net.gerritk.raspberry.lcd.interfaces.Screen;
import net.gerritk.raspberry.lcd.resources.Color;

import java.awt.image.BufferedImage;

/**
 * The simulator screen class represents the screen to use with simulator.
 */
public class SimScreen implements Screen {
    private static final int SCALE = 3;

    private static SimScreen instance;

    private boolean backlight;
    private int contrast;
    private int[][] framebuffer;
    private BufferedImage screen;

    /**
     * Constructs a new simulator screen.
     */
    private SimScreen() {
        this.framebuffer = new int[WIDTH + 1][HEIGHT + 1];
        this.screen = new BufferedImage(framebuffer.length * SCALE, framebuffer[0].length * SCALE, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Returns the instance of the simulator screen.
     *
     * @return the instance
     */
    public static SimScreen getInstance() {
        if(instance == null) {
            synchronized (SimScreen.class) {
                if(instance == null) {
                    instance = new SimScreen();
                }
            }
        }
        return instance;
    }

    /**
     * Returns the screen image of this simulator screen.
     *
     * @return the screen image
     */
    public BufferedImage getScreen() {
        return screen;
    }

    @Override
    public void init() {
        clear();
        flush();
        setBacklightEnabled(true);
        setContrast(9);
    }

    @Override
    public void toggleBacklight() {
        setBacklightEnabled(!isBacklightEnabled());
    }

    @Override
    public void setBacklightEnabled(boolean enabled) {
        this.backlight = enabled;
    }

    @Override
    public boolean isBacklightEnabled() {
        return backlight;
    }

    @Override
    public void increaseContrast() {
        setContrast(getContrast() + 1);
    }

    @Override
    public void decreaseContrast() {
        setContrast(getContrast() + 1);
    }

    @Override
    public void setContrast(int contrast) {
        if(contrast >= 64 || contrast < 0) {
            throw new IllegalArgumentException("contrast out of range! (0..63)");
        }

        this.contrast = contrast;
    }

    @Override
    public int getContrast() {
        return contrast;
    }

    @Override
    public void setPixel(int x, int y, Color color) {
        if(x < 0 || x > WIDTH) {
            throw new IllegalArgumentException("x coordinate out of range! must be greater than or equals 0 and less than or equals " + WIDTH);
        }

        if(y < 0 || y > HEIGHT) {
            throw new IllegalArgumentException("y coordinate out of range! must be greater than or equals 0 and less than or equals " + HEIGHT);
        }

        framebuffer[x][y] = color.getValue();
    }

    @Override
    public void clear() {
        for(int x = 0; x < framebuffer.length; x++) {
            for(int y = 0; y < framebuffer[x].length; y++) {
                framebuffer[x][y] = 0;
            }
        }
    }

    @Override
    public void flush() {
        for(int x = 0; x < framebuffer.length * SCALE; x++) {
            for(int y = 0; y < framebuffer[x / SCALE].length * SCALE; y++) {
                screen.setRGB(x, y, framebuffer[x / SCALE][y / SCALE] == Color.WHITE.getValue() ? 0xffffff : 0x000000);
            }
        }
    }
}
