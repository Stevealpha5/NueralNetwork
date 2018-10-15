package com.company.NEAT;

import com.company.Utils.ActivationFunctions;

import java.util.HashMap;
import java.util.TreeMap;

public class Network implements Comparable<Network>
{
    public int fitness = 0;
    boolean toBeReplaced = false;
    boolean isInSpecies = false;

    private HashMap<Integer, ConnectionGene> connectionGenes = new HashMap<>();
    private TreeMap<Integer, NodeGene> nodeGenes = new TreeMap<>();

    public Network()
    {

    }

    public Network(int fitness, boolean toBeReplaced, boolean isInSpecies, HashMap<Integer, ConnectionGene> connectionGenes, TreeMap<Integer, NodeGene> nodeGenes)
    {
        this.fitness = fitness;
        this.isInSpecies = isInSpecies;
        this.toBeReplaced = toBeReplaced;
        this.connectionGenes = connectionGenes;
        this.nodeGenes = nodeGenes;
    }

    //TODO make less computationally expensive
    private void formNetwork()
    {
        for(NodeGene node : nodeGenes.values())
        {
            node.incomingConnections.clear();
        }

        for(ConnectionGene connection : connectionGenes.values())
        {
            nodeGenes.get(connection.getOutNode()).incomingConnections.add(connection);
        }
    }

    public float[] fire(float[] input)
    {
        float sum;
        float[] out = new float[Config.OUTPUTS];

        formNetwork();

        for(int i = 0; i < Config.INPUTS; i++)
        {
            nodeGenes.get(i).setValue(input[i]);
        }

        for(NodeGene node: nodeGenes.values())
        {
            sum = 0;

            for (ConnectionGene connection : node.incomingConnections)
            {
                if(connection.getExpressed())
                    sum += connection.getWeight() * nodeGenes.get(connection.getInNode()).getValue();
            }

            node.setValue(ActivationFunctions.sigmoid(sum, -5));
        }

        for(int i = 0; i < Config.OUTPUTS; i++)
        {
            out[i] = nodeGenes.get(Config.INPUTS + i).getValue();
        }

        return out;
    }

    HashMap<Integer, ConnectionGene> getConnectionGenes()
    {
        return connectionGenes;
    }

    TreeMap<Integer, NodeGene> getNodeGenes()
    {
        return nodeGenes;
    }

    void addNode(NodeGene node)
    {
        nodeGenes.put(node.getId(), node);
    }

    void addConnection(ConnectionGene connection)
    {
        connectionGenes.put(connection.getInnovation(), connection);
    }

    public void print()
    {
        formNetwork();

        for(NodeGene node: nodeGenes.values())
        {
            System.out.println("Node: " + node.getId());

            for (ConnectionGene connection : node.incomingConnections)
            {
                System.out.println("Connection: " + connection.getInNode() + " to " + connection.getOutNode() + " : " + connection.getWeight() + " : " + connection.getExpressed() + " : " + connection.getInnovation());
            }

            System.out.println("________________________________");
        }
    }

    public Network copy()
    {
        return new Network(fitness, toBeReplaced, isInSpecies, connectionGenes, nodeGenes);
    }

    public int compareTo(Network o)
    {

        if(Float.isNaN(fitness))
            return -1;

        if(this.fitness > o.fitness)
        {
            return -1;
        }else if(this.fitness < o.fitness)
        {
            return 1;
        }else
        {
            return 0;
        }
    }
}


