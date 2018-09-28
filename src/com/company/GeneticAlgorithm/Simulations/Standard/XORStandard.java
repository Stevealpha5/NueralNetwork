package com.company.GeneticAlgorithm.Simulations.Standard;

import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.XMLLogger;

import java.util.ArrayList;

public class XORStandard
{
    private ArrayList<NeuralNetwork> population = new ArrayList<>();
    private float[][] input = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    private float[] expectedOutput = {1, 0, 0, 1};
    private Arena arena;
    private XMLLogger logger = new XMLLogger("log.xml");

    /**
     * @param popSize the size of the population
     */
    public XORStandard(int popSize, int... neuronCFG)
    {
        for (int i = 0; i < popSize; i++)
        {
            population.add(new NeuralNetwork(neuronCFG));
        }

        arena = new Arena(population, 0.5f);
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
            logger.logToXML(i, arena.getPopulation());
            arena.evolve();

            if (i % 10 == 0)
            {
                System.out.println("_____________________________________________________________________________________");
                System.out.println("Generation: " + i);
                arena.printBestStats();
            }
        }
    }

    /**
     * Runs the simulation until the most fit network is equal to or grater than the target fitness
     *
     * @param targetFitness ...it's the target fitness...
     */
    public int runUntil(int targetFitness)
    {
        int generation = 0;

        while (arena.getHighestFitness() < targetFitness)
        {
            assignFitness();
            logger.logToXML(generation, arena.getPopulation());
            arena.evolve();

            generation++;

            if (generation % 10 == 0)
            {
                /*System.out.println("_____________________________________________________________________________________");
                System.out.println("Generation: " + generation);
                arena.printBestStats();*/
            }

        }

       /* System.out.println("_____________________________________________________________________________________");
        System.out.println("Generation: " + generation);
        arena.printBestStats();*/

        return generation;

    }

    /**
     * Runs the simulation and assigned a fitness based on the networks performance
     */
    private void assignFitness()
    {
        float[] output;
        float numberRight = 0;


        for (int i = 0; i < population.size(); i++)
        {
            population.get(i).fitness = 0;

            for (int j = 0; j < input.length; j++)
            {

                output = population.get(i).fire(input[j]);


                for (int k = 0; k < output.length; k++)
                {
                    if ((int) (output[k] + .5) == (int) expectedOutput[j])
                    {
                        numberRight++;
                    }

                    if (expectedOutput[j] == 1 && output[k] < 0.5)
                    {
                        population.get(i).fitness -= (0.5 - output[k]) * 100;
                    } else if (expectedOutput[j] == 0 && output[k] >= 0.5)
                    {
                        population.get(i).fitness -= (output[k] - 0.51) * 100;
                    }

                    if (Float.isNaN(output[k]))
                    {
                        population.get(i).fitness = -1000;
                        population.get(i).printWeights();
                        System.out.println();
                        break;
                    }
                }

                if( population.get(i).fitness == -1000)
                    break;


            }

            population.get(i).fitness += (numberRight) * 15;
            population.get(i).percentRight = numberRight / expectedOutput.length;
            numberRight = 0;

            //System.out.println("Individual: " + i + " Fitness: " + population[i].fitness);


        }

        //System.out.println("-------------------------------------");

    }

}
