/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.SudokuController;
import java.awt.Color;
import javax.swing.JFrame;
import model.SudokuGameFunctionality;

/**
 *
 * @author marti
 */
public class MainView
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int gridSize = 9;
        // model
        SudokuGameFunctionality sudokuModel = new SudokuGameFunctionality(gridSize);
        // view
        SudokuView sudokuView = new SudokuView(frame, gridSize);
        // controller
        SudokuController sudokuController = new SudokuController(sudokuModel, sudokuView);

        frame.setResizable(false);
//        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
}
