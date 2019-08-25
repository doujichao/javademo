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

    
}
