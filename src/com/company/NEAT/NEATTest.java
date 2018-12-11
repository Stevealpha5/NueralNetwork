package com.company.NEAT;

import com.company.NEAT.Simulations.XOR;

import java.util.Random;

public class NEATTest
{
    public static void main (String[] args)
    {
        XOR sim = new XOR(5000);
        sim.run(5000);

        //trainingTest();
        //crossoverTest();

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

        net2 = initPopulation();
        net1 = net2.copy();

        net2.print();
        NEATMating.addNodeMutation(net2);
        NEATMating.addNodeMutation(net2);
        NEATMating.addNodeMutation(net2);
        NEATMating.addNodeMutation(net2);
        NEATMating.addNodeMutation(net2);
        System.out.println("111111111111111111111111111111111111111111111111111111111111111111111");
        net2.print();
        //NEATMating.clearSavedMutations();
        NEATMating.addConnectionMutation(net2);
        NEATMating.addConnectionMutation(net2);
        NEATMating.addConnectionMutation(net2);
        NEATMating.addConnectionMutation(net2);
        NEATMating.addConnectionMutation(net2);
        System.out.println("22222222222222222222222222222222222222222222222222222222222222222222");
        net2.print();


        System.out.println("============================================================================");
        net1.print();
        NEATMating.addNodeMutation(net1);
        System.out.println("============================================================================");
        net1.print();
        NEATMating.addConnectionMutation(net1);
        System.out.println("============================================================================");
        net1.print();




        float[] in = {1 , 1};
        System.out.println(net2.fire(in)[0]);
        System.out.println(net1.fire(in)[0]);
        /*System.out.println("============================================================================");
        net2.fire(in);
        System.out.println("============================================================================");
        child.fire(in);*/
    }

    private static void trainingTest()
    {
        Network net = new Network();

        net.addNode(new NodeGene(NodeGene.Type.INPUT, 0));
        net.addNode(new NodeGene(NodeGene.Type.OUTPUT, 1));

        net.addConnection(new ConnectionGene(0, 1, 0.49049497f, 0, true));

        float[] in = {0,1};
        float[] out = net.fire(in);
        System.out.println(out[0]);
    }

    private static void mutaionTest()
    {
        float[] in = {1 };

        Network net = new Network();

        net.addNode(new NodeGene(NodeGene.Type.INPUT, InovationGenerator.getNodeNewInnovation()));
        net.addNode(new NodeGene(NodeGene.Type.OUTPUT, InovationGenerator.getNodeNewInnovation()));
        net.addConnection(new ConnectionGene(0, 1, 0.5f, InovationGenerator.getConnectionNewInnovation(),true));
        net.addConnection(new ConnectionGene(0, 0, 0.5f, InovationGenerator.getConnectionNewInnovation(),true));

        //NEATMating.addNodeMutation(net);
        net.print();

        System.out.println("========================");

        Network net2 = new Network();

        net2.addNode(new NodeGene(NodeGene.Type.INPUT, 0));
        net2.addNode(new NodeGene(NodeGene.Type.OUTPUT, 1));
        net2.addConnection(new ConnectionGene(0, 1, 0.5f, 0,true));
        net2.addConnection(new ConnectionGene(0, 0, 0.5f, 1,true));

       //NEATMating.addNodeMutation(net2);
        net2.print();

        /*
        float[] out = net.fire(in);
        System.out.println(out[0]);
        out = net.fire(in);
        System.out.println(out[0]);

        NEATMating.weightMutation(net);
        System.out.println("========================");
        net.print();

        NEATMating.weightMutation(net);
        System.out.println("========================");
        net.print();*/
    }

    private static void speciationTeest()
    {
        Network net1 = new Network();

        net1.addNode(new NodeGene(NodeGene.Type.INPUT, 0));
        net1.addNode(new NodeGene(NodeGene.Type.INPUT, 1));
        net1.addNode(new NodeGene(NodeGene.Type.OUTPUT, 2));

        // net1.addConnection(new ConnectionGene(0, 2, 1, 0, true));
        net1.addConnection(new ConnectionGene(1, 2, 0.5f, 1, true));


        Network net2 = new Network();

        net2.addNode(new NodeGene(NodeGene.Type.INPUT, 0));
        net2.addNode(new NodeGene(NodeGene.Type.INPUT, 1));
        net2.addNode(new NodeGene(NodeGene.Type.OUTPUT, 2));
        net2.addNode(new NodeGene(NodeGene.Type.HIDDEN, 3));

        net2.addConnection(new ConnectionGene(0, 2, 1, 0, true));
        net2.addConnection(new ConnectionGene(1, 2, 1, 1, false));
        net2.addConnection(new ConnectionGene(1, 3, 1, 2, true));
        net2.addConnection(new ConnectionGene(3, 2, 1, 3, true));

        System.out.println(NEATUtils.getCompatibilityDistance(net1, net2));
    }

    private static Network initPopulation()
    {
        Network network = new Network();
        Random r = new Random();

        for (int i = 0; i < Config.INPUTS; i++)
            network.addNode(new NodeGene(NodeGene.Type.INPUT, InovationGenerator.getNodeNewInnovation()));


        for (int i = 0; i < Config.OUTPUTS; i++)
            network.addNode(new NodeGene(NodeGene.Type.OUTPUT, InovationGenerator.getNodeNewInnovation()));

        //adds connection genes
        if(Config.INPUTS >= Config.OUTPUTS)
        {
            for (int i = 0; i < Config.INPUTS; i++)
            {
                network.addConnection(new ConnectionGene(i, (i % Config.OUTPUTS) + Config.INPUTS, r.nextFloat(), InovationGenerator.getConnectionNewInnovation(), true));
            }

        }else{

            for (int i = 0; i < Config.OUTPUTS; i++)
            {
                network.addConnection(new ConnectionGene(i % Config.INPUTS, i + Config.INPUTS, r.nextFloat(), InovationGenerator.getConnectionNewInnovation(), true));
            }
        }

        return network;
    }
}
