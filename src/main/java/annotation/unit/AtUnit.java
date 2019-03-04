package annotation.unit;

import net.mindview.util.ProcessFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AtUnit implements ProcessFiles.Strategy {

    static Class<?> testClass;
    static List<String> failedTests=new ArrayList<>();
    static long testRun=0;
    static long failures=0;

    public static void main(String[] args){
        ClassLoader.getSystemClassLoader()
                .setDefaultAssertionStatus(true);
        new ProcessFiles(new AtUnit(),"class").start(args);
        if (failures == 0){
            System.out.println("Ok ("+testRun+" tests)");
        }else {
            System.out.println("("+testRun+" tests)");
            System.out.println("\n>>> "+failures+" FAILURE"+(failures >1 ? "S":"")+" <<<");
            for (String failed: failedTests){
                System.out.println(" "+failed);
            }
        }
    }

    @Override
    public void process(File file) {

    }
}
