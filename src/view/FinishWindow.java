/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Frame;
import javax.swing.JOptionPane;
import model.SudokuGameFunctionality;

/**
 *
 * @author marti
 */
public class FinishWindow
{
    private int newGame;
    
    public FinishWindow(SudokuGameFunctionality sudokuModel)//Frame frame, SudokuGameFunctionality sudokuModel)
    {
        Boolean winCond = sudokuModel.correctMatch();

        String stats = "\nStats" + "\nNumbers right: " + sudokuModel.rightNumbers()
                + "\nNumbers wrong: " + sudokuModel.wrongNumbers() + "\nScore: " + sudokuModel.getScore();

        String playerName = JOptionPane.showInputDialog(null, "Enter Player Name");

        if (playerName == null)
        {
            playerName = "player";
        }

        sudokuModel.savePlayerData(playerName);

        if (winCond)
        {
            JOptionPane.showMessageDialog(null, "You win\n" + stats);
        } else
        {
            JOptionPane.showMessageDialog(null, "You lose\n" + stats);
        }
        newGame = JOptionPane.showOptionDialog(null, "Do you want to play again?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    }
    
    public boolean newGame()
    {
        return newGame == 0;
    }
}
