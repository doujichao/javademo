package 算法.设计模式.状态模式;

public class DayState implements State {

    private static DayState singleton=new DayState();

    private DayState(){}

    public static State getInstance(){
        return singleton;
    }

    @Override
    public void doClock(Context context, int hour) {
        if (hour<9||hour>=17){
            context.changeState(NightState.getInstance());
        }else if (hour==12){
            context.changeState(NoonState.getInstance());
        }
    }

    @Override
    public void doUse(Context context) {
        context.recordLog("使用金库（白天）");
    }

    @Override
    public void doAlarm(Context context) {
        context.callSecurityCenter("按下警铃（白天）");
    }

    @Override
    public void doPhone(Context context) {
        context.callSecurityCenter("正常通话");
    }

    @Override
    public String toString() {
        return "[白天]";
    }
}
