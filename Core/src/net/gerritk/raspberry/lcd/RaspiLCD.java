package net.gerritk.raspberry.lcd;

import net.gerritk.raspberry.lcd.interfaces.Font;
import net.gerritk.raspberry.lcd.interfaces.Screen;
import net.gerritk.raspberry.lcd.resources.Color;

import java.awt.image.BufferedImage;

/**
 * The raspi lcd class represents the main component of the module.
 */
public class RaspiLCD {
    public static final String VERSION = "0.0.0.2";

    private final Screen screen;

    private Color penColor = Color.BLACK;
    private Color fillColor = Color.WHITE;
    private Font font;

    /**
     * Constructs a new raspi lcd object with the screen to work with.
     *
     * @param screen the screen to work with
     */
    public RaspiLCD(Screen screen) {
        this.screen = screen;
    }

    /**
     * Draws a rectangle at the given coordinates to the screen.
     *
     * @param x0 the first x coordinate of the rectangle
     * @param y0 the first y coordinate of the rectangle
     * @param x1 the second x coordinate of the rectangle
     * @param y1 the second y coordinate of the rectangle
     */
    public void drawRect(int x0, int y0, int x1, int y1) {
        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) {
                if (x == x0 || x == x1 || y == y0 || y == y1) {
                    screen.setPixel(x, y, penColor);
                } else {
                    screen.setPixel(x, y, fillColor);
                }
            }
        }
    }

    /**
     * Draws an image at the given coordinates to the screen.
     *
     * @param x   the x coordinate of the upper left corner
     * @param y   the y coordinate of the upper left corner
     * @param img the image array to draw
     */
    public void drawImage(int x, int y, int[][] img) {
        drawImage(x, y, img, 1);
    }

    /**
     * Draws an image at the given coordinates scaled by the value to the screen.
     *
     * @param x     the x coordinate of the upper left corner
     * @param y     the y coordinate of the upper left corner
     * @param img   the image array to draw
     * @param scale the scale factor; must be > 0
     */
    public void drawImage(int x, int y, int[][] img, int scale) {
        if (scale <= 0) return;

        for (int iy = 0; iy < img.length; iy++) {
            for (int ix = 0; ix < img[iy].length; ix++) {
                for (int sx = 0; sx < scale; sx++) {
                    for (int sy = 0; sy < scale; sy++) {
                        int rgb = img[iy][ix];
                        Color color;

                        if (rgb == 1) {
                            color = penColor;
                        } else if (rgb == 0) {
                            color = fillColor;
                        } else {
                            continue;
                        }

                        screen.setPixel(x + ix * scale + sx, y + iy * scale + sy, color);
                    }
                }
            }
        }
    }

    /**
     * Draws an image at the given coordinates to the screen.
     *
     * @param x   the x coordinate of the upper left corner
     * @param y   the y coordinate of the upper left corner
     * @param img the image to draw as an buffered image; only considers white and black pixels, all other would be skipped.
     */
    public void drawImage(int x, int y, BufferedImage img) {
        drawImage(x, y, img, 1);
    }

    /**
     * Draws an image at the given coordinates to the screen.
     *
     * @param x     the x coordinate of the upper left corner
     * @param y     the y coordinate of the upper left corner
     * @param img   the image to draw as an buffered image; only considers white and black pixels, all other would be skipped.
     * @param scale the scale factor; must be > 0
     */
    public void drawImage(int x, int y, BufferedImage img, int scale) {
        if (scale <= 0) return;

        for (int iy = 0; iy < img.getHeight(); iy++) {
            for (int ix = 0; ix < img.getWidth(); ix++) {
                for (int sx = 0; sx < scale; sx++) {
                    for (int sy = 0; sy < scale; sy++) {
                        int rgb = img.getRGB(ix, iy);
                        Color color;

                        if (new java.awt.Color(rgb).equals(java.awt.Color.BLACK)) {
                            color = penColor;
                        } else if (new java.awt.Color(rgb).equals(java.awt.Color.WHITE)) {
                            color = fillColor;
                        } else {
                            continue;
                        }

                        screen.setPixel(x + ix * scale + sx, y + iy * scale + sy, color);
                    }
                }
            }
        }
    }

    /**
     * Draws a line between the given coordinates.
     *
     * @param x0 the x coordinate of the begin
     * @param y0 the y coordinate of the begin
     * @param x1 the x coordinate of the end
     * @param y1 the y coordinate of the end
     */
    public void drawLine(int x0, int y0, int x1, int y1) {
        if (y0 == y1) {
            if (x0 > x1) {
                int t = x0;
                x0 = x1;
                x1 = t;
            }

            for (int x = x0; x <= x1; x++) {
                screen.setPixel(x, y1, penColor);
            }
        } else if (x0 == x1) {
            if (y0 == y1) {
                int t = y0;
                y0 = y1;
                y1 = t;
            }

            for (int y = y0; y <= y1; y++) {
                screen.setPixel(x0, y, penColor);
            }
        } else {
            int dx = Math.abs(x1 - x0);
            int sx = x0 < x1 ? 1 : -1;
            int dy = -Math.abs(y1 - y0);
            int sy = y0 < y1 ? 1 : -1;
            int err = dx + dy;

            while (true) {
                screen.setPixel(x0, y0, penColor);

                if (x0 == x1 && y0 == y1) break;

                int e2 = 2 * err;

                if (e2 > dy) {
                    err += dy;
                    x0 += sx;
                }

                if (e2 < dx) {
                    err += dx;
                    y0 += sy;
                }
            }
        }
    }

    /**
     * Draws a string to the screen. Would use the selected font.
     *
     * @param x      the x coordinate of the upper left corner
     * @param y      the y coordinate of the upper left corner
     * @param string the string to draw
     */
    public void drawString(int x, int y, String string) {
        font.drawString(string, x, y);
    }

    /**
     * Draws a string to the screen. Would use the selected font.
     *
     * @param x       the x coordinate of the upper left corner
     * @param y       the y coordinate of the upper left corner
     * @param string  the string to draw
     * @param centerX whether to draw centered to the x coordinate
     * @param centerY whether to draw centered to the y coordinate
     */
    public void drawString(int x, int y, String string, boolean centerX, boolean centerY) {
        if (centerX) {
            x -= font.getWidth(string) / 2;
        }

        if (centerY) {
            y -= font.getHeight() / 2;
        }

        drawString(x, y, string);
    }

    /**
     * Returns the screen of this raspi lcd.
     *
     * @return the screen
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * Returns the pen color.
     *
     * @return the pen color
     */
    public Color getPenColor() {
        return penColor;
    }

    /**
     * Sets the pen color.
     *
     * @param penColor the pen color to set
     */
    public void setPenColor(Color penColor) {
        this.penColor = penColor;
    }

    /**
     * Returns the fill color.
     *
     * @return the fill color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets the fill color.
     *
     * @param fillColor the fill color to set
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    /**
     * Returns the font.
     *
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    /**
     * Sets the font.
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
    }
}
