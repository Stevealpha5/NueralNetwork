package com.company.NEAT;

import java.util.Random;

class NEATMating
{
    private static Random r = new Random();
    /**
     * Parent 1 must be more fit parent
     */
    static Network crossover(Network parent1, Network parent2)
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

        addNodeMutation(child);
        addConnectionMutation(child);
        weightMutation(child);

        return child;
    }

    private static void addNodeMutation(Network net)
    {
        NodeGene newNode;

        for(ConnectionGene connection : net.getConnectionGenes().values())
        {
            if(r.nextFloat() <= Config.ADD_NODE_CHANCE)
            {
                newNode = new NodeGene(NodeGene.Type.HIDDEN, InovationGenerator.getNodeInovation());
                connection.dissable();
                net.addNode(newNode);
                net.addConnection(new ConnectionGene(connection.getInNode(), newNode.getId(), 1.0f, InovationGenerator.getConnectionInovation(), true));
                net.addConnection(new ConnectionGene(newNode.getId(), connection.getOutNode(), connection.getWeight(), InovationGenerator.getConnectionInovation(), true));
                break;
            }
        }
    }

    private static void addConnectionMutation(Network net)
    {
        if(r.nextFloat() <= Config.ADD_CONNECTION_CHANCE)
        {
            net.addConnection(new ConnectionGene(r.nextInt(net.getNodeGenes().size()),
                    r.nextInt(net.getNodeGenes().size()), 1.0f, InovationGenerator.getConnectionInovation(), true));
        }
    }

    private static void weightMutation(Network net)
    {
        for(ConnectionGene connection : net.getConnectionGenes().values())
        {
            if(r.nextFloat() > Config.WEIGH_MUTATION_CHANCE)
                continue;

            if(r.nextFloat() < Config.PERTURB_CHANCE)
            {
                if(r.nextBoolean())
                {
                    connection.setWeight(connection.getWeight() * (1 + Config.PERTURB_PERCENT));
                }else
                {
                    connection.setWeight(connection.getWeight() * (1 - Config.PERTURB_PERCENT));
                }

                continue;
            }

            connection.setWeight(r.nextFloat() * (Config.RANDOM_WEIGHT_UPPER_BOUND - Config.RANDOM_WEIGHT_LOWER_BOUND) + Config.RANDOM_WEIGHT_LOWER_BOUND);
        }
    }
}
