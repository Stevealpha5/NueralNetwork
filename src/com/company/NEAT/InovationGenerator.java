package com.company.NEAT;

import java.util.ArrayList;

public class InovationGenerator
{
    private static int globalConnectionInnovation = -1;
    private static int globalNodeInnovation = -1;

    private static int speciesCounter = -1;

    static int getConnectionNewInnovation()
    {
        globalConnectionInnovation++;
        return globalConnectionInnovation;
    }

    static int getNodeNewInnovation()
    {
        globalNodeInnovation++;
        return globalNodeInnovation;
    }

    static int getConnectionMaxInovation()
    {
        return globalConnectionInnovation;
    }
}
