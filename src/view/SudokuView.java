/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.SudokuController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.PlayerStats;
import model.SudokuGameFunctionality;

/**
 *
 * @author marti
 */
public class SudokuView extends JPanel
{

    private JPanel sudokuPanel, buttonPanel, difficultyPanel, playerPanel;
    private JPanel sudokuPanelBorder;
    private JPanel sudokuPanel3by3[][];

    // right side
    private JPanel rightPanelBorder;
    private JPanel buttonPanelBorder, difficultyPanelBorder;
    private JButton sudokuButtons[][];
    private JButton[] buttonArrayNumber;
    private JButton leaderBoardButton;
    private int sizeOfSudoku;
//    private SudokuGameFunctionality gameFunction;
    private JFrame frame;
    private Container pane;
    private JComboBox difficultyLevels;
    private JMenuItem genSudokuItem, helpItem, musicItem;

    // Controller
    private SudokuController sudokuController;

    private int row, column;

    public SudokuView(JFrame frame, int sizeOfSudoku)
    {
        super();
        this.frame = frame;
        pane = frame.getContentPane();

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        genSudokuItem = new JMenuItem("Generate Sudoku");
        musicItem = new JMenuItem("Helpful Music");
        helpItem = new JMenuItem("Help");

        menu.add(genSudokuItem);
        menu.add(musicItem);
        menu.add(helpItem);

        menuBar.add(menu);

        this.frame.setJMenuBar(menuBar);

        //now create the components and set their attributes.
        this.sizeOfSudoku = sizeOfSudoku;
    }

    public JMenuItem getSudokuItem()
    {
        return genSudokuItem;
    }

    public JMenuItem getHelpItem()
    {
        return helpItem;
    }

    public JMenuItem getMusicItem()
    {
        return musicItem;
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public void setUpSudokuBoard(SudokuGameFunctionality sudokuModel)
    {
        sudokuPanel(sudokuModel);
        sudokuPanelBorder.add(sudokuPanel);
        pane.add(sudokuPanelBorder, BorderLayout.CENTER);
    }

    // leave alone
    public void sudokuPanel(SudokuGameFunctionality sudokuModel)
    {
        sudokuPanelBorder = new JPanel();
        sudokuPanel = new JPanel(new GridBagLayout());
        sudokuPanel.setLayout(new GridLayout(3, 3, 3, 3));

        sudokuButtons = new JButton[sizeOfSudoku][sizeOfSudoku];

//        sudokuPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        sudokuPanel3by3 = new JPanel[3][3];

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sudokuPanel3by3[i][j] = new JPanel();
                sudokuPanel3by3[i][j].setLayout(new GridLayout(3, 3, 1, 1));
            }
        }
        Font font;

        for (int i = 0; i < sizeOfSudoku; i++)
        {
            for (int j = 0; j < sizeOfSudoku; j++)
            {
                // 20 width per field
                sudokuButtons[i][j] = new JButton();

                // plus another 20 width per field
                font = sudokuButtons[i][j].getFont().deriveFont(Font.PLAIN, 20f);
                sudokuButtons[i][j].setHorizontalAlignment(JTextField.CENTER);

                if (!(sudokuModel.getPlayersSudoku()[i][j] == 0))
                {
//                    font = sudokuButtons[i][j].getFont().deriveFont(Font.BOLD, 20f);
                    sudokuButtons[i][j].setText(String.valueOf(sudokuModel.getPlayersSudoku()[i][j]));
                }

                sudokuButtons[i][j].setFont(font);
                // top left bottom right

                if (sudokuModel.getPlayer().getLockedNumbers()[i][j])
                {
                    sudokuButtons[i][j].setEnabled(false);
                }

                sudokuButtons[i][j].setBackground(Color.white);
                sudokuPanel3by3[i / 3][j / 3].add(sudokuButtons[i][j]);
//                sudokuPanel.add(sudokuButtons[i][j]);
            }
        }

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                sudokuPanel.add(sudokuPanel3by3[i][j]);
                sudokuPanel3by3[i][j].setBackground(Color.BLACK);
            }
        }
        sudokuPanel.setBackground(Color.BLACK);
        sudokuPanel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.black));
    }

    public void setUpRightPanel()
    {
        rightPanelBorder = new JPanel();
        rightPanelBorder.setLayout(new GridLayout(2, 2));

        buttonPanel();
        difficultyPanel();

        rightPanelBorder.add(buttonPanelBorder);
        rightPanelBorder.add(difficultyPanelBorder);

        pane.add(rightPanelBorder, BorderLayout.EAST);
    }

    // leave alone
    public void buttonPanel()
    {
        buttonPanelBorder = new JPanel();
        buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setLayout(new GridLayout(3, 3, 2, 2));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        buttonPanel.setBackground(Color.black);

        buttonArrayNumber = new JButton[sizeOfSudoku];

        for (int i = 0; i < sizeOfSudoku; i++)
        {
            // create buttons
            buttonArrayNumber[i] = new JButton();
            buttonArrayNumber[i].setText(String.valueOf(i + 1));
            buttonArrayNumber[i].setVisible(true);
            buttonPanel.add(buttonArrayNumber[i]);
        }

        buttonPanelBorder.add(buttonPanel);
    }

    // leave alone
    public void difficultyPanel()
    {
        difficultyPanelBorder = new JPanel();
        difficultyPanel = new JPanel(new GridBagLayout());

        leaderBoardButton = new JButton("Leaderboard");

        difficultyPanel.setLayout(new GridLayout(3, 3, 0, 10));

        String difficulty[] = new String[5];
        difficulty[0] = "Very Easy";
        difficulty[1] = "Easy";
        difficulty[2] = "Medium";
        difficulty[3] = "Hard";
        difficulty[4] = "Very Hard";

        JLabel diffLabel = new JLabel("         Difficulty");
        difficultyLevels = new JComboBox(difficulty);

        difficultyPanel.add(diffLabel);
        difficultyPanel.add(difficultyLevels);
        difficultyPanel.add(leaderBoardButton);
        difficultyPanelBorder.add(difficultyPanel);
    }

    // leave alone
    public JComboBox changeDifficulty()
    {
        return difficultyLevels;
    }

    // leave alone
    public void repaintSudokuBoard(SudokuGameFunctionality sudokuModel)
    {
        pane.remove(sudokuPanelBorder);
        sudokuPanel(sudokuModel);
        sudokuPanelBorder.add(sudokuPanel);
        pane.add(sudokuPanelBorder, BorderLayout.CENTER);
        pane.revalidate();
        pane.repaint();
    }

    // leave alone
    public JButton[] getNumberPressed()
    {
        return buttonArrayNumber;
    }

    // leave alone
    public JButton getLeaderBoardButton()
    {
        return leaderBoardButton;
    }

    // leave alone
    public JButton[][] getSudokuButtons()
    {
        return sudokuButtons;
    }
}
