/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author marti
 */
public class SudokuGameFunctionality
{

    // Make sudoku numbers
    private GenerateSudoku newSudoku[];

    // place sudoku numbers into answer and player
    private TypeOfSudoku answer[];
    private PlayerSudoku player[];

    // use this is check if it matches for the end
    private SudokuChecker checker;
    private int gridSize;

    // selected mode
    private int selectedMode;
    private PlayerStats newPlayerStats;

    private SudokuDataBase sudokuDB;

    private int row, column;

    public SudokuGameFunctionality(int gridSize)
    {
        // set it to 9 for now
        this.gridSize = gridSize;

        // initialise grid size and use it for new sudoku
        checker = new SudokuChecker(gridSize);

        generateSudokuForAll();
    }

    public void generateSudokuForAll()
    {
        newSudoku = new GenerateSudoku[5];
        answer = new TypeOfSudoku[5];
        player = new PlayerSudoku[5];

        sudokuDB = new SudokuDataBase();
        newPlayerStats = new PlayerStats();

        newSudoku[0] = new GenerateSudoku(gridSize, "Very Easy");
        newSudoku[1] = new GenerateSudoku(gridSize, "Easy");
        newSudoku[2] = new GenerateSudoku(gridSize, "Medium");
        newSudoku[3] = new GenerateSudoku(gridSize, "Hard");
        newSudoku[4] = new GenerateSudoku(gridSize, "Very Hard");

        answer[0] = new AnswerSudoku(newSudoku[0].getGeneratedAnswer(), gridSize);
        answer[1] = new AnswerSudoku(newSudoku[1].getGeneratedAnswer(), gridSize);
        answer[2] = new AnswerSudoku(newSudoku[2].getGeneratedAnswer(), gridSize);
        answer[3] = new AnswerSudoku(newSudoku[3].getGeneratedAnswer(), gridSize);
        answer[4] = new AnswerSudoku(newSudoku[4].getGeneratedAnswer(), gridSize);

        player[0] = new PlayerSudoku(newSudoku[0].getGeneratedPlayer(), newSudoku[0].getLockedNumbers(), gridSize);
        player[1] = new PlayerSudoku(newSudoku[1].getGeneratedPlayer(), newSudoku[1].getLockedNumbers(), gridSize);
        player[2] = new PlayerSudoku(newSudoku[2].getGeneratedPlayer(), newSudoku[2].getLockedNumbers(), gridSize);
        player[3] = new PlayerSudoku(newSudoku[3].getGeneratedPlayer(), newSudoku[3].getLockedNumbers(), gridSize);
        player[4] = new PlayerSudoku(newSudoku[4].getGeneratedPlayer(), newSudoku[4].getLockedNumbers(), gridSize);
    }

    public void setPlayersScore()
    {
        boolean add = true;
        if (!checkCell())
        {
            add = false;
        }

//        System.out.println("Score count: " + getScore());
        if (add)
        {
            newPlayerStats.addScore(selectedMode);
        } else
        {
            add = false;
            newPlayerStats.minusScore(selectedMode);
        }
    }

    public boolean checkCell()
    {
        return checker.checkSingleCell(player[selectedMode].getSudoku()[row][column], answer[selectedMode].getSudoku()[row][column], row, column);
    }

    public void setRowAndColumnNumber(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    public ArrayList<PlayerStats> getPlayerStatsFromDB()
    {
        return sudokuDB.getPlayerStatsFromDB();
    }

    public void savePlayerData(String playerName)
    {
        PlayerStats savePlayerStats = new PlayerStats();
        savePlayerStats.setPlayersName(playerName);
        savePlayerStats.setScore(getScore(), selectedMode);

        sudokuDB.save(savePlayerStats, selectedMode);
    }

    public int getScore()
    {
        return newPlayerStats.getScore(selectedMode);
    }

    public void setNumberForModel(int number)
    {
        player[selectedMode].enterNumber(row, column, number);
    }

    public void setMode(int selectedMode)
    {
        this.selectedMode = selectedMode;
    }

    public PlayerSudoku getPlayer()
    {
        return player[selectedMode];
    }

    public int[][] getPlayersSudoku()
    {
        return player[selectedMode].getSudoku();
    }

    public boolean gameFinished()
    {
        return checker.checkForGameFinish(player[selectedMode].getSudoku());
    }

    public int rightNumbers()
    {
        return checker.numbersRight(player[selectedMode].getSudoku(), answer[selectedMode].getSudoku(), player[selectedMode].getLockedNumbers());
    }

    public int wrongNumbers()
    {
        return checker.numbersWrong(player[selectedMode].getSudoku(), answer[selectedMode].getSudoku(), player[selectedMode].getLockedNumbers());
    }

    public boolean correctMatch()
    {
//        System.out.println(selectedMode);
        return checker.playerToCheckAnswers(player[selectedMode].getSudoku(), answer[selectedMode].getSudoku());
    }
}
