package demo.enumdemo;

import java.util.EnumSet;
import java.util.Iterator;

public class EnumSets {

    public static void main(String[] args){
        EnumSet<AlarmPoints> points=EnumSet.noneOf(AlarmPoints.class);
        points.add(AlarmPoints.BATHROOM);
        print(points);
        points.addAll(EnumSet.of(AlarmPoints.STATI1, AlarmPoints.STATI2, AlarmPoints.KITCHEN));
        print(points);
        points=EnumSet.allOf(AlarmPoints.class);
        points.removeAll(EnumSet.of(AlarmPoints.STATI1, AlarmPoints.STATI2, AlarmPoints.KITCHEN));
        print(points);
        points=EnumSet.complementOf(points);
        print(points);
        System.out.println(-points.size());
        System.out.println(-256 >>> -3);
        System.out.println(256 >> 3);
    }

    private static int i=1;

    private static void print(EnumSet<AlarmPoints> points) {
        Iterator<AlarmPoints> it = points.iterator();
        System.out.print("["+i++);
        while (it.hasNext()){
            AlarmPoints alarmPoints = it.next();
            System.out.print(", "+alarmPoints.name());
        }
        System.out.println("]");
    }

}
