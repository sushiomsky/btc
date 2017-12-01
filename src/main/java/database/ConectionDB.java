/*
 * This is the source code of Telegram Bot v. 2.0
 * It is licensed under GNU GPL v. 3 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Ruben Bermudez, 3/12/14.
 */
package database;

import java.sql.*;

/**
 * @author Ruben Bermudez
 * @version 2.0
 * @brief Connector to database
 * @date 3/12/14
 */
public class ConectionDB {
    private Connection currentConection;

    public ConectionDB() {
        this.currentConection = openConexion();
    }

    private Connection openConexion() {
        Connection connection = null;
        try {
            Class.forName(BuildVars.controllerDB).newInstance();
            connection = DriverManager.getConnection(BuildVars.linkDB, BuildVars.userDB, BuildVars.password);
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void closeConexion() {
        try {
            this.currentConection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public Boolean executeQuery(String query) throws SQLException {
        final Statement statement = this.currentConection.createStatement();
        return statement.execute(query);
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return this.currentConection.prepareStatement(query);
    }

    public PreparedStatement getPreparedStatement(String query, int flags) throws SQLException {
        return this.currentConection.prepareStatement(query, flags);
    }

    /**
     * Initilize a transaction in database
     * @throws SQLException If initialization fails
     */
    public void initTransaction() throws SQLException {
        this.currentConection.setAutoCommit(false);
    }

    /**
     * Finish a transaction in database and commit changes
     * @throws SQLException If a rollback fails
     */
    public void commitTransaction() throws SQLException {
        try {
            this.currentConection.commit();
        } catch (SQLException e) {
            if (this.currentConection != null) {
                this.currentConection.rollback();
            }
        } finally {
            this.currentConection.setAutoCommit(false);
        }
    }
}
