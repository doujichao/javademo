package enumdemo;

import java.util.Random;

enum Outcome{WIN,LOSE,DRAW}

interface Item{
    Outcome compete(Item item);
    Outcome eval(Paper item);
    Outcome eval(Scissors item);
    Outcome eval(Rock item);
}
class Scissors implements Item{
    @Override
    public String toString() {
        return "Scissors";
    }

    @Override
    public Outcome compete(Item item) {
        return item.eval(this);
    }

    @Override
    public Outcome eval(Paper item) {
        return Outcome.LOSE;
    }

    @Override
    public Outcome eval(Scissors item) {
        return Outcome.DRAW;
    }

    @Override
    public Outcome eval(Rock item) {
        return Outcome.WIN;
    }
}

class Rock implements Item{
    @Override
    public String toString() {
        return "Rock";
    }

    @Override
    public Outcome compete(Item item) {
        return item.eval(this);
    }

    @Override
    public Outcome eval(Paper item) {
        return Outcome.WIN;
    }

    @Override
    public Outcome eval(Scissors item) {
        return Outcome.LOSE;
    }

    @Override
    public Outcome eval(Rock item) {
        return Outcome.DRAW;
    }
}

class Paper implements Item{

    @Override
    public String toString() {
        return "Paper";
    }

    @Override
    public Outcome compete(Item item) {
        return item.eval(this);
    }

    @Override
    public Outcome eval(Paper item) {
        return Outcome.DRAW;
    }

    @Override
    public Outcome eval(Scissors item) {
        return Outcome.WIN;
    }

    @Override
    public Outcome eval(Rock item) {
        return Outcome.LOSE;
    }
}

public class RoShamBo1 {
    static final int SIZE=20;
    private static Random rand=new Random(47);
    public static Item newItem(){
        switch (rand.nextInt(3)){
            default:
            case 0: return new Scissors();
            case 1: return new Paper();
            case 2: return new Rock();
        }
    }

    public static void match(Item a,Item b){
        System.out.println(a + " VS " + b
        +": "+a.compete(b));
    }

    public static void main(String[] args){
        for (int i=0;i< SIZE;i ++){
            match(newItem(),newItem());
        }
    }

}
