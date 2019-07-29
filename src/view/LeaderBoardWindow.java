/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import model.PlayerStats;
import model.SudokuGameFunctionality;

/**
 *
 * @author marti
 */
public class LeaderBoardWindow
{
    public LeaderBoardWindow(SudokuGameFunctionality sudokuModel)
    {
        JDialog window = new JDialog();
        Container pane = window.getContentPane();
        window.setTitle("Leaderboard");
        window.setResizable(false);

        JPanel leaderBoardPanel = new JPanel();
        leaderBoardPanel.setLayout(new GridLayout(2, 2));

        String[] columnNames =
        {
            "Player Name", "Score"
        };

        Object[][] leaderBoardData = new Object[sudokuModel.getPlayerStatsFromDB().size()][2];

//        System.out.println(sudokuModel.getPlayerStatsFromDB().size());

        int counter = 0;
        for (PlayerStats aPlayerStat : sudokuModel.getPlayerStatsFromDB())
        {
            leaderBoardData[counter][0] = aPlayerStat.getPlayerName();
            leaderBoardData[counter][1] = aPlayerStat.getScore(0);
            counter++;
        }

        JTable scoreBoard = new JTable(leaderBoardData, columnNames);

        pane.add(scoreBoard.getTableHeader(), BorderLayout.PAGE_START);
        pane.add(scoreBoard);
        window.setSize(300, 208);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
