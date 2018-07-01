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

    public static void main(String[] args)
    {
        //MatingTest();
        XORSim();
        //test();
        //testGANN();
        //sim();
        //GANNSim();
    }

    private static void GANNSim()
    {
        MultiplicationSimGANN sime = new MultiplicationSimGANN(5000, 4, 4);
        sime.runUntil(360);
    }

    private static void XORSim()
    {
        XORGANN sime = new XORGANN(5000, 2, 1);
        sime.run();


        /*XORStandard sim = new XORStandard(5000, 2,1);
        sim.runUntil(45);*/
    }

    private static void MatingTest()
    {
        int[] neuronCfg1 = {4, 4};
        float[][][] weights1 = {{{0.6956465f, -2.912303E24f, 1.2674293E-9f, 1.6371532E21f}, {0.08716219f, -3.6352992E37f, -9.56901E-20f, -6.1716853E-25f}, {-2.195911E37f, 7.1071417E12f, 2.9737603E-13f, 3.0650045E-35f}, {1.6748218E-21f, -1.5044068E-11f, 1.2473832f, -0.028573634f}},{{1.2767371E-36f, 1.0664789E34f, -1.5167836E36f, -8.4283581E18f}, {7.229037E-7f, 9.783057E-8f, -8.057186E23f, -1.40653E-20f}, {-2.7384458E-25f, 2.2904607E36f, 3.35441241E16f, -2.1689193E-25f}, {5.424783E-26f, -1.2652859E29f, 4.367356E12f, 9.010474E-27f}}};
        float[][] baises1 = {{-6.2973613E31f, -1.24514976E11f, -5.3778083E28f, -9.664866E34f},{7.379684E-12f, 2.3698105E-10f, 1.3262255E-6f, -1.4510446E32f}};

        int[] neuronCfg2 = {4, 4};
        float[][][] weights2 = {{{-2.780727f, -5.1964896E-18f, -3.8808733E-20f, 0.909701f}, {-3.0513002E35f, 4.172833E-23f, -3.9480214E30f, 2.0449509E-36f}, {-2.600797E-17f, 0.4398456f, 0.075604044f, -1.3140608E-8f}, {1.5772796f, 5.0718327E10f, 1.931826E34f, 5.44601E-34f}},{{-1.977319E27f, -5.6154186E-35f, -1.3110422E29f, -1.5377294E-9f}, {-6.666E-18f, 2.3912082E-33f, 1.296837E-18f, -1.9518598E-13f}, {2.6041536E-13f, 1.0397539E-34f, 6.461431E-35f, 1.04548125E24f}, {-4.0681758E-29f, -1246.9501f, -6.3108723E-28f, -9.4901725E-12f}}};
        float[][] baises2 = {{-2.8722148E37f, -5.0253196E26f, -1.6130993E27f, -1.5226209E25f},{1.7759812f, -7.392014E-29f, 2.447585E-26f, 2.249998E-38f}};

        GANeuralNetwork N1 = new GANeuralNetwork();
        N1.setDNA(neuronCfg1, weights1, baises1);

        GANeuralNetwork N2 = new GANeuralNetwork();
        N2.setDNA(neuronCfg2, weights2, baises2);

        GANeuralNetwork child = Mating.simpleGANNMate(N1, N2, 4, 4);

        child.printFormattedDNA();


    }

    private static void testGANN()
    {
        int[] neuronCfg = {2, 1};
        float[][][] weights =  {{{0.65626377f, 0.45503402f, 0.34994417f, 0.8283774f}, {0.9847615f, 0.6095839f, 0.9914519f, 0.1634422f}},{{0.93527085f, 0.9583307f}}};
        float[][] baises = {{0.5618551f, 0.41083246f},{0.30463398f}};

        float[][] input = {{0, 0}, {0, 1}, {1, 0}};
        float[] expectedOutput = {0, 1, 1};
        float[] out;
        /*
        float[][] input = {{0, 0, 1, 1}, {0, 1, 0, 1}, {1, 0, 0, 1}, {1, 0, 1, 1}, {1, 0, 1, 0}, {1, 1, 1, 0}, {1, 1, 0, 0}};
        loat[][] expectedOutput = {{0, 0, 0, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        float[] out;*/


        GANeuralNetwork NN = new GANeuralNetwork();
        NN.setDNA(neuronCfg, weights, baises);

        for (int i = 0; i < input.length; i++)
        {
            out = NN.fire(input[i]);

            System.out.println("out: " + out[0]);
            System.out.println("expected: " + expectedOutput[i]);

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
        byte[] DNA = {5, -52, 54, 8, -15, 42, -54, -38, -28, -102, -60, -5};

        float[][] input = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        float[] expectedOutput = {1, 0, 0, 1};
        float[] out;
        /*
        float[][] input = {{0, 0, 1, 1}, {0, 1, 0, 1}, {1, 0, 0, 1}, {1, 0, 1, 1}, {1, 0, 1, 0}, {1, 1, 1, 0}, {1, 1, 0, 0}};
        float[][] expectedOutput = {{0, 0, 0, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        float[] out;*/


        NeuralNetwork NN = new NeuralNetwork(2, 1);
        NN.setDNA(DNA);

        for (int i = 0; i < input.length; i++)
        {


            System.out.println("out: " + NN.fire(input[i])[0]);
            System.out.println("Expected out: " + expectedOutput[i]);
        }
/*
        for (int i = 0; i < input.length; i++)
        {
            out = NN.fire(input[i]);

            for (int j = 0; j < input[i].length; j++)
            {
                System.out.print(out[j]);
                System.out.print("   ");
                System.out.print(expectedOutput[i]);
                System.out.print("\t\t");
                System.out.println(input[i][j]);
            }

            System.out.println("\n________________________________________________________");
        }*/
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
