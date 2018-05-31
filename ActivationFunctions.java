package com.company.NuralNetwork;

class ActivationFunctions
{
    static float sigmoid(float x)
    {
        return (float)(1 / (1 + Math.exp(-x)));
    }

    public static float sigmoid(float x, int k)
    {
        return (float)(1 / (1 + Math.exp(-x)));
    }
}
