package com.company.GeneticAlgorithm.Simulations.Standard;

import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.XMLLogger;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

public class MultiplicationSim
{
    /**
     * DefualtFitness = the fitness that will be the base of the fitness calculation
     * <p>
     * population = the population of neural nets
     * <p>
     * input = an array of array inputs
     * <p>
     * output = an array of expected array outputs
     * <p>
     * arena = so the neural nets can fight to the death
     * <p>
     * neuronCFG =  the configuration of the neurons
     */
    private int defualtFitness = 100000;
    private ArrayList<NeuralNetwork> population = new ArrayList<>();
    private float[][] input = {{0, 0, 1, 1}, {0, 1, 0, 1}, {1, 0, 0, 1}, {1, 0, 1, 1}, {1, 0, 1, 0}, {1, 1, 1, 0}};
    private float[][] expectedOutput = {{0, 0, 0, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}};
    private Arena arena;
    private int[] neuronCFG;
    private XMLLogger logger = new XMLLogger("log.xml");

    /**
     * @param popSize   the size of the population
     * @param neuronCFG the configuration of the neurons
     */
    public MultiplicationSim(int popSize, int... neuronCFG)
    {
        this.neuronCFG = neuronCFG;

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
        try
        {
            logger.start();
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < numberOfGenerations; i++)
        {
            assingeFitness();
            logger.logToXML(i, population);
            arena.evolve();

            if (i % 10 == 0)
            {
                System.out.println("_____________________________________________________________________________________");
                System.out.println("Generation: " + i);
                arena.printBestStats();
            }
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

        while (arena.getHighestFitness() <= targetFitness)
        {
            assingeFitness();
            arena.evolve();

            generation++;

            if (generation % 10 == 0)
            {
                System.out.println("_____________________________________________________________________________________");
                System.out.println("Generation: " + generation);
                arena.printBestStats();
            }


        }


        System.out.println("_____________________________________________________________________________________");
        System.out.println("Generation: " + generation);
        arena.printBestStats();


    }

    /**
     * Runs the simulation and assigned a fitness based on the networks performance
     */
    private void assingeFitness()
    {
        float[] output;


        for (int i = 0; i < population.size(); i++)
        {
            int fitnessDeducted = 0;
            population.get(i).fitness = defualtFitness;


            int numberRight = 0;

            for (int j = 0; j < input.length; j++)
            {
                output = population.get(i).fire(input[j]);

                int numRight = 0;
                for (int k = 0; k < output.length; k++)
                {

                    if ((int) (output[k] + .5) == (int) expectedOutput[j][k])
                    {
                        numRight++;
                        numberRight++;
                    } else
                    {
                        fitnessDeducted += 200;
                    }

                    if (Float.isNaN(output[k]))
                    {
                        fitnessDeducted += -1000;
                        break;
                    }

                    if(population.get(i).fitness == -1000)
                        break;

                }

                fitnessDeducted -= Math.pow(10, numRight);

                population.get(i).fitness -= fitnessDeducted;
            }

            //population[i].fitness = (int)((float) numberRight / (4.0f * (float) input.length) * 10000);

            population.get(i).percentRight = (float) numberRight / (4.0f * (float) input.length);

        }

    }

}
