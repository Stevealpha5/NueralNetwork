package com.company.NEAT;

import java.util.Random;

public class NEATMating
{
    private static Random r = new Random();
    /**
     * Parent 1 must be more fit parent
     */
    public static Network crossover(Network parent1, Network parent2)
    {
        Network child = new Network();

        for(NodeGene node : parent1.getNodeGenes().values())
        {
            child.addNode(node.copy());
        }

        for(ConnectionGene connectionGene : parent1.getConnectionGenes().values())
        {
            if(parent2.getConnectionGenes().containsKey(connectionGene.getInnovation()))
            {
                if(r.nextBoolean())
                {
                    child.addConnection(connectionGene.copy());
                }else {
                    child.addConnection(parent2.getConnectionGenes().get(connectionGene.getInnovation()).copy());
                }
            }else{
                child.addConnection(connectionGene.copy());
            }
        }

        return child;
    }
}
