package com.company.GANeuralNetwork;

import com.company.Utils.InvalidNeuronConfigurationExeption;
import com.company.Utils.Utils;

import java.util.Random;

public class GANeuralNetwork
{

    /***
     * r = you know what this does
     *
     * neuronLimit = the maximum amount of neurons per layer that will be randomly generated
     * hiddenLayerLimit = the maximum amount of layers that will be randomly generated
     *
     * NN = an array of Layers that make up the neural network itself
     * neuronDfg = the layout of the network, the number of indexes is i=the number of layers and the number in each index is the number of neurons for that layer
     * weights = the weights for all of the neuron in the network
     * baises = all of th baises for all of the neuron in the network
     * fitness = this networks fitness rating
     */
    private Random r = new Random();

    private int neuronLimit = 4;
    private int hiddenLayerLimit = 0;

    private Layer[] NN;
    private int[] neuronCfg;
    private float[][][] weights;
    private float[][] baises;
    public int fitness;
    public int inputLayer;
    public int outputLayer;
    public float percentRight = 0;


    /**
     * Constructs a neural network that is designed to work with genetic algorithms. The
     * neural network is initialized with random values
     *
     * @param inputLayer  number of input neurons
     * @param outputLayer number of output neurons
     */
    public GANeuralNetwork(int inputLayer, int outputLayer)
    {
        if (inputLayer <= 0 || outputLayer <= 0)
        {
            try
            {
                throw new InvalidNeuronConfigurationExeption("input and output layers must have a value greater than 0");
            } catch (InvalidNeuronConfigurationExeption e)
            {
                e.printStackTrace();
            }
        }

        this.inputLayer = inputLayer;
        this.outputLayer = outputLayer;
        initNeuronCFG(inputLayer, outputLayer);
        initWeights();
        intiBaises();
        construct();
    }

    /**
     * An empty constructor, DNA will need to be set via the set methods
     */
    public GANeuralNetwork()
    {
    }

    /**
     * Properly formats the NeuronCfg by removing all of the zeros and making sure the input and output layers are correctly assigned
     *
     * @param inputLayer  number of input neurons
     * @param outputLayer number of output neurons
     */
    private void initNeuronCFG(int inputLayer, int outputLayer)
    {
        if(hiddenLayerLimit > 0)
        {
            neuronCfg = new int[r.nextInt(hiddenLayerLimit) + 2];
        }else
        {
            neuronCfg = new int[2];
        }

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
                } else
                {
                    weights[i][j] = new float[neuronCfg[i] * neuronCfg[i - 1]];
                }

                for (int k = 0; k < weights[i][j].length; k++)
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

        for (int i = 0; i < baises.length; i++)
        {
            baises[i] = new float[neuronCfg[i]];

            for (int j = 0; j < baises[i].length; j++)
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

        System.out.print("\nNeuron Config From Network: ");
        printNeuronCfgFromNN();
        System.out.print("\n");

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

        for (int i = 0; i < baises.length; i++)
        {

            for (int j = 0; j < baises[i].length; j++)
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

            if (i < neuronCfg.length - 1)
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

                    if (k < weights[i][j].length - 1)
                        System.out.print(", ");
                }

                System.out.print("}");

                if (j < weights[i].length - 1)
                    System.out.print(", ");
            }
            System.out.print("}");

            if (i < weights.length - 1)
                System.out.print(",");
        }

        System.out.print("};");

        System.out.println('\n' + "_____________________________________________________" + '\n');

        System.out.print("Baises: {");
        for (int i = 0; i < baises.length; i++)
        {
            System.out.print("{");
            for (int j = 0; j < baises[i].length; j++)
            {
                System.out.print(baises[i][j] + "f");

                if (j < baises[i].length - 1)
                    System.out.print(", ");
            }
            System.out.print("}");

            if (i < baises.length - 1)
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
     *
     * @param neuronCfg new neuron configuration data
     * @param weights   new weight data
     * @param baises    new bais data
     */
    public void setDNA(int[] neuronCfg, float[][][] weights, float[][] baises)
    {
        this.neuronCfg = neuronCfg;
        this.weights = weights;
        this.baises = baises;

        construct();
    }


    /**
     * Reforms the network based on the current DNA (neuronCfg, weights, and baises)
     * and updates all of the neurons weights and baises based on that DNA
     */
    private void construct()
    {
        formNetwork();

        for (int i = 0; i < baises.length - 1; i++)
        {
            //NN[i].baises = baises[i];
            NN[i].weights = weights[i];
            NN[i].setNeuronWeightsAndBaises();
        }
    }


    /**
     * Creates the structure of a network based on current DNA but does not update values
     */
    private void formNetwork()
    {
        NN = new Layer[neuronCfg.length];

        for (int i = 0; i < NN.length; i++)
        {
            if (i == 0)
            {
                NN[i] = new Layer(neuronCfg[i], neuronCfg[i]);
            } else
            {
                NN[i] = new Layer(neuronCfg[i], neuronCfg[i - 1]);
            }
        }
    }

    /**
     * Prints out the neuron configuration from the currently constructed network instead of returning the neuronCfg array
     */
    public void printNeuronCfgFromNN()
    {
        for (Layer aNN : NN)
        {
            System.out.print(aNN.layer.length + " ");
        }
    }

    /**
     * All of the functions after this point should be self explanatory
     */
    public int[] getNeuronCfg()
    {
        return neuronCfg;
    }

    public void setNeuronCfg(int[] neuronCfg)
    {
        this.neuronCfg = neuronCfg;
    }

    public float[][][] getWeights()
    {
        return weights;
    }

    public float[][] getBaises()
    {
        return baises;
    }

}
