package example;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;


public class NaoBater implements Behavior {
	
	UltrasonicSensor us;
	
	public NaoBater(UltrasonicSensor us) {
		this.us = us;
	}
	
	@Override
	public boolean takeControl() {
		return us.getDistance() < 20;
	}

	@Override
	public void action() {
		Motor.C.rotate(-65);
		Motor.A.rotate(65);
	}

	@Override
	public void suppress() {
		Motor.A.stop();
		Motor.C.stop();
	}

}
