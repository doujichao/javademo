package 算法.设计模式.解释器模式;

import java.text.ParseException;

/**
 * 	<repeat command>::= repeat <number> <command list>
 */
public class RepeatCommandNode extends Node {

    private int number;

    private Node commandListNode;

    @Override
    public void parse(Context context) throws ParseException {
        context.skipToken("repeat");
        number=context.currentNumber();
        context.nextToken();
        commandListNode=new CommandListNode();
        commandListNode.parse(context);
    }

    @Override
    public String toString() {
        return "[repeat "+number+" "+commandListNode+"]";
    }
}
