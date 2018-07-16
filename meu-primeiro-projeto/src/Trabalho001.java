import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

public class Trabalho001 {
	
	static int CONST_ROTATE = 200;
	static int rotate = 0;
	static boolean isPreto = false;
	static int valor = 0;
	static LightSensor ls = null;
	static UltrasonicSensor us = null;
	static int preto = 52;
	static int angulo_ajuste = 1;
	static int rotangle = 290;
	static int milisForward = 500;
	static boolean lastLeft = false;
	
	static int qtdBlocks = 0;
	static int speedOriginal = 400;
	static int distance = 0;
	static boolean isGap = false;
	static int delayPreto = 350;
	
	public static void main(String[] args) {
		ls = new LightSensor(SensorPort.S1);
		us = new UltrasonicSensor(SensorPort.S2);
		
		us.continuous();
		Motor.A.setSpeed(speedOriginal);
		Motor.A.resetTachoCount();
		Motor.A.rotateTo(0);
		
		Motor.B.setSpeed(speedOriginal);
		Motor.B.resetTachoCount();
		Motor.B.rotateTo(0);
		
		
		while (true) {
			checkDistance();		
			valor = ls.readValue();
			System.out.println(valor);
			
			if (valor > preto) {
				Motor.A.setSpeed(450);
				Motor.B.setSpeed(450);
				
				//branco
				curvaPreto(angulo_ajuste);
				//Se nao e preto
				if (!isPreto) {
					if (!isGap) {
						//Anda reto
						Motor.A.forward();
						Motor.B.forward();
						Delay.msDelay(500);
						Motor.A.stop(true);
						Motor.B.stop(true);
						
						//Le o valor
						valor = ls.readValue();
						if (valor <= preto) {
							isPreto = true;
							isGap = true;
							CONST_ROTATE = 270;
							System.out.println("Eh gap");
						} 

						if (!isPreto) {
							curvaPreto(angulo_ajuste);
							
							if (!isPreto) {
								//Volta para a posicao original
								Motor.A.backward();
								Motor.B.backward();
								Delay.msDelay(350);
								Motor.A.stop(true);
								Motor.B.stop(true);
								angulo_ajuste += 3;
							} else {
								System.out.println("Eh gap");
								isGap = true;
								CONST_ROTATE = 270;
							}
						}
					} else {
						isGap = false;
						delayPreto -= 150;
						preto = 49;
						//Aumenta o angulo de ajuste
						lastLeft = true;
						angulo_ajuste += 3;
						curvaPreto(angulo_ajuste);
						if (!isPreto) {							
							Motor.A.forward();
							Motor.B.forward();
							Delay.msDelay(500);
							Motor.A.stop(true);
							Motor.B.stop(true);
							
							//Le o valor
							valor = ls.readValue();
							if (valor <= preto) {
								isPreto = true;
							} 
							
							if (!isPreto) {
								//Aumenta o angulo de ajuste
								curvaPreto(angulo_ajuste);
								
								//Volta para a posicao original
								Motor.A.backward();
								Motor.B.backward();
								Delay.msDelay(350);
								Motor.A.stop(true);
								Motor.B.stop(true);
								angulo_ajuste += 3;
							}
						}
					}
				}
			} else {
				Motor.A.setSpeed(speedOriginal);
				Motor.B.setSpeed(speedOriginal);
				
				angulo_ajuste = 1;
				isPreto = true;
				
				//Preto
				Motor.A.forward();
				Motor.B.forward();
				Delay.msDelay(delayPreto);
				Motor.A.stop(true);
				Motor.B.stop(true);
			}
		}
	}
	
	static void curvaPreto(int anguloAjuste) {
		//Rotaciona para o outro lado
		isPreto = false;
		int rotFim = CONST_ROTATE * anguloAjuste;
		
		if (lastLeft) {
			rotateA(rotFim);
			valor = ls.readValue();
			rotateB(rotFim);
		} else {
			rotateB(rotFim);
			valor = ls.readValue();
			rotateA(rotFim);
		}		
	}
	
	static void rotateB(int rotFim) {
		rotate = 0;
		if (valor <= preto) {
			isPreto = true;
			return;
		}
		while(!isPreto && rotate < rotFim) {
			rotate += 70;
			Motor.B.rotate(70);
			Delay.msDelay(50);
			Motor.B.stop(true);
			valor = ls.readValue();
			
			if (valor <= preto) {
				isPreto = true;
				lastLeft = false;
			}
		}
		
		if (!isPreto) {
			//Reset position
			Motor.B.rotate(-rotFim);
			Delay.msDelay(50);
			Motor.B.stop(true);
			lastLeft = false;
		}
	}
	
	static void rotateA(int rotFim) {
		rotate = 0;
		if (valor <= preto) {
			isPreto = true;
			return;
		}
		
		while(!isPreto && rotate < rotFim) {
			rotate += 70;
			Motor.A.rotate(70);
			Delay.msDelay(50);
			Motor.A.stop(true);
			valor = ls.readValue();
			
			if (valor <= preto) {
				isPreto = true;
				lastLeft = true;
			}
		}
		
		//Se nao encontro, volta para a posicao original
		if (!isPreto) {
			//Reset position
			Motor.A.rotate(-rotFim);
			Delay.msDelay(50);
			Motor.A.stop(true);
			lastLeft = true;
		}
	}
	
	static void jumpBlockOne() {
		Motor.B.rotate(rotangle);
		Delay.msDelay(100);
		Motor.A.stop(true);
		Motor.B.stop(true);
		
		Motor.A.forward();
		Motor.B.forward();
		Delay.msDelay(milisForward);
		Motor.A.stop(true);
		Motor.B.stop(true);
		
		Motor.A.rotate(rotangle);
		Delay.msDelay(100);
		Motor.A.stop(true);
		Motor.B.stop(true);
		
		Motor.A.forward();
		Motor.B.forward();
		Delay.msDelay(milisForward * 3);
		Motor.A.stop(true);
		Motor.B.stop(true);
		
		Motor.A.rotate(rotangle);
		Delay.msDelay(100);
		Motor.A.stop(true);
		Motor.B.stop(true);
		
		isPreto = false;
		while(!isPreto) {
			valor = ls.readValue();
			if (valor <= preto) {
				isPreto = true;
				//Ajuste especifico de pista
				Motor.B.rotate(270);
				Delay.msDelay(200);
				Motor.A.stop(true);
				Motor.B.stop(true);
			} else {
				Motor.A.forward();
				Motor.B.forward();
				Delay.msDelay(50);
				Motor.A.stop(true);
				Motor.B.stop(true);
			}
		}
	}
	
	static boolean checkDistance() {
		valor = ls.readValue();
		distance = us.getDistance();
		
		if (distance != 255 && distance < 22) {
			jumpBlockOne();
			//distance += 10;
			return true;
		}
		return false;
	}
}