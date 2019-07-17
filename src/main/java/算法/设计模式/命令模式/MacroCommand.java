package 算法.设计模式.命令模式;

import java.util.Iterator;
import java.util.Stack;

/**
 * 多条指令
 */
public class MacroCommand implements Command {
    //命令的集合
    private Stack commands=new Stack();

    @Override
    public void execute() {
        Iterator it = commands.iterator();
        while (it.hasNext()){
            Command command= (Command) it.next();
            command.execute();
        }
    }

    /**
     * 新增明林
     * @param command 新命令
     */
    public void append(Command command){
        if (!(command instanceof MacroCommand)){
            commands.push(command);
        }
    }

    /**
     * 撤销命令
     */
    public void undo(){
        if (!commands.empty()){
            commands.pop();
        }
    }

    /**
     * 删除所有命令
     */
    public void clear(){
        commands.clear();
    }
}
