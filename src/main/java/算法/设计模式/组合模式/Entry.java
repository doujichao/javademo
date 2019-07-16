package 算法.设计模式.组合模式;

public abstract class Entry {

    /**
     * 获取名字
     * @return
     */
    public abstract String getName();
    public abstract int getSize();
    public Entry add(Entry entry) throws FileTreatmentException {
        throw new FileTreatmentException();
    }

    public void printList(){
        printList("");
    }

    protected abstract void printList(String prefix);

    @Override
    public String toString() {
        return getName()+" ("+getSize()+") ";
    }
}
