package com.company.GeneticAlgorithm.Simulations.Standard;


import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.XMLLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class MostInputted
{
    private float[] inputs = new float[11];
    private float expectedOut;
    private ArrayList<NeuralNetwork> population = new ArrayList<>();
    private Arena arena;
    private XMLLogger logger = new XMLLogger("log.xml");

    public MostInputted(int popSize, int... neuronCFG)
    {

        for (int i = 0; i < popSize; i++)
        {
           population.add(new NeuralNetwork(neuronCFG));
        }

        arena = new Arena(population, 0.25f);
    }

    public void runUntil(int targetFitness)
    {
        int generation = 0;

        while (arena.getHighestFitness() < targetFitness)
        {
            assignFitness();

            logger.logToXML(generation, arena.getPopulation());

            arena.evolve();

            generation++;

            /*
            if (generation % 10 == 0)
            {*/
                System.out.println("_____________________________________________________________________________________");
                System.out.println("Generation: " + generation);
                arena.printBestStats();
            //}

        }

        System.out.println("_____________________________________________________________________________________");
        System.out.println("Generation: " + generation);
        arena.printBestStats();

    }

    private void generateInputs()//todo generate non random test set
    {
        Random r = new Random();
        for(int i = 0; i < inputs.length; i++)
        {
            if(r.nextBoolean())
            {
                inputs[i] = 0;
            }else
            {
                inputs[i] = 1;
            }
        }
    }

    private void generateExpectedOut(float[] inputs)
    {
        int zero = 0;
        int one = 0;


        for (float input : inputs)
        {
            if (input == 0)
            {
                zero++;
            } else
            {
                one++;
            }
        }

        if(zero > one)
        {
            expectedOut = 0;
        }else
        {
            expectedOut = 1;
        }
    }

    private void assignFitness()
    {
        int numberOfTests = 1024;
        float[] output = {-1};
        float percentageRight;
        int numberRight;

        for(int i = 0; i < population.size(); i++)
        {
            numberRight = 0;

            for(int j = 0; j < numberOfTests; j++)
            {
                generateInputs();
                generateExpectedOut(inputs);

                output = population.get(i).fire(inputs);

                if(Math.round(output[0]) == expectedOut)
                    numberRight++;
            }

            percentageRight = ((float)numberRight / (float)numberOfTests) * 100;
            population.get(i).fitness = (int)percentageRight;

            if(Float.isNaN(output[0]))
                population.get(i).fitness = -1000;

            //System.out.println(population.get(i).fitness);

            if(output[0] == -1)
            {
                try
                {
                    throw new IOException("Output cannot be -1");
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }


}
