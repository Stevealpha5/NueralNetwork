package com.company.NEAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NEATArena
{
    private ArrayList<Network> population;
    private ArrayList<Species> speciatedPopulation = new ArrayList<>();
    protected float survivalPercent;
    private float deathPercent;
    private Random random = new Random();
    private int killCounter;
    private int totalFitness = 0;

    public NEATArena(ArrayList<Network> population, float survivalPercent)
    {
        this.population = population;
        this.survivalPercent = survivalPercent;
        deathPercent = 1.0f - survivalPercent;
    }

    public void evolve()
    {

        speciate();
        setSpeciesPopsize();

       for(Species species : speciatedPopulation)
       {
           Collections.sort(species.individuals);
       }
        Collections.sort(population);

        kill();
        cleanSpecies();
        repopulate();
    }

    private void speciate()
    {
        for(Network net : population)
        {
            if(!net.isInSpecies)
            {
                if(speciatedPopulation.size() == 0)
                {
                    speciatedPopulation.add(new Species(net));
                    continue;
                }

                for(int i = 0; i < speciatedPopulation.size(); i++)
                {
                    if(NEATUtils.getCompatibilityDistance(speciatedPopulation.get(i).getMascot(), net) < Config.SPECIES_THRESHOLD)
                    {
                        speciatedPopulation.get(i).add(net);
                        break;
                    }

                    speciatedPopulation.add(new Species(net));
                }
            }
        }
    }

    private void setSpeciesPopsize()
    {
        for (Species species: speciatedPopulation)
        {
            species.assingeFitness();
            totalFitness += species.speciesFitness;
        }

        for (Species species: speciatedPopulation)
            species.percentageOfPopulation = species.speciesFitness / totalFitness;

    }

    private void cleanSpecies()
    {
        int numSpecies = speciatedPopulation.size();
        for (int i = 0; i < numSpecies; i++)
        {
            if(speciatedPopulation.get(i).individuals.size() == 0)
            {
                speciatedPopulation.remove(i);
                i--;
                numSpecies--;
            }
        }
    }

    private void kill()
    {
        float popSize = population.size();
        killCounter = 0;

        //checks to make sure that the kills are equal to the desired about (to integer precision)
        while (killCounter < (int) (deathPercent * popSize))
        {

            for (Species species : speciatedPopulation)
            {
                if(species.individuals.size() < Config.SAFE_SPECIES_SIZE)
                    continue;

                while(species.individuals.size() > species.percentageOfPopulation * popSize)
                {
                    float r = random.nextFloat();

                    if (r * (species.individuals.size() / (float) ((species.individuals.size() * 0.1 * i) + 1)) < deathPercent)
                        species.individuals.get(i).toBeReplaced = true;

                }

                for(int i = 0; i < species.individuals.size(); i++)
                {
                    float r = random.nextFloat();

                    //adjusts the chances of survival based on fitness NOTE: the population must be sorted by fitness
                    if (r * (species.individuals.size() / (float) ((species.individuals.size() * 0.1 * i) + 1)) < deathPercent)
                    {
                        species.individuals.get(i).toBeReplaced = true;
                        killCounter++;
                    }

                    if (killCounter >= (int) (deathPercent * popSize))
                        return;
                }
            }
        }


    }

    private void repopulate()
    {
        NEATMating.clearSavedMutations();

        for (Species species : speciatedPopulation)
        {

            System.out.println("lsdj;lahf " + speciatedPopulation.size());

           if(speciatedPopulation.size() > 1)
           {
               for (Species s :
                       speciatedPopulation)
               {
                   s.getMascot().print();
                   System.out.println("################################");
               }

               try
               {
                   Thread.sleep(30000);
               } catch (InterruptedException e)
               {
                   e.printStackTrace();
               }
           }


            for (int i = 0; i < species.individuals.size(); i++)
            {
                if(!species.individuals.get(i).toBeReplaced)
                    continue;

                int parent1Pos = random.nextInt(species.individuals.size());
                int parent2pos = random.nextInt(species.individuals.size());

                System.out.println("0.5: " + species.individuals.get(parent1Pos).toBeReplaced + species.individuals.get(parent2pos).toBeReplaced);
                System.out.println(parent1Pos + " " + parent2pos);

                int couter = 0;
                while (/*parent1Pos == parent2pos ||*/ species.individuals.get(parent1Pos).toBeReplaced || species.individuals.get(parent2pos).toBeReplaced)
                {
                    parent1Pos = random.nextInt(species.individuals.size());
                    parent2pos = random.nextInt(species.individuals.size());
                    couter++;
                    System.out.println(species.individuals.get(parent1Pos).toBeReplaced +  " " + species.individuals.get(parent2pos).toBeReplaced);
                    System.out.println("parent pos: " + parent1Pos + " " + parent2pos);
                    System.out.println("Species zis: " + species.individuals.size());
                    System.out.println("# of species: " + speciatedPopulation.size());
                }

                System.out.println("9: " + couter);

                species.individuals.set(i, NEATMating.crossover(species.individuals.get(parent1Pos), species.individuals.get(parent2pos)));
            }
        }
    }

    /**
     * Prints the fitness of each individual in the population
     */
    private void printFitness()
    {
        for (Network individual : population)
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

        for (Network individual : population)
        {
            System.out.println(individual.fitness);
        }
    }

    public Network getMostFitNetwork()
    {
        return population.get(0);
    }

    public ArrayList<Network> getPopulation(){return population;}

    public ArrayList<Network> getSortedPopulation()
    {
        Collections.sort(population);
        return population;
    }

    public void zeroFitness()
    {
        for(Network net: population)
        {
            net.fitness = -1;
        }
    }

    public void printBestStats()
    {
        Collections.sort(population);
        System.out.println("Fitness: " + population.get(0).fitness);
    }
}
