/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author marti
 */
public class PlayerSudoku extends TypeOfSudoku
{

    private final boolean lockedNumbers[][];

    public PlayerSudoku(int playerSudoku[][], boolean lockedNumbers[][], int gridSize)
    {
        this.gridSize = gridSize;
        this.lockedNumbers = lockedNumbers;
        this.sudoku = new int[gridSize][gridSize];
        this.sudoku = playerSudoku;
    }

    @Override
    public int[][] getSudoku()
    {
        return sudoku;
    }

    public void enterNumber(int row, int column, int playersNum)
    {
//        System.out.println("Before it contains: " + sudoku[row][column]);
        sudoku[row][column] = playersNum;
//        System.out.println("ENTERED = Row: " + row + ", Column: " + column + ", PlayersNum: " + playersNum);
    }

    public boolean isLocked(int row, int column)
    {
        return lockedNumbers[row][column];
    }

    public boolean[][] getLockedNumbers()
    {
        return lockedNumbers;
    }

    public int getNumsFilled()
    {
        int numsFilled = 0;
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (sudoku[i][j] == 0)
                {

                } else
                {
                    numsFilled++;
                }
            }
        }
        return numsFilled;
    }
}
