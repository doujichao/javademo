package 算法.设计模式.解释器模式;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * <command list>::=<command>* end
 */
public class CommandListNode extends Node {

    private ArrayList list=new ArrayList();

    @Override
    public void parse(Context context) throws ParseException {
        while (true){
            if (context.currentToken()==null){
                throw new ParseException("Missing 'end'",0);
            }else if (context.currentToken().equals("end")){
                context.skipToken("end");
                break;
            }else {
                Node commandNode=new CommandNode();
                commandNode.parse(context);
                list.add(commandNode);
            }
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
