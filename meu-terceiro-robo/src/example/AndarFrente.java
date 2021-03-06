package example;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;


public class AndarFrente implements Behavior {

	@Override
	public boolean takeControl() {
		return true;
	}

	@Override
	public void action() {
		Motor.A.forward();
		Motor.C.forward();
	}

	@Override
	public void suppress() {
		Motor.A.stop();
		Motor.C.stop();
	}
}
