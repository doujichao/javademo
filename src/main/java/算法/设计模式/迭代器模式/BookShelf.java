package 算法.设计模式.迭代器模式;

/**
 * 表示书架的类
 */
public class BookShelf implements Aggregate{

    /**
     * 书架的数组
     */
    private Book[] books;
    /**
     * 最后一本书
     */
    private int last=0;

    /**
     * 传入一个数字初始化书柜
     * @param maxsize 书柜大小
     */
    public BookShelf(int maxsize){
        books=new Book[maxsize];
    }

    /**
     * 获取第几本书
     * @param index 书籍的编号
     * @return 书籍
     */
    public Book getBookAt(int index){
        return books[index];
    }

    public void appendBook(Book book){
        if (last==books.length){
            System.out.println("书柜已满");
        }else {
            this.books[last]=book;
            last++;
        }
    }

    public int getLength(){
        return last;
    }

    @Override
    public Iterator iterator() {
        return new  BookShelfIterator(this);
    }
}
