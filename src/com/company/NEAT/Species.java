package com.company.NEAT;

import java.util.ArrayList;
import java.util.Random;

public class Species implements Comparable<Species>
{
    static Random r = new Random();
    public ArrayList<Network> individuals = new ArrayList<>();
    public int speciesFitness = 0;
    double percentageOfPopulation;

    public Species(Network network)
    {
        individuals.add(network);
        network.isInSpecies = true;
    }

    public Network getMascot()
    {
        return individuals.get(r.nextInt(individuals.size()));
    }

    public void add(Network net)
    {
        individuals.add(net);
    }
    public void assingeFitness()
    {
        for(Network individual : individuals)
            speciesFitness += individual.fitness;

        speciesFitness /= individuals.size();
    }

    public int compareTo(Species o)
    {

        if(Float.isNaN(speciesFitness))
            return -1;

        if(this.speciesFitness > o.speciesFitness)
        {
            return -1;
        }else if(this.speciesFitness < o.speciesFitness)
        {
            return 1;
        }else
        {
            return 0;
        }
    }
}
