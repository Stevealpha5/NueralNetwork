package com.company;

import com.company.GeneticAlgorithm.Simulations.MultiplicationSim;
import com.company.NuralNetwork.NeuralNetwork;


public class Main
{

    public static void main(String[] args)
    {
        test();

        //sim();

    }

    private static void sim()
    {
        MultiplicationSim sim = new MultiplicationSim(10000, 4, 4, 4);//50000
        sim.runUntil(250000);
    }

    private static void test()
    {
        byte[] DNA = {14,56,27,-65,62,-28,57,-127,-102,-38,109,-1,64,-19,19,-6,-49,-34,21,26,-107,-126,22,16,-65,-32,-51,-80,-29,-42,-95,39,-10,-115,37,97,109,103,-76,-43,112,53,127,28,17,87,64,14,69,4,-41,8,-105,-111,103,45,23,-18,29,-81,15,-121,24,54,50,-59,-64,-106,12,-23,84,-24,-11,65,-19,-72,125,-93,-1,119,56,-107,-24,-122,-32,14,7,119,51,-38,18,-65,71,54,100,38,-38,-46,12,72,-124,68,-121,-41,119,36,-11,-111,14,-10,64,51,-29,-79,-12,58,-7,64,26,-5,50,38,9,48,-113,-31,-31,-6,-49,67,-103,17,-38,90,105,54,-26,-109,69,63,-37,124,-70,110,-6,-77,-121,-114,119,61,-128,43,78,-70,47,36,-78,-104,-124,124};
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

}
