package com.company.NEAT;

public class InovationGenerator
{
    private static int globalConnectionInnovation = -1;
    private static int globalNodeInnovation = -1;

    static int getConnectionInovation()
    {
        globalConnectionInnovation++;
        return globalConnectionInnovation;
    }

    static int getNodeInovation()
    {
        globalNodeInnovation++;
        return globalNodeInnovation;
    }
}
