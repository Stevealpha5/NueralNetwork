package com.company.NuralNetwork;


public class Layer
{
    /**
     * dataOut = the output data for the layer where each index is the output of a neuron
     *
     * layer = the array of neurons that make up the layer
     */
    private float[] dataOut;
    private Neuron[] layer;

    /**
     * @param numberOfNuerons the number of neurons in this layer
     * @param numInPrevLayer the number of neurons in the layer before to tell each neuron how many inputs it's getting
     */
    public Layer(int numberOfNuerons, int numInPrevLayer)
    {
        layer = new Neuron[numberOfNuerons];
        dataOut = new float[numberOfNuerons];

        for (int i = 0; i < layer.length; i++)
        {
            layer[i] = new Neuron(numInPrevLayer);
        }
    }

    /**
     * @param dataIn the output data from the previous layer, if this is the input layer then this is simply the input data
     * @return an array of the values outputted by each neuron
     */
    public float[] fire(float[] dataIn)
    {
        for(int i = 0; i < layer.length; i++)
        {
            dataOut[i] = layer[i].fire(dataIn);
        }

        return dataOut;
    }
}
