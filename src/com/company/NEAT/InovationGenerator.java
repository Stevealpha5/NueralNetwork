package com.company.NEAT;

public class InovationGenerator
{
    private int globalInovation = 0;

    public int getInovation()
    {
        globalInovation++;
        return globalInovation;
    }
}
