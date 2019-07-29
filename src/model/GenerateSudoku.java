/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

/**
 *
 * @author marti
 */
public final class GenerateSudoku
{

    private int[][] answerSudokuBoxes;
    private int[][] playerSudokuBoxes;
    private boolean[][] lockNumbers;
    private final int sizeLengthOfSudoku;

    public GenerateSudoku(int sizeLengthOfSudoku, String difficulty)
    {
        this.sizeLengthOfSudoku = sizeLengthOfSudoku;
        // answers must be there in order to do sudoku
        generateAnswersToSudoku();
        generatePlayersSudoku(difficulty);
    }

    private void generateAnswersToSudoku()
    {
        int columnsOfColumns = 0;
        int rowOfRows = 0;
        int counterToResetChecker;
        int resetCombination;
        int rowChanger = 0;
        int columnChanger = 0;

        // To note: the print out in comments are used for testing
        Random rand = new Random();

        answerSudokuBoxes = new int[sizeLengthOfSudoku][sizeLengthOfSudoku];

        for (int row = 0; row < sizeLengthOfSudoku; row++)
        {
            for (int column = 0; column < sizeLengthOfSudoku; column++)
            {
                // Setting the numbers for boxes
                answerSudokuBoxes[row][column] = rand.nextInt(sizeLengthOfSudoku) + 1;
                //System.out.println(sudokuBoxes[row][column] + " : BEFORE");
                resetCombination = 0;

                int addNineToChecker;
                do
                {
                    // Must repeat from start if it changes from columns or rows, so that
                    // it doesn't miss any double ups on numbers.
                    counterToResetChecker = 0;

                    // Checks rows
                    for (int rowsOfColumns = 0; rowsOfColumns < sizeLengthOfSudoku; rowsOfColumns++)
                    {
                        // comparison of original to the others in the current row
                        if (answerSudokuBoxes[row][column] == answerSudokuBoxes[rowOfRows][rowsOfColumns]
                                && rowOfRows == row && column != rowsOfColumns)
                        {
                            answerSudokuBoxes[row][column] = rand.nextInt(sizeLengthOfSudoku) + 1;
                            // Resets loop
                            counterToResetChecker--;
                            //System.out.println(sudokuBoxes[row][column] + " : ROW");
                        } else
                        {
                            // Reset do while loop to check through row, columns and 3 by 3 grid
                            counterToResetChecker++;
                        }
                    }

                    // Checks columns
                    for (int columnsOfRows = 0; columnsOfRows < sizeLengthOfSudoku; columnsOfRows++)
                    {
                        // comparison of original to the others in the current column
                        if (answerSudokuBoxes[row][column] == answerSudokuBoxes[columnsOfRows][columnsOfColumns]
                                && columnsOfRows != row && columnsOfColumns == column)
                        {
                            answerSudokuBoxes[row][column] = rand.nextInt(sizeLengthOfSudoku) + 1;
                            counterToResetChecker--;
                            //System.out.println(sudokuBoxes[row][column] + " : COLUMN");
                        } else
                        {
                            // Reset do while loop to check through row, columns and 3 by 3 grid
                            counterToResetChecker++;
                        }
                    }

                    addNineToChecker = 0;
                    if (sizeLengthOfSudoku == 9)
                    {
                        addNineToChecker = 9;
                        // rotation of 3 by 3 check
                        switch (row)
                        {
                            case 0:
                            case 1:
                            case 2:
                                rowChanger = 0;
                                switch (column)
                                {
                                    case 0:
                                        columnChanger = 0;
                                        break;
                                    case 3:
                                        columnChanger = 3;
                                        break;
                                    case 6:
                                        columnChanger = 6;
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 3:
                            case 4:
                            case 5:
                                rowChanger = 3;
                                switch (column)
                                {
                                    case 0:
                                        columnChanger = 0;
                                        break;
                                    case 3:
                                        columnChanger = 3;
                                        break;
                                    case 6:
                                        columnChanger = 6;
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case 6:
                            case 7:
                            case 8:
                                rowChanger = 6;
                                switch (column)
                                {
                                    case 0:
                                        columnChanger = 0;
                                        break;
                                    case 3:
                                        columnChanger = 3;
                                        break;
                                    case 6:
                                        columnChanger = 6;
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            default:
                                break;
                        }

                        // two seperate conditions for row and column
                        // Checks 3 by 3
                        for (int threeByThreeRow = rowChanger; threeByThreeRow < rowChanger + 3; threeByThreeRow++)
                        {
                            for (int threeByThreeColumn = columnChanger; threeByThreeColumn < columnChanger + 3; threeByThreeColumn++)
                            {
                                if (answerSudokuBoxes[threeByThreeRow][threeByThreeColumn] == answerSudokuBoxes[row][column]
                                        && row != threeByThreeRow && column != threeByThreeColumn
                                        || answerSudokuBoxes[threeByThreeRow][threeByThreeColumn] == answerSudokuBoxes[row][column]
                                        && row == threeByThreeRow && column != threeByThreeColumn
                                        || answerSudokuBoxes[threeByThreeRow][threeByThreeColumn] == answerSudokuBoxes[row][column]
                                        && row != threeByThreeRow && column == threeByThreeColumn)
                                {
                                    answerSudokuBoxes[row][column] = rand.nextInt(sizeLengthOfSudoku) + 1;
                                    counterToResetChecker--;
                                } else
                                {
                                    // Reset do while loop to check through row, columns and 3 by 3 grid
                                    counterToResetChecker++;
                                }
                            }
                        }
                    }

                    resetCombination++;
                    //System.out.println("Reset Combination: " + resetCombination);
                    // If combination is impossible reset and make another one, does it by gridSize * gridSize for boxes and possible
                    // combinations in each grid equaling to sizeLengthOfSudoku * 3
                    // 1 time error, condition was false but always ran.
                    if (resetCombination == (Math.pow((sizeLengthOfSudoku - 1), 3)))
                    {
                        //System.out.println("Reset at number: " + sudokuBoxes[row][column] + "Row: " + row + "Column: " + column);
                        row = 0;
                        rowOfRows = 0;
                        column = 0;
                        columnsOfColumns = 0;
                        counterToResetChecker = (sizeLengthOfSudoku + sizeLengthOfSudoku + 9);

                        // sudokuBoxes[row][column] = rand.nextInt(sizeLengthOfSudoku) + 1;
                        //System.out.println("The start: " + sudokuBoxes[row][column]);
                        for (int rowReseter = 0; rowReseter < sizeLengthOfSudoku; rowReseter++)
                        {
                            for (int columnReseter = 0; columnReseter < sizeLengthOfSudoku; columnReseter++)
                            {
                                answerSudokuBoxes = new int[sizeLengthOfSudoku][sizeLengthOfSudoku];
                            }
                        }

                        answerSudokuBoxes[row][column] = rand.nextInt(sizeLengthOfSudoku) + 1;
                    }

                } while (counterToResetChecker != (sizeLengthOfSudoku + sizeLengthOfSudoku + addNineToChecker));

                columnsOfColumns++;
            }
            //i++;
            //System.out.println("Row done "+i);
            columnsOfColumns = 0;
            rowOfRows++;
        }
    }

    private void generatePlayersSudoku(String difficulty)
    {
        Random rand = new Random();
        int roller;
        int sudokuFull = 0;

        playerSudokuBoxes = new int[this.sizeLengthOfSudoku][this.sizeLengthOfSudoku];
        lockNumbers = new boolean[this.sizeLengthOfSudoku][this.sizeLengthOfSudoku];

        int difficultyNum = 0;

        switch (difficulty)
        {
            case "Very Easy":
                roller = rand.nextInt(10) + 1;
                difficultyNum = 50 + roller;
                break;
            case "Easy":
                roller = rand.nextInt(13) + 1;
                difficultyNum = 36 + roller;
                break;
            case "Medium":
                roller = rand.nextInt(3) + 1;
                difficultyNum = 32 + roller;
                break;
            case "Hard":
                roller = rand.nextInt(3) + 1;
                difficultyNum = 28 + roller;
                break;
            case "Very Hard":
                roller = rand.nextInt(5) + 1;
                difficultyNum = 22 + roller;
                break;
            default:
                System.out.println("Error with difficulty");
                break;
        }

//        setAllNumbersButOne();
        // difficulty
        int diffNumCount = 0;
        do
        {
            for (int row = 0; row < this.sizeLengthOfSudoku; row++)
            {
                for (int column = 0; column < this.sizeLengthOfSudoku; column++)
                {
                    // prevent user changing the original sudoku numbers
                    roller = rand.nextInt(100);
                    if (roller < 25 && diffNumCount < difficultyNum && lockNumbers[row][column] == false)
                    {
                        lockNumbers[row][column] = true;
                        this.playerSudokuBoxes[row][column] = this.answerSudokuBoxes[row][column];
                        diffNumCount++;
                        sudokuFull++;
                    }
                }
            }
//            System.out.println("first"+diffNumCount);
//            System.out.println("second"+difficultyNum);

//        this.playerSudokuBoxes[sizeLengthOfSudoku - 1][sizeLengthOfSudoku - 1] = 0;
            sudokuFull--;

            if (sudokuFull == Math.pow(sizeLengthOfSudoku, 2))
            {
                // use recursion to repeat process if player's sudoku is the answers
                System.out.println("Generator failed to populate player's sudoku, retrying...");
                generatePlayersSudoku(difficulty);
            }
        } while (diffNumCount < difficultyNum);
    }

    public void setAllNumbersButOne()
    {
        int difficulty = 0;
        for (int row = 0; row < this.sizeLengthOfSudoku; row++)
        {
            for (int column = 0; column < this.sizeLengthOfSudoku; column++)
            {
                if (row == 8 && column == 8)
                {
                    lockNumbers[row][column] = false;
                    this.playerSudokuBoxes[row][column] = 0;
                    break;
                }
                lockNumbers[row][column] = true;
                this.playerSudokuBoxes[row][column] = this.answerSudokuBoxes[row][column];
            }
        }
    }

    public int[][] getGeneratedPlayer()
    {
        return this.playerSudokuBoxes;
    }

    public boolean[][] getLockedNumbers()
    {
        return this.lockNumbers;
    }

    public int[][] getGeneratedAnswer()
    {
        return this.answerSudokuBoxes;
    }
}
