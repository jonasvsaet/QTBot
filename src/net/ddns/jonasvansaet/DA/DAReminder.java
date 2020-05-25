package net.ddns.jonasvansaet.DA;

import net.ddns.jonasvansaet.Objects.Quote;
import net.ddns.jonasvansaet.Objects.Reminders;
import net.ddns.jonasvansaet.utils.Config;

import java.sql.*;
import java.util.ArrayList;

public class DAReminder {
    private Connection connection = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public DAReminder () {
        try {
            connection= DriverManager.getConnection(
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

    public ArrayList<Reminders> getReminders(){
        ArrayList<Reminders> result = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.prepareStatement("SELECT * FROM reminder");
            resultSet =  statement.executeQuery();

            while (resultSet.next()) {

                Reminders reminder = new Reminders();
                reminder.setId(resultSet.getLong(1));
                reminder.setUserId(resultSet.getString(2));
                reminder.setReminder(resultSet.getString(3));
                reminder.setTime(resultSet.getTimestamp(4));
                reminder.setChannelId(resultSet.getString(5));

                result.add(reminder);
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
                System.out.println("error while getting reminders");
            }
        }
        return result;
    }

    public void insertReminder(Reminders reminders){
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement("INSERT INTO reminder (userid, reminder, time, channelid) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, reminders.getUserId());
            statement.setString(2, reminders.getReminder());
            statement.setTimestamp(3, reminders.getTime());
            statement.setString(4, reminders.getChannelId());
            statement.execute();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                reminders.setId(generatedKeys.getLong(1));
            }

            connection.close();
        } catch (Exception e) {
            System.out.println("Error while inserting quote: " + e);
        }
    }

    public void removeReminder(long id){
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement("DELETE FROM reminder WHERE Id = ?");
            statement.setLong(1, id);
            statement.execute();

            connection.close();
        } catch (Exception e) {
            System.out.println("failed removing reminder: " + e);
        }
    }
}
