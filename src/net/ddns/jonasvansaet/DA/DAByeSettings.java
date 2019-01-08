package net.ddns.jonasvansaet.DA;


import net.ddns.jonasvansaet.Objects.ByeSettings;
import net.ddns.jonasvansaet.utils.Config;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by jonas on 1/02/2017.
 */
public class DAByeSettings {
    private Connection connection = null;

    public DAByeSettings () {
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

    public ByeSettings getSettingsByServerId (String serverId) {
        ByeSettings result = new ByeSettings();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM byesettings WHERE serverId = ?");
            statement.setString(1, serverId);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                result.setServerId(resultSet.getString(1));
                result.setByeEnabled(resultSet.getBoolean(2));
                result.setByeMessage(resultSet.getString(3));
                result.setByeChannelId(resultSet.getString(4));
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

    public ArrayList<ByeSettings> getAllByeSettings() {
        ArrayList<ByeSettings> result = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM byesettings");
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                ByeSettings byeSettings = new ByeSettings();

                byeSettings.setServerId(resultSet.getString(1));
                byeSettings.setByeEnabled(resultSet.getBoolean(2));
                byeSettings.setByeMessage(resultSet.getString(3));
                byeSettings.setByeChannelId(resultSet.getString(4));

                result.add(byeSettings);
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

    public void createServer (String serverId, boolean byeEnabled, String byeMessage, String byeChannelId){
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement("INSERT INTO byesettings (serverId, byeenabled, byemessage, byechannelid) VALUES (?, ?, ?, ?)");
            statement.setString(1, serverId);
            statement.setBoolean(2, byeEnabled);
            statement.setString(3, byeMessage);
            statement.setString(4, byeChannelId);
            statement.execute();
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean serverExists (String serverId){
        PreparedStatement statement = null;
        ResultSet resultSet;
        boolean result = false;

        try {
            statement = connection.prepareStatement("SELECT * FROM byesettings WHERE serverId = ?");
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

    public void updateByeMessage(String serverId, String byeMessage, String byeChannelID){
        PreparedStatement statement;

        try {
            statement = connection.prepareStatement("UPDATE byesettings SET byemessage = ? , byechannelid = ? WHERE serverId = ?;");
            statement.setString(1, byeMessage);
            statement.setString(2, byeChannelID);
            statement.setString(3, serverId);
            statement.execute();
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public void updateByeMessageEnabled(String serverId, boolean byeEnabled, String byeChannelId){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("UPDATE byesettings SET byeenabled = ?, byechannelid = ? WHERE serverId = ?;");
            statement.setBoolean(1, byeEnabled);
            statement.setString(2, byeChannelId);
            statement.setString(3, serverId);
            statement.execute();
            connection.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

}
