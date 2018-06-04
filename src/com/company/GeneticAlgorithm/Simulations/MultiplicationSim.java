package com.company.GeneticAlgorithm.Simulations;

import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.ActivationFunctions;
import com.company.NuralNetwork.NeuralNetwork;

import java.util.Random;

public class MultiplicationSim
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
    private NeuralNetwork[] population;
    /*private float[][] input = {{0,0,1,1},{0,1,0,1},{1,0,0,1},{1,0,1,1},{1,0,1,0},{1,1,1,0}};
    private float[][] expectedOutput = {{0,0,0,0},{0,0,0,1},{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,1,1,0}};*/
    private float[][] input = {{1},{2},{3},{4},{5},{6}};
    private float[][] expectedOutput = {{0,0,1},{0,1,0},{0,1,1},{1,0,0},{1,0,1},{1,1,0}};
    private Arena arena;
    private int[] neuronCFG;

    /**
     * @param popSize the size of the population
     * @param neuronCFG the configuration of the neurons
     */
    public MultiplicationSim(int popSize, int ... neuronCFG)
    {
        this.neuronCFG = neuronCFG;
        population = new NeuralNetwork[popSize];

        for(int i = 0; i < population.length; i++)
        {
            population[i] = new NeuralNetwork(neuronCFG);
        }

        arena = new Arena(population, 0.25f);
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
            arena.printBestStats();
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

            if(generation % 10 == 0)
            {
                System.out.println("_____________________________________________________________________________________");
                System.out.println("Generation: " + generation);
                arena.printBestStats();
            }

            generation++;
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
            int fitnessDeducted = 0;
            population[i].fitness = defualtFitness;

            for(int j = 0; j < input.length; j++)
            {
                output = population[i].fire(input[j]);

                for(int k = 0; k < output.length; k++)
                {
                     fitnessDeducted = (int) ((Math.abs(output[k] - expectedOutput[j][k])) * 1000);

                    if(Float.isNaN(output[k]))
                        fitnessDeducted = 25000;

                    population[i].fitness -= fitnessDeducted;
/*
                    System.out.println("Output, Expected: " + output[k] + ',' + expectedOutput[j][k]);
                    System.out.println("Fitness Deducted: " + fitnessDeducted);*/
                }
            }

        }

    }


}
