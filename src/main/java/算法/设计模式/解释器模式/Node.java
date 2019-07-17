package 算法.设计模式.解释器模式;

import java.text.ParseException;

/**
 * 表示语法树的最顶层类
 */
public abstract class Node {
    public abstract void parse(Context context) throws ParseException;
}
