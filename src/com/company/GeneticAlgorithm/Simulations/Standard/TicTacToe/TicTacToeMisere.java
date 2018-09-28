package com.company.GeneticAlgorithm.Simulations.Standard.TicTacToe;

import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.CustomCSVLogger;
import java.util.ArrayList;

public class TicTacToeMisere
{
    private ArrayList<NeuralNetwork> population = new ArrayList<>();
    private Arena arena;
    private CustomCSVLogger logger;
    private TicTacToeMisereGame game = new TicTacToeMisereGame();
    private float[] dataIn = new float[9];

    private int numberOfRounds;

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
    }

    //TODO throw an error if the number is not a power of 2
    private void assignFitness()
    {
        int p1Pos;
        int p2Pos;
        int counter;

        for(int i = 0; i < numberOfRounds - 1; i++)
        {
            for(int j = 0; j < population.size() - 1; j++)
            {
                if(population.get(j).fitness != i - 1)
                    continue;

                p1Pos = j;

                counter = j + 1;

                while (counter < population.size() - 1 && population.get(counter).fitness != population.get(p1Pos).fitness)
                    counter++;


                p2Pos = counter;

                if(population.get(p1Pos).fitness != population.get(p2Pos).fitness)
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
                    population.get(p1Pos).fitness = i;
                else
                    population.get(p2Pos).fitness = i;

                if(i == numberOfRounds - 2)
                    if (game.getWinner() == TicTacToeMisereGame.Player.PLAYER1)
                        population.get(p1Pos).age++;
                    else
                        population.get(p2Pos).age++;

                game.resetBoard();
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
