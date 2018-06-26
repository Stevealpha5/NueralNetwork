package com.company.GANeuralNetwork;

import com.company.Utils.ActivationFunctions;

class Neuron
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

    private float valueOut;
    float bais = (float) Math.random();
    float[] weightIndex;
    float[] wightedInput;

    /**
     * @param numberIn the number of neurons this neuron will be accepting inputs fro
     *
     */
    Neuron(int numberIn)
    {
        weightIndex = new float[numberIn];
        wightedInput = new float[numberIn];

        for(int i = 0; i < weightIndex.length; i++)
        {
            weightIndex[i] = (float) Math.random();
        }
    }

    /**
     * @param dataIn a float array of data this neuron is to process
     * @return a single value that is the sigmoid(dataIn * weight.....+ bais)
     */
    float fire(float[] dataIn)
    {
        wightedInput = new float[weightIndex.length];//TODO put this in a better location that actually makes sense

        float sum = 0;

        for(int i = 0; i < dataIn.length; i++)
        {
            try
            {
                wightedInput[i] = dataIn[i] * weightIndex[i];
            }catch (Exception e)
            {
                System.out.println("WeightedIndexLengths: " + weightIndex.length);
                System.out.println("WightedInPutLength: " + wightedInput.length);
                System.out.println("DataInLength: " + dataIn.length);
                e.printStackTrace();
            }

        }

        for (float i : wightedInput)
            sum += i;

        sum += bais;
        valueOut = ActivationFunctions.sigmoid(sum);

        return valueOut;
    }

}
