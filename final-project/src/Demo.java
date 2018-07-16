import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

public class Demo {

	static int END_STATE = 4;
	static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
	static boolean esticado = true;

	public static void main(String[] args) {
		// lastDistance = us.getDistance();

		Motor.A.setSpeed(50);
		Motor.B.setSpeed(70);

		int Q[][] = new int[5][5];
		// Initialize with 0
		for (int i = 0; i < Q.length; i++) {
			for (int j = 0; j < Q[i].length; j++) {
				Q[i][j] = 0;
			}
		}

		int R[][] = new int[5][5];
		int r0[] = new int[] { -1, -1, 100, 0, -1 };
		int r1[] = new int[] { -1, -1, 0, 0, 100 };
		int r2[] = new int[] { 0, 0, -1, 100, -1 };
		int r3[] = new int[] { 0, 100, 0, -1, -1 };
		int r4[] = new int[] { 100, -1, -1, -1, -1 };

		R[0] = r0;
		R[1] = r1;
		R[2] = r2;
		R[3] = r3;
		R[4] = r4;

		// 0 <= Gamma < 1
		float gamma = 0.8f;
		int processed = 2;
		int[] validState = new int[5];

		int initialState = 0;
		int nextState = initialState;
		int currentState = nextState;

		validState[0] = initialState;
		validState[4] = END_STATE;
		
		Random r = new Random();
		while (processed < 6) {
			boolean endEpisode = false;
			while (!endEpisode) {
				nextState = getIndexNextState(R[currentState]);
				Q[currentState][nextState] = R[currentState][nextState]
						+ (int) (gamma * getMaxFromRow(Q[nextState]));
				nextMove(currentState, nextState);
				currentState = nextState;

				if (currentState == END_STATE) {
					endEpisode = true;
				}
			}

			if (processed < 5) {
				boolean valid = false;
				//int last = initialState;
				while (!valid) {
					valid = true;
					// Set currentState randomically
					initialState = r.nextInt(R.length);
					for (int i = 0; i < validState.length; i++) {
						if (initialState == validState[i]) {
							valid = false;
							break;
						}
					}
				}

				System.out.println("Current State " + initialState);
				validState[processed] = initialState;
				goToState(END_STATE, initialState);
				currentState = initialState;
				processed++;
			} else {
				processed++;
			}
		}

		percorrer(Q, currentState);
	}

	public static void percorrer(int[][] Q, int anterior) {
		int initialPosition = 0;
		List<Integer> caminho = new ArrayList<Integer>();
		caminho.add(initialPosition);

		while (caminho.size() < 5) {
			int max = 0;
			int iMax = 0;
			for (int j = 0; j < Q[initialPosition].length; j++) {
				if (Q[initialPosition][j] > max && !caminho.contains(j)) {
					iMax = j;
					max = Q[initialPosition][j];
				}
			}

			caminho.add(iMax);
			initialPosition = iMax;
		}

		while (us.getDistance() > 20) {
			for (int i = 0; i < caminho.size(); i++) {
				nextMove(anterior, caminho.get(i));
				anterior = caminho.get(i);
			}
		}
	}

	public static void nextMove(int anterior, int proximo) {
		System.out.println(anterior + ", " + proximo);
		switch (proximo) {
		case 0:
			if (anterior == 2) {
				abaixar();
			} else if (anterior == 3) {
				esticar();
				abaixar();
			}
			break;
		case 1:
			if (anterior == 2) {
				recolher();
				abaixar();
			} else if (anterior == 3) {
				abaixar();
			}
			break;
		case 2:
			if (anterior == 0) {
				levantar();
			} else if (anterior == 1) {
				levantar();
				esticar();
			} else if (anterior == 3) {
				esticar();
			}
			break;
		case 3:
			if (anterior == 0) {
				levantar();
				recolher();
			} else if (anterior == 1) {
				levantar();
			} else if (anterior == 2) {
				recolher();
			}
			break;
		case 4:
			if (anterior == 1) {
				empuxo();
			}
			break;
		}
	}
	
	public static void goToState(int anterior, int proximo) {
		switch (proximo) {
		case 0:
			switch (anterior) {
			case 1:
				nextMove(1, 2);
				nextMove(2, 0);
				break;
			default:
				nextMove(anterior, proximo);
				break;
			}
		break;
		
		case 1:
			switch (anterior) {
			case 0:
			case 4:
				nextMove(0, 2);
				nextMove(2, 1);
				break;
			default:
				nextMove(anterior, proximo);
				break;
			}
		break;
		
		case 2:
			switch (anterior) {
			case 4:
				nextMove(0, 2);
				break;

			default:
				nextMove(anterior, proximo);
				break;
			}
		break;
		
		case 3:
			switch (anterior) {
			case 4:
				nextMove(0, 2);
				nextMove(2, 3);
				break;

			default:
				nextMove(anterior, proximo);
				break;
			}
		break;
		
		case 4:
			levantar();
			esticar();
			abaixar();
		break;
		}
	}

	public static void levantar() {
		if (esticado) {
			Motor.A.rotate(-50);
			Motor.A.stop();
		} else {
			Motor.A.rotate(-40);
			Motor.A.stop();
		}
		Delay.msDelay(1000);
	}
	
	public static void abaixar() {
		if (esticado) {
			Motor.A.rotate(50);
			Motor.A.stop();
		} else {
			Motor.A.rotate(40);
			Motor.A.stop();
		}
		Delay.msDelay(1000);
	}

	public static void recolher() {
		if (esticado) {
			Motor.B.rotate(-80);
			Motor.B.stop();
			Delay.msDelay(1000);
		}
		
		esticado = false;
	}

	public static void esticar() {
		if (!esticado) {
			Motor.B.rotate(80);
			Motor.B.stop();
			Delay.msDelay(1000);
		}
		esticado = true;
	}
	
	public static void empuxo() {
		Motor.A.rotate(15);
		Motor.A.stop();
		Delay.msDelay(500);
		Motor.B.rotate(80);
		Motor.B.stop();
		Delay.msDelay(500);
		esticado = true;
	}

	public static int getIndexNextState(int[] values) {
		Random r = new Random();
		int randomValue = 0;
		boolean find = false;
		while (!find) {
			randomValue = r.nextInt(values.length);
			if (values[randomValue] >= 0) {
				return randomValue;
			}
		}

		return 0;
	}

	public static int getMaxFromRow(int[] values) {
		int max = 0;

		for (int i = 0; i < values.length; i++) {
			if (values[i] > max) {
				max = values[i];
			}
		}

		return max;
	}
}
