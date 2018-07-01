package com.company.GeneticAlgorithm.Simulations;

import com.company.GANeuralNetwork.GANeuralNetwork;
import com.company.GeneticAlgorithm.Arena;
import com.company.GeneticAlgorithm.ArenaGANN;
import com.company.NuralNetwork.NeuralNetwork;

import java.util.Random;

public class MultiplicationSimGANN
{
    /**
     * DefualtFitness = the fitness that will be the base of the fitness calculation
     *
     * population = the population of neural nets
     *
     * input = an array of array inputs
     *
     * output = an array of expected array outputs
     *
     * arena = so the neural nets can fight to the death
     *
     * neuronCFG =  the configuration of the neurons
     */
    private int defualtFitness = 100000;
    private GANeuralNetwork[] population;
    private float[][] input = {{0,0,1,1},{0,1,0,1},{1,0,0,1},{1,0,1,1},{1,0,1,0},{1,1,1,0}};
    private float[][] expectedOutput = {{0,0,0,0},{0,0,0,1},{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,1,1,0}};
    private ArenaGANN arena;

    /**
     * @param popSize the size of the population
     *
     */
    public MultiplicationSimGANN(int popSize, int inputLayer, int outputLayer)
    {

        population = new GANeuralNetwork[popSize];

        for(int i = 0; i < population.length; i++)
        {
            population[i] = new GANeuralNetwork(inputLayer, outputLayer);
        }

        arena = new ArenaGANN(population, 0.25f, inputLayer, outputLayer);
    }

    /**
     * Runs the simulation for a set number of generations
     * @param numberOfGenerations the number of generations the simulation will run for
     */
    public void run(int numberOfGenerations)
    {

        for(int i = 0; i < numberOfGenerations; i++)
        {
            assingeFitness();
            arena.evolve();

            if(i % 10 == 0)
           {
                System.out.println("_____________________________________________________________________________________");
                System.out.println("Generation: " + i);
                arena.printBestStats();
           }
        }


    }

    /**
     * Runs the simulation until the most fit network is equal to or grater than the target fitness
     * @param targetFitness ...it's the target fitness...
     */
    public void runUntil(int targetFitness)
    {
        int generation = 0;

        while(arena.getHighestFitness() <= targetFitness)
        {
            assingeFitness();
            arena.evolve();

            generation++;

            if(generation % 10 == 0)
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

    private void psudoAddingeFitness()
    {
        Random r = new Random();

        for(int i = 0; i < population.length; i++)
        {
            population[i].fitness = r.nextInt(population.length);
        }
    }

    /**
     * Runs the simulation and assigned a fitness based on the networks performance
     */
    private void assingeFitness()
    {
        float[] output;


        for(int i = 0; i < population.length; i++)
        {
            population[i].fitness = 0;


            int numberRight = 0;

            for(int j = 0; j < input.length; j++)
            {
                try
                {
                    output = population[i].fire(input[j]);
                }catch (Exception e)
                {
                    population[i].printFormattedDNA();
                    System.out.println("\ni: " + i);
                    output = null;
                    e.printStackTrace();
                }



                for(int k = 0; k < output.length; k++)
                {

                    if((int)(output[k] + .5) == (int)expectedOutput[j][k])
                    {
                        numberRight++;
                    }

                    if(expectedOutput[j][k] == 1 && output[k] < 0.5)
                    {
                        population[i].fitness -= (0.5 - output[k]) * 1000;
                    }else if(expectedOutput[j][k] == 0 && output[k] >= 0.5)
                    {
                        population[i].fitness -= (output[k] - 0.51) * 1000;
                    }

                    if(Float.isNaN(output[k]))
                        population[i].fitness -= 2500000;

                }
            }

            population[i].fitness += (numberRight) * 150;
            population[i].percentRight = numberRight / 24;

            //System.out.println("Individual: " + i + " Fitness: " + population[i].fitness);



        }

        //System.out.println("-------------------------------------");

    }

}
