package com.company.Utils;

public class ActivationFunctions
{
    public static float sigmoid(float x)
    {
        return (float)(1 / (1 + Math.exp(-x)));
    }
/*
    public static float sigmoidLookUp(float x)
    {

    }*/

    public static float sigmoid(float x, int k)
    {
        return (float)(1 / (1 + Math.exp(-x)));
    }

    public static float threashold(float x)
    {
        if(x < 0.5f)
        {
            return 0f;
        }else
        {
            return 1.0f;
        }
    }

    public static float ReLU(float x)
    {
        if(x < 0)
        {
            return 0;
        }else
        {
            return x;
        }
    }
}
