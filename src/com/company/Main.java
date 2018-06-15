package com.company;

import com.company.GANeuralNetwork.GANeuralNetwork;
import com.company.GeneticAlgorithm.Simulations.MultiplicationSim;
import com.company.NuralNetwork.NeuralNetwork;


public class Main
{

    public static void main(String[] args)
    {
        testGANN();

        //test();

        //sim();

    }

    private static void testGANN()
    {
        System.out.println("\n\n\n\n" + "NN" + "\n");
        GANeuralNetwork nn = new GANeuralNetwork(2, 1);
        nn.printFormattedDNA();

        System.out.println("\n\n\n\n" + "NN2" + "\n");
        GANeuralNetwork nn2 = new GANeuralNetwork(5, 1);
        nn2.printFormattedDNA();

        System.out.println("\n\n\n\n" + "NN2a" + "\n");
        nn2.setDNAByte(nn.getNeuronCfgByte(), nn.getWeightsByte(), nn.getBaisesBytes());
        nn2.printFormattedDNA();
    }

    private static void sim()
    {
        MultiplicationSim sim = new MultiplicationSim(10000, 4, 4, 4);//50000
        sim.runUntil(250000);
    }

    private static void test()
    {
        byte[] DNA = {6,107,-49,50,110,-78,-60,-62,-71,-17,-121,-49,47,119,2,61,-62,-53,-17,-84,44,22,-108,-67,-61,66,-15,-85,10,0,-9,-65,45,-16,6,-108,18,-119,-30,96,-105,118,-112,16,-49,-120,-43,-64,-91,29,38,-57,1,-47,-55,-22,112,-38,4,127,-18,113,11,93,-53,-30,-5,79,100,-44,-45,119,-118,77,110,46,-13,-48,16,16,-11,-57,-100,-22,90,-67,-45,38,-83,25,37,-47,23,-105,-39,26,-122,-99,9,-25,89,29,32,29,1,-33,-96,-100,36,-83,64,22,-24,-89,46,41,41,-80,113,109,21,17,-109,-106,124,-106,12,-5,-51,-13,48,-123,62,100,104,33,-93,-55,-33,68,-5,1,-118,121,-101,-19,42,-41,-97,-37,5,48,83,75,59,77,-17,108,88,-120};


        float[][] input = {{0,0,1,1},{0,1,0,1},{1,0,0,1},{1,0,1,1},{1,0,1,0},{1,1,1,0}, {1,1,0,0}};
        float[][] expectedOutput = {{0,0,0,0},{0,0,0,1},{0,0,1,0},{0,1,1,0},{0,1,0,0},{0,1,1,0},{0,0,0,0}};
        float[] out;


        NeuralNetwork NN = new NeuralNetwork(4, 4, 4);
        NN.setDNA(DNA);

        for(int i = 0; i < input.length; i++)
        {
            out = NN.fire(input[i]);

            System.out.print("out   expected   in \n");

            for(int j = 0; j < input[i].length; j++)
            {
                System.out.print(out[j]);
                System.out.print("   ");
                System.out.print(expectedOutput[i][j]);
                System.out.print("\t\t");
                System.out.println(input[i][j]);
            }

            System.out.println("\n________________________________________________________");
        }
    }

    private static void printFormattedDNAByte(byte[] neuronCfg, byte[][][] weights, byte[][] baises)
    {
        System.out.print("Neuron Config: ");

        for (int x : neuronCfg)
            System.out.print(x + " ");

        System.out.println('\n' + "_____________________________________________________" + '\n');

        for (int i = 0; i < weights.length; i++)
        {
            for (int j = 0; j < weights[i].length; j++)
            {
                System.out.print("Weights of neuron " + j + " in layer " + i + ": ");

                for (int k = 0; k < weights[i][j].length; k++)
                {
                    System.out.print(weights[i][j][k] + ", ");
                }

                System.out.print('\n');
            }
        }

        System.out.println('\n' + "_____________________________________________________" + '\n');

        for(int i = 0; i < baises.length; i++)
        {

            for(int j = 0; j < baises[i].length; j++)
            {
                System.out.print("Bais of neuron " + j + " in layer " + i + ": " + baises[i][j] + '\n');

            }

        }


    }


}
