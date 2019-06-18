package 算法.数据结构.树;

/**
 * 红黑树颜色规则：
 * 1、每一个节点不是红色，就是黑色
 * 2、根节点总是黑色的
 * 3、如果节点时红色的，那么它的子节点必须是黑色的（反之倒不一定）
 * 4、从根到叶节点或空姐点的每条路径，必须包含相同数量的黑色节点
 */
public class RedBlackTree extends Tree {

    /**
     * 1、只要有一个黑色节点有两个红色节点
     *
     * @param id 节点包含int数据
     * @param dd 节点包含double数据
     */
    @Override
    public void insert(int id, double dd) {
        Node temp = root;
        Node current = null;
        if (temp == null) {
            root = new Node(id, dd);
            root.changeColor();
            return;
        }
        Node nowNode = new Node(id, dd);
        while (true) {
            current = temp;
            if (id >= temp.iData) {
                temp = temp.rightChild;
                if (temp == null) {
                    current.rightChild = nowNode;
                    nowNode.parent = current;
                    break;
                }
            } else if (id < temp.iData) {
                temp = temp.leftChild;
                if (temp == null) {
                    current.leftChild = nowNode;
                    nowNode.parent = current;
                    break;
                }
            }
        }
        fixRedBlackTree(nowNode);
    }

    private void fixRedBlackTree(Node current) {
        //循环遍历到根节点
        while (current != root) {
            //如果当前节点为红色节点的话
            if (current.color) {
                Node parent = current.parent;
                //如果父节点为黑色节点的话直接返回
                if (!parent.color) {
                    current = current.parent;
                    break;
                }//此时父亲节点为红色
                else {
                    //祖父节点
                    Node pparent = parent.parent;
                    //当父亲节点为祖父节点的左节点
                    if (parent == pparent.leftChild) {
                        //如果叔叔节点为红色
                        if (parent.rightChild != null && pparent.rightChild.color) {
                            changeColor(pparent);
                        } else {
                            //解决内侧子孙问题
                            if (current == parent.rightChild) {

                            } else {

                            }
                        }
                    } else
                        //当父亲节点为祖父节点的右节点
                        if (parent == pparent.rightChild) {
                            //如果叔叔节点为红色
                            if (parent.leftChild != null && pparent.leftChild.color) {
                                changeColor(pparent);
                            } else {
                                //解决内侧子孙问题
                                if (current == parent.leftChild) {

                                } else {

                                }
                            }
                        }

                }

            }
            //父亲节点为黑色节点或者执行完if条件判断
            current = current.parent;
        }
    }

    /**
     * 改变颜色
     *
     * @param pparent 祖父节点
     */
    private void changeColor(Node pparent) {
        pparent.changeColor();
        pparent.rightChild.changeColor();
        pparent.leftChild.changeColor();
    }
}
