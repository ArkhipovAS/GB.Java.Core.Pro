package server;

import java.sql.*;

public class DBAuthService {
    private static Connection connection;
    private static Statement stmt;
//    private static String login;
//    private static String pass;
//    private static String nickname;

//    public DBAuthService(String login, String pass, String nickname) {
//        this.login = login;
//        this.pass = pass;
//        this.nickname = nickname;
//
//    }

    public static void connectDB() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        stmt = connection.createStatement();
    }
/*Добавление нового пользователя*/
    public void requestInsertDB(String loginDB, String passDB, String nicknameDB) {
        try {
            connectDB();
            System.out.println("DB connect");
            insertUserToDB(loginDB, passDB, nicknameDB);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnectDB();
            System.out.println("DB disconnect");
        }
    }

    public void insertUserToDB(String loginIns, String passIns, String nicknameIns) {
            String str = "INSERT INTO users (login, password, nickname) " +
                        "VALUES('" + loginIns + "', '" + passIns + "', '" + nicknameIns + "')";
        System.out.println(str);
        try {
//            stmt.executeUpdate("INSERT INTO users (login, password, nickname) VALUES("+ loginIns + "," + passIns + "," + nicknameIns + ")");
            stmt.executeUpdate(str);
            System.out.println("new user ADD to db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*Конец добавления нового пользователя*/

    /*Смена ника*/
    public void requestUpdateDB(String loginDB, String passDB, String nickDB) {
        try {
            connectDB();
            System.out.println("DB connect");
            updateUserToDB(loginDB, passDB, nickDB);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnectDB();
            System.out.println("DB disconnect");
        }

    }

    private void updateUserToDB(String loginUpd, String passUpd, String nicknameUpd) {
        String str = "UPDATE users SET nickname = '" + nicknameUpd + "' WHERE login = '" + loginUpd + "'";
        System.out.println(str);
        try {
            stmt.executeUpdate(str);
            System.out.println("user nick UPDATE to db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*Конец смены ника*/

    public String requestSelectDB(String loginDB, String passDB) {
        try {
            connectDB();
            System.out.println("DB connect");
            return selectUserToDB(loginDB, passDB);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            disconnectDB();
            System.out.println("DB disconnect");
        }

        return null;
    }

    private String selectUserToDB(String loginDB, String passDB) {
        String str = "SELECT * FROM users WHERE login = '" + loginDB + "' AND password = '" + passDB + "'";
        System.out.println(str);
        try {
//            stmt.executeUpdate("INSERT INTO users (login, password, nickname) VALUES("+ loginIns + "," + passIns + "," + nicknameIns + ")");
//            stmt.executeUpdate(str);
            ResultSet rs = stmt.executeQuery(str);
            System.out.println(rs.getString("login") + " "+ rs.getString("password") + " " + rs.getString("nickname"));
            return rs.getString("nickname");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void disconnectDB() {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}