package com.company.GeneticAlgorithm.Simulations.Standard.TicTacToe;

import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.XMLLogger;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToeMisere
{
    private ArrayList<NeuralNetwork> population = new ArrayList<>();
    private Arena arena;
    private XMLLogger logger = new XMLLogger();
    private TicTacToeMisereGame game = new TicTacToeMisereGame();
    private Random r = new Random();
    private float[] dataIn = new float[9];
    private float[] dataOut = new float[9];

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
    }

    /**
     * Runs the simulation for a set number of generations
     *
     * @param numberOfGenerations the number of generations the simulation will run for
     */
    public void run(int numberOfGenerations)
    {
        try
        {
            logger.start();

        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < numberOfGenerations; i++)
        {
            assignFitness();
            logger.logToXML(i, arena.getPopulation());
            arena.evolve();


            System.out.println("_____________________________________________________________________________________");
            System.out.println("Generation: " + i);
            arena.printBestStats();

        }

        logger.close();


    }

    /**
     * Runs the simulation until the most fit network is equal to or grater than the target fitness
     *
     * @param targetFitness ...it's the target fitness...
     */
    public void runUntil(int targetFitness)
    {
        int generation = 0;

        try
        {
            logger.start();
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }

        while (arena.getHighestFitness() < targetFitness)
        {
            assignFitness();
            logger.logToXML(generation, arena.getPopulation());
            arena.evolve();

            generation++;

            if (generation % 10 == 0)
            {
                System.out.println("_____________________________________________________________________________________");
                System.out.println("Generation: " + generation);
                arena.printBestStats();
            }

        }

        logger.close();

        System.out.println("_____________________________________________________________________________________");
        System.out.println("Generation: " + generation);
        arena.printBestStats();

    }

    /**
     * Runs the simulation and assigned a fitness based on the networks performance
     */
    private void assignFitness()
    {
        int numberOfGames = 5;
        int numberOfOpponents = 100;
        int p2;
        int p1Win = 0;
        int p2Win = 0;



        for (int i = 0; i < population.size(); i++)
        {
            for(int k = 0; k <  numberOfOpponents; k++)
            {
                p2 = r.nextInt(population.size());
                for (int j = 0; j < numberOfGames; j++)
                {
                    while (game.getWinner() == TicTacToeMisereGame.Player.NONE)
                    {
                        flatten(game.getBoard());

                        try
                        {
                            game.turnP1(numFromNNOut(population.get(i).fire(dataIn)));
                        } catch (IllegalStateException e)
                        {
                            population.get(i).fitness -= 100;
                            game.setWinner(TicTacToeMisereGame.Player.PLAYER2);
                            break;
                        }

                        if (game.getWinner() != TicTacToeMisereGame.Player.NONE)
                            break;

                        flatten(game.getBoard());


                        try
                        {
                            game.turnP2(numFromNNOut(population.get(p2).fire(dataIn)));
                        } catch (IllegalStateException e)
                        {
                            population.get(k).fitness -= 100;
                            game.setWinner(TicTacToeMisereGame.Player.PLAYER1);
                            break;
                        }
                    }

                    if (game.getWinner() == TicTacToeMisereGame.Player.PLAYER1)
                        p1Win++;
                    else
                        p2Win++;

                    game.resetBoard();
                }

                population.get(k).fitness = (int)(((((float)p2Win / (float)numberOfGames) * 100) + population.get(p2).fitness) / 2);
                p2Win = 0;

                population.get(i).fitness = (int)(((((float)p1Win / (float)numberOfGames) * 100) + population.get(i).fitness) / 2);
                p1Win = 0;
            }

        }
    }

    private int numFromNNOut(float[] rawOut)
    {

        int biggestLoc = -1;
        float maxNum = -21;

        for(int i = 0; i < rawOut.length; i++)
        {
            if(rawOut[i] > maxNum)
            {
                maxNum = rawOut[i];
                biggestLoc = i;
            }
        }

        return biggestLoc;
    }

    private void flatten(int[][] in)
    {
        for (int i = 0; i < 9; i++)
        {
            dataIn[i] = in[i / 3][i % 3];
        }
    }
}
