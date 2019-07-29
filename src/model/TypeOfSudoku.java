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
abstract public class TypeOfSudoku
{
    protected int sudoku[][];
    protected int gridSize;
    
    abstract public int[][] getSudoku();
}
