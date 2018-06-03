package com.company.GeneticAlgorithm.Simulations;

import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.NeuralNetwork;

import java.util.Random;

public class MultiplicationSim
{
    private int defualtFitness = 100000;
    private NeuralNetwork[] population;
    private float[][] input = {{0,0,0,1},{0,0,1,1},{0,1,0,1},{1,0,0,1},{1,0,1,1},{1,0,1,0},{1,1,1,0}};
    private float[][] expectedOutput = {{0,0,0,0},{0,0,0,0},{0,0,0,1},{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,1,1,0}};
    private Arena arena;
    private int[] neuronCFG;

    public MultiplicationSim(int popSize, int ... neuronCFG)
    {
        this.neuronCFG = neuronCFG;
        population = new NeuralNetwork[popSize];

        for(int i = 0; i < population.length; i++)
        {
            population[i] = new NeuralNetwork(neuronCFG);
        }

        arena = new Arena(population, 0.05f);
    }

    public void run(int numberOfGenerations)
    {
        for(int i = 0; i < numberOfGenerations; i++)
        {
            assingeFitness();
            arena.evolve();
            arena.printBestStats();
        }

    }

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

    private int assingeFitness()
    {
        float[] output;

        Random r = new Random();
        int inputIndex = r.nextInt(input.length);

        for(int i = 0; i < population.length; i++)
        {
            output = population[i].fire(input[inputIndex]);
            population[i].fitness = fitnessCalc(output, inputIndex);
            //System.out.println(population[i].fitness);
        }

        return inputIndex;
    }

    private int fitnessCalc(float[] NNOutput, int inputIndex)
    {
        int fitness = defualtFitness;

        for(int i = 0; i < NNOutput.length; i++)
        {
            fitness -= (Math.abs(NNOutput[i] - expectedOutput[inputIndex][i])) * 10000;
            //System.out.println(NNOutput[i]);
        }

        return fitness;
    }

}
