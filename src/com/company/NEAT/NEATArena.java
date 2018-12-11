package com.company.NEAT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NEATArena
{
    private ArrayList<Network> unspeciatedPopulation;
    private ArrayList<Species> speciatedPopulation = new ArrayList<>();
    protected float survivalPercent;
    private float deathPercent;
    private Random random = new Random();
    private int killCounter;
    private int totalFitness = 0;
    private int popSize;

    public NEATArena(ArrayList<Network> population, float survivalPercent)
    {
        this.popSize = population.size();
        this.unspeciatedPopulation = population;
        this.survivalPercent = survivalPercent;
        deathPercent = 1.0f - survivalPercent;
    }

    public void evolve()
    {
        printSpecies();

        speciate();
        setSpeciesPopSize();

       for(Species species : speciatedPopulation)
       {
           Collections.sort(species.individuals);
       }

       Collections.sort(speciatedPopulation);

        kill();

        cleanSpecies();
        repopulate();
    }

    private void speciate()
    {
        for(Network net : unspeciatedPopulation)
        {
            if (speciatedPopulation.size() == 0)
            {
                speciatedPopulation.add(new Species(net));
                net.isInSpecies = true;
                continue;
            }

            for (int i = 0; i < speciatedPopulation.size(); i++)
            {
                if (NEATUtils.getCompatibilityDistance(speciatedPopulation.get(i).getMascot(), net) < Config.SPECIES_THREASHOLD)
                {
                    speciatedPopulation.get(i).add(net);
                    net.isInSpecies = true;
                    break;
                }

                speciatedPopulation.add(new Species(net));
                net.isInSpecies = true;
            }
        }

        unspeciatedPopulation.clear();
    }

    private void setSpeciesPopSize()
    {
        totalFitness = 0;

        for (Species species: speciatedPopulation)
        {
            species.assignFitness();
            totalFitness += species.getSpeciesFitness();
        }

        for (Species species: speciatedPopulation)
        {
            species.percentageOfPopulation = (float)species.getSpeciesFitness() / totalFitness;
            //System.out.println("percentage Of Pop: " + (float)species.getSpeciesFitness() / totalFitness);
        }

    }

    private void cleanSpecies()
    {
        int numSpecies = speciatedPopulation.size();

        for (int i = 0; i < numSpecies; i++)
        {
            if(speciatedPopulation.get(i).individuals.size() == 0)
            {
                //System.out.println("Species Removed");
                speciatedPopulation.remove(i);
                i--;
                numSpecies--;
            }
        }
    }

    private void kill()
    {
        killCounter = 0;
        int counter;
        int speciesSize;

        for (Species species : speciatedPopulation)
        {
            counter = species.size() - 1;
            speciesSize = species.size();

            //System.out.println("\tSpecies Percent " + species.percentageOfPopulation);

            while(species.percentageOfPopulation * popSize < species.size())
            {
                species.individuals.remove(counter);
                killCounter++;
                counter--;
            }

            counter = species.size() - 1;

            while((float)species.size() /*/ (float)speciesSize*/ > (float)speciesSize * survivalPercent && counter > 0)
            {
                if(random.nextFloat() < 1.0f - survivalPercent)
                {
                    species.individuals.remove(counter);
                    killCounter++;
                }
                counter--;
            }
        }

        //System.out.println("\tKill Counter: " + killCounter);

    }

    private void repopulate()
    {
        //NEATMating.clearSavedMutations();

        for (Species species : speciatedPopulation)
        {
            int speciesSize = species.size();

            while (speciesSize  < popSize * species.percentageOfPopulation)
            {
                int parent1Pos = random.nextInt(species.individuals.size());
                int parent2pos = random.nextInt(species.individuals.size());

                //System.out.println("0.5: " + species.individuals.get(parent1Pos).toBeReplaced + species.individuals.get(parent2pos).toBeReplaced);
                //System.out.println(parent1Pos + " " + parent2pos);

                while (species.individuals.get(parent1Pos).toBeReplaced || species.individuals.get(parent2pos).toBeReplaced)
                {
                    parent1Pos = random.nextInt(species.individuals.size());
                    parent2pos = random.nextInt(species.individuals.size());
                    System.out.println(species.individuals.get(parent1Pos).toBeReplaced +  " " + species.individuals.get(parent2pos).toBeReplaced);
                    System.out.println("parent pos: " + parent1Pos + " " + parent2pos);
                    System.out.println("Species size: " + species.individuals.size());
                    System.out.println("# of species: " + speciatedPopulation.size());
                }

                speciesSize++;
                unspeciatedPopulation.add(NEATMating.crossover(species.individuals.get(parent1Pos), species.individuals.get(parent2pos)));
            }
        }
    }

    /**
     * Prints the fitness of each individual in the unspeciatedPopulation
     */
    private void printFitness()
    {
        for (Network individual : unspeciatedPopulation)
        {
            System.out.println(individual.fitness);
        }
    }

    /**
     * Prints the sorted fitness of each individual in the unspeciatedPopulation
     */
    public void printSorttedFitness()
    {
        Collections.sort(unspeciatedPopulation);

        for (Network individual : unspeciatedPopulation)
        {
            System.out.println(individual.fitness);
        }
    }

    public Network getMostFitNetwork()
    {
        return unspeciatedPopulation.get(0);
    }

    public ArrayList<Network> getUnspeciatedPopulation(){return unspeciatedPopulation;}

    public ArrayList<Network> getSortedPopulation()
    {
        Collections.sort(unspeciatedPopulation);
        return unspeciatedPopulation;
    }

    public void zeroFitness()
    {
        for(Network net: unspeciatedPopulation)
        {
            net.fitness = -1;
        }
    }

    public void printBestStats()
    {
        cleanSpecies();
        Collections.sort(speciatedPopulation);
        Collections.sort(speciatedPopulation.get(0).individuals);
        System.out.println("Fitness: " + speciatedPopulation.get(0).individuals.get(0).fitness +"\n");
        unspeciatedPopulation.get(0).print();
    }

    public void printSpecies()
    {
        int count = 0;
        for(Species s : speciatedPopulation)
        {
            System.out.println("\tSpecies, Size, Percent: " + count + ", " + s.size() + ",  " + s.percentageOfPopulation);
            count ++;
        }
    }
}
