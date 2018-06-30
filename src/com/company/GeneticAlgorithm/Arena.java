package com.company.GeneticAlgorithm;

import com.company.NuralNetwork.NeuralNetwork;

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

    private NeuralNetwork[] population;
    private float survivalPercent = 0.25f;
    private float deathPercent;
    private Random random = new Random();

    /**
     * @param population      the neural nets that will go though the arena
     * @param survivalPercent the percent of the population that will survive
     */
    public Arena(NeuralNetwork[] population, float survivalPercent)
    {
        this.population = population;
        this.survivalPercent = survivalPercent;
        deathPercent = 1.0f - survivalPercent;
    }

    /**
     * @param population the neural nets that will go though the arena
     */
    public Arena(NeuralNetwork[] population)
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
        arrangeByFitness();
        kill();
        repopulate();
    }

    /**
     * sorts the population by fitness from greatest to least
     */
    private void arrangeByFitness()
    {
        int sortedIndicies = 0;

        while (sortedIndicies < population.length)
        {
            for (int i = 0; i < population.length - 1; i++)
            {
                NeuralNetwork NN1 = population[i + 1];
                NeuralNetwork NN2 = population[i];

                if (population[i + 1].fitness > population[i].fitness)
                {
                    population[i] = NN1;
                    population[i + 1] = NN2;
                }
            }

            //loops through the population and checks to see if it is completely sorted

            for (int i = 0; i < population.length - 1; i++)
            {
                if (population[i].fitness < population[i + 1].fitness)
                {
                    break;
                } else
                {
                    sortedIndicies++;
                }
            }

        }
    }

    /**
     * Randomly kills the percentage of the population defined by survivalPercent and deathPercent
     */
    private void kill()
    {
        int killCounter = 0;

        //checks to make sure that the kills are equal to the desired about (to integer precision)
        while (killCounter < (int) (deathPercent * population.length))
        {
            for (int i = 0; i < population.length; i++)
            {
                float r = random.nextFloat();

                //adjusts the chances of survival based on fitness NOTE: the population must be sorted by fitness
                if (r * ((float) population.length / (float) (i + 1)) < deathPercent)
                {
                    population[i] = null;
                    killCounter++;
                }

                if (killCounter >= (int) (deathPercent * population.length))
                    return;
            }
        }
    }

    /**
     * Searches the population for null spots and makes sure the are populated
     */
    private void repopulate()
    {

        for (int i = 0; i < population.length; i++)
        {
            if (population[i] == null)
            {
                int parent1Pos = random.nextInt(population.length);
                int parent2pos = random.nextInt(population.length);

                //makes sure the selected parents are not the same or equal to null
                while (parent1Pos == parent2pos || population[parent1Pos] == null || population[parent2pos] == null)
                {
                    parent1Pos = random.nextInt(population.length);
                    parent2pos = random.nextInt(population.length);
                }

                population[i] = Mating.simpleMate(population[parent1Pos], population[parent2pos]);
            }
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

    public void printBestStats()
    {
        System.out.print("DNA: " + '{');
        for (byte x : population[0].getDNA())
        {
            System.out.print(x);
            System.out.print(',');
        }

        System.out.println('}');
        System.out.println("Fitness: " + population[0].fitness);
        System.out.println("% Accuracy: " + population[0].percentRight);
    }

    public int getHighestFitness()
    {
        return population[0].fitness;
    }


}
