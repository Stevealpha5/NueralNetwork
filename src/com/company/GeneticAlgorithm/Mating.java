package com.company.GeneticAlgorithm;

import com.company.GANeuralNetwork.GANeuralNetwork;
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
    private static float MUTATION_CHANCE = 0.05f;
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

        NeuralNetwork child = new NeuralNetwork(NN1.getNeuronCfg());

        child.setDNA(simpleMateArray(DNA1, DNA2, DNA1.length));
        return child;
    }

    public static GANeuralNetwork simpleGANNMate(GANeuralNetwork NN1, GANeuralNetwork NN2)
    {
        boolean fitNetworkIsNN1 = NN1.fitness > NN2.fitness;

        GANeuralNetwork child = new GANeuralNetwork();

        int[] childCfg;

        byte[] neuronCfg1 = NN1.getNeuronCfgByte();
        byte[][][] weights1 = NN1.getWeightsByte();
        byte[][] baises1 = NN1.getBaisesBytes();

        byte[] neuronCfg2 = NN2.getNeuronCfgByte();
        byte[][][] weights2 = NN2.getWeightsByte();
        byte[][] baises2 = NN2.getBaisesBytes();

        byte[] neuronCfgChild;
        byte[][][] weightsChild;
        byte[][] baisesChild;


        //NeuronCfg
        if(fitNetworkIsNN1)
        {
            neuronCfgChild = new byte[neuronCfg1.length];
        }else
        {
            neuronCfgChild = new byte[neuronCfg2.length];
        }

        neuronCfgChild = simpleMateArray(neuronCfg1, neuronCfg2, neuronCfgChild.length);


        child.setNeuronCfgByte(neuronCfgChild, NN1.getNeuronCfg()[0], NN1.getNeuronCfg()[NN1.getNeuronCfg().length - 1]);

        childCfg = child.getNeuronCfg();

        System.out.print("Child CFG: ");
        for(int x: childCfg)
            System.out.print(x + ", ");


        //weights
        weightsChild = new byte[childCfg.length * 4][][];

        for(int i = 0; i < weightsChild.length; i++)
        {
            weightsChild[i] = new byte[childCfg[i/4] * 4][];

            for(int j = 0; j < weightsChild[i].length; j++)
            {
                int arraySize;

                if(i == 0)
                {
                    arraySize = childCfg[i/4] * childCfg[i/4];
                }else
                {
                    arraySize = childCfg[(i - 1) / 4] * childCfg[i/4];
                }

                try
                {
                    weightsChild[i][j] = simpleMateArray(weights1[i][j], weights2[i][j], arraySize * 4);
                }catch (Exception e)
                {
                    weightsChild[i][j] = new byte[arraySize * 4];
                    random.nextBytes(weightsChild[i][j]);
                }
            }
        }

        child.setWeightsByte(weightsChild);

        //Baises

        baisesChild = new byte[childCfg.length * 4][];

        for(int i = 0; i < baisesChild.length; i++)
        {
            try
            {
                baisesChild[i] = simpleMateArray(baises1[i], baises2[i], childCfg[i/4] * 4);
            }catch(Exception e)
            {
                baisesChild[i] = new byte[childCfg[i/4] * 4];
                random.nextBytes(baisesChild[i/4]);
            }
        }

        child.setBaisesBytes(baisesChild);

        return child;
    }

    private static byte[] simpleMateArray(byte[] array1, byte[] array2, int arraySize)
    {

        byte[] childArray = new byte[arraySize];
        byte[] randomByte = new byte[1];

        for (int i = 0; i < childArray.length; i++)
        {
            if(random.nextBoolean())
            {
                if(i < array1.length)
                    childArray[i] = array1[i];

            }else{
                if(i < array2.length)
                    childArray[i] = array2[i];
            }

            if(random.nextFloat() < MUTATION_CHANCE)
            {
                random.nextBytes(randomByte);
                if(random.nextBoolean())
                {
                    childArray[i] += randomByte[0];
                }else{
                    childArray[i] -= randomByte[0];
                }
            }
        }

        return childArray;
    }

}
