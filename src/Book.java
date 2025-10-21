import structure.IBSTData;

/**
 * Just class for testing structures, will delete this later, but already using MyInteger (it is the same)
 */
public class Book implements IBSTData<Book> {
    private int id;

    public Book(int id) {
        this.setId(id);
    }

    @Override
    public int compareTo(Book comparedBook) {
        return Integer.compare(this.id, comparedBook.getId());
    }

    // implements Comparable<Book>
//    @Override
//    public int compareTo(structure.BSTNode<Book> comparedNode) {
//        System.out.println("Compare to book with parameter structure.BSTNode<Book>");
//        return Integer.compare(this.id, comparedNode.getData().getId());
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
