package net.ddns.jonasvansaet.DA;

import net.ddns.jonasvansaet.Objects.ByeSettings;
import net.ddns.jonasvansaet.Objects.IgnoredServers;
import net.ddns.jonasvansaet.utils.Config;

import java.sql.*;
import java.util.ArrayList;

public class DAIgnoredServers {
    private Connection connection = null;

    public DAIgnoredServers () {
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

    public ArrayList<IgnoredServers> getAllIgnoredServers(){
        ArrayList<IgnoredServers> result = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;

        try{
            statement = connection.prepareStatement("SELECT * FROM ignoredServers WHERE enabled = 1");
            rs = statement.executeQuery();

            while(rs.next()){
                IgnoredServers is = new IgnoredServers();

                is.setServerId(rs.getString(1));
                is.setEnabled(rs.getBoolean(2));

                result.add(is);
            }

        } catch (Exception e){
            System.out.println(e);
        } finally {
            try{
                statement.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return result;
    }
}
