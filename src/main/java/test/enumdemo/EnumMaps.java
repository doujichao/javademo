package test.enumdemo;

import java.util.EnumMap;
import java.util.Map;

import static test.enumdemo.AlarmPoints.BATHROOM;
import static test.enumdemo.AlarmPoints.KITCHEN;

public class EnumMaps {
    public static void main(String[] args){
        EnumMap<AlarmPoints,Command> em=new EnumMap<AlarmPoints, Command>(AlarmPoints.class);
        em.put(KITCHEN,() -> print("Kitchen fire"));
        em.put(BATHROOM,() -> print("bathroom alert"));

        for (Map.Entry<AlarmPoints,Command> e:em.entrySet()){
            print(e.getKey()+":");
            e.getValue().action();
        }
    }
    public static   void print(String msg){
        System.out.println(msg);
    }
}
