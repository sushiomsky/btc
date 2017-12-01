/*
 * This is the source code of Telegram Bot v. 2.0
 * It is licensed under GNU GPL v. 3 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Ruben Bermudez, 3/12/14.
 */
package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @author Ruben Bermudez
 * @version 2.0
 * @brief Database Manager to perform database operations
 * @date 3/12/14
 */
public class DatabaseManager {

    private static volatile DatabaseManager instance;
    private static volatile ConectionDB connetion;

    /**
     * Private constructor (due to Singleton)
     */
    private DatabaseManager() {
        connetion = new ConectionDB();
    }

    /**
     * Get Singleton instance
     *
     * @return instance of the class
     */
    public static DatabaseManager getInstance() {
        final DatabaseManager currentInstance;
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }

    public boolean createTable(String table){
    	String query = "CREATE TABLE IF NOT EXISTS `" + table + "` (`id` int(11) NOT NULL AUTO_INCREMENT,  `ask` double NOT NULL,  `bid` double NOT NULL,  `high` double NOT NULL,  `low` double NOT NULL,  `ctime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,  PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
    	return true;
    }

    public boolean logRate(String table, Double[] data) {
        int updatedRows = 0;
        try {
            final PreparedStatement preparedStatement = connetion.getPreparedStatement("INSERT INTO "+table+" (ask, bid, high, low) VALUES (?,?,?,?)");
			preparedStatement.setDouble(1, data[0]);
			preparedStatement.setDouble(2, data[1]);
			preparedStatement.setDouble(3, data[2]);
			preparedStatement.setDouble(4, data[3]);
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updatedRows > 0;
    }

    public LinkedList getTrainingData(String table) {
        try {
			final PreparedStatement preparedStatement = connetion.getPreparedStatement("SELECT * FROM " + table + " ORDER BY ctime ASC LIMIT 30");
			final ResultSet result = preparedStatement.executeQuery();
			LinkedList data = new LinkedList();
			while (result.next()) {
				data.add(result.getDouble("ask"));
			}
			return data;
		}catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}