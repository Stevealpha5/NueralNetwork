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
        byte[] DNA1 = NN1.getDNA();
        byte[] DNA2 = NN2.getDNA();
        byte[] childDNA = new byte[DNA1.length];
        byte[] randomByte = new byte[1];

        NeuralNetwork child = new NeuralNetwork(NN1.getNeuronCfg());

        for (int i = 0; i < DNA1.length; i++)
        {
            if(random.nextBoolean())
            {
                childDNA[i] = DNA1[i];
            }else{
                childDNA[i] = DNA2[i];
            }

            if(random.nextFloat() < MUTATION_CHANCE)
                random.nextBytes(randomByte);
            childDNA[i] = randomByte[0];
        }

        child.setDNA(childDNA);
        return child;
    }

}
