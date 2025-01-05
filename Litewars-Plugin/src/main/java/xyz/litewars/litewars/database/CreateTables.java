package xyz.litewars.litewars.database;

import xyz.litewars.litewars.Litewars;
import xyz.litewars.litewars.RunningData;

import java.sql.SQLException;

public class CreateTables {
    public static void initDatabase() {
        try {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS player_datas (" +
                    "player_name VARCHAR(255) NOT NULL," +
                    "player_uuid CHAR(36) NOT NULL," +
                    "level INTEGER NOT NULL DEFAULT 0," +
                    "kills INTEGER NOT NULL DEFAULT 0," +
                    "final_kills INTEGER NOT NULL DEFAULT 0," +
                    "wins INTEGER NOT NULL DEFAULT 0," +
                    "deaths INTEGER NOT NULL DEFAULT 0," +
                    "final_deaths INTEGER NOT NULL DEFAULT 0," +
                    "PRIMARY KEY (player_name)" +
                    ");";
            RunningData.databaseManager.createTable(createTableSQL);
        }catch (SQLException e){
            Litewars.logger.severe("在操作数据库时遇到错误！" + e.getMessage());
        }
    }
}
