/*
 * RaspiLCD-K
 * Module: Core
 *
 * ByteFont.java
 *
 * Author: Gerrit Kaul
 * Date: 21.03.2015
 *
 * Copyright (c) 2015 - K.Design
 * All rights reserved
 */

package net.gerritk.raspberry.lcd.resources;

import net.gerritk.raspberry.lcd.RaspiLCD;
import net.gerritk.raspberry.lcd.interfaces.Font;
import net.gerritk.raspberry.lcd.interfaces.Screen;

public class ByteFont implements Font {
	private net.gerritk.bytefont.ByteFont byteFont;
	private RaspiLCD lcd;

	public ByteFont(net.gerritk.bytefont.ByteFont byteFont, RaspiLCD lcd) {
		this.byteFont = byteFont;
		this.lcd = lcd;
	}

	@Override
	public void drawString(String s, int x, int y) {
		for(char c : s.toCharArray()) {
			byte[][] bytes = byteFont.getBytes(c);
			for(int xb = 0; xb < bytes.length; xb++) {
				for(int yb = 0; yb < bytes[1].length; yb++) {
					byte b = bytes[xb][yb];
					for(int bit = 0; bit < 8; bit++) {
						int dx = x + xb;
						int dy = y + yb * 8 + bit;

						if(dx >= 0 && dy >= 0 && dx < Screen.WIDTH && dy < Screen.HEIGHT && yb * 8 + bit < getHeight()) {
							if (((b >> bit) & 1) == 1) {
								lcd.getScreen().setPixel(dx, dy, lcd.getPenColor());
							}
						}
					}
				}
			}
			x += bytes.length;
		}
	}

	@Override
	public int getHeight() {
		return byteFont.getHeight();
	}

	@Override
	public int getWidth(String s) {
		int width = 0;

		for(char c : s.toCharArray()) {
			byte[][] bytes = byteFont.getBytes(c);
			width += bytes.length;
		}

		return width;
	}
}
