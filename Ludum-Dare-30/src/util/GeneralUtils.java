package util;

import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;

public class GeneralUtils {

	private GeneralUtils() {
	}

	public static boolean isDevMode() {
		if (System.getenv("devMode") == null)
			return false;
		return true;
	}

	public static Font scaleFont(String text, Rectangle rect, Font initialFont) {
		float min = 0.1f;
		float max = 72f;
		float size = 18.0f;
		Canvas c = new Canvas();
		Font font = initialFont;
		FontMetrics fm;

		while (!(max - min <= 1)) {
			font = font.deriveFont(size);
			fm = c.getFontMetrics(font);
			int width = fm.stringWidth(text);
			if (width == rect.width) {
				return font;
			} else {
				if (width < rect.width) {
					min = size;
				} else {
					max = size;
				}
				size = Math.min(max, Math.max(min, size * (float) rect.width / (float) width));
			}
		}
		return font;
	}
}
