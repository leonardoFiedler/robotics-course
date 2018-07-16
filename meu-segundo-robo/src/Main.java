import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Motor;
import lejos.util.Delay;

public class Main {
	
    public static void main(String[] args) {
    	try {
    		ArrayList<Node> graphNodes = readFile("file.json");
    		List<Node> nodesToVerify = new ArrayList<Node>();
    		Node node = null;
    		node = graphNodes.get(0);
    		node.setValue(2);
			node.setIsObstacle(false);
			
			List<Edge> edges = getEdges(graphNodes, 0, 0, node.getValue(), node);

			for (int k = 0; k < edges.size(); k++) {
				Edge edge = edges.get(k);
				Node destiny =  edge.getDestiny();
				
				destiny.setValue(node.getValue() + 1);				
				if (!nodeContains(nodesToVerify, destiny)) {
					nodesToVerify.add(destiny);
				}
			}
			
			while(nodesToVerify.size() > 0) {
				node = nodesToVerify.get(0);
				nodesToVerify.remove(0);
				
				edges = getEdges(graphNodes, node.getX(), node.getY(), node.getValue(), node);
				for (int k = 0; k < edges.size(); k++) {
					Edge edge = edges.get(k);
					Node destiny =  edge.getDestiny();
					
					destiny.setValue(node.getValue() + 1);
					
					if (!nodeContains(nodesToVerify, destiny)) {
						nodesToVerify.add(destiny);
					}
				}		
			}
			
			int posicaoRoboX = 6;
			int posicaoRoboY = 6;
			Node noRobo = getNodeAt(graphNodes, posicaoRoboX, posicaoRoboY);
			Node lastPosition = getNodeAt(graphNodes, posicaoRoboX, posicaoRoboY);
			
			List<Edge> edgesAll = null;
			
			while(noRobo.getValue() == 1 || noRobo.getValue() > 2) {
				edgesAll = getAllEdges(graphNodes, posicaoRoboX, posicaoRoboY, noRobo.getValue(), noRobo);
				noRobo = null;
				for (int i = 0; i < edgesAll.size(); i++) {
					if (noRobo == null) {
						noRobo = edgesAll.get(i).getDestiny();
					}
					Node curr = edgesAll.get(i).getDestiny();
					
					if (curr.getValue() < noRobo.getValue()) {
						noRobo = curr;
					}
				}
				
				int orientacao = getOrientacao(lastPosition, noRobo);
				
				switch(orientacao) {
				case 1:
					rotateRight();
					forward(1400);
					rotateLeft();
					break;
				case 2:
					rotateLeft();
					forward(1400);
					rotateRight();
					break;
				case 3:
					forward(1400);
					break;
				case 4:
					rotateLeft();
					rotateLeft();
					forward(1400);
					rotateRight();
					rotateRight();
					break;
				}
				
				lastPosition = getNodeAt(graphNodes, noRobo.getX(), noRobo.getY());
				//Andar com o robo
				System.out.println(noRobo.toString());
			}
			
//    		Delay.msDelay(3000);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    }
    
    public static void rotateLeft() {
    	Motor.C.rotate(-170);
    	Motor.A.rotate(190);
    	Delay.msDelay(300);
    	Motor.C.stop(true);
    	Motor.A.stop(true);   	
    }
    
    public static void rotateRight() {
    	Motor.A.rotate(-170);
    	Motor.C.rotate(190);
    	Delay.msDelay(300);
    	Motor.A.stop(true);
    	Motor.C.stop(true);
    }
    
    public static void forward(int delay) {
    	Motor.A.forward();
    	Motor.C.forward();
    	Delay.msDelay(delay);
    	Motor.A.stop(true);
    	Motor.C.stop(true);
    }
    
    public static int getOrientacao(Node last, Node atual) {    	
    	//Direita
    	if (last.getX() > atual.getX()) {
    		return 1;
    	} else if (last.getX() < atual.getX()){
    		//Esquerda
    		return 2;
    	}
    	
    	//Andar pra frente
    	if (last.getY() > atual.getY()) {
    		return 3;
    	} else {
    		return 4;
    	}
    }
    
    public static ArrayList<Edge> getEdges(List<Node> graphNodes, 
    		int i, 
    		int j, 
    		int initialNodeValue, 
    		Node node) {
    	ArrayList<Edge> edges = new ArrayList<Edge>();
    	
		if (j < 6) {			
			Node neighbor = getNodeAt(graphNodes, node.getX(), node.getY() + 1);
			
			if (!neighbor.getIsObstacle() && neighbor.getValue() == 0 ) {
				edges.add(new Edge(node, neighbor));
			}
		}
		
		if (j > 0) {
			Node neighbor = getNodeAt(graphNodes, node.getX(), node.getY() - 1);
			
			if (!neighbor.getIsObstacle() && neighbor.getValue() == 0) {
				edges.add(new Edge(node, neighbor));
			}
		}

		if (i < 6) {
			Node neighbor = getNodeAt(graphNodes, node.getX() + 1, node.getY());
			
			if (!neighbor.getIsObstacle() && neighbor.getValue() == 0) {
				edges.add(new Edge(node, neighbor));
			}
		}
		
		if (i > 0) {
			Node neighbor = getNodeAt(graphNodes, node.getX() - 1, node.getY());
			if (!neighbor.getIsObstacle() && neighbor.getValue() == 0) {
				edges.add(new Edge(node, neighbor));
			}
		}
		
		return edges;
    }
    
    
    public static ArrayList<Edge> getAllEdges(List<Node> graphNodes,
    		int i, 
    		int j, 
    		int initialNodeValue, 
    		Node node) {
    	ArrayList<Edge> edges = new ArrayList<Edge>();
    	
		if (j < 6) {			
			Node neighbor = getNodeAt(graphNodes, node.getX(), node.getY() + 1);
			
			if (!neighbor.getIsObstacle()) {
				edges.add(new Edge(node, neighbor));
			}
		}
		
		if (j > 0) {
			Node neighbor = getNodeAt(graphNodes, node.getX(), node.getY() - 1);
			
			if (!neighbor.getIsObstacle()) {
				edges.add(new Edge(node, neighbor));
			}
		}

		if (i < 6) {
			Node neighbor = getNodeAt(graphNodes, node.getX() + 1, node.getY());
			
			if (!neighbor.getIsObstacle()) {
				edges.add(new Edge(node, neighbor));
			}
		}
		
		if (i > 0) {
			Node neighbor = getNodeAt(graphNodes, node.getX() - 1, node.getY());
			if (!neighbor.getIsObstacle()) {
				edges.add(new Edge(node, neighbor));
			}
		}
		
		return edges;
    }
    
    public static ArrayList<Node> readFile(String pathname) throws IOException {
		ArrayList<Node> nodes = new ArrayList<Node>();
		File file = new File(pathname);
		InputStream inputStream = new FileInputStream(file);
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		int linha = 0;
		int coluna = 0;

		try {
			while (inputStream.available() > 1) {
				int valor = dataInputStream.read() - 48;
				if (linha == 7) {
					linha = 0;
					coluna++;
					continue;
				}

				nodes.add(new Node(coluna, linha, valor, valor == 1));
				linha++;
			}
		} finally {
			dataInputStream.close();
		}

		return nodes;
	}

    public static boolean nodeContains(List<Node> nodes, Node node) {
    	for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).equals(node)) {
				return true;
			}
		}
    	return false;
    }
    
    public static Node getNodeAt(List<Node> lst, int x, int y) {
    	Node no = null;
    	for (int i = 0; i < lst.size(); i++) {
    		no = lst.get(i);
    		if (no.getX() == x && no.getY() == y) {
    			break;
    		}
		}
    	
    	return no;
    }
}
