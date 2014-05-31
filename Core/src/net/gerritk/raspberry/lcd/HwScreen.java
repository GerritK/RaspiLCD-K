/*
 * RaspiLCD-K
 * Module: Core
 *
 * HwScreen.java
 *
 * Author: Gerrit Kaul
 * Date: 31.05.2014
 *
 * Copyright (c) 2014 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd;

import com.pi4j.io.gpio.*;
import net.gerritk.raspberry.lcd.interfaces.Screen;
import net.gerritk.raspberry.lcd.resources.Color;

/**
 * The hardware screen class represents the hardware screen of the raspi lcd module.
 */
public class HwScreen implements Screen {
    private static HwScreen instance;

    private static final int OFFSET = 4;

    private final GpioPinDigitalOutput LCD_LED;
    private final GpioPinDigitalOutput LCD_RST;
    private final GpioPinDigitalOutput LCD_CS;
    private final GpioPinDigitalOutput LCD_RS;
    private final GpioPinDigitalOutput LCD_CLK;
    private final GpioPinDigitalOutput LCD_DATA;

    private final int[][] framebuffer = new int[WIDTH][HEIGHT / 8];
    private int contrast;
    private boolean backlight;

    /**
     * Constructs a new hardware screen.
     */
    private HwScreen() {
        GpioController gpio = GpioFactory.getInstance();

        LCD_LED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LCD_LED", PinState.HIGH);
        LCD_RST = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06, "LCD_RST", PinState.LOW);
        LCD_CS = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10, "LCD_CS", PinState.LOW);
        LCD_RS = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11, "LCD_RS", PinState.LOW);
        LCD_CLK = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_14, "LCD_CLK", PinState.LOW);
        LCD_DATA = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_12, "LCD_DATA", PinState.LOW);

        LCD_LED.setShutdownOptions(true, PinState.LOW);
        LCD_RST.setShutdownOptions(true, PinState.LOW);
    }

    /**
     * Returns the instance of the hardware screen.
     *
     * @return the instance
     */
    public static HwScreen getInstance() {
        if(instance == null) {
            synchronized (HwScreen.class) {
                if(instance == null) {
                    instance = new HwScreen();
                }
            }
        }

        return instance;
    }

    @Override
    public void init() {
        synchronized (LCD_RST) {
            try {
                LCD_RST.low();
                Thread.sleep(50);
                LCD_RST.high();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        writeCommand((byte) 0xE2);
        writeCommand((byte) 0x40);
        writeCommand((byte) 0xA1);
        writeCommand((byte) 0xC0);
        writeCommand((byte) 0xA4);
        writeCommand((byte) 0xA6);
        writeCommand((byte) 0xA2);
        writeCommand((byte) 0x2F);
        writeCommand((byte) 0x27);

        writeCommand((byte) 0x81);
        writeCommand((byte) 6);

        writeCommand((byte) 0xFA);
        writeCommand((byte) 0x90);
        writeCommand((byte) 0xAF);

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

        synchronized (LCD_LED) {
            LCD_LED.setState(enabled);
        }
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
        setContrast(getContrast() - 1);
    }

    @Override
    public void setContrast(int contrast) {
        if(contrast >= 64 || contrast < 0) {
            throw new IllegalArgumentException("contrast out of range! (0..63)");
        }

        this.contrast = contrast;
        writeCommand((byte) 0x81);
        writeCommand((byte) contrast);
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

        if(color.getValue() != 0) {
            framebuffer[x][y >> 3] |= (1 << (y & 7));
        } else {
            framebuffer[x][y >> 3] &= ~(1 << (y & 7));
        }
    }

    @Override
    public void clear() {
        for(int y = 0; y < HEIGHT / 8; y++) {
            for(int x = 0; x < WIDTH; x++) {
                framebuffer[x][y] = 0;
            }
        }
    }

    @Override
    public void flush() {
        for(int y = 0; y < HEIGHT / 8; y++) {
            setXY(0, y);

            for(int x = 0; x < WIDTH; x++) {
                writeData((byte) framebuffer[x][y]);
            }
        }
    }

    private synchronized void writeCommand(byte d) {
        LCD_CS.low();
        LCD_RS.low();
        spiPutChar(d);
        LCD_CS.high();
    }

    private synchronized void writeData(byte d) {
        LCD_CS.low();
        LCD_RS.high();
        spiPutChar(d);
        LCD_CS.high();
    }

    private void setXY(int x, int y) {
        x += OFFSET;
        writeCommand((byte) (x & 0x0F));
        writeCommand((byte) (0x10 + ((x >> 4) & 0x0F)));
        writeCommand((byte) (0xB0 + (y & 0x07)));
    }

    private synchronized void spiPutChar(byte d) {
        for (int i = 0; i < 8; i++) {
            if ((d & 0x80) != 0) {
                LCD_DATA.high();
            } else {
                LCD_DATA.low();
            }

            d <<= 1;

            LCD_CLK.low();
            LCD_CLK.high();
        }
    }
}
