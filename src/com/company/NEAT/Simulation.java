package com.company.NEAT;

import java.util.ArrayList;
import java.util.Random;

public abstract class Simulation
{
    protected Random r = new Random();
    protected ArrayList<Network> population = new ArrayList<>();
    protected NEATArena arena;
    protected int popSize;

    public Simulation(int popSize)
    {
        this.popSize = popSize;
        initPopulation();

        arena = new NEATArena(population, 0.25f);
    }

    private void initPopulation()
    {
        Network network = new Network();

        for (int i = 0; i < Config.INPUTS; i++)
            network.addNode(new NodeGene(NodeGene.Type.INPUT, InovationGenerator.getNodeNewInnovation()));


        for (int i = 0; i < Config.OUTPUTS; i++)
            network.addNode(new NodeGene(NodeGene.Type.OUTPUT, InovationGenerator.getNodeNewInnovation()));

        if(Config.INPUTS >= Config.OUTPUTS)
        {
            for (int i = 0; i < Config.INPUTS; i++)
            {
                network.addConnection(new ConnectionGene(i, i % Config.OUTPUTS, r.nextFloat(), InovationGenerator.getConnectionNewInnovation(), true));
            }

        }else{

            for (int i = 0; i < Config.OUTPUTS; i++)
            {
                network.addConnection(new ConnectionGene(i % Config.INPUTS, i, r.nextFloat(), InovationGenerator.getConnectionNewInnovation(), true));
            }
        }

        for (int i = 0; i < popSize; i++)
            population.add(network.copy());
    }

    public void run(int numberOfGenerations)
    {
        for(int i = 0; i < numberOfGenerations; i++)
        {
            System.out.println("1");
            assignFitness();
            System.out.println("2");
            arena.evolve();


            System.out.println("_____________________________________________________________________________________");
            System.out.println("Generation: " + i);
            arena.printBestStats();
        }
    }


    protected abstract void assignFitness();
}
