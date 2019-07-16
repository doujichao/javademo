package 网络编程和IO.集合;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListIteration {
    public static void main(String[] args){
        List<String> list=new ArrayList<>();
        for (int i=0;i<12;i++){
            list.add(""+i);
        }
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next()+", "+iterator.nextIndex()+
                    ". "+iterator.previousIndex()+"; ");
        }
        System.out.println();
        while (iterator.hasPrevious()){
            System.out.println(iterator.previous()+" ");
        }
    }
}
