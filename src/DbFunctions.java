import java.sql.*;


public class DbFunctions {

    public Connection connect_to_db(String dbName, String user, String password) {
        Connection conn = null;
        try {
            String urlDb = "jdbc:postgresql://localhost:5432/" + dbName;
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(urlDb, user, password);
        } catch (Exception e) {
            System.out.println();
        }
        return conn;
    }

    public void insert_row(Connection conn, String table_name, String title, String author) {
        Statement statement;

        try {
            String query = String.format("INSERT INTO " + table_name + " (title,author,isavailable)" +
                    " VALUES ('" + title + "','" + author + "'," + "'1')");
            statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Book added succesfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void listItems(Connection conn, String table_name) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + table_name + " ORDER BY book_id";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                int id = resultSet.getInt("book_id");
                boolean available = resultSet.getBoolean("isAvailable");
                String showAvailable = "";
                if (available) {
                    showAvailable = "✔";
                } else {
                    showAvailable = "✖";
                }
                System.out.println("Title: " + title + "\n" + "Author: " + author + "\nID: " + id + "\n" + "Avabiliy: " + showAvailable + "\n---------");
            }

        } catch (Exception e) {
            System.out.println();
        }
    }

    public void listAvailableBooks(Connection conn, String table_name) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + table_name + " ORDER BY book_id";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                boolean available = resultSet.getBoolean("isAvailable");
                int id = resultSet.getInt("book_id");
                String showAvailable = "";
                if (available) {
                    System.out.println("Title: " + title + "\n" + "Author: " + author + "\n" + "ID: " + id + "\n---------");
                }

            }

        } catch (Exception e) {
            System.out.println();
        }
    }

    public void listBorrowedBooks(Connection conn, String table_name) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + table_name + " ORDER BY book_id";
            statement = conn.createStatement();
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                boolean available = resultSet.getBoolean("isAvailable");
                int id = resultSet.getInt("book_id");
                String showAvailable = "";
                if (!available) {
                    System.out.println("Title: " + title + "\n" + "Author: " + author + "\n" + "ID: " + id + "\n---------");
                }

            }

        } catch (Exception e) {
            System.out.println();
        }
    }

    public void borrowBook(Connection conn, String table_name, int id) {
        Statement statement1;
        Statement statement2;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + table_name + " WHERE book_id = '" + id + "'";
            statement1 = conn.createStatement();
            resultSet = statement1.executeQuery(query);
            while (resultSet.next()) {
                boolean isAvailable = resultSet.getBoolean("isAvailable");
                if (isAvailable) {
                    String updateQuery = "UPDATE " + table_name + " SET " + "isAvailable = '0' WHERE book_id ='" + id + "'";
                    System.out.println("Book borrowed!");
                    statement2 = conn.createStatement();
                    statement2.executeQuery(updateQuery);
                } else {
                    System.out.println("This book is already borrowed");
                }
            }

        } catch (Exception e) {
        }
    }

    public void returnBook(Connection conn, String table_name, int id) {
        Statement statement1;
        Statement statement2;
        ResultSet resultSet = null;
        try {
            String query = "SELECT * FROM " + table_name + " WHERE book_id = '" + id + "'";
            statement1 = conn.createStatement();
            resultSet = statement1.executeQuery(query);
            while (resultSet.next()) {
                boolean isAvailable = resultSet.getBoolean("isAvailable");
                if (!isAvailable) {
                    String updateQuery = "UPDATE " + table_name + " SET " + "isAvailable = '1' WHERE book_id ='" + id + "'";
                    System.out.println("Book returned!");
                    statement2 = conn.createStatement();
                    statement2.executeQuery(updateQuery);
                } else {
                    System.out.println("This book is already on the bookshelf!");
                }
            }

        } catch (Exception e) {
        }
    }

}
