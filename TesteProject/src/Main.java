import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Main {
	
    private static Node initialNode;
    private static ArrayList<Node> nodesPath;

    public static void main(String[] args) {
    	try {
    		ArrayList<Node> graphNodes = readFile("/Users/leonardofiedler/eclipse-workspace/TesteProject/src/file.json");

    		int currentNode = 0;
    		
    		List<Node> nodesToVerify = new ArrayList();
    		Node node = null;
    		node = graphNodes.get(currentNode);
    		node.setValue(2);
			node.setIsObstacle(false);
			
			List<Edge> edges = getEdges(graphNodes, currentNode, 0, 0, node.getValue(), node);

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
				
				edges = getEdges(graphNodes, currentNode, node.getX(), node.getY(), node.getValue(), node);
				for (int k = 0; k < edges.size(); k++) {
					Edge edge = edges.get(k);
					Node destiny =  edge.getDestiny();
					
					destiny.setValue(node.getValue() + 1);
					
					if (!nodeContains(nodesToVerify, destiny)) {
						nodesToVerify.add(destiny);
					}
				}		
			}
			
//			int posicaoRoboX = 3;
//			int posicaoRoboY = 5;
			int posicaoRoboX = 6;
			int posicaoRoboY = 1;
			Node noRobo = getNodeAt(graphNodes, posicaoRoboX, posicaoRoboY);
			
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
				//Andar com o robo
				System.out.println(noRobo.toString());
			}			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
    }
    
    public static void moveLeft() {
    	
    }
    
    public static void moveRight() {
    	
    }
    
    public static void moveTop() {
    	
    }
    
    public static void moveDown() {
    	
    }
    
    public static ArrayList<Edge> getEdges(List<Node> graphNodes, 
    		int currentNode, 
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

    public static void search(Graph graph, PriorityQueue<Node> queueNodes, ArrayList<Node> visited) {
        while (!queueNodes.isEmpty()) {
            Node node = (Node) queueNodes.peek();
            System.out.println("Processando o nó: " + node.toString());

            if (node.equals(initialNode)) {
                nodesPath.add(node);
                System.out.println("Encontrou o nó final");
                return;
            }

            List<Node> childrenNodes = new ArrayList<Node>();

            for (Edge edge : graph.getEdges()) {
                if (!edge.getOrigin().equals(node))
                    continue;

                if (!visited.contains(edge.getDestiny()))
                    childrenNodes.add(edge.getDestiny());
            }
        	
            //Se o node ainda tiver filhos, continua a adicioná-lo no caminho.
            if (!childrenNodes.isEmpty()) {
                nodesPath.add(node);
                //TODO: Testar se esta funcionando com o Java 6
                //Ordena os nos pela ordem de precedência
                Arrays.sort(childrenNodes.toArray());
                
                //TODO: Testar se esta funcionando com o Java 6
                for (Node node2 : childrenNodes) {
                	queueNodes.add(node2);					
				}
            } else
                nodesPath.remove(node);

            visited.add(node);
        }
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
