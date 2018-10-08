package com.company.GeneticAlgorithm;

import com.company.NuralNetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Arena
{
    /**
     * population = all of the neural nets to be killed and breaded
     * <p>
     * survivalPercent = the percent of the population that will survive (default = 25%)
     * <p>
     * deathPercent = the percent of the population that will die
     */

    private ArrayList<NeuralNetwork>  population;
    private float survivalPercent = 0.25f;
    private float deathPercent;
    private Random random = new Random();
    private int killCounter;

    /**
     * @param population      the neural nets that will go though the arena
     * @param survivalPercent the percent of the population that will survive
     */
    public Arena(ArrayList<NeuralNetwork> population, float survivalPercent)
    {
        this.population = population;
        this.survivalPercent = survivalPercent;
        deathPercent = 1.0f - survivalPercent;
    }

    /**
     * @param population the neural nets that will go though the arena
     */
    public Arena(ArrayList<NeuralNetwork> population)
    {
        this.population = population;
        deathPercent = 1.0f - survivalPercent;
    }

    /**
     * arranges the population from greatest to least fitness
     * randomly kills them and then reproduces the replacements
     */
    public void evolve()
    {
        Collections.sort(population);
        kill();
        repopulate();
    }


    /**
     * Randomly kills the percentage of the population defined by survivalPercent and deathPercent
     */
    private void kill()
    {
        float popSize = population.size();
        killCounter = 0;

        //checks to make sure that the kills are equal to the desired about (to integer precision)
        while (killCounter < (int) (deathPercent * popSize))
        {
            for (int i = (int)popSize - 1; i > 0; i--)
            {
                float r = random.nextFloat();

                //adjusts the chances of survival based on fitness NOTE: the population must be sorted by fitness
                if (r * (population.size() / (float) ((population.size() * 0.1 * i) + 1)) < deathPercent)
                {
                    population.get(i).toBeReplaced = true;
                    killCounter++;
                }

                if (killCounter >= (int) (deathPercent * popSize))
                    return;
            }
        }
    }

    /**
     * Searches the population for null spots and makes sure the are populated
     */
    private void repopulate()
    {
        for (int i = 0; i < killCounter; i++)
        {
            int parent1Pos = random.nextInt(population.size());
            int parent2pos = random.nextInt(population.size());

            while (parent1Pos == parent2pos  || !population.get(parent1Pos).toBeReplaced || !population.get(parent2pos).toBeReplaced)
            {
                parent1Pos = random.nextInt(population.size());
                parent2pos = random.nextInt(population.size());
            }

            population.get(i).newNetwork(Mating.simpleDNA(population.get(parent1Pos), population.get(parent2pos)));
        }
    }

    /**
     * Prints the fitness of each individual in the population
     */
    private void printFitness()
    {
        for (NeuralNetwork individual : population)
        {
            System.out.println(individual.fitness);
        }
    }
    /**
     * Prints the sorted fitness of each individual in the population
     */
    public void printSorttedFitness()
    {
        Collections.sort(population);

        for (NeuralNetwork individual : population)
        {
            System.out.println(individual.fitness);
        }
    }

    public void printBestStats()
    {
        System.out.print("DNA: " + '{');
        for (byte x : population.get(0).getDNA())
        {
            System.out.print(x);
            System.out.print(',');
        }

        System.out.println('}');
        System.out.println("Fitness: " + population.get(0).fitness);
        System.out.println("% Accuracy: " + population.get(0).percentRight);
    }

    @Deprecated
    public int getHighestFitness()
    {
        return population.get(0).fitness;
    }

    public NeuralNetwork getMostFitNetwork()
    {
        return population.get(0);
    }


    public ArrayList<NeuralNetwork> getPopulation(){return population;}

    public ArrayList<NeuralNetwork> getSortedPopulation()
    {
        Collections.sort(population);
        return population;
    }

    public void zeroFitness()
    {
        for(NeuralNetwork net: population)
        {
            net.fitness = -1;
        }
    }

}
