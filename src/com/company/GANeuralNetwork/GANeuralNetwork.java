package com.company.GANeuralNetwork;

import com.company.Utils.Utils;

import java.util.ArrayList;
import java.util.Random;

import static com.company.Utils.Utils.*;

public class GANeuralNetwork
{

    private Random r = new Random();

    private int neuronLimit = 3;
    private int depthLimit = 3;

    private Layer[] NN;
    private int[] neuronCfg;
    private float[][][] weights;
    private float[][] baises;
    public int fitness;// = r.nextInt(13);
    public float percentRight = 0;

    public GANeuralNetwork(int inputLayer, int outputLayer)
    {

        if (inputLayer <= 0 || outputLayer <= 0)
            System.out.println("You stupid");

        initNeuronCFG(inputLayer, outputLayer);
        initWeights();
        intiBaises();



    }

    private void initNeuronCFG(int inputLayer, int outputLayer)
    {
        neuronCfg = new int[r.nextInt(depthLimit) + 2];

        for (int i = 0; i < neuronCfg.length; i++)
            neuronCfg[i] = r.nextInt(neuronLimit);

        neuronCfg[0] = inputLayer;
        neuronCfg[neuronCfg.length - 1] = outputLayer;
        neuronCfg = Utils.removeZeros(neuronCfg);
    }

    private void initWeights()
    {
        //System.out.println("# of layers: " + weights.length);

        weights = new float[neuronCfg.length][][];

        for (int i = 0; i < weights.length; i++)
        {
            weights[i] = new float[neuronCfg[i]][];

           // System.out.println("# of neurons on layer " + i + ": " + weights[i].length);

            for (int j = 0; j < weights[i].length; j++)
            {
                if ((i - 1) < 0)
                {
                    weights[i][j] = new float[neuronCfg[i] * neuronCfg[i]];
                }else
                {
                    weights[i][j] = new float[neuronCfg[i] * neuronCfg[i - 1]];
                }

                for(int k = 0; k < weights[i][j].length; k++)
                {
                    weights[i][j][k] = r.nextFloat();
                }
               // System.out.println("# of weights for neuron # " + j + " in layer " + i + ": " + weights[i][j].length);

            }
        }
    }

    private void intiBaises()
    {
        baises = new float[neuronCfg.length][];

        for(int i = 0; i < baises.length; i++)
        {
            baises[i] = new float[neuronCfg[i]];

            for(int j = 0; j < baises[i].length; j++)
            {
                baises[i][j] = r.nextFloat();
            }
        }
    }

    public void printHumanReadableDNA()
    {
        System.out.print("Neuron Config: ");

        for (int x : neuronCfg)
            System.out.print(x + " ");

        System.out.println('\n' + "_____________________________________________________" + '\n');

        for (int i = 0; i < weights.length; i++)
        {
            for (int j = 0; j < weights[i].length; j++)
            {
                System.out.print("Weights of neuron " + j + " in layer " + i + ": ");

                for (int k = 0; k < weights[i][j].length; k++)
                {
                    System.out.print(weights[i][j][k] + ", ");
                }

                System.out.print('\n');
            }
        }

        System.out.println('\n' + "_____________________________________________________" + '\n');

        for(int i = 0; i < baises.length; i++)
        {

            for(int j = 0; j < baises[i].length; j++)
            {
                System.out.print("Bais of neuron " + j + " in layer " + i + ": " + baises[i][j] + '\n');
            }

        }


    }

    public void printRawDNA()
    {
        System.out.print("Neuron Config: ");

        System.out.print("{");
        for (int i = 0; i < neuronCfg.length; i++)
        {
            System.out.print(neuronCfg[i]);

            if(i < neuronCfg.length - 1)
                System.out.print(", ");
        }

        System.out.print("};");

        System.out.println('\n' + "_____________________________________________________" + '\n');

        System.out.print("Weights: {");
        for (int i = 0; i < weights.length; i++)
        {
            System.out.print("{");
            for (int j = 0; j < weights[i].length; j++)
            {

                System.out.print("{");
                for (int k = 0; k < weights[i][j].length; k++)
                {
                    System.out.print(weights[i][j][k] + "f");

                    if(k < weights[i][j].length - 1)
                        System.out.print(", ");
                }

                System.out.print("}");

                if(j < weights[i].length -1)
                    System.out.print(", ");
            }
            System.out.print("}");

            if( i < weights.length - 1)
                System.out.print(",");
        }

        System.out.print("};");

        System.out.println('\n' + "_____________________________________________________" + '\n');

        System.out.print("Baises: {");
        for(int i = 0; i < baises.length; i++)
        {
            System.out.print("{");
            for(int j = 0; j < baises[i].length; j++)
            {
                System.out.print( baises[i][j] + "f");

                if(j < baises[i].length - 1)
                    System.out.print(", ");
            }
            System.out.print("}");

            if(i < baises.length - 1)
                System.out.print(",");
        }

        System.out.print("};");


    }

    /**
     * @param dataIn the data that is fed to the input layer of the network
     * @return the values of the output layer
     */
    public float[] fire(float[] dataIn)
    {
        float[] data = dataIn;

        for (int i = 0; i < NN.length; i++)
        {
            data = NN[i].fire(data);
        }

        return data;
    }

    /**
     * Oh boy this is a method... It cycles through all of the neurons layer by layer to save the bais value and the weights to 'DNA'
     * that will be used in the genetic algorithm
     * @return A float array list that will be used by the genetic algorithm
     */
    public byte[] getDNA()
    {
        ArrayList<Float> DNAFloat = new ArrayList<Float>();
        byte[] DNAByte;

        for(int i = 0; i < NN.length; i++)//layers in NN
        {
            for(int j = 0; j < NN[i].getNeurons().length; j++)//Neurons in layer
            {
                DNAFloat.add(NN[i].getNeuron(j).bais); //saves the bais value
                for (int k = 0; k < NN[i].getNeuronWeights(j).length; k++)//weights in neurons
                {
                    DNAFloat.add(NN[i].getNeuronWeights(j)[k]);//saves the weights to the DNA

                }
            }
        }

        DNAByte = floatArrayToByteArray(DNAFloat);

        return DNAByte;
    }

    /**
     * This sets the DNA of this neural net equal to that of the DNA being passed in
     * @param inputDNA The new DNA values for this neural net NOTE: the new DNA <b>MUST</>
     *            have come from a neural net with identical layer configuration
     */
    public void setDNA(byte[] inputDNA)
    {
        float[] DNA = floatArrayFromByteArray(inputDNA);

        int DNACounter = 0;

        for(int i = 0; i < NN.length; i++)//layers in NN
        {
            for(int j = 0; j < NN[i].getNeurons().length; j++)//Neurons in layer
            {
                NN[i].getNeuron(j).bais = DNA[DNACounter];
                DNACounter++;

                for (int k = 0; k < NN[i].getNeuronWeights(j).length; k++)//weights in neurons
                {
                    NN[i].getNeuronWeights(j)[k] = DNA[DNACounter];
                    DNACounter++;
                }
            }
        }
    }

    /**
     * @return The configuration of the neurons in the network where index 0 is the input layer and the last index is the output layer, the value at each index is the number of
     *                neurons in that layer
     */
    public int[] getNeuronCfg()
    {
        return neuronCfg;
    }



}
