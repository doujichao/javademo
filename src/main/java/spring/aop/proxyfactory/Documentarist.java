package spring.aop.proxyfactory;

public class Documentarist {
    protected GrammyGuitarist guitarist;

    public void execute() {
        guitarist.sing();
        guitarist.talk();
    }

    public void setDep(GrammyGuitarist guitarist) {
        this.guitarist = guitarist;
    }
}
