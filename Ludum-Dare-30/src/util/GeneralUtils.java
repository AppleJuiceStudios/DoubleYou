package util;

public class GeneralUtils {

	private GeneralUtils() {
	}

	public static boolean isDevMode() {
		if (System.getenv("devMode") == null)
			return false;
		return true;
	}
}