package trabalho;
public class Edge {
    private Node origin;
    private Node destiny;

    public Edge(Node origin, Node destiny) {
        this.origin = origin;
        this.destiny = destiny;
    }

    public Node getOrigin() {
        return origin;
    }

    public void setOrigin(Node origin) {
        this.origin = origin;
    }

    public Node getDestiny() {
        return destiny;
    }

    public void setDestiny(Node destiny) {
        this.destiny = destiny;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        //TODO: Testar se esta funcionando com o Java 6
        return (origin.equals(edge.origin) &&
                destiny.equals(edge) ||
                origin.equals(edge.destiny) &&
                destiny.equals(edge.origin));
    }
}
