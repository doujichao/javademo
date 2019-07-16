package 算法.设计模式.迭代器模式;

/**
 * 书籍遍历的迭代器
 */
public class BookShelfIterator implements Iterator {

    private BookShelf bookShelf;

    private int index;

    BookShelfIterator(BookShelf bookShelf){
        this.bookShelf=bookShelf;
        this.index=0;
    }

    @Override
    public boolean hasNext() {
        return index < bookShelf.getLength();
    }

    @Override
    public Object next() {
        Book book = bookShelf.getBookAt(index);
        index++;
        return book;
    }
}
