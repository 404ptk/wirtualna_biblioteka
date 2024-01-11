public class BookA {
    public String book_name;
    public String book_author;
    public int book_amount;

    public BookA(){

    }
    public BookA(String book_name, String book_author){
        setBook_name(book_name);
        setBook_author(book_author);
        setBook_amount(1);
    }
    public BookA(String book_name, String book_author, int book_amount){
        setBook_name(book_name);
        setBook_author(book_author);
        setBook_amount(book_amount);
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public int getBook_amount() {
        return book_amount;
    }

    public void setBook_amount(int book_amount) {
        this.book_amount = book_amount;
    }
}
