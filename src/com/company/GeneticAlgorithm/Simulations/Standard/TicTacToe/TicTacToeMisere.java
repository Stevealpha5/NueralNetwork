package com.company.GeneticAlgorithm.Simulations.Standard.TicTacToe;

import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.CustomCSVLogger;
import com.company.Utils.XMLLogger;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToeMisere
{
    private ArrayList<NeuralNetwork> populationP1 = new ArrayList<>();
    private ArrayList<NeuralNetwork> populationP2 = new ArrayList<>();
    private Arena arenaP1;
    private Arena arenaP2;
    private CustomCSVLogger logger1;
    private CustomCSVLogger logger2;
    private TicTacToeMisereGame game = new TicTacToeMisereGame();
    private float[] dataIn = new float[9];

    /**
     * @param popSize the size of the populationP1
     */
    public TicTacToeMisere(int popSize, int... neuronCFG)
    {
        for (int i = 0; i < popSize; i++)
        {
            populationP1.add(new NeuralNetwork(neuronCFG));
            populationP2.add(new NeuralNetwork(neuronCFG));
        }

        arenaP1 = new Arena(populationP1, 0.25f);
        arenaP2 = new Arena(populationP2, 0.25f);

        logger1 = new CustomCSVLogger(populationP1, "P1");
        logger2 = new CustomCSVLogger(populationP2, "P2");

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

            arenaP1.evolve();
            arenaP2.evolve();

            logger1.log();
            logger2.log();

            System.out.println(i);

/*
            System.out.println("P1________________________________________________________________________________");
            System.out.println("Generation: " + i);
            arenaP1.printBestStats();

            System.out.println("P2________________________________________________________________________________");
            System.out.println("Generation: " + i);
            arenaP2.printBestStats();*/

        }

        logger1.close();
        logger2.close();

    }

    /**
     * Runs the simulation until the most fit network is equal to or grater than the target fitness
     *
     * @param targetFitness ...it's the target fitness...
     */
    public void runUntil(int targetFitness)
    {
        int generation = 0;

        while (arenaP1.getHighestFitness() < targetFitness)
        {
            assignFitness();

            arenaP1.evolve();
            arenaP2.evolve();


            System.out.println("P1________________________________________________________________________________");
            System.out.println("Generation: " + generation);
            arenaP1.printBestStats();

            System.out.println("P2________________________________________________________________________________");
            System.out.println("Generation: " + generation);
            arenaP2.printBestStats();

            generation++;
        }

        System.out.println("P1________________________________________________________________________________");
        System.out.println("Generation: " + generation);
        arenaP1.printBestStats();

        System.out.println("P2________________________________________________________________________________");
        System.out.println("Generation: " + generation);
       arenaP2.printBestStats();

    }

    /**
     * Runs the simulation and assigned a fitness based on the networks performance
     */
    private void assignFitness()
    {
        int p1Win = 0;
        int p2Win = 0;
        float multFactor = 100;

        for (NeuralNetwork p1: populationP1)
        {
            for(NeuralNetwork p2: populationP2)
            {
                while (game.getWinner() == TicTacToeMisereGame.Player.NONE)
                {
                    flatten(game.getBoard());

                    game.turnP1NN(p1.fire(dataIn));

                    if (game.getWinner() != TicTacToeMisereGame.Player.NONE)
                        break;

                    flatten(game.getBoard());
                    game.turnP2NN(p2.fire(dataIn));
                }

                if (game.getWinner() == TicTacToeMisereGame.Player.PLAYER1)
                    p1Win++;
                else
                    p2Win++;

                game.resetBoard();

                p2.fitness = (int)((((((float)p2Win) * multFactor) + (float)p2.fitness) / 2.0));
                p2Win = 0;

                p1.fitness = (int)((((((float)p1Win) * multFactor) + (float)p1.fitness) / 2.0));
                p1Win = 0;
            }
        }
    }

    private void flatten(int[][] in)
    {
        for (int i = 0; i < 9; i++)
        {
            dataIn[i] = in[i / 3][i % 3];
        }
    }
}
