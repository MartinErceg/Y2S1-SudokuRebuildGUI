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
public class PlayerStats
{

    private String playerName;
    private int[] score;

    public PlayerStats()
    {
        score = new int[5];

        for (int i = 0; i < score.length; i++)
        {
            score[i] = 0;
        }
    }

    public void setPlayersName(String playerName)
    {
        this.playerName = playerName;
    }
    
    public void setScore(int score, int difficulty)
    {
        this.score[difficulty] = score;
    }

    public void addScore(int difficulty)
    {
        switch (difficulty)
        {
            case 0:
                score[difficulty]++;
                break;
            case 1:
                score[difficulty] += 2;
                break;
            case 2:
                score[difficulty] += 3;
                break;
            case 3:
                score[difficulty] += 4;
                break;
            case 4:
                score[difficulty] += 5;
                break;
        }
    }

    public void minusScore(int difficulty)
    {
        switch (difficulty)
        {
            case 0:
                break;
            case 1:
                score[difficulty]--;
                break;
            case 2:
                score[difficulty]--;
                break;
            case 3:
                score[difficulty] -= 2;
                break;
            case 4:
                score[difficulty] -= 2;
                break;
        }
        
        if (score[difficulty] < 0)
        {
            score[difficulty] = 0;
        }
    }

    public void resetScore(int difficultyChange)
    {
        score[difficultyChange] = 0;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public int getScore(int difficulty)
    {
        return score[difficulty];
    }
}
