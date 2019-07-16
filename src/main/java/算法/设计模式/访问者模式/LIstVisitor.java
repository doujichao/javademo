package 算法.设计模式.访问者模式;

import java.util.Iterator;

public class LIstVisitor extends Visitor {

    private String currentdir="";

    @Override
    public void visit(File file) {
        System.out.println(currentdir+"/"+file);
    }

    @Override
    public void visit(Directory directory) {
        System.out.println(currentdir+"/"+directory);
        String savedir=currentdir;
        currentdir=currentdir+"/"+directory.getName();
        Iterator iterator = directory.iterator();
        while (iterator.hasNext()){
            Entry entry= (Entry) iterator.next();
            entry.accept(this);
        }
        currentdir=savedir;
    }
}
