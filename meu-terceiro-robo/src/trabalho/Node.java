package trabalho;

import java.util.ArrayList;
import java.util.List;

public class Node {
	public int X;
    public int Y;
    public boolean visitado;
    public int color;
    public int colorBFS;
    public Node paiBFS;
    public int distanciaBFS;
    
    public Node noPai;
    public List<Node> filhos = new ArrayList<Node>();

    public Node(int x, int y) {
        X = x;
        Y = y;
        visitado = false;
    }

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return X == node.X &&
                Y == node.Y && color == node.color;
    }

    @Override
    public String toString() {
        return "Node{" +
                "X=" + X +
                ", Y=" + Y +
                ", Visitado=" + visitado +
                ", color=" + color +
                '}';
    }
}