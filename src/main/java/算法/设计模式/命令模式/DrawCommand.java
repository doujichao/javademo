package 算法.设计模式.命令模式;

import java.awt.*;

public class DrawCommand implements Command {
    //绘制对象
    protected Drawable drawable;
    //绘制位置
    private Point position;
    public DrawCommand(Drawable drawable,Point point){
        this.drawable=drawable;
        this.position=point;
    }
    @Override
    public void execute() {
        drawable.draw(position.x,position.y);
    }
}
