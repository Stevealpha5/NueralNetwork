package com.company.NEAT;

import com.company.Utils.ActivationFunctions;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Network
{
    int fittness = 0;

    private boolean hasBais = true;

    private HashMap<Integer, ConnectionGene> connectionGenes = new HashMap<>();
    private TreeMap<Integer, NodeGene> nodeGenes = new TreeMap<>();

    public Network()
    {

    }

    private void formNetwork()
    {

        for(ConnectionGene connection : connectionGenes.values())
        {
            nodeGenes.get(connection.getOutNode()).incomingConnections.add(connection);
        }
    }

    public float[] fire(float[] input)
    {
        float sum;
        int key;
        NodeGene node;
        float[] out = new float[Config.OUTPUTS];

        formNetwork();

        for(int i = 0; i < Config.INPUTS; i++)
        {
            nodeGenes.get(i).setValue(input[i]);
        }

        for(Map.Entry<Integer, NodeGene> entry: nodeGenes.entrySet())
        {
            sum = 0;
            key = entry.getKey();
            node = entry.getValue();

            if(key >= Config.INPUTS)
            {
                //System.out.println("Node: " + key);
                for (ConnectionGene connection : node.incomingConnections)
                {
                    if(connection.getExpressed())
                        sum += connection.getWeight() * nodeGenes.get(connection.getInNode()).getValue();

                    //System.out.println("Connection: " + connection.getInNode() + " to " + connection.getOutNode() + " : " + connection.getWeight() + " : " + connection.getExpressed());
                }


                node.setValue(ActivationFunctions.sigmoid(sum));
                //System.out.println("Node value: " + node.getValue());
                //System.out.println("________________________________");
            }
        }

        for(int i = 0; i < Config.OUTPUTS; i ++)
        {
            out[i] = nodeGenes.get(Config.INPUTS + Config.HIDDEN_NODES + i).getValue();
        }

        return out;
    }

    public HashMap<Integer, ConnectionGene> getConnectionGenes()
    {
        return connectionGenes;
    }

    public TreeMap<Integer, NodeGene> getNodeGenes()
    {
        return nodeGenes;
    }

    public void addNode(NodeGene node)
    {
        nodeGenes.put(node.getId(), node);
    }

    public void addConnection(ConnectionGene connection)
    {
        connectionGenes.put(connection.getInnovation(), connection);
    }
}
