/*
 * RaspiLCD-K
 * Module: Demo
 *
 * RaspiLCDDemo.java
 *
 * Author: Gerrit Kaul
 * Date: 21.03.2015
 *
 * Copyright (c) 2015 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.demo;

import com.pi4j.system.SystemInfo;
import net.gerritk.bytefont.ByteFontReader;
import net.gerritk.raspberry.lcd.HwScreen;
import net.gerritk.raspberry.lcd.RaspiLCD;
import net.gerritk.raspberry.lcd.input.ButtonEvent;
import net.gerritk.raspberry.lcd.input.ButtonListener;
import net.gerritk.raspberry.lcd.input.Buttons;
import net.gerritk.raspberry.lcd.interfaces.Font;
import net.gerritk.raspberry.lcd.interfaces.Screen;
import net.gerritk.raspberry.lcd.resources.ByteFont;
import net.gerritk.raspberry.lcd.simulator.RaspiLCDSimulator;
import net.gerritk.raspberry.lcd.simulator.SimScreen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RaspiLCDDemo implements ButtonListener, Runnable {
	public static final int INTRO = 0;
	public static final int CONTRAST_LIGHT = 1;
	public static final int GEOMETRY = 2;
	public static final int IMAGE = 3;
	public static final int SYSTEM = 4;

	public static final int LAST = 4;

    private final RaspiLCD raspiLCD;

	private int screen = -1;
	private boolean quit;
	private boolean update;
	private float tempLog[];

	private BufferedImage logo;
	private BufferedImage raspberry;
	private Font normal;
	private Font small;

    public RaspiLCDDemo(RaspiLCD raspiLCD) {
		System.out.println("Launching RaspiLCD-K Demo");

		this.raspiLCD = raspiLCD;

		normal = new ByteFont(ByteFontReader.loadByteFont(new File("Demo/res/visitor_p_10.bff")), this.raspiLCD);
		small = new ByteFont(ByteFontReader.loadByteFont(new File("Demo/res/wendy_p_10.bff")), this.raspiLCD);

		try {
			logo = ImageIO.read(RaspiLCDDemo.class.getResourceAsStream("/logo.png"));
			raspberry = ImageIO.read(RaspiLCDDemo.class.getResourceAsStream("/raspberry.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Buttons.addListener(this);

		setScreen(INTRO);

		System.out.println("READY!");
	}

	public void nextScreen() {
		setScreen(getScreen() + 1);
	}

	public void previousScreen() {
		setScreen(getScreen() - 1);
	}

	public void setScreen(int screen) {
		if(screen >= 0 && screen <= LAST && screen != this.screen) {
			this.screen = screen;

			if(screen == SYSTEM && !update) {
				update = true;
				tempLog = new float[Screen.WIDTH - 10];
				new Thread(this).start();
			} else {
				update = false;
				drawCurrentScreen();
			}
		}
	}

	public int getScreen() {
		return screen;
	}

	public void drawCurrentScreen() {
		raspiLCD.getScreen().clear();

		switch (getScreen()) {
			case INTRO:
				drawIntroScreen();
				break;
			case CONTRAST_LIGHT:
				drawContrastLightScreen();
				break;
			case GEOMETRY:
				drawGeometryScreen();
				break;
			case IMAGE:
				drawImageScreen();
				break;
			case SYSTEM:
				drawSystemScreen();
				break;
		}

		raspiLCD.setFont(small);
		raspiLCD.drawString(0, Screen.HEIGHT - raspiLCD.getFont().getHeight(), "Press B to quit...");
		raspiLCD.drawString(Screen.WIDTH - raspiLCD.getFont().getWidth((screen + 1) + "/" + (LAST + 1)), Screen.HEIGHT - raspiLCD.getFont().getHeight(), (screen + 1) + "/" + (LAST + 1));

		raspiLCD.getScreen().flush();
	}

	public void drawIntroScreen() {
		raspiLCD.drawImage((Screen.WIDTH - logo.getWidth()) / 2, (Screen.HEIGHT - logo.getHeight()) / 2 - 6, logo);

		raspiLCD.setFont(normal);
		raspiLCD.drawString(Screen.WIDTH / 2, 0, "RaspiLCD-K", true, false);

		raspiLCD.setFont(small);
		raspiLCD.drawString(Screen.WIDTH / 2, Screen.HEIGHT - raspiLCD.getFont().getHeight() * 2, "Press A or D to navigate", true, false);
	}

	public void drawContrastLightScreen() {
		raspiLCD.setFont(small);

		raspiLCD.drawString(Screen.WIDTH / 2, (int) (Screen.HEIGHT / 2 - raspiLCD.getFont().getHeight() * 2.5f), "BACKLIGHT", true, true);
		raspiLCD.drawString(Screen.WIDTH / 2, (int) (Screen.HEIGHT / 2 - raspiLCD.getFont().getHeight() * 1.5f), "Toggle with C", true, true);
		raspiLCD.drawString(Screen.WIDTH / 2, Screen.HEIGHT / 2, "CONTRAST", true, true);
		raspiLCD.drawString(Screen.WIDTH / 2, Screen.HEIGHT / 2 + raspiLCD.getFont().getHeight(), "Change with UP / DOWN", true, true);
	}

	public void drawGeometryScreen() {
		raspiLCD.setFont(small);

		raspiLCD.drawString(Screen.WIDTH / 2, 0, "You can draw", true, false);
		raspiLCD.drawString(Screen.WIDTH / 2, raspiLCD.getFont().getHeight(), "Lines, rectangles etc.", true, false);
		raspiLCD.drawLine(10, 10, Screen.WIDTH - 10, Screen.HEIGHT - 10);
		raspiLCD.drawLine(10, Screen.HEIGHT - 10, Screen.WIDTH - 10, 10);
		raspiLCD.drawRect(40, 20, Screen.WIDTH - 40, Screen.HEIGHT - 20);
	}

	public void drawImageScreen() {
		raspiLCD.setFont(small);

		raspiLCD.drawImage((Screen.WIDTH - raspberry.getWidth()) / 2, (Screen.HEIGHT - raspberry.getHeight()) / 2 + 2, raspberry);
		raspiLCD.drawString(Screen.WIDTH / 2, 0, "You can draw images", true, false);
	}

	public void drawSystemScreen() {
		for(int i = 1; i < tempLog.length; i++) {
			if(i < tempLog.length - 1) {
				tempLog[i] = tempLog[i + 1];
			} else {
				if(raspiLCD.getScreen() instanceof HwScreen) {
					try {
						tempLog[i] = SystemInfo.getCpuTemperature();
					} catch (IOException | InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					if(tempLog[i - 1] == 0) {
						tempLog[i] = (float) Math.random() * 10 + 45;
					} else {
						float move = 1.5f;
						if(tempLog[i - 1] < 30) {
							move = -3f;
						} else if(tempLog[i - 1] > 70) {
							move = 3f;
						}
						tempLog[i] = (float) (tempLog[i - 1] + (Math.random() * 3) - move);
					}
				}
			}
		}

		raspiLCD.setFont(small);
		String text = "TEMP: " + tempLog[tempLog.length - 1];
		raspiLCD.drawString(Screen.WIDTH - raspiLCD.getFont().getWidth(text), 0, text);

		raspiLCD.drawLine(10, 0, 10, Screen.HEIGHT - 10);
		for(int i = 0; i < Screen.HEIGHT - 10; i += 10) {
			raspiLCD.drawString(0, Screen.HEIGHT - 10 - i, "" + (i + 20), false, true);
			raspiLCD.drawLine(9, Screen.HEIGHT - 10 - i, 11, Screen.HEIGHT - 10 - i);
		}

		for(int i = 0; i < tempLog.length; i++) {
			if(tempLog[i] < 20 || tempLog[i] > Screen.HEIGHT - 10 + 20) continue;

			int y = Screen.HEIGHT - (int) (tempLog[i] - 20) - 10;

			if(y < Screen.HEIGHT - 10) {
				raspiLCD.getScreen().setPixel(i + 10, y, raspiLCD.getPenColor());
			}
		}
	}

	@Override
	public void run() {
		long last;

		while(update) {
			last = System.currentTimeMillis();
			drawCurrentScreen();

			while(System.currentTimeMillis() - last <= 250) {
				Thread.yield();
			}
		}
	}

	@Override
	public void onButtonPressed(ButtonEvent e) {
		switch (e.getButtonCode()) {
			case Buttons.A:
				previousScreen();
				break;
			case Buttons.B:
				quit = true;
				break;
			case Buttons.C:
				raspiLCD.getScreen().toggleBacklight();
				break;
			case Buttons.D:
				nextScreen();
				break;
			case Buttons.DOWN:
				raspiLCD.getScreen().decreaseContrast();
				break;
			case Buttons.UP:
				raspiLCD.getScreen().increaseContrast();
				break;
		}
	}

	@Override
	public void onButtonReleased(ButtonEvent e) {
		// Nothing to do...
	}

	@Override
	public void onButtonChanged(ButtonEvent e) {
		// Nothing to do...
	}

	public static void main(String[] args) {
		RaspiLCD lcd;
		if(args.length > 0 && args[0].equals("simulator")) {
			lcd = new RaspiLCD(SimScreen.getInstance());
			new RaspiLCDSimulator(lcd);
		} else {
			lcd = new RaspiLCD(HwScreen.getInstance());
		}

		RaspiLCDDemo demo = new RaspiLCDDemo(lcd);

		while(!demo.quit) {
			Thread.yield();
		}
		System.exit(0);
	}
}
