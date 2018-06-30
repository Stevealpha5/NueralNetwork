package com.company;

import com.company.GANeuralNetwork.GANeuralNetwork;
import com.company.GeneticAlgorithm.Mating;
import com.company.GeneticAlgorithm.Simulations.MultiplicationSim;
import com.company.GeneticAlgorithm.Simulations.MultiplicationSimGANN;
import com.company.GeneticAlgorithm.Simulations.XORGANN;
import com.company.GeneticAlgorithm.Simulations.XORStandard;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.Utils;

import java.util.Random;

import static com.company.Utils.ArrayPrinter.*;
import static com.company.Utils.DNAManager.*;


public class Main
{
    static Random r = new Random();

    public static void main(String[] args)
    {
        //XORSim();
        //test();
        //testGANN();
        //sim();
        GANNSim();
    }

    private static void GANNSim()
    {
        MultiplicationSimGANN sime = new MultiplicationSimGANN(5000, 4, 4);
        sime.runUntil(360);
    }

    private static void XORSim()
    {/*
        XORGANN sime = new XORGANN(5000, 4, 4);
        sime.runUntil(360);*/


        XORStandard sim = new XORStandard(5000, 4,4);
        sim.runUntil(360);
    }

    private static void DNAManagerTest()
    {
        int[] neuronCfg = {1, 1};
        float[][] baises = {{1.0f},{1.0f, 1.0f},{1.0f}};

        print2dArray(baises);

        byte[][] BB = getBaisesBytes(baises);

        // print2dArray(BB);

        baises = getBaises(BB, neuronCfg);

        print2dArray(baises);


/*
        float[][][] weights = {{{2.0f}}, {{1.0f}, {1.5f}}, {{3.0f, 3.0f}}};

        print3dArray(weights);

        byte[][][] WB = getWeightsByte(weights);

        print3dArray(WB);

        weights = getWeights(WB, neuronCfg);

        print3dArray(weights);*/


    }

    private static void testGANN()
    {
        int[] neuronCfg =  {4, 4};;
        float[][][] weights = {{{5.414587E-31f, 0.3290283f, 2.4517258E-6f, -2.6342478E-33f}, {-2.1422118E-37f, -1.48010455E10f, 0.09309518f, -1.2937613E-7f}, {0.04438377f, -1083816.4f, 1.27231f, -2.7064704E23f}, {-7.4866037E27f, -1.9414932E-25f, -2.5728565E20f, 6.196945E-37f}},{{-2.8557472E-14f, -2.721844E33f, -4.654696E12f, 5.986043E26f}, {-2.1966572E-13f, 5.6521448E7f, -1.7899597E-16f, -8.59419E24f}, {-8.2083003E37f, 8.8292844E-32f, -4.717661E-12f, -4.8116336E31f}, {-5.8098984E-25f, -255.95125f, -3.6117223E-10f, 9.557999E-21f}}};
        float[][] baises = {{-1.4391865E29f, -16.242563f, -3.60168307E15f, -5.6473276E21f},{-4.6297325E-26f, 1.363876E-19f, 1.2555053E-38f, 2.5397E-41f}};

        float[][] input = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        float[] expectedOutput = {1, 0, 0,1};
        float[] out;
        /*
        float[][] input = {{0, 0, 1, 1}, {0, 1, 0, 1}, {1, 0, 0, 1}, {1, 0, 1, 1}, {1, 0, 1, 0}, {1, 1, 1, 0}, {1, 1, 0, 0}};
        float[][] expectedOutput = {{0, 0, 0, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        float[] out;*/


        GANeuralNetwork NN = new GANeuralNetwork();
        NN.setDNA(neuronCfg, weights, baises);

        for (int i = 0; i < input.length; i++)
        {
            out = NN.fire(input[i]);

            System.out.print("out   expected   in \n");

            for (int j = 0; j < input[i].length; j++)
            {
                System.out.print(out[i]);
                System.out.print("   ");
                System.out.print(expectedOutput[i]);
                System.out.print("\t\t");
                System.out.println(input[i][j]);
            }

            System.out.println("\n________________________________________________________");
        }
    }

    private static void sim()
    {
        MultiplicationSim sim = new MultiplicationSim(10000, 4, 4, 4);//50000
        sim.runUntil(250000);
    }

    private static void test()
    {
        byte[] DNA = {46,53,-110,32,-86,44,-127,-71,-95,85,86,23,-23,123,46,92,-66,-17,-68,-49,-107,38,-13,71,45,22,79,-46,-33,42,37,-74,-4,-14,-48,-118,63,93,10,-43,-92,26,4,-117,-48,75,124,-110,43,109,64,115,124,63,-70,-113,62,98,33,41,-100,69,97,38,14,87,0,20,44,28,44,-94,62,-113,-56,110,-46,-68,-127,-60};


        float[][] input = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        float[] expectedOutput = {1, 0, 0,1};
        float[] out;
        /*
        float[][] input = {{0, 0, 1, 1}, {0, 1, 0, 1}, {1, 0, 0, 1}, {1, 0, 1, 1}, {1, 0, 1, 0}, {1, 1, 1, 0}, {1, 1, 0, 0}};
        float[][] expectedOutput = {{0, 0, 0, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        float[] out;*/


        NeuralNetwork NN = new NeuralNetwork(4,  4);
        NN.setDNA(DNA);

        for (int i = 0; i < input.length; i++)
        {
            out = NN.fire(input[i]);

            System.out.print("out   expected   in \n");

            for (int j = 0; j < input[i].length; j++)
            {
                System.out.print(out[i]);
                System.out.print("   ");
                System.out.print(expectedOutput[i]);
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

        for (int i = 0; i < baises.length; i++)
        {

            for (int j = 0; j < baises[i].length; j++)
            {
                System.out.print("Bais of neuron " + j + " in layer " + i + ": " + baises[i][j] + '\n');

            }

        }


    }



}
