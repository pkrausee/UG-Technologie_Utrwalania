package pl.edu.ug.tud.jdbcdemo;

import java.net.ConnectException;
import java.sql.*;

public class Main {

  public static void main(String[] args) throws SQLException {

    final String DB_URL = "jdbc:hsqldb:hsql://localhost/workdb";
    final String CREATE_TABLE_PERSON = "CREATE TABLE Person (id bigint, name varchar(40), yob int)";

    Connection connection = DriverManager.getConnection(DB_URL);

    Statement statement = connection.createStatement();

    ResultSet rs = connection.getMetaData().getTables(null, null, null, null);

    boolean tableExists = false;

    while (rs.next()) {
      if ("Person".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
        tableExists = true;
        break;
      }
    }

    if (!tableExists)
      statement.executeUpdate(CREATE_TABLE_PERSON);
  }
}
