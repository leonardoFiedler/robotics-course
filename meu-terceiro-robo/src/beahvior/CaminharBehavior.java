package beahvior;

import trabalho.Node;
import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;
import lejos.util.Delay;

public class CaminharBehavior implements Behavior {
	
	int orientacao;
	boolean caminhar = false;
	Node noOrigem = null; 
	Node noDestino = null;
	
	public CaminharBehavior(int orientacao,
							boolean caminhar,
							Node noOrigem,
							Node noDestino
							) {
		this.orientacao = orientacao;
		this.caminhar = caminhar;
		this.noOrigem = noOrigem;
		this.noDestino = noDestino;
	}

	@Override
	public boolean takeControl() {
		return caminhar;
	}

	@Override
	public void action() {
		
	}

	@Override
	public void suppress() {
		
	}
	
	public static void andarFrente(int velocidade) {
		Delay.msDelay(250);
		Motor.C.setSpeed(370);
		Motor.A.setSpeed(365);
		
//		int distancia = us.getDistance();
//		if (distancia < 30) {
//			while (distancia > 10 && distancia < 100 && distancia != 255) {
//				Motor.A.forward();
//				Motor.C.forward();
//				Delay.msDelay(150);	
//				Motor.A.stop(true);
//				Motor.C.stop(true);
//				distancia = us.getDistance();
//			}
//		} else {
			Motor.A.forward();
			Motor.C.forward();
			if (velocidade == 0) {
				Delay.msDelay(1450);	
			} else {
				Delay.msDelay(velocidade);
			}
			Motor.A.stop(true);
			Motor.C.stop(true);
//		}
	}
	
	public static void andarTras(int velocidade) {
		Delay.msDelay(250);
		Motor.C.setSpeed(370);
		Motor.A.setSpeed(365);
		Motor.A.backward();
		Motor.C.backward();
		
		if (velocidade == 0) {
			Delay.msDelay(1420);	
		} else {
			Delay.msDelay(velocidade);
		}
		
		Motor.A.stop(true);
		Motor.C.stop(true);
	}

	public void andarDireita() {
		Motor.A.rotate(200);
		Motor.C.rotate(-200);
		Delay.msDelay(350);
		andarFrente(1460);
		
		if (orientacao == 0) {
			orientacao = 3;
		} else {
			orientacao--;
		}
	}
	
	public void andarEsquerda() {
		Motor.A.rotate(-200);
		Motor.C.rotate(200);
		Delay.msDelay(350);
		andarFrente(1460);
		
		if (orientacao == 3) {
			orientacao = 0;
		} else {
			orientacao++;
		}
	}
	
	public void caminhar() {
		Delay.msDelay(250);
		
		//Baixo
		switch (orientacao) {
		case 0:
			if (noOrigem.X == noDestino.X) {
				if (noOrigem.Y < noDestino.Y) {
					andarFrente(0);
				} else {
					andarTras(0);
				}
			} else {
				if (noOrigem.X < noDestino.X) {
					//Direita
					andarEsquerda();
				} else {
					//Esquerda
					andarDireita();
				}
			}
		break;
		//Direita
		case 1:
			if (noOrigem.X == noDestino.X) {
				if (noOrigem.Y < noDestino.Y) {
					andarDireita();
				} else {
					andarEsquerda();
				}
			} else {
				if (noOrigem.X < noDestino.X) {
					andarFrente(0);					
				} else {
					andarTras(0);
				}
			}
		break;
		//Cima
		case 2:
			if (noOrigem.X == noDestino.X) {
				if (noOrigem.Y < noDestino.Y) {
					andarTras(0);
				} else {
					andarFrente(0);
				}
			} else {
				if (noOrigem.X < noDestino.X) {
					//Esquerda
					andarDireita();
				} else {
					//Direita
					andarEsquerda();
				}
			}	
			break;
		//Esquerda
		case 3:
			if (noOrigem.X == noDestino.X) {
				if (noOrigem.Y < noDestino.Y) {
					andarEsquerda();
				} else {
					andarDireita();
				}
			} else {
				if (noOrigem.X < noDestino.X) {
					andarTras(0);
				} else {
					andarFrente(0);
				}
			}	
			break;
		}
	}

}
