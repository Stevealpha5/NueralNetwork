package com.company.GeneticAlgorithm.Simulations.Standard.TicTacToe;

import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.CustomCSVLogger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TicTacToeMisere
{
    private ArrayList<NeuralNetwork> population = new ArrayList<>();
    private Arena arena;
    private CustomCSVLogger logger;
    private TicTacToeMisereGame game = new TicTacToeMisereGame();
    private float[] dataIn = new float[9];

    private boolean[] advances;
    private int numberOfRounds;
    private PrintWriter temp;

    /**
     * @param popSize the size of the population
     */
    public TicTacToeMisere(int popSize, int... neuronCFG)
    {
        for (int i = 0; i < popSize; i++)
        {
            population.add(new NeuralNetwork(neuronCFG));
        }

        arena = new Arena(population, 0.25f);
        logger = new CustomCSVLogger(population, "P1");
        numberOfRounds = (int)(Math.log(popSize) / Math.log(2));
        advances = new boolean[population.size()];
        System.out.println(population.size());
        try
        {
            temp = new PrintWriter("temp");
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Runs the simulation for a set number of generations
     *
     * @param numberOfGenerations the number of generations the simulation will run for
     */
    public void run(int numberOfGenerations)
    {
        for (int i = 0; i < numberOfGenerations; i++)
        {
            assignFitness();
            logger.log();
            arena.evolve();
            arena.zeroFitness();
            System.out.println(i);
        }

        logger.close();
        temp.close();
    }

    public void runUntil(int age)
    {
        int counter = 0;

        while (arena.getMostFitNetwork().age < age)
        {
            assignFitness();
            logger.log();
            arena.evolve();
            arena.zeroFitness();
            System.out.println(counter);

            counter++;
        }

        logger.close();
    }

    //TODO throw an error if the number is not a power of 2
    private void assignFitness()
    {
        int p1Pos;
        int p2Pos;


        resetAdvancesArray();


        for(int i = 0; i < numberOfRounds; i++)
        {
            for(int j = 0; j < population.size() - 1; j++)
            {

                if(!advances[j])
                    continue;

                p1Pos = j;
                p2Pos = j + 1;


                while (!advances[p2Pos])
                {
                    p2Pos++;

                    if(p2Pos > population.size() - 1)
                        break;
                }

                if(p2Pos > population.size() - 1)
                    break;

                while (game.getWinner() == TicTacToeMisereGame.Player.NONE)
                {

                    flatten(game.getBoard());

                    game.turnP1NN(population.get(p1Pos).fire(dataIn));

                    if (game.getWinner() != TicTacToeMisereGame.Player.NONE)
                        break;

                    flatten(game.getBoard());
                    game.turnP2NN(population.get(p2Pos).fire(dataIn));
                }

                if (game.getWinner() == TicTacToeMisereGame.Player.PLAYER1)
                {
                    population.get(p1Pos).fitness = i;
                    advances[p2Pos] = false;
                }else if(game.getWinner() == TicTacToeMisereGame.Player.PLAYER2)
                {
                    population.get(p2Pos).fitness = i;
                    advances[p1Pos] = false;
                    j = p2Pos;
                }


                if(i == numberOfRounds - 1)
                {
                    if (game.getWinner() == TicTacToeMisereGame.Player.PLAYER1)
                    {
                        System.out.println("Here");
                        population.get(p1Pos).age++;
                    } else if(game.getWinner() == TicTacToeMisereGame.Player.PLAYER2)
                    {
                        System.out.println("Here");
                        population.get(p2Pos).age++;

                    }
                }

                game.resetBoard();
            }
/*
            int trueCounter = 0;

            for (int k = 0; k < advances.length - 1; k++)
            {

                if(advances[k])
                    trueCounter++;

                temp.print(advances[k] + " ");

            }

            temp.print(trueCounter + "\n");
*/
        }
    }

    private void flatten(int[][] in)
    {
        for (int i = 0; i < 9; i++)
        {
            dataIn[i] = in[i / 3][i % 3];
        }
    }

    private void resetAdvancesArray()
    {
        for (int i = 0; i < advances.length; i++)
        {
            advances[i] = true;
        }
    }
}
