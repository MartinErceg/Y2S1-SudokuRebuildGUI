/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marti
 */
public class SudokuDataBase
{

    private Connection conn;
//    private String URL = "jdbc:derby://localhost:1527/SudokuDB; create=true;";
    private final String URL = "jdbc:derby:SudokuDB; create=true";
    private final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private String username = "sudokuDB";
    private String password = "sudoku";
    private String tableName = "sudoku";
    private Statement statement;
    private ArrayList<PlayerStats> playersDB;

    public SudokuDataBase()
    {
        conn = null;
        try
        {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, username, password);
            statement = conn.createStatement();
        } catch (ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(SudokuDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        playersDB = new ArrayList<>();

//        dropTable();
        if (!checkTableExisting(tableName))
        {
            createTable();
            insert();
        }
        open();
    }

    private boolean checkTableExisting(String newTableName)
    {
        boolean found = false;
        try
        {
//            System.out.println("check existing tables.... ");

            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            Statement dropStatement = null;

            while (rsDBMeta.next())
            {
                String tableName = rsDBMeta.getString("TABLE_NAME");
//                System.out.println("found: " + tableName);
                if (tableName.compareToIgnoreCase(newTableName) == 0)
                {
                    found = true;
                }
            }
            if (rsDBMeta != null)
            {
                rsDBMeta.close();
            }
            if (dropStatement != null)
            {
                dropStatement.close();
            }

        } catch (SQLException ex)
        {
        }
        return found;
    }

    private void insert()
    {
        String player1 = ("insert into " + tableName + " values('Martin', 22)");
        String player2 = ("insert into " + tableName + " values('James', 33)");
        String player3 = ("insert into " + tableName + " values('Jethro', 9)");

        try
        {
            statement.executeUpdate(player1);
            statement.executeUpdate(player2);
            statement.executeUpdate(player3);
        } catch (SQLException ex)
        {
            Logger.getLogger(SudokuDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void dropTable()
    {
        try
        {
            statement.execute("drop table " + tableName);
        } catch (SQLException ex)
        {
            Logger.getLogger(SudokuDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void createTable()
    {
        String sqlCreateTable = ("CREATE TABLE " + tableName + " (playersName varchar(32), score int)");
        try
        {
            statement.executeUpdate(sqlCreateTable);
        } catch (SQLException ex)
        {
            Logger.getLogger(SudokuDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void open()
    {
        try
        {
            String columnName = "score";
            ResultSet rs = statement.executeQuery("select * from " + tableName + " ORDER BY " + columnName + " DESC");
            if (rs != null)
            {
                while (rs.next())
                {
                    String playerName = rs.getString(1);
                    int score = rs.getInt(2);

//                    System.out.println(playerName);
//                    System.out.println(score);
                    PlayerStats player = new PlayerStats();
                    player.setPlayersName(playerName);
                    player.setScore(score, 0);
                    playersDB.add(player);
                    // Only show Top 10
                    if (playersDB.size() > 9)
                    {
                        break;
                    }
                }
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(SudokuDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<PlayerStats> getPlayerStatsFromDB()
    {
        return playersDB;
    }

    public void save(PlayerStats pStats, int difficulty)
    {
        // get players from leaderboard
        try
        {
            PreparedStatement pstm = conn.prepareCall("INSERT INTO SUDOKU VALUES(?, ?)");

            for (int i = 0; i < 2; i++)
            {
                // data
                pstm.setString(1, pStats.getPlayerName());
                pstm.setInt(2, pStats.getScore(difficulty));
            }
            pstm.executeUpdate();

            conn.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(SudokuDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
