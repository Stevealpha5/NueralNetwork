package com.company.GeneticAlgorithm;

import com.company.NuralNetwork.NeuralNetwork;

import java.util.ArrayList;
import java.util.Random;

public class Mating
{
    /**
     * MUTATION_CHANCE = the chance (0.0 - 1.0) that a given gene will be converted to a random gene
     *
     * random = you should know what this is
     */
    private static float MUTATION_CHANCE = 0.025f;
    private static Random random = new Random();

    /**
     * <b>NOTE:</> the parents must have the same neuron configuration
     * @param NN1 Parent 1
     * @param NN2 Parent 2
     * @return outputs a new neural network with the same neuron configuration as the parent
     * the only difference is the genes have been shuffled up
     */
    //TODO add check to make sure the parents have the same neuron configuration
    public static NeuralNetwork simpleMate(NeuralNetwork NN1, NeuralNetwork NN2)
    {
        ArrayList<Float> DNA1 = NN1.getDNA();
        ArrayList<Float> DNA2 = NN2.getDNA();
        ArrayList<Float> childDNA = new ArrayList<Float>();

        NeuralNetwork child = new NeuralNetwork(NN1.getNeuronCfg());

        for (int i = 0; i < DNA1.size(); i++)
        {
            if(random.nextBoolean())
            {
                childDNA.add(DNA1.get(i));
            }else{
                childDNA.add(DNA2.get(i));
            }

            if(random.nextFloat() < MUTATION_CHANCE)
                childDNA.add(i, random.nextFloat());
        }

        child.setDNA(childDNA);
        return child;
    }

    public static NeuralNetwork simpleMateBinary(NeuralNetwork NN1, NeuralNetwork NN2)
    {
        ArrayList<Float> DNA1 = NN1.getDNA();
        ArrayList<Float> DNA2 = NN2.getDNA();
        ArrayList<Float> childDNA = new ArrayList<Float>();

        NeuralNetwork child = new NeuralNetwork(NN1.getNeuronCfg());

        for (int i = 0; i < DNA1.size(); i++)
        {
            if(random.nextBoolean())
            {
                childDNA.add(DNA1.get(i));
            }else{
                childDNA.add(DNA2.get(i));
            }

            if(random.nextFloat() < MUTATION_CHANCE)
                childDNA.add(i, random.nextFloat());
        }

        child.setDNA(childDNA);
        return child;
    }
}
