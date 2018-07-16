public class Node implements Comparable<Node> {
    private int X;
    private int Y;
    private int Value;
    private boolean IsObstacle;

    public Node(int x, int y, int value, boolean isObstacle) {
        X = x;
        Y = y;
        Value = value;
        IsObstacle = isObstacle;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public boolean getIsObstacle() {
    	return IsObstacle;
    }
    
    public void setIsObstacle(boolean isObstacle) {
    	IsObstacle = isObstacle;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return X == node.X &&
                Y == node.Y && Value == node.Value && IsObstacle == node.IsObstacle;
    }

    @Override
    public String toString() {
        return "Node{" +
                "X=" + X +
                ", Y=" + Y +
                ", Value=" + Value +
                ", IsObstacle=" + IsObstacle +
                '}';
    }


    //1 -> this <= o
    //-1 -> this > o
    @Override
    public int compareTo(Node o) {
        if (this.getValue() >= o.getValue() || this.getX() >= o.getX() || this.getY() > o.getY())
            return -1;
        else
            return 1;
    }
}