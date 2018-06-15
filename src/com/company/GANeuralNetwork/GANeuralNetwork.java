package com.company.GANeuralNetwork;

import com.company.Utils.Utils;

import java.util.Random;

public class GANeuralNetwork
{

    /***
     * r = you know what this does
     *
     * neuronLimit = the maximum amount of neurons per layer that will be randomly generated
     * depthLimit = the maximum amount of layers that will be randomly generated
     *
     * NN = an array of Layers that make up the neural network itself
     * neuronDfg = the layout of the network, the number of indexes is i=the number of layers and the number in each index is the number of neurons for that layer
     * weights = the weights for all of the neuron in the network
     * baises = all of th baises for all of the neuron in the network
     * fitness = this networks fitness rating
     */
    private Random r = new Random();

    private int neuronLimit = 10;
    private int depthLimit = 10;

    private Layer[] NN;
    private int[] neuronCfg;
    private float[][][] weights;
    private float[][] baises;
    public int fitness;


    /**
     * Constructs a neural network that is designed to work with genetic algorithms
     * @param inputLayer number of input neurons
     * @param outputLayer number of output neurons
     */
    public GANeuralNetwork(int inputLayer, int outputLayer)
    {
        if (inputLayer <= 0 || outputLayer <= 0)
            System.out.println("You stupid");

        initNeuronCFG(inputLayer, outputLayer);
        initWeights();
        intiBaises();

    }

    /**
     * Properly formats the NeuronCfg by removing all of the zeros and making sure the input and output layers are correctly assigned
     * @param inputLayer number of input neurons
     * @param outputLayer number of output neurons
     */
    private void initNeuronCFG(int inputLayer, int outputLayer)
    {
        neuronCfg = new int[r.nextInt(depthLimit) + 2];

        for (int i = 0; i < neuronCfg.length; i++)
            neuronCfg[i] = r.nextInt(neuronLimit);

        neuronCfg[0] = inputLayer;
        neuronCfg[neuronCfg.length - 1] = outputLayer;
        neuronCfg = Utils.removeZeros(neuronCfg);
    }

