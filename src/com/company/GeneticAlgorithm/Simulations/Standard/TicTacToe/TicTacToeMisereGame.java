package com.company.GeneticAlgorithm.Simulations.Standard.TicTacToe;

import com.company.NuralNetwork.NeuralNetwork;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class TicTacToeMisereGame
{
    private int[][] board = new int[3][3];

    private Player winner = Player.NONE;

    public enum Player
    {
        PLAYER1, PLAYER2, NONE
    }

    public TicTacToeMisereGame()
    {

    }

    public void turnP1(int input)
    {
        validateInput(input);

        board[input / 3][input % 3] = 1;

        if(checkLose())
            winner = Player.PLAYER2;

    }

    public void turnP2(int input)
    {
        validateInput(input);

        board[input / 3][input % 3] = 1;

        if(checkLose())
            winner = Player.PLAYER1;

    }
    public void turnP1NN(float[] input)
    {
        int playedPos = translateNNOut(input);

        validateInput(playedPos);
        board[playedPos / 3][playedPos % 3] = 1;

        if(checkLose())
            winner = Player.PLAYER2;

    }

    public void turnP2NN(float[] input)
    {
        int playedPos = translateNNOut(input);

        validateInput(playedPos);
        board[playedPos / 3][playedPos % 3] = 1;

        if(checkLose())
            winner = Player.PLAYER1;

    }


    private boolean checkLose()
    {
        return checkColumns() || checkRow() || checkDiagnals();
    }

    private boolean checkRow()
    {
        for(int i = 0; i < 3; i++)
        {
            if(board[i][0] + board[i][1] + board[i][2] == 3)
                return true;
        }

        return false;
    }

    private boolean checkColumns()
    {
        for(int i = 0; i < 3; i++)
        {
            if(board[0][i] + board[1][i] + board[2][i] == 3)
                return true;
        }

        return false;
    }

    private boolean checkDiagnals()
    {
        return (board[0][0] + board[1][1] + board[2][2] == 3 || board[0][2] + board[1][1] + board[2][0] == 3);
    }

    public void printBoard()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                System.out.print(board[i][j] + " ");
            }

            System.out.print("\n");
        }
    }

    private void validateInput(int input)
    {

        if(board[input / 3][input % 3] == 1)
            throw new IllegalStateException("The board position " + input + " is already occupied");

        if(input > 8 || input < 0)
            throw new NoSuchElementException("Board requires an input between 0 - 8 you entered: " + input);
    }

    public Player getWinner()
    {
        return winner;
    }

    public void resetBoard()
    {
        for(int i = 0; i < board.length; i++)
        {
            for(int j = 0; j < board[i].length; j++)
            {
                board[i][j] = 0;
            }
        }

        winner = Player.NONE;
    }

    public int[][] getBoard()
    {
        return board;
    }

    public void manVMachine(byte[] DNA, int... neuronCFG)
    {
        Scanner scanner = new Scanner(System.in);
        NeuralNetwork bot = new NeuralNetwork(neuronCFG);
        bot.setDNA(DNA);

        printBoard();

        while (winner == Player.NONE)
        {
            System.out.println("----Player 1----");

            turnP1NN(bot.fire(flatten(getBoard())));

            printBoard();
            System.out.println("----------------\n\n");

            if(winner != Player.NONE)
                break;

            System.out.println("----Player 2----");
            try
            {
                turnP2(scanner.nextInt());
            } catch (Exception e)
            {
                System.out.println("Player 2 skipped their turn");
            }
            printBoard();
            System.out.println("----------------\n\n");

        }

        if(winner == Player.PLAYER1)
            System.out.println("Player 1 Won!!!!!");
        else
            System.out.println("Player 2 Won!!!!!");

        resetBoard();
    }


    public void machineVMachine(byte[] DNA, byte[] DNA2, int... neuronCFG)
    {
        NeuralNetwork bot1 = new NeuralNetwork(neuronCFG);
        NeuralNetwork bot2 = new NeuralNetwork(neuronCFG);

        bot2.setDNA(DNA2);
        bot1.setDNA(DNA);


        while (winner == Player.NONE)
        {
            System.out.println("----Player 1----");

            turnP1NN(bot1.fire(flatten(getBoard())));

            printBoard();
            System.out.println("----------------\n\n");

            if(winner != Player.NONE)
                break;

            System.out.println("----Player 2----");
            turnP2NN(bot1.fire(flatten(getBoard())));
            printBoard();
            System.out.println("----------------\n\n");

        }

        if(winner == Player.PLAYER1)
            System.out.println("Player 1 Won!!!!!");
        else
            System.out.println("Player 2 Won!!!!!");

        resetBoard();
    }


    private float[] flatten(int[][] in)
    {
        float[] dataIn = new float[9];

        for (int i = 0; i < 9; i++)
        {
            dataIn[i] = in[i / 3][i % 3];
        }

        return dataIn;
    }

    public void setWinner(Player winner)
    {
        this.winner = winner;
    }

    private int translateNNOut(float[] in)
    {
        int biggestLocation = -1;
        float maxNum = -21;
        boolean isLegal = false;

        while (!isLegal)
        {
            for (int i = 0; i < in.length; i++)
            {
                if (in[i] > maxNum)
                {

                    maxNum = in[i];
                    biggestLocation = i;
                }
            }

            isLegal = isLegalMove(biggestLocation);

            if(!isLegal)
            {
                in[biggestLocation] = -10f;
                maxNum = -10f;
            }
        }

        return biggestLocation;
    }

    private boolean isLegalMove(int boardPos)
    {
        return board[boardPos / 3][boardPos % 3] == 0;
    }
}
