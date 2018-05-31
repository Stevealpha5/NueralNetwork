package com.company.NuralNetwork;


class Layer
{
    /**
     * dataOut = the output data for the layer where each index is the output of a neuron
     *
     * layer = the array of neurons that make up the layer
     *
     * numberOfNuerons = the number neurons in this layer
     */
    private float[] dataOut;
    private Neuron[] layer;
    private int numberOfNuerons;

    /**
     * @param numberOfNuerons the number of neurons in this layer
     * @param numInPrevLayer the number of neurons in the layer before to tell each neuron how many inputs it's getting
     */
    Layer(int numberOfNuerons, int numInPrevLayer)
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
    float[] fire(float[] dataIn)
    {
        for(int i = 0; i < layer.length; i++)
        {
            dataOut[i] = layer[i].fire(dataIn);
        }

        return dataOut;
    }

    int getNumberOfNuerons()
    {
        return numberOfNuerons;
    }

    /**
     * returns the layer so the neurons can be accessed elsewhere
     * @return the outputted neurons in an array
     */
    Neuron[] getNeurons()
    {
        return layer;
    }

    /**
     * @param index the index of the neuron in the layer that you want to retrieve
     * @return a specific neuron in the layer
     */
    Neuron getNeuron(int index)
    {
        return layer[index];
    }

    /**
     * @param index the index of the neuron that you want to get the weights for
     * @return all of the weights of the requested neuron
     */
    float[] getNeuronWeights(int index)
    {
        return layer[index].weightIndex;
    }
}
