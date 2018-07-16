package trabalho;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;

public class Main {

	static Node[][] matriz = new Node[7][7];
	static int orientacao = 0;
	static int distanciaPadrao = 20;
	static UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
	static ColorSensor cs = new ColorSensor(SensorPort.S2);
	static List<Node> nodesBFS = new ArrayList<Node>();
	
	//Sensores
	//Color: 2
	//Ultrasonic: 1
	//Motor A: Esquerda
	//Motor C: Direita
	//Motor B: Auxiliar (Rotacao)
	
	//0 = baixo
	//1 - esquerda
	//2 - cima
	//3 - direita
	
	public static void main(String[] args) {
		cs.calibrateLow();
		
//		boolean valeu = true;
//		while(valeu) {
//			int color = getColor();
//			System.out.println("color " + color);
//			Button.waitForAnyPress();
//		}
		
		us.continuous();
		int x = 0;
		int y = 0;
		Node noAtual = new Node(x, y);		
		
		//Obtem os nos filhos
		int i = 0;
		while (hasElementosMatriz()) {
			i = 0;
			
			if (matriz[x][y] == null || matriz[x][y].filhos.size() == 0) {
				for (; i <= 2; i++) {
					if (orientacao == 0) {
						switch(i) {
						case 0:
							if (us.getDistance() > distanciaPadrao) {
								if (y < 6) {
									if (matriz[x][y + 1] == null) {
										Node no = new Node(x, y + 1);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x][y + 1] = no;						
									} else {
										Node no = matriz[x][y + 1];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}			
							}
							break;
						case 1:
							if (x > 0) {
								rotate(90);
								if (us.getDistance() > distanciaPadrao) {
									if (matriz[x - 1][y] == null) {
										Node no = new Node(x - 1, y);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x - 1][y] = no;							
									} else {
										Node no = matriz[x - 1][y];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
								rotate(-90);
							}
							break;					
						case 2:
							if (x < 6) {	
								rotate(-90);
								if (us.getDistance() > distanciaPadrao) {
													
									if (matriz[x + 1][y] == null) {
										Node no = new Node(x + 1, y);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x + 1][y] = no;
									} else {
										Node no = matriz[x + 1][y];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
								rotate(90);
							}
							break;
						}
					//Direita
					} else if (orientacao == 1) {
						switch(i) {
						case 0:
							if (us.getDistance() > distanciaPadrao) {
								if (x < 6) {
									if (matriz[x+1][y] == null) {
										Node no = new Node(x + 1, y);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x+1][y] = no;						
									} else {
										Node no = matriz[x+1][y];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
							}
						break;
						case 1:
							if (y < 6) {
								rotate(90);
								if (us.getDistance() > distanciaPadrao) {
								
									if (matriz[x][y+1] == null) {
										Node no = new Node(x, y+1);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x][y+1] = no;								
									} else {
										Node no = matriz[x][y+1];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
								rotate(-90);
							}
							break;			
						case 2:
							if (y > 0) {		
								rotate(-90);
								if (us.getDistance() > distanciaPadrao) {
									if (matriz[x][y - 1] == null) {
										Node no = new Node(x, y - 1);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x][y - 1] = no;
									} else {
										Node no = matriz[x][y - 1];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
								rotate(90);
							}
							break;
						}
					} else if (orientacao == 2) {
						switch(i) {
						case 0:
							if (us.getDistance() > distanciaPadrao) {
								if (y > 0) {
									if (matriz[x][y - 1] == null) {
										Node no = new Node(x, y - 1);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x][y - 1] = no;								
									} else {
										Node no = matriz[x][y - 1];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
							}
							break;
						case 1:
							if (x > 0) {
								rotate(-90);
								if (us.getDistance() > distanciaPadrao) {
									if (matriz[x - 1][y] == null) {
										Node no = new Node(x - 1, y);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x-1][y] = no;						
									} else {
										Node no = matriz[x - 1][y];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
								rotate(90);
							}
							break;					
						case 2:
							if (x < 6) {						
								rotate(90);
								if (us.getDistance() > distanciaPadrao) {
									if (matriz[x+1][y] == null) {
										Node no = new Node(x+1, y);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x+1][y] = no;								
									} else {
										Node no = matriz[x+1][y];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
								rotate(-90);
							}
							break;
						}
					} else {
						switch(i) {
						case 0:
							if (us.getDistance() > distanciaPadrao) {
								if (x > 0) {
									if (matriz[x-1][y] == null) {
										Node no = new Node(x-1, y);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x-1][y] = no;							
									} else {
										Node no = matriz[x-1][y];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
							}
							break;
						case 1:
							if (y > 0) {
								rotate(90);
								if (us.getDistance() > distanciaPadrao) {
									if (matriz[x][y-1] == null) {
										Node no = new Node(x, y-1);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x][y-1] = no;								
									} else {
										Node no = matriz[x][y-1];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
								rotate(-90);
							}
							break;					
						case 2:
							if (y < 6) {						
								rotate(-90);
								if (us.getDistance() > distanciaPadrao) {
									if (matriz[x][y+1] == null) {
										Node no = new Node(x, y+1);
										no.noPai = noAtual;
										noAtual.filhos.add(no);
										matriz[x][y+1] = no;
									} else {
										Node no = matriz[x][y+1];
										if (!no.visitado) {
											no.noPai = noAtual;
										}
										noAtual.filhos.add(no);
									}
								}						
								rotate(90);
							}
							break;
						}
					}				
				}
			}
			
			noAtual.visitado = true;
			noAtual.color = getColor();
			
			if (noAtual.color != 0) {
				Sound.beep();
				System.out.println("Color " + noAtual.color);
				nodesBFS.add(noAtual);
			}
			
			matriz[x][y] = noAtual;
			Node filhoSelected = null;
			for (Node filho : noAtual.filhos) {
				if (!filho.visitado) {
					filhoSelected = filho;
					break;
				}
			}
			
			if (filhoSelected != null) {
				caminhar(noAtual, filhoSelected);
				noAtual = filhoSelected;
			} else {
				if (noAtual.noPai != null) {
					caminhar(noAtual, noAtual.noPai);
					noAtual = noAtual.noPai;
				} else {
					noAtual = null;
				}
			}
			
			if (noAtual != null) {
				x = noAtual.X;
				y = noAtual.Y;
			}
		}
		
		//Terminou
		nodesBFS.add(0, noAtual);
		List<Node> percorre = null;
		for (int j = 0; j < nodesBFS.size() - 1; j++) {
			Node noPercorrer = nodesBFS.get(j);
			bfs(noPercorrer);
			percorre = new ArrayList<Node>();
			boolean achou = false;
			Node origem = nodesBFS.get(j + 1);
			percorre.add(origem);
			while (!achou) {
				if (noPercorrer.X == origem.X && noPercorrer.Y == origem.Y) {
					achou = true;
				} else {
					origem = origem.paiBFS;
					percorre.add(origem);
				}
			}
			
			for (int k = percorre.size() - 1; k > 0; k--) {
				System.out.println("Caminhando de: "  + percorre.get(k).X + " " + percorre.get(k).Y);
				System.out.println("Caminhando para: "  + percorre.get(k - 1).X + " " + percorre.get(k - 1).Y);
				caminhar(percorre.get(k), percorre.get(k - 1));
				Delay.msDelay(500);
			}
		}
	}
	
	public static void rotate(int angle) {
		Motor.B.rotate(angle);
		Delay.msDelay(250);
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

	public static void andarDireita() {
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
	
	public static void andarEsquerda() {
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
	
	public static void caminhar(Node noOrigem, Node noDestino) {
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
	
	public static boolean hasElementosMatriz() {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				if (matriz[i][j] == null || !matriz[i][j].visitado) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void bfs(Node origem) {
		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[i].length; j++) {
				matriz[i][j].colorBFS = 0;
				matriz[i][j].paiBFS = null;
				matriz[i][j].distanciaBFS = 0;
			}
		}
		
		List<Node> Q = new ArrayList<Node>();
		Q.add(origem);
		Node u = null;
		while (Q.size() > 0) {
			u = Q.remove(0);
			for (Node node : u.filhos) {
				if (matriz[node.X][node.Y].colorBFS == 0) {
					Q.add(matriz[node.X][node.Y]);
					//Cinza
					matriz[node.X][node.Y].colorBFS = 1;
					matriz[node.X][node.Y].paiBFS = u;
					matriz[node.X][node.Y].distanciaBFS += 1;
				}
			}
			
			if (u.noPai != null && matriz[u.noPai.X][u.noPai.Y].colorBFS == 0) {
				Q.add(matriz[u.noPai.X][u.noPai.Y]);
				//Cinza
				matriz[u.noPai.X][u.noPai.Y].colorBFS = 1;
				matriz[u.noPai.X][u.noPai.Y].paiBFS = u;
				matriz[u.noPai.X][u.noPai.Y].distanciaBFS += 1;
			}
			u.colorBFS = 2;
		}
	}
	
	public static int getColor() {
		Color colorRead = cs.getColor();
		int r = colorRead.getRed();
		int g = colorRead.getGreen();
		int b = colorRead.getBlue();

		int color = ColorUtils.getColor(r, g, b);
		System.out.println("R = " + r);
		System.out.println("G = " + g);
		System.out.println("B = " + b);
		
		switch (color) {
		case 1:
			System.out.println("Rosa");
			return color;				
		case 2:
			System.out.println("Verde");
			return color;
		
		case 3:
			System.out.println("Azul escuro");
			return color;
		
		case 4:
				System.out.println("Azul Claro");
				return color;			
		case 5:
			System.out.println("Vermelho");
			return color;
		default:
			System.out.println("Nao encontrei");
			return 0;
		}
	}
	
	public static int getColorTest() {
		int res1 = getColor();
		int res2 = getColor();
		int res3 = getColor();
		
		if (res1 != 0 ||  res2 != 0 || res3 != 0) {
			System.out.println("Achei " + res1 + " " + res2 + " " + res3);
		} else {
			System.out.println("N‹o achei");
		}

		return 0;
	}
}