    /**
     * assigns values to all of the weights in the network
     */
    private void initWeights()
    {
        weights = new float[neuronCfg.length][][];

        for (int i = 0; i < weights.length; i++)
        {
            weights[i] = new float[neuronCfg[i]][];

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

            }
        }
    }

    /**
     * assigns values to all of the baises in the network
     */
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

    /**
     * Prints all of the network information (neuron  config, weights, and baises) in a nice pretty format
     */
    public void printFormattedDNA()
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

    /**
     * prints all of the network information (neuron  config, weights, and baises) formatted as arrays, to be copy and pasted into a network
     */
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
     * Changes the network based on input DNA
     * @param neuronCfg new neuron configuration data
     * @param weights new weight data
     * @param baises new bais data
     */
    public void setDNA(int[] neuronCfg, float[][][] weights, float[][] baises)
    {
       this.neuronCfg = neuronCfg;
       this.weights = weights;
       this.baises = baises;

       construct();
    }

    public void setDNAByte(byte[] neuronCfg, byte[][][] weights, byte[][] baises)
    {
        setNeuronCfgByte(neuronCfg);
        setWeightsByte(weights);
        setBaisesBytes(baises);

        construct();
    }

    /**
     * Reforms the network based on the current DNA (neuronCfg, weights, and baises)
     * and updates all of the neurons weights and baises based on that DNA
     */
    private void construct()
    {
        matchDNA();
        formNetwork();

        for(int i = 0; i < baises.length - 1; i++)
        {/*
            for(int x: neuronCfg)
                System.out.print("," + x);

            for(int j = 0; j < baises[j].length; j++)
            {
                System.out.println("Baises:" + baises[j].length);
                System.out.println("Weights:" + weights[j].length);
            }*/

            NN[i].baises = baises[i];
            NN[i].weights = weights[i];
            NN[i].setNeuronWeightsAndBaises();
        }
    }

    private void matchDNA()
    {
        //weights
        for(int i = 0; i < weights.length; i++)
        {
            for(int j = 0; j < weights[i].length; j++)
            {
                for(int k = 0; k < weights[i][j].length; k++)
                {
                    if(weights[i][j][k] == -1)
                    {
                        weights[i][j][k] = r.nextFloat();
                    }
                }
            }
        }

        //baises
        for(int i = 0; i < baises.length; i++)
        {
            if(baises[i] == null)
                baises[i] = new float[neuronCfg[i]];

            for(int j = 0; j < baises[i].length; j++)
            {
                if(baises[i][j] == -1)
                {
                    baises[i][j] = r.nextFloat();
                }
            }
        }
    }

    /**
     * Creates the structure of a network based on current DNA but does not update values
     */
    private void formNetwork()
    {
        NN = new Layer[neuronCfg.length];

        for(int i = 0; i < NN.length; i++)
        {
            if(i == 0)
            {
                NN[i] = new Layer(neuronCfg[i], neuronCfg[i]);
            }else
            {
                NN[i] = new Layer(neuronCfg[i], neuronCfg[i - 1]);
            }
        }
    }

    public int[] getNeuronCfg()
    {
        return neuronCfg;
    }

    public  float[][][] getWeights()
    {
        return  weights;
    }

    public float[][] getBaises()
    {
        return baises;
    }

    public byte[] getNeuronCfgByte()
    {
        float[] neuronCfgFloat = new float[neuronCfg.length];

        for(int i = 0; i < neuronCfg.length; i++)
            neuronCfgFloat[i] = neuronCfg[i];


        return Utils.floatArrayToByteArray(neuronCfgFloat);
    }

    public byte[][][] getWeightsByte()
    {
        byte[][][] weightsBytes = new byte[weights.length][][];

        for(int i = 0; i < weights.length; i++)
        {
            weightsBytes[i] = new byte[weights[i].length][];

            for(int j = 0; j < weights[i].length; j++)
            {
                weightsBytes[i][j] = Utils.floatArrayToByteArray(weights[i][j]);
            }
        }

        return weightsBytes;
    }

    public byte[][] getBaisesBytes()
    {
        byte[][] baisesBytes = new byte[baises.length][];

        for(int i = 0; i < baises.length; i++)
        {
            baisesBytes[i] = Utils.floatArrayToByteArray(baises[i]);
        }

        return baisesBytes;
    }

    private void setNeuronCfgByte(byte[] neuronCfgByte)
    {
        float[] neuronCfgFloat = Utils.floatArrayFromByteArray(neuronCfgByte);
        int[] neuronCfgInt = new int[neuronCfgByte.length];

        for(int i = 0; i < neuronCfgFloat.length; i++)
            neuronCfgInt[i] = (int)neuronCfgFloat[i];


        this.neuronCfg = Utils.removeZeros(neuronCfgInt);
    }

    private void setWeightsByte(byte[][][] weightsByte)
    {
        float[][][] weightsFloat = new float[weightsByte.length][][];

        for(int i = 0; i < (weightsByte.length); i++)
        {
            weightsFloat[i] = new float[weightsByte[i].length][];

            for(int j = 0; j < (weightsByte[i].length); j++)
            {

                if(i == 0)
                {
                    weightsFloat[i][j] = Utils.floatArrayFromByteArray(weightsByte[i][j], (int)(neuronCfg[i] * neuronCfg[i]));
                }else
                {
                    weightsFloat[i][j] = Utils.floatArrayFromByteArray(weightsByte[i][j], (int)(neuronCfg[i] * neuronCfg[i - 1]));
                }

            }
        }

        this.weights = weightsFloat;
    }

    private void setBaisesBytes(byte[][] baisesBytes)
    {
        float[][] baisesFloat = new float[neuronCfg.length][];

        for(int i = 0; i < (baisesBytes.length); i++)
        {
            baisesFloat[i] = Utils.floatArrayFromByteArray(baisesBytes[i], neuronCfg[i]);
        }

        this.baises = baisesFloat;
    }

    public void printNeuronCfgFromNN()
    {
        formNetwork();

        for (Layer aNN : NN)
        {
            System.out.print(aNN.layer.length);
        }
    }
}
