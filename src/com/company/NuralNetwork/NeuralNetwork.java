package com.company.NuralNetwork;

import java.util.ArrayList;

public class NeuralNetwork
{
    /**
     * NN (Neural Network) = the array of layers that make up the neural network
     */
    private Layer[] NN;
    private int[] nueronCfg;

    /**
     * @param neurons saves the input into the array 'neurons' where index 0 is the input layer and the last index is the output layer, the value at each index is the number of
     *                neurons in that layer
     */
    public NeuralNetwork(int ... neurons)//5.3.3
    {
        nueronCfg = neurons;

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
    public ArrayList<Float> getDNA()
    {
        ArrayList<Float> DNA = new ArrayList<Float>();

        for(int i = 0; i < NN.length; i++)//layers in NN
        {
            for(int j = 0; j < NN[i].getNeurons().length; j++)//Neurons in layer
            {
                DNA.add(NN[i].getNeuron(j).bais); //saves the bais value
                for (int k = 0; k < NN[i].getNeuronWeights(j).length; k++)//weights in neurons
                {
                    DNA.add(NN[i].getNeuronWeights(j)[k]);//saves the weights to the DNA

                    /*System.out.println("I: " + i + " J: " + j + " k: " + k);
                    System.out.println("Value: " + NN[i].getNeuronWeights(j)[k]);*/
                }
            }
        }

        return DNA;
    }

    /**
     * This sets the DNA of this neural net equal to that of the DNA being passed in
     * @param DNA The new DNA values for this neural net NOTE: the new DNA <b>MUST</>
     *            have come from a neural net with identical layer configuration
     */
    public void setDNA(ArrayList<Float> DNA)
    {
        int DNACounter = 0;

        for(int i = 0; i < NN.length; i++)//layers in NN
        {
            for(int j = 0; j < NN[i].getNeurons().length; j++)//Neurons in layer
            {
                NN[i].getNeuron(j).bais = DNA.get(DNACounter);
                DNACounter++;

                for (int k = 0; k < NN[i].getNeuronWeights(j).length; k++)//weights in neurons
                {
                    NN[i].getNeuronWeights(j)[k] = DNA.get(DNACounter);
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
        return nueronCfg;
    }
}
