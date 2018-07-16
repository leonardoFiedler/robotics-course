package example;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;


public class Main {
	public static void main(String[] args) {
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);

		Behavior andar = new AndarFrente();
		Behavior naoBater = new NaoBater(us);
		Motor.A.setSpeed(500);
		Motor.C.setSpeed(500);
		
		Behavior[] comportamentos = {andar, naoBater};
		
		Arbitrator arb = new Arbitrator(comportamentos);
		arb.start();
	}
}
