package com.company.NEAT;

import com.company.GeneticAlgorithm.Mating;

public class NEATTest
{
    public static void main (String[] args)
    {
        float[] in = {1 };

        Network net = new Network();

        net.addNode(new NodeGene(NodeGene.Type.INPUT, InovationGenerator.getNodeInovation()));
        net.addNode(new NodeGene(NodeGene.Type.OUTPUT, InovationGenerator.getNodeInovation()));
        net.addConnection(new ConnectionGene(0, 1, 0.5f, InovationGenerator.getConnectionInovation(),true));
        net.addConnection(new ConnectionGene(1, 0, 0.5f, InovationGenerator.getConnectionInovation(),true));

        net.print();

        float[] out = net.fire(in);
        System.out.println(out[0]);
        out = net.fire(in);
        System.out.println(out[0]);

        /*NEATMating.weightMutation(net);
        System.out.println("========================");
        net.print();

        NEATMating.weightMutation(net);
        System.out.println("========================");
        net.print();*/
    }

    private static void crossoverTest()
    {
        Network net1 = new Network();

        //Input nodes
        net1.addNode(new NodeGene(NodeGene.Type.INPUT, 0));
        net1.addNode(new NodeGene(NodeGene.Type.INPUT, 1));
        net1.addNode(new NodeGene(NodeGene.Type.INPUT, 2));

        //Hidden nodes
        net1.addNode(new NodeGene(NodeGene.Type.HIDDEN, Config.INPUTS));

        //Output nodes
        net1.addNode(new NodeGene(NodeGene.Type.INPUT, Config.INPUTS + Config.HIDDEN_NODES));


        //Connections
        net1.addConnection(new ConnectionGene(0,Config.INPUTS + Config.HIDDEN_NODES,0.5f, 1, true));
        net1.addConnection(new ConnectionGene(2,Config.INPUTS + Config.HIDDEN_NODES,0.5f, 3, true));
        net1.addConnection(new ConnectionGene(1,Config.INPUTS ,0.5f, 4, true));
        net1.addConnection(new ConnectionGene(1,Config.INPUTS + Config.HIDDEN_NODES,0.5f, 2, false));
        net1.addConnection(new ConnectionGene(Config.INPUTS,Config.INPUTS + Config.HIDDEN_NODES,0.5f, 5, true));
        net1.addConnection(new ConnectionGene(0,Config.INPUTS,0.5f, 8, true));

        Network net2 = new Network();

        //Input nodes
        net2.addNode(new NodeGene(NodeGene.Type.INPUT, 0));
        net2.addNode(new NodeGene(NodeGene.Type.INPUT, 1));
        net2.addNode(new NodeGene(NodeGene.Type.INPUT, 2));

        //Hidden nodes
        net2.addNode(new NodeGene(NodeGene.Type.HIDDEN, Config.INPUTS));
        net2.addNode(new NodeGene(NodeGene.Type.HIDDEN, Config.INPUTS + 1));

        //Output nodes
        net2.addNode(new NodeGene(NodeGene.Type.INPUT, Config.INPUTS + Config.HIDDEN_NODES));


        //Connections
        net2.addConnection(new ConnectionGene(0,Config.INPUTS + Config.HIDDEN_NODES,1f, 1, true));
        net2.addConnection(new ConnectionGene(1,Config.INPUTS + Config.HIDDEN_NODES,1f, 2, false));
        net2.addConnection(new ConnectionGene(0,Config.INPUTS + 1,1f, 10, true));
        net2.addConnection(new ConnectionGene(2,Config.INPUTS + Config.HIDDEN_NODES,1f, 3, true));
        net2.addConnection(new ConnectionGene(2,Config.INPUTS,1f, 9, true));
        net2.addConnection(new ConnectionGene(1,Config.INPUTS ,1f, 4, true));
        net2.addConnection(new ConnectionGene(Config.INPUTS,Config.INPUTS + 1,1f, 6, true));
        net2.addConnection(new ConnectionGene(Config.INPUTS,Config.INPUTS +  Config.HIDDEN_NODES,1f, 5, false));
        net2.addConnection(new ConnectionGene(Config.INPUTS +1,Config.INPUTS + Config.HIDDEN_NODES,1f, 7, true));

        Network child = NEATMating.crossover(net2, net1);



        float[] in = {1 , 1, 1};
        net1.fire(in);
        System.out.println("============================================================================");
        net2.fire(in);
        System.out.println("============================================================================");
        child.fire(in);
    }
}
