package 内部类;

public class Egg {
    private Yolk yolk=new Yolk();

    protected class Yolk{
        public Yolk(){
            System.out.println("new Egg.Yolk()");
        }

        public void f(){
            System.out.println("Egg.Yolk.f()");
        }
    }
    public Egg(){
        System.out.println("new Egg()");
    }
    public void insertYolk(Yolk yy){
        yolk=yy;
    }
    public void g(){yolk.f();}
}
