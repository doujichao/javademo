package 算法.数据结构.图;

/**
 * 双链式存储结构的边
 */
public class Edge {

    public static final int NORMAL=0;

    public static final int MST=1;      //MST边

    public static final int CRITICAL=2; //关键路径中的边权值

    private int weight;

    private Object info;

    /**
     * 边在表中的位置
     */
    private Node edgePosition;

    /**
     * 边的第一个顶点与第二个顶点
     */
    private Node firstVexPosition;

    private Node secondVexPosition;

    private Node edgeFirstPosition;

    private Node edgeSecondPostion;

    /**
     * 边的类型
     */
    private int type;

    /**
     * 所在图的类型
     */
    private int graphType;

    public Edge(Graph g, Vertex u, Vertex v,Object info) {
       this(g,u,v,info,1);
    }

    public Edge(Graph g, Vertex u, Vertex v, Object info, int weight) {
        this.weight = weight;
        this.info = info;
        edgePosition=g.insert(this);
        firstVexPosition=u.getVexPosition();
        secondVexPosition=v.getVexPosition();
        type=Edge.NORMAL;
        graphType=g.getType();
        if (graphType==Graph.UndirectedGraph){

        }else {

        }
    }

    public void setToMST(){
        type=Edge.MST;
    }

    public void setToCritical(){type=Edge.CRITICAL;}
    public void resetType(){type=Edge.NORMAL;}
    public boolean isMSTEdge(){return type==Edge.MST;}
    public boolean isCritical(){return type==Edge.CRITICAL;}

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Node getEdgePosition() {
        return edgePosition;
    }

    public void setEdgePosition(Node edgePosition) {
        this.edgePosition = edgePosition;
    }

    public Node getFirstVexPosition() {
        return firstVexPosition;
    }

    public void setFirstVexPosition(Node firstVexPosition) {
        this.firstVexPosition = firstVexPosition;
    }

    public Node getSecondVexPosition() {
        return secondVexPosition;
    }

    public void setSecondVexPosition(Node secondVexPosition) {
        this.secondVexPosition = secondVexPosition;
    }

    public Node getEdgeFirstPosition() {
        return edgeFirstPosition;
    }

    public void setEdgeFirstPosition(Node edgeFirstPosition) {
        this.edgeFirstPosition = edgeFirstPosition;
    }

    public Node getEdgeSecondPostion() {
        return edgeSecondPostion;
    }

    public void setEdgeSecondPostion(Node edgeSecondPostion) {
        this.edgeSecondPostion = edgeSecondPostion;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getGraphType() {
        return graphType;
    }

    public void setGraphType(int graphType) {
        this.graphType = graphType;
    }
}
