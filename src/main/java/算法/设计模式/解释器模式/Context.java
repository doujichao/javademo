package 算法.设计模式.解释器模式;

import java.text.ParseException;
import java.util.StringTokenizer;

public class Context {

    private StringTokenizer tokenizer;
    private String currentToken;

    public Context(String text) {
        tokenizer=new StringTokenizer(text);
        nextToken();
    }

    public void skipToken(String program) throws ParseException {
        if (!program.equals(currentToken)){
            throw new ParseException("Waring:"+tokenizer+" is expected,but "
                    +currentToken+" is found.",0);
        }
        nextToken();
    }

    public String currentToken() {
        return currentToken;
    }

    public int currentNumber() throws ParseException {
        int number=0;
        try {
            number=Integer.parseInt(currentToken);
        }catch (NumberFormatException e){
            throw new ParseException("Waring:"+e,0);
        }
        return number;
    }

    public String nextToken() {
        if (tokenizer.hasMoreTokens()){
            currentToken=tokenizer.nextToken();
        }else {
            currentToken=null;
        }
        return currentToken;
    }

}
