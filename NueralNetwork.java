package com.company.NuralNetwork;

public class NueralNetwork
{
    /**
     * NN (Neural Network) = the array of layers that make up the neural network
     */
    private Layer[] NN;

    /**
     * @param nuerons saves the input into the array 'nuerons' where index 0 is the input layer and the last index is the output layer, the value at each index is the number of
     *                neurons in that layer
     */
    public NueralNetwork(int ... nuerons)
    {
        //the - 1 is because the output layer is not a layer it will be returned as a float[]
        NN = new Layer[nuerons.length -1];

        for(int i = 0; i < NN.length; i++)
        {
            NN[i] = new Layer(nuerons[i + 1], nuerons[i]);
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
}
