package 算法.数据结构.图;

import java.util.LinkedList;

/**
 * 点
 */
public class Vertex {

    /**
     * 顶点信息
     */
    private Object info;

    /**
     * 邻接表
     */
    private LinkedList adjacentEdges;

    /**
     * 逆邻接表
     */
    private LinkedList reAdjacentEdges;

    /**
     * 访问状态
     */
    private boolean visited;

    /**
     * 顶点在顶点表中的位置
     */
    private Node vexPosition;

    /**
     * 顶点所在图的类型
     */
    private int graphType;

    /**
     * 应用，如求最短路径时为path，求关键路径时为Vtime
     */
    private Object application;

    public Vertex(Graph g,Object info) {
        this.info=info;
        adjacentEdges=new LinkedList();
        reAdjacentEdges=new LinkedList();
        visited=false;
        graphType=g.getType();
        vexPosition=g.insert(this);
        application=null;
    }

    private boolean isUnDiGraphNode(){
        return graphType==Graph.UndirectedGraph;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public int getDeg(){
        if (isUnDiGraphNode()){
            return adjacentEdges.size();
        }else {
            return getOutDeg()+getInDeg();
        }
    }

    private int getInDeg() {
        if (isUnDiGraphNode()){
            return adjacentEdges.size();
        }else {
            return reAdjacentEdges.size();
        }
    }

    private int getOutDeg() {
        return adjacentEdges.size();
    }

    public LinkedList getAdjacentEdges() {
        return adjacentEdges;
    }

    public void setAdjacentEdges(LinkedList adjacentEdges) {
        this.adjacentEdges = adjacentEdges;
    }

    public LinkedList getReAdjacentEdges() {
        if (isUnDiGraphNode()){
            return adjacentEdges;
        }else {
            return reAdjacentEdges;
        }
    }

    public void setReAdjacentEdges(LinkedList reAdjacentEdges) {
        this.reAdjacentEdges = reAdjacentEdges;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setToVisited(){
        visited=true;
    }
    public void setToUnVisited(){
        visited=false;
    }

    public Node getVexPosition() {
        return vexPosition;
    }

    public void setVexPosition(Node vexPosition) {
        this.vexPosition = vexPosition;
    }

    public int getGraphType() {
        return graphType;
    }

    public void setGraphType(int graphType) {
        this.graphType = graphType;
    }

    public Object getAppObj() {
        return application;
    }

    public void setAppObj(Object app) {
        this.application = app;
    }

    /**
     * 重置顶点信息
     */
    public void resetStatus(){
        visited=false;
        application=null;
    }
}
