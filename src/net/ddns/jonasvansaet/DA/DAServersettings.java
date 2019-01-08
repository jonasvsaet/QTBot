package net.ddns.jonasvansaet.DA;

import net.ddns.jonasvansaet.Objects.Serversettings;
import net.ddns.jonasvansaet.utils.Config;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jonas on 1/02/2017.
 */
public class DAServersettings {
    private Connection connection = null;

    public DAServersettings () {
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

    public Serversettings getSettingsByServerId (String serverId) {
        Serversettings result = new Serversettings();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM serversettings WHERE serverId = ?");
            statement.setString(1, serverId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.setServerId(resultSet.getString(1));
                result.setWelcomeEnabled(resultSet.getBoolean(2));
                result.setWelcomeMessage(resultSet.getString(3));
                result.setWelcomeChannelId(resultSet.getString(4));
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

    public ArrayList<Serversettings> getAllServerSettings() {
        ArrayList<Serversettings> result = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM serversettings");
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Serversettings serversettings = new Serversettings();

                serversettings.setServerId(resultSet.getString(1));
                serversettings.setWelcomeEnabled(resultSet.getBoolean(2));
                serversettings.setWelcomeMessage(resultSet.getString(3));
                serversettings.setWelcomeChannelId(resultSet.getString(4));

                result.add(serversettings);
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

    public void createServer (String serverId, boolean welcomeEnabled, String welcomeMessage, String welcomeChannelId){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO serversettings (serverId, welcomeEnabled, WelcomeMessage, WelcomeChannelId) VALUES (?, ?, ?, ?)");
            statement.setString(1, serverId);
            statement.setBoolean(2, welcomeEnabled);
            statement.setString(3, welcomeMessage);
            statement.setString(4, welcomeChannelId);
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
            statement = connection.prepareStatement("SELECT * FROM serversettings WHERE serverId = ?");
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

    public void updateWelcomeMessage(String serverId, String welcomeMessage, String welcomeChannelId){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE serversettings SET WelcomeMessage = ? , WelcomeChannelId = ? WHERE serverId = ?;");
            statement.setString(1, welcomeMessage);
            statement.setString(2, welcomeChannelId);
            statement.setString(3, serverId);
            statement.execute();
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateWelcomeMessageEnabled(String serverId, boolean welcomeEnabled, String channelId){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE serversettings SET welcomeEnabled = ?, WelcomeChannelId = ? WHERE serverId = ?;");
            statement.setBoolean(1, welcomeEnabled);
            statement.setString(2, channelId);
            statement.setString(3, serverId);
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
