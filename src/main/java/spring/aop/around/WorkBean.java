package spring.aop.around;

public class WorkBean {

    public void doSomeWork(int no) {
        for (int x = 0; x < no; x++) {
            System.out.println(x);
        }
    }
}
