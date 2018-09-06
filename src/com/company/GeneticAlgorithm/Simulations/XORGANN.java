package com.company.GeneticAlgorithm.Simulations;

import com.company.GANeuralNetwork.GANeuralNetwork;
import com.company.GeneticAlgorithm.ArenaGANN;

import java.util.Random;

public class XORGANN
{
    private int popSize;
    private int inputLayer;
    private int outputLayer;
    private GANeuralNetwork[][] population = new GANeuralNetwork[10][];
    private float[][] input = {{0, 0}, {0, 1}, {1, 0}};
    private float[] expectedOutput = {0, 1, 1};

    private Random r = new Random();


    /**
     * @param popSize the size of the population
     */
    public XORGANN(int popSize, int inputLayer, int outputLayer)
    {
        this.popSize = popSize;
        this.inputLayer = inputLayer;
        this.outputLayer = outputLayer;

        for (int i = 0; i < population.length; i++)
        {
            population[i] = new GANeuralNetwork[popSize];

            for (int j = 0; j < popSize; j++)
            {
                population[i][j] = new GANeuralNetwork(inputLayer, outputLayer);
            }

        }

    }


    public void run()
    {
        for(int f = 0; f < 20; f++)
        {
            Thread t1  = new Thread(new Biome(population[0], input, expectedOutput, inputLayer, outputLayer));
            Thread t2  = new Thread(new Biome(population[1], input, expectedOutput, inputLayer, outputLayer));
            Thread t3  = new Thread(new Biome(population[2], input, expectedOutput, inputLayer, outputLayer));
            Thread t4  = new Thread(new Biome(population[3], input, expectedOutput, inputLayer, outputLayer));
            Thread t5  = new Thread(new Biome(population[4], input, expectedOutput, inputLayer, outputLayer));
            Thread t6  = new Thread(new Biome(population[5], input, expectedOutput, inputLayer, outputLayer));
            Thread t7  = new Thread(new Biome(population[6], input, expectedOutput, inputLayer, outputLayer));
            Thread t8  = new Thread(new Biome(population[7], input, expectedOutput, inputLayer, outputLayer));
            Thread t9  = new Thread(new Biome(population[8], input, expectedOutput, inputLayer, outputLayer));
            Thread t10 = new Thread(new Biome(population[9], input, expectedOutput, inputLayer, outputLayer));

            t1.start();
            t2.start();
            t3.start();
            t4.start();
            t5.start();
            t6.start();
            t7.start();
            t8.start();
            t9.start();
            t10.start();


            try
            {
                t1.join();
                t2.join();
                t3.join();
                t4.join();
                t5.join();
                t6.join();
                t7.join();
                t8.join();
                t9.join();
                t10.join();

            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            for (int i = 0; i < popSize * (population.length / 2); i++)
            {
                int populationIndex1 = r.nextInt(10);
                int populationIndex2 = r.nextInt(10);

                int individualIndex = r.nextInt(popSize);

                while (populationIndex1 == populationIndex2)
                {
                    populationIndex1 = r.nextInt(10);
                }

                GANeuralNetwork temp0 = population[populationIndex1][individualIndex];
                GANeuralNetwork temp1 = population[populationIndex2][individualIndex];

                population[populationIndex2][individualIndex] = temp0;
                population[populationIndex1][individualIndex] = temp1;

            }

            System.out.println("___________________________________________________________________");
            System.out.println("Biome collision: " + f);
            System.out.println("___________________________________________________________________");
        }


    }

}

class Biome implements Runnable
{
    float[][] input;
    float[] expectedOutput;
    private GANeuralNetwork[] population;
    private ArenaGANN arena;

    Biome(GANeuralNetwork[] population, float[][] input, float[] expectedOutput, int inputLayer, int outputLayer)
    {
        this.input = input;
        this.expectedOutput = expectedOutput;
        this.population = population;
        arena  = new ArenaGANN(population, 0.25f, inputLayer, outputLayer);
    }

    @Override
    public void run()
    {
        for(int i = 0; i < 250; i++)
        {
            assingeFitness();
            arena.evolve();
        }

        System.out.println("_____________________________________________________________________________________");
        arena.printBestStats();
    }

    /**
     * Runs the simulation and assigned a fitness based on the networks performance
     */
    private void assingeFitness()
    {
        float[] output;
        float numberRight;
        long startTime;
        long endTime;
        long timeDiff;


        for (int i = 0; i < population.length; i++)
        {
            population[i].fitness = 0;



            numberRight = 0;
            for (int j = 0; j < input.length; j++)
            {

               // startTime = System.currentTimeMillis();

                output = population[i].fire(input[j]);

                //endTime = System.currentTimeMillis();
                //timeDiff = startTime - endTime;


                for (int k = 0; k < output.length; k++)
                {
                    if ((int) (output[k] + .5) == (int) expectedOutput[j])
                    {
                        numberRight++;
                    }

                    if (expectedOutput[j] == 1 && output[k] < 0.5)
                    {
                        population[i].fitness -= (0.5 - output[k]) * 100;
                    } else if (expectedOutput[j] == 0 && output[k] >= 0.5)
                    {
                        population[i].fitness -= (output[k] - 0.51) * 100;
                    }

                    if (Float.isNaN(output[k]))
                        population[i].fitness -= 250000;
                }
            }

            population[i].fitness += (numberRight) * 150;
            population[i].percentRight = numberRight / expectedOutput.length;
        }

    }
}
