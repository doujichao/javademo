package 算法.设计模式.解释器模式;

import java.text.ParseException;

/**
 * 	<primitive command >::= go | right | left
 */
public class PrimitiveCommandNode extends Node {

    private String name;

    @Override
    public void parse(Context context) throws ParseException {
        name=context.currentToken();
        context.skipToken(name);
        if (!name.equals("go") && !name.equals("right") && !name.equals("left")){
            throw new ParseException(name+"is undefinded",0);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
