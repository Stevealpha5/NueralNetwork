package com.company.NuralNetwork;

import com.company.Utils.Utils;

import java.util.ArrayList;

public class NeuralNetwork implements Comparable<NeuralNetwork>
{
    /**
     * NN (Neural Network) = the array of layers that make up the neural network
     *
     * neuronCfg = the configuration of the neurons in the network where index 0 is the input layer and the last index is the output layer, the value at each index is the number of
     *                neurons in that layer
     *
     * fitness = the fitness score
     */
    private Layer[] NN;
    private int[] neuronCfg;
    public int fitness = -1;
    public int age = 0;
    public float percentRight = 0;
    public boolean toBeReplaced = false;

    /**
     * @param neurons saves the input into the array 'neurons' where index 0 is the input layer and the last index is the output layer, the value at each index is the number of
     *                neurons in that layer
     */
    public NeuralNetwork(int ... neurons)//5.3.3
    {
        neuronCfg = neurons;

        //the - 1 is because the output layer is not a layer it will be returned as a float[]
        NN = new Layer[neurons.length -1];

        for(int i = 0; i < NN.length; i++)
        {
            NN[i] = new Layer(neurons[i + 1], neurons[i]);
        }

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

                for (int k = 0; k < NN[i].getNeuronWeights(j).length; k++)//weights in neurons
                {
                    DNAFloat.add(NN[i].getNeuronWeights(j)[k]);//saves the weights to the DNA

                }
            }
        }

        DNAByte = Utils.floatArrayListToByteArray(DNAFloat);

        return DNAByte;
    }

    public void printWeights()
    {
        for(int i = 0; i < NN.length; i++)//layers in NN
        {
            for(int j = 0; j < NN[i].getNeurons().length; j++)//Neurons in layer
            {

                for (int k = 0; k < NN[i].getNeuronWeights(j).length; k++)//weights in neurons
                {
                   System.out.print(NN[i].getNeuronWeights(j)[k] + "      ");//saves the weights to the DNA

                }
            }
        }

    }

    /**
     * This sets the DNA of this neural net equal to that of the DNA being passed in
     * @param inputDNA The new DNA values for this neural net NOTE: the new DNA <b>MUST</>
     *            have come from a neural net with identical layer configuration
     */
    public void setDNA(byte[] inputDNA)
    {
        float[] DNA = Utils.floatArrayFromByteArray(inputDNA);

        int DNACounter = 0;

        for(int i = 0; i < NN.length; i++)//layers in NN
        {
            for(int j = 0; j < NN[i].getNeurons().length; j++)//Neurons in layer
            {
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

    public void newNetwork(byte[] DNA)
    {
        setDNA(DNA);
        fitness = -1;
        age = 0;
        percentRight = 0;
        toBeReplaced = false;
    }

    public int compareTo(NeuralNetwork o)
    {

        if(Float.isNaN(fitness))
            return -1;

        if(this.fitness > o.fitness)
        {
            return -1;
        }else if(this.fitness < o.fitness)
        {
            return 1;
        }else
        {
            return 0;
        }
    }
}
