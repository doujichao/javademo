package 网络编程和IO.集合;

import java.util.*;

public class Countries {
    /**
     * 数据实体
     */
    public static final String[][] DATA={
            {"China","Beijing"},{"USA","NewYork"},
            {"Benin","Porto"},{"Algeria","Algiers"},
            {"Cameroon","Yaounde"},{"Chad","N'djamena"}
    };

    private static class FlyweightMap extends AbstractMap<String,String>{

        private static class Entry implements Map.Entry<String,String>{

            /**
             * Entry的下标
             */
            int index;

            Entry(int index){
                this.index=index;
            }

            @Override
            public boolean equals(Object o) {
                return DATA[index][0].equals(o);
            }

            @Override
            public int hashCode() {
                return DATA[index][0].hashCode();
            }

            @Override
            public String getKey() {
                return DATA[index][0];
            }

            @Override
            public String getValue() {
                return DATA[index][1];
            }

            @Override
            public String setValue(String value) {
                throw new UnsupportedOperationException();
            }
        }

        static class EntrySet extends AbstractSet<Map.Entry<String,String>>{

            /**
             * entrySet的大小
             */
            private int size;

            EntrySet(int size){
                if (size<0){
                    this.size=0;
                }
                else if(size > DATA.length){
                    this.size=DATA.length;
                }
                else
                    this.size=size;
            }

            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                return new Iter();
            }

            private class Iter implements Iterator<Map.Entry<String, String>>{

                /**
                 * 初始化实体对象下标为-1
                 */
                private Entry entry=new Entry(-1);

                @Override
                public boolean hasNext() {
                    return entry.index < size -1;
                }

                @Override
                public Map.Entry<String, String> next() {
                    entry.index++;
                    return entry;
                }

                public void remove(){
                    throw new UnsupportedOperationException();
                }
            }

            @Override
            public int size() {
                return size;
            }

        }
        /**
         * 实体操作类
         */
        private static Set<Map.Entry<String, String>> entries=new EntrySet(DATA.length);

        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return entries;
        }
    }

    static Map<String,String> select(final int size){
        return new FlyweightMap(){
            public Set<Map.Entry<String, String>> entrySet(){
                return new EntrySet(size);
            }
        };
    }

    static Map<String, String> map =new FlyweightMap();
    public static Map<String,String> capitals(){
        return map;
    }
    public static Map<String,String> capitals(int size){
        return select(size);
    }

    static List<String> names=new ArrayList<>(map.keySet());
    public static List<String> names(){
        return names;
    }
    public static List<String> names(int size){
        return new ArrayList<>(select(size).keySet());
    }

    public static void main(String[] args){
        System.out.println(capitals(2));
    }
}
