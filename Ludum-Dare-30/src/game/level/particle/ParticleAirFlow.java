package game.level.particle;

public class ParticleAirFlow extends ParticleMoving {

	public ParticleAirFlow(double x, double y, int lifeTime, double xMovement, double yMovement) {
		super(x, y, 5, 5, "/level/particle/AirFlow.png", lifeTime, xMovement, yMovement);
	}

}
