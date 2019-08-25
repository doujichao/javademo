package 算法.数据结构.图;

import java.util.Iterator;

public interface Graph {

    int UndirectedGraph=0;
    int DirectedGraph=1;

    /**
     * 返回图的类型
     * @return
     */
    int getType();

    /**
     * 返回图的顶点数
     * @return
     */
    int getVexNum();

    /**
     * 返回图的边数
     * @return
     */
    int getEdgeNum();

    /**
     * 返回图的所有顶点
     * @return
     */
    Iterator getVertex();

    /**
     * 返回图的所有边
     * @return
     */
    Iterator getEdge();

    /**
     * 移除一个顶点
     * @param v
     */
    void remove(Vertex v);

    /**
     * 删除一条边
     * @param e
     */
    void remove(Edge e);

    /**
     * 添加一个顶点v
     * @param vertex 顶点
     * @return
     */
    Node insert(Vertex vertex);

    /**
     * 新增一条边
     * @param edge
     * @return
     */
    Node insert(Edge edge);

    /**
     * 判断顶点u和v是否邻接，是否有边从u到v
     * @param u
     * @param v
     * @return
     */
    boolean areAdjacent(Vertex u,Vertex v);

    /**
     * 返回从u到v的边，不存在返回null
     * @param u
     * @param v
     * @return
     */
    Edge degeFromTo(Vertex u,Vertex v);

    /**
     * 返回从u出发而可以直接达到的邻接顶点
     * @param vertex
     * @return
     */
    Iterator adjVertexs(Vertex vertex);

    /**
     * 对图进行深度优先遍历
     * @param vertex
     * @return
     */
    Iterator DFSTraverse(Vertex vertex);

    /**
     * 对图进行广度优先遍历
     * @param vertex
     * @return
     */
    Iterator BFSTraverse(Vertex vertex);


    /**
     * 从顶点v到其他顶点的最短路径
     * @param vertex
     * @return
     */
    Iterator shortestPath(Vertex vertex);

    /**
     *
     * @throws UnsupportedOperationException
     */
    void generateMST() throws UnsupportedOperationException;

    /**
     * 有向图的拓扑序列，无向图不支持此操作
     * @return
     * @throws UnsupportedOperationException
     */
    Iterator toplogicalSort() throws UnsupportedOperationException;

    /**
     * 求有向无环图的关键路径，无向图不支持此操作
     * @throws UnsupportedOperationException
     */
    void cirticalPath() throws UnsupportedOperationException;
}

