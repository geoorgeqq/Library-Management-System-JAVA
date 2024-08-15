
import java.sql.Connection;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DbFunctions db = new DbFunctions();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Password");
        String password = scanner.nextLine();
        Connection conn = db.connect_to_db("libraryDB", "postgres", password);
        if (!password.isEmpty()) {
            System.out.println("Welcome to my library management system!");
            while (true) {
                System.out.println("Available commands: ");
                System.out.println("1 - List books");
                System.out.println("2 - Add  book");
                System.out.println("3 - Checkout book");
                System.out.println("4 - Return book");
                System.out.println("5 - Quit");
                String command = scanner.nextLine();

                if (command.equals("5")) {
                    break;
                }
                if (command.equals("1")) {
                    db.listItems(conn,"library_table");
                } else if (command.equals("2")) {
                    System.out.println("Title:");
                    String title = scanner.nextLine();
                    System.out.println("Author:");
                    String author = scanner.nextLine();
                    db.insert_row(conn, "library_table", title, author);
                } else if (command.equals("3")) {
                    System.out.println("Available books: ");
                    db.listAvailableBooks(conn, "library_table");
                    System.out.println("Book id: ");
                    int bookID = Integer.parseInt(scanner.nextLine());
                    db.borrowBook(conn,"library_table",bookID);
                } else if(command.equals("4")){
                    System.out.println("Borrowed books: ");
                    db.listBorrowedBooks(conn, "library_table");
                    System.out.println("Book title:");
                    int bookID = Integer.parseInt(scanner.nextLine());
                    db.returnBook(conn,"library_table",bookID);
                }

            }
        }


    }
}