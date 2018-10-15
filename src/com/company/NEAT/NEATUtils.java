package com.company.NEAT;

public class NEATUtils
{

    //todo properly count disjoint  genes
    public static double getCompatibilityDistance(Network net1, Network net2)
    {
        int excessGenes = 0;
        int disjointGenes = 0;
        int mismatchGenes = 0;

        int counter = 0;
        double weights = 0;
        double averageWeightDifference;

        for (int i = 0; i <= InovationGenerator.getConnectionMaxInovation(); i++)
        {
            if(net1.getConnectionGenes().containsKey(i) && net2.getConnectionGenes().containsKey(i))
            {
                counter++;
                weights += Math.abs(net2.getConnectionGenes().get(i).getWeight() - net1.getConnectionGenes().get(i).getWeight()) / 2;

                disjointGenes += mismatchGenes;
                mismatchGenes = 0;
                continue;
            }

            if(net1.getConnectionGenes().containsKey(i) || net2.getConnectionGenes().containsKey(i))
            {
                mismatchGenes++;
            }
        }

        excessGenes = mismatchGenes;
        averageWeightDifference = weights / counter;

        //System.out.println("Excess: " + excessGenes);
        //System.out.println("Disjoint: " + disjointGenes);
        //System.out.println("Weight Difference: " + averageWeightDifference);

        //todo add support for large networks by dividing by the number of neurons
        return (Config.C1 * excessGenes) + (Config.C2 * disjointGenes) + (Config.C3 * averageWeightDifference);
    }
}
