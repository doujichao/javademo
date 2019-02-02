package 内部类;

public class BigEgg extends Egg{
    public class Yolk extends Egg.Yolk{
        public Yolk(){
            System.out.println("new BigEgg.Yolk()");
        }
        public void f(){
            System.out.println("BigEgg.Yolk.f()");
        }
    }
    public BigEgg(){
        insertYolk(new Yolk());
    }
    public static void main(String[] args){
        Egg e=new BigEgg();
        e.g();
    }
}
