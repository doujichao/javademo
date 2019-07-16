package 算法.设计模式.访问者模式;

import 算法.设计模式.组合模式.FileTreatmentException;

import java.util.Iterator;

/**
 * 文件和文件夹的抽象类
 */
public abstract class Entry implements Element{

    public abstract String getName();

    public abstract int getSize();

    public Entry add(Entry entry) throws FileTreatmentException{
        throw new FileTreatmentException();
    }

    public Iterator iterator() throws FileTreatmentException{
        throw new FileTreatmentException();
    }

    @Override
    public String toString() {
        return getName()+"("+getSize()+")";
    }
}
