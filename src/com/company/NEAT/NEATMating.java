package com.company.NEAT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class NEATMating
{
    private static Random r = new Random();
    private static HashMap<ConnectionGene, Integer> splitConnections = new HashMap<>(); //Integer is the ID of the node that is added, the connection gene is the gene that is split
    private static ArrayList<ConnectionGene> newConnections = new ArrayList<>();
  ;
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
        boolean mutationFound = false;

        NodeGene newNode = null;
        ConnectionGene connection1 = null;
        ConnectionGene connection2 = null;

        for(ConnectionGene connection : net.getConnectionGenes().values())
        {
            if(r.nextFloat() <= Config.ADD_NODE_CHANCE)
            {
                //TODO make this a separate function if possible

                for (ConnectionGene con : splitConnections.keySet())
                {
                    if(con.equals(connection))
                    {
                        newNode = new NodeGene(NodeGene.Type.HIDDEN, splitConnections.get(con));


                        for (ConnectionGene con2 : newConnections)
                        {
                            if(con2.getInNode() == con.getInNode() && con2.getOutNode() == newNode.getId())
                            {
                                connection1 = con2.copy();
                            }

                            if(con2.getInNode() == newNode.getId() && con2.getOutNode() == con.getOutNode())
                            {
                                connection2 = con2.copy();
                            }
                        }

                        mutationFound = true;
                        break;
                    }
                }


                if(!mutationFound)
                {
                    newNode = new NodeGene(NodeGene.Type.HIDDEN, InovationGenerator.getNodeNewInnovation());
                    connection1 = new ConnectionGene(connection.getInNode(), newNode.getId(), 1.0f, InovationGenerator.getConnectionNewInnovation(), true);
                    connection2 = new ConnectionGene(newNode.getId(), connection.getOutNode(), connection.getWeight(), InovationGenerator.getConnectionNewInnovation(), true);

                    splitConnections.put(connection, newNode.getId());

                    newConnections.add(connection1);
                    newConnections.add(connection2);
                }

                connection.dissable();
                net.addNode(newNode);
                net.addConnection(connection1);
                net.addConnection(connection2);
                break;
            }
        }
    }

    private static void addConnectionMutation(Network net)
    {
        if(r.nextFloat() <= Config.ADD_CONNECTION_CHANCE)
        {
            boolean mutationFound = false;
            int node1 = r.nextInt(net.getNodeGenes().size());
            int node2 = r.nextInt(net.getNodeGenes().size());
            ConnectionGene newConnection = null;

            for(ConnectionGene con : newConnections)
            {
                if(node1 == con.getInNode() && node2 == con.getOutNode())
                {
                    newConnection = con.copy();
                    mutationFound = true;
                    break;
                }
            }

            if(!mutationFound)
            {
                newConnection = new ConnectionGene(node1, node2, 1.0f, InovationGenerator.getConnectionNewInnovation(), true);
                newConnections.add(newConnection);
            }

            net.addConnection(newConnection);
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

    static void clearSavedMutations()
    {
        splitConnections.clear();
        newConnections.clear();
    }
}
