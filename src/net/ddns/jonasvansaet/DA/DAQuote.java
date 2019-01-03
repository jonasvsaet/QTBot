package net.ddns.jonasvansaet.DA;
import net.ddns.jonasvansaet.Objects.Quote;
import net.ddns.jonasvansaet.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by jonas on 12/18/2016.
 */
public class DAQuote {
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public DAQuote () {
        try {
            connection=DriverManager.getConnection(
                    Config.dbUrl,
                    Config.dbUser,
                    Config.dbPassword);
        } catch(Exception e) {
            System.out.println("Error in DB Connection " + e);
        }
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public  ArrayList<Quote> getQuoteByName(String name){
        ArrayList<Quote> result = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM quote WHERE name = ?");
            statement.setString(1, name);
            resultSet =  statement.executeQuery();

            while (resultSet.next()) {

                Quote quote = new Quote();
                quote.setId(resultSet.getInt(1));
                quote.setName(resultSet.getString(2));
                quote.setContent(resultSet.getString(3));

                result.add(quote);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("error while getting quote by name: " + e);
            }
        }
        return result;
    }

    public void insertQuote(Quote quote){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO quote(id, name, content) VALUES (NULL, ?, ?)");
            statement.setString(1, quote.getName());
            statement.setString(2, quote.getContent());
            statement.execute();
            connection.close();
        } catch (Exception e) {
            System.out.println("Error while inserting quote: " + e);
        }
    }

}
