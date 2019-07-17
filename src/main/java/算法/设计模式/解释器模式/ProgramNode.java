package 算法.设计模式.解释器模式;

import java.text.ParseException;

/**
 * <program> ::=program<command list>
 */
public class ProgramNode extends Node {

    private Node commandListNode;

    @Override
    public void parse(Context context) throws ParseException {
        context.skipToken("program");
        commandListNode=new CommandListNode();
        commandListNode.parse(context);
    }

    @Override
    public String toString() {
        return "[program"+commandListNode+"]";
    }
}
