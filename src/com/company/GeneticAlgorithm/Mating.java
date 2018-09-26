package com.company.GeneticAlgorithm;

import com.company.GANeuralNetwork.GANeuralNetwork;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.DNAManager;
import com.company.Utils.Utils;

import java.util.ArrayList;
import java.util.Random;

public class Mating
{
    /**
     * MUTATION_CHANCE = the chance (0.0 - 1.0) that a given gene will be converted to a random gene
     *
     * random = you should know what this is
     */
    private static float MAX_VAL = 10;
    private static float MIN_VAL = -10;
    private static float MUTATION_CHANCE = 0.05f;
    private static Random random = new Random();
    private static int maxDepth = 6;

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

        child.setDNA(Utils.capValues(simpleMateArray(DNA1, DNA2, DNA1.length), MAX_VAL, MIN_VAL));
        return child;
    }

    //TODO autogen counterpoints
    public static NeuralNetwork fivePointCrossover(NeuralNetwork NN1, NeuralNetwork NN2)
    {
        int[] crossoverPoints = {NN1.getDNA().length / 6, (NN1.getDNA().length / 6) * 2, (NN1.getDNA().length / 6) * 3, (NN1.getDNA().length / 6) * 4, (NN1.getDNA().length / 6) * 5};

        boolean isNN1DNA = true;
        int crossoverCounter = 0;
        byte[] randomByte = new byte[1];

        byte[] DNA1 = NN1.getDNA();
        byte[] DNA2 = NN2.getDNA();


        NeuralNetwork child = new NeuralNetwork(NN1.getNeuronCfg());
        byte[] childDNA = new byte[NN1.getDNA().length];

        for(int i = 0; i < NN1.getDNA().length; i++)
        {
            if(crossoverCounter < crossoverPoints.length && i == crossoverPoints[crossoverCounter])
            {
                isNN1DNA = !isNN1DNA;
                crossoverCounter++;
            }

            if(isNN1DNA)
            {
                childDNA[i] = DNA1[i];
            }else
            {
                childDNA[i] = DNA1[i];
            }

            if(random.nextFloat() < MUTATION_CHANCE)
            {
                random.nextBytes(randomByte);
                if(random.nextBoolean())
                {
                    childDNA[i] += randomByte[0];
                }else{
                    childDNA[i] -= randomByte[0];
                }
            }

        }

        child.setDNA(Utils.capValues(simpleMateArray(DNA1, DNA2, DNA1.length), MAX_VAL, MIN_VAL));

        return child;
    }

    public static byte[] simpleDNA(NeuralNetwork NN1, NeuralNetwork NN2)
    {
        byte[] DNA1 = NN1.getDNA();
        byte[] DNA2 = NN2.getDNA();

        return Utils.capValues(simpleMateArray(DNA1, DNA2, DNA1.length), MAX_VAL, MIN_VAL);
    }

    public static GANeuralNetwork simpleGANNMate(GANeuralNetwork NN1, GANeuralNetwork NN2, int inputLayer, int outputLayer)
    {
        boolean fitNetworkIsNN1 = NN1.fitness > NN2.fitness;

        GANeuralNetwork child = new GANeuralNetwork();

        int[] childCfg;

        byte[] neuronCfg1 = DNAManager.getNeuronCfgByte(NN1.getNeuronCfg());
        byte[][][] weights1 = DNAManager.getWeightsByte(NN1.getWeights());
        byte[][] baises1 = DNAManager.getBaisesBytes(NN1.getBaises());

        byte[] neuronCfg2 = DNAManager.getNeuronCfgByte(NN2.getNeuronCfg());
        byte[][][] weights2 = DNAManager.getWeightsByte(NN2.getWeights());
        byte[][] baises2 = DNAManager.getBaisesBytes(NN2.getBaises());

        byte[] neuronCfgChild;
        byte[][][] weightsChild;
        byte[][] baisesChild;


        //NeuronCfg
        if(random.nextFloat() < MUTATION_CHANCE)
        {
            neuronCfgChild = new byte[neuronCfg1.length + 1];
        }else
        {
            if (fitNetworkIsNN1)
            {
                neuronCfgChild = new byte[neuronCfg1.length];
            } else
            {
                neuronCfgChild = new byte[neuronCfg2.length];
            }
        }

        neuronCfgChild = simpleMateArray(neuronCfg1, neuronCfg2, neuronCfgChild.length);

        child.setNeuronCfg(DNAManager.getNeuronCfgClean(neuronCfgChild, inputLayer, outputLayer, maxDepth));

        childCfg = child.getNeuronCfg();




        //weights
        weightsChild = new byte[childCfg.length][][];

        for(int i = 0; i < weightsChild.length; i++)
        {
            weightsChild[i] = new byte[childCfg[i]][];

            for(int j = 0; j < weightsChild[i].length; j++)
            {
                int arraySize;

                if(i == 0)
                {
                    arraySize = childCfg[i];
                }else
                {
                    arraySize = childCfg[i - 1];
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

        //Baises

        baisesChild = new byte[childCfg.length ][];

        for(int i = 0; i < baisesChild.length; i++)
        {
            try
            {
                baisesChild[i] = simpleMateArray(baises1[i], baises2[i], childCfg[i] * 4);
            }catch(Exception e)
            {
                baisesChild[i] = new byte[childCfg[i] * 4];
                random.nextBytes(baisesChild[i]);
            }
        }

        child.setDNA(childCfg, DNAManager.getWeights(weightsChild, childCfg),DNAManager.getBaises(baisesChild, childCfg));

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
