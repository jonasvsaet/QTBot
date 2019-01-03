package net.ddns.jonasvansaet.DA;

import net.ddns.jonasvansaet.Objects.Prefix;
import net.ddns.jonasvansaet.utils.Config;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jonas on 1/02/2017.
 */
public class DAPrefix {
    private Connection connection = null;

    public DAPrefix () {
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

    public Prefix getPrefixById (String serverId) {
        Prefix result = new Prefix();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM prefix WHERE serverId = ?");
            statement.setString(1, serverId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.setId(resultSet.getString(1));
                result.setPrefix(resultSet.getString(2));
                result.setEnabled(resultSet.getBoolean(3));
            }
        } catch (Exception e){
            System.out.println(e);
        } finally {
            try {
                connection.close();
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("error while getting quote by name: " + e);
            }
        }

        return result;
    }

    public ArrayList<Prefix> getAllPrefixes() {
        ArrayList<Prefix> result = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM prefix");
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Prefix prefix = new Prefix();

                prefix.setId(resultSet.getString(1));
                prefix.setPrefix(resultSet.getString(2));
                prefix.setEnabled(resultSet.getBoolean(3));

                result.add(prefix);
            }
        } catch (Exception e) {
            System.out.println(e);
        }finally {
            try {
                connection.close();
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                System.out.println("error while getting quote by name: " + e);
            }
        }

        return result;
    }

    public void createPrefix (String serverId, String prefix, boolean welcomeEnabled){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO prefix (serverId, prefix, enabled) VALUES (?, ?, ?)");
            statement.setString(1, serverId);
            statement.setString(2, prefix);
            statement.setBoolean(3, welcomeEnabled);
            statement.execute();
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean serverExists (String serverId){
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        boolean result = false;

        try {
            statement = connection.prepareStatement("SELECT * FROM prefix WHERE serverId = ?");
            statement.setString(1, serverId);
            resultSet = statement.executeQuery();

            if (resultSet.next()){
                result = true;
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                statement.close();
            } catch (Exception e){
                System.out.println(e);
            }
        }

        return result;
    }

    public void updatePrefix(String serverId, String prefix){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE prefix SET prefix = ? WHERE serverId = ?;");
            statement.setString(1, prefix);
            statement.setString(2, serverId);
            statement.execute();
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void updatePrefixEnabled(String serverId, boolean welcomeEnabled){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE prefix SET enabled = ? WHERE serverId = ?;");
            statement.setBoolean(1, welcomeEnabled);
            statement.setString(2, serverId);
            statement.execute();
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean welcomeEnabled (String serverId) {
        return true;
    }

}
