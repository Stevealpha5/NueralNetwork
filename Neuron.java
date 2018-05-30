package com.company.NuralNetwork;

public class Neuron
{
    /**
     * valueOut = the returned value after summation and activation functions
     *
     * Bais = the value of the  bais neuron
     *
     * weightIndex = array of all of the weights that this neurons accepts input from
     *
     * weightedInput =  an array of the input data multiplied with the corresponding weight
     */

    public float valueOut;  
    public float bais = 1;
    public float[] weightIndex;
    private float[] wightedInput;

    /**
     * @param numberIn the number of neurons this neuron will be accepting inputs fro
     *
     */
    public Neuron(int numberIn)
    {
        wightedInput = new float[numberIn];
        weightIndex = new float[numberIn];

        for(int i = 0; i < weightIndex.length; i++)
        {
            weightIndex[i] = 1.0f;
        }

    }

    /**
     *
     * @param dataIn a float array of data this neuron is to process
     * @return a single value that is the sigmoid(dataIn * weight.....+ bais)
     */
    public float fire(float[] dataIn)
    {
        float sum = 0;

        for(int i = 0; i < dataIn.length; i++)
        {
            wightedInput[i] = dataIn[i] * weightIndex[i];
        }

        for (float i : wightedInput)
            sum += i;

        sum += bais;
        valueOut = ActivationFunctions.sigmoid(sum);

        return valueOut;
    }

}
