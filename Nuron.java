package com.company.NuralNetwork;

public class Nuron
{
    public float valueOut;  
    public float bais = 1;
    private int prevLayerLen;

    public Nuron(int prevLayerLen)
    {
        this.prevLayerLen = prevLayerLen;
    }

    public float fire(float dataIn, float weightIn)
    {
        valueOut = ActivationFunctions.sigmoid((dataIn * weightIn) + bais);
        return valueOut;
    }

}
