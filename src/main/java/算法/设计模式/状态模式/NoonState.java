package 算法.设计模式.状态模式;

public class NoonState implements State {

    private NoonState(){}

    private static NoonState instance=new NoonState();

    public static NoonState getInstance() {
        return instance;
    }

    @Override
    public void doClock(Context context, int hour) {
        if (hour<9||hour>=17){
            context.changeState(NightState.getInstance());
        }else if (hour<12||hour>=13){
            context.changeState(DayState.getInstance());
        }
    }

    @Override
    public void doUse(Context context) {
        context.callSecurityCenter("午餐时间使用金库");
    }

    @Override
    public void doAlarm(Context context) {
        context.callSecurityCenter("午餐时间按下警铃");
    }

    @Override
    public void doPhone(Context context) {
        context.recordLog("午餐时间呼叫报警电话");
    }

    @Override
    public String toString() {
        return "[午餐时间]";
    }
}
