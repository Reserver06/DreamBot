package bot.readers;

import bot.Player;

import java.sql.*;
import java.util.Map;

public class Database {

    //Adds data to member table and returns true when successful
    public void addData(String discord_id, String name, long phone) throws SQLException {

        Statement statement = startConnection();
        statement.executeUpdate("INSERT INTO member(discord_id,name,phone) VALUES('"+
                discord_id+"','"+name+"','"+phone+"');");
    }
    public void updateData(String discord_id,String name, long phone) throws SQLException {

        Statement statement = startConnection();
        statement.executeUpdate("UPDATE member SET phone="+phone+",name='"+name+"' WHERE discord_id='<@!"+discord_id+">';");
    }
    //Reads data from member table and returns a list of Players if successful
    public Map<String,Player> readData(Map<String,Player> members) {
        try {
            Statement statement = startConnection();

            ResultSet resultSet = statement.executeQuery("select * from member;");
            while(resultSet.next()) {
                String discordID = resultSet.getString("discord_id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String loot = resultSet.getString("loot");

                if(loot==null)
                    loot = "0";

                members.put(name,new Player(discordID,name,phone,loot));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return members;
    }


    //Starts connection to SQL Database
    private Statement startConnection() throws SQLException {
        String url = "jdbc:mysql://na05-sql.pebblehost.com/customer_183968_dreambot";
        String user = "customer_183968_dreambot";
        String password = "Es_14997369!";

        Connection connection = DriverManager.getConnection(url,user,password);
        return connection.createStatement();
    }


}
