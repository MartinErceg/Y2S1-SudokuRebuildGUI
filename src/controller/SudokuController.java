/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import model.SudokuGameFunctionality;
import view.FinishWindow;
import view.LeaderBoardWindow;
import view.MainView;
import view.SudokuView;

/**
 *
 * @author marti
 */
public class SudokuController implements ActionListener
{

    // controller
    private String selectedNum;
    private JButton selectedButton;

    // controller handler
    private Object source;

    // View
    SudokuView sudokuView;
    // Model
    SudokuGameFunctionality sudokuModel;

    public SudokuController(SudokuGameFunctionality sudokuModel, SudokuView sudokuView)
    {
        this.sudokuModel = sudokuModel;
        this.sudokuView = sudokuView;

        sudokuView.setUpSudokuBoard(sudokuModel);
        sudokuView.setUpRightPanel();
        sudokuModel.setMode(0);

        setNumberListener();
        setSudokuListener();
        setDifficultyBoxListener();
        setLeaderBoardButton();
        setMenuItemListener();
    }

    public void setMenuItemListener()
    {
        sudokuView.getSudokuItem().addActionListener(this);
        sudokuView.getHelpItem().addActionListener(this);
        sudokuView.getMusicItem().addActionListener(this);
    }

    public void setNumberListener()
    {
        for (int i = 0; i < 9; i++)
        {
            sudokuView.getNumberPressed()[i].addActionListener(this);
        }
    }

    public void setSudokuListener()
    {
        for (int r = 0; r < 9; r++)
        {
            for (int c = 0; c < 9; c++)
            {
                sudokuView.getSudokuButtons()[r][c].addActionListener(this);
            }
        }
    }

    public void setDifficultyBoxListener()
    {
        sudokuView.changeDifficulty().addActionListener(this);
    }

    public void setLeaderBoardButton()
    {
        sudokuView.getLeaderBoardButton().addActionListener(this);
    }

    private void handleSudokuFunctionality()
    {
        for (int r = 0; r < 9; r++)
        {
            // Number button pressed on top right
            if (source == sudokuView.getNumberPressed()[r] && selectedButton != null)
            {
                selectedNum = sudokuView.getNumberPressed()[r].getText();
                selectedButton.setText(selectedNum);
                sudokuModel.setNumberForModel(Integer.valueOf(selectedNum));
                sudokuModel.setPlayersScore();
                break;
            }

            for (int c = 0; c < 9; c++)
            {
                // Setting the number pressed to sudoku button
                if (source == sudokuView.getSudokuButtons()[r][c])
                {
                    if (selectedButton != null)
                    {
                        selectedButton.setBackground(Color.white);
                    }
                    selectedButton = (JButton) source;
                    selectedButton.getModel().setPressed(true);
                    selectedButton.setBackground(Color.cyan);
//                    System.out.println("row: " + r + ", column: " + c);
                    sudokuModel.setRowAndColumnNumber(r, c);
                    break;
                }
            }
        }
    }

    private void handleDifficultyEvent()
    {
        // difficulty
        if (source == sudokuView.changeDifficulty())
        {
            sudokuModel.setMode(sudokuView.changeDifficulty().getSelectedIndex());
            sudokuView.repaintSudokuBoard(sudokuModel);
            setSudokuListener();
            selectedButton = null;
        }
    }

    private void handleLeaderBoardEvent()
    {
        if (source == sudokuView.getLeaderBoardButton())
        {
            new LeaderBoardWindow(sudokuModel);
        }
    }

    private void handleGameFinishedEvent()
    {
        // game finished
        if (sudokuModel.gameFinished())
        {
            selectedButton.setBackground(Color.white);
            FinishWindow gameFinish = new FinishWindow(sudokuModel);
            if (gameFinish.newGame())
            {
                sudokuModel.generateSudokuForAll();
                sudokuView.repaintSudokuBoard(sudokuModel);
                setSudokuListener();
            } else
            {
                sudokuView.getFrame().setVisible(false);
                sudokuView.getFrame().dispose();
            }
        }
    }

    private void handleJMenuItems()
    {
        if (source == sudokuView.getSudokuItem())
        {
            sudokuModel.generateSudokuForAll();
            sudokuView.repaintSudokuBoard(sudokuModel);
            setSudokuListener();
        } else if (source == sudokuView.getHelpItem())
        {
            Desktop d = Desktop.getDesktop();
            try
            {
                d.browse(new URI("https://www.youtube.com/watch?v=OtKxtvMUahA"));
            } catch (Exception ex)
            {
                Logger.getLogger(SudokuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (source == sudokuView.getMusicItem())
        {
            Desktop d = Desktop.getDesktop();
            try
            {
                d.browse(new URI("https://www.youtube.com/watch?v=WGylrGZPuWQ"));
            } catch (Exception ex)
            {
                Logger.getLogger(SudokuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        source = e.getSource();

        handleSudokuFunctionality();
        handleDifficultyEvent();
        handleGameFinishedEvent();
        handleLeaderBoardEvent();
        handleJMenuItems();
    }
}
