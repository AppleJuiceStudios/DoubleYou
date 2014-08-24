package game.main;

import game.main.GameCanvas.FpsManager;

public class Monitoring {

	public final static boolean DISPLAY = false;

	private static long start = 0;
	private static long end = 0;
	private static long totTime = 0;
	private static long unMon = 0;
	private static long totMon = 0;

	public static void tick() {
		end = System.currentTimeMillis();
		totTime = end - start;
		unMon = totTime - totMon;
		if (start != 0) print();
		totMon = 0;
		start = System.currentTimeMillis();
	}

	private static void print() {
		if (DISPLAY) {
			System.out.println("################################");
			System.out.println("FPS: \t\t\t" + FpsManager.getFps());
			System.out.println("Drawing time:\t\t" + String.format("%02d", draw) + " ms");
			System.out.println("   Physics time:\t  " + String.format("%02d", physics) + " ms");
			System.out.println("   Unmon drawing:\t  " + String.format("%02d", (draw - (physics))) + " ms");
			System.out.println("Sleep time:\t\t" + String.format("%02d", sleep) + " ms");
			System.out.println("Unmonitored time:\t" + String.format("%02d", unMon) + " ms");
			System.out.println("Total time:\t\t" + String.format("%02d", totTime) + " ms");
			System.out.println("################################\n\n\n");
		}
	}

	/**
	 * Sleep
	 */
	private static long sleepStart = 0;
	private static long sleepStop = 0;
	private static long sleep = 0;

	public static void startSleep() {
		sleepStart = System.currentTimeMillis();
	}

	public static void stopSleep() {
		sleepStop = System.currentTimeMillis();
		sleep = sleepStop - sleepStart;
		totMon += sleep;
	}

	/**
	 * Draw
	 */
	private static long drawStart = 0;
	private static long drawStop = 0;
	private static long draw = 0;

	public static void startDraw() {
		drawStart = System.currentTimeMillis();
	}

	public static void stopDraw() {
		drawStop = System.currentTimeMillis();
		draw = drawStop - drawStart;
		totMon += draw;
	}

	/**
	 * Physics
	 */
	private static long physicsStart = 0;
	private static long physicsStop = 0;
	private static long physics = 0;

	public static void startPhysics() {
		physicsStart = System.currentTimeMillis();
	}

	public static void stopPhysics() {
		physicsStop = System.currentTimeMillis();
		physics = physicsStop - physicsStart;
		totMon += physics;
	}
}
