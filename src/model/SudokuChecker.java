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
public class SudokuChecker
{

    private int numbersRight, numbersWrong;
    private boolean scoredAlready[][];
    
    public SudokuChecker(int gridSize)
    {
        scoredAlready = new boolean[gridSize][gridSize];
    }

    public boolean checkForGameFinish(int playerSudoku[][])
    {
        for (int r = 0; r < playerSudoku.length; r++)
        {
            for (int c = 0; c < playerSudoku[r].length; c++)
            {
//                System.out.println("Stopped at Row: "+r+", Column: "+c+", Number: "+playerSudoku[r][c]);
                if (playerSudoku[r][c] == 0)
                {
//                    System.out.println(playerSudoku[r][c]);
                    return false;
                }
            }
        }

        return true;
    }

    public boolean playerToCheckAnswers(int playerSudoku[][], int answerSudoku[][])
    {
        for (int r = 0; r < playerSudoku.length; r++)
        {
            for (int c = 0; c < playerSudoku[r].length; c++)
            {
                if (playerSudoku[r][c] != answerSudoku[r][c])
                {
                    return false;
                }
            }
        }

        return true;
    }

    public int numbersRight(int playerSudoku[][], int answerSudoku[][], boolean lockedNumbers[][])
    {
        numbersRight = 0;
        for (int r = 0; r < playerSudoku.length; r++)
        {
            for (int c = 0; c < playerSudoku[r].length; c++)
            {
                if (playerSudoku[r][c] == answerSudoku[r][c] && lockedNumbers[r][c] == false)
                {
                    numbersRight++;
                }
            }
        }
        return numbersRight;
    }

    public int numbersWrong(int playerSudoku[][], int answerSudoku[][], boolean lockedNumbers[][])
    {
        numbersWrong = 0;
        for (int r = 0; r < playerSudoku.length; r++)
        {
            for (int c = 0; c < playerSudoku[r].length; c++)
            {
                if (playerSudoku[r][c] != answerSudoku[r][c] && lockedNumbers[r][c] == false)
                {
                    numbersWrong++;
                }
            }
        }
        return numbersWrong;
    }
    
    public boolean checkSingleCell(int playerCell, int answerCell, int row, int column)
    {
        if (playerCell == answerCell && !scoredAlready[row][column])
        {
            scoredAlready[row][column] = true;
            return scoredAlready[row][column];
        }
        return playerCell == answerCell;
    }
}
