package com.company.NuralNetwork;


public class Layer
{
    Nuron[] layer;

    public Layer(int numberOfNuerons)
    {
        layer = new Nuron[numberOfNuerons - 1];

        for (Nuron nuron:layer)
        {
            nuron = new Nuron();
        }
    }

    public void fire(float[] dataIn)
    {
        for(int i = 0; i < layer.length; i++)
        {
            for(int j = 0; j < dataIn.length; j++)
            {

            }
        }
    }
}
