package com.company;

import com.company.GANeuralNetwork.GANeuralNetwork;
import com.company.GeneticAlgorithm.Mating;
import com.company.GeneticAlgorithm.Simulations.Standard.MostInputted;
import com.company.GeneticAlgorithm.Simulations.Standard.MultiplicationSim;
import com.company.GeneticAlgorithm.Simulations.GANN.XORGANN;
import com.company.GeneticAlgorithm.Simulations.Standard.XORStandard;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.XMLLogger;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Main
{

    public static void main(String[] args) throws ParserConfigurationException, InterruptedException
    {
        //MatingTest();
        //XORSim();
        //test();
        //testGANN();
        //sim();
        //GANNSim();
        MostInputted();
    }

    private static void GANNSim()
    {
        XORGANN sime = new XORGANN(5000, 4, 4);
        sime.run();
    }

    private static  void MostInputted()
    {
        MostInputted MI = new MostInputted(1000, 11, 11, 1);

        MI.runUntil(90);
    }

    private static void XORSim()
    {
        /*
        XORGANN sim = new XORGANN(5000, 2, 1);
        sim.run();*/


        XORStandard sim = new XORStandard(5000, 2,4,1);
        sim.runUntil(60);

/*
        byte[] DNA = {-119,-64,27,54,-11,50,95,-54,115,116,-93,-76,-34,-101,53,16,-51,9,-54,-11,34,22,-117,115,-26,18,27,110,-28,-106,90,-6,-65,-37,-7,-23,-84,65,-118,-90,-47,-51,-25,4,87,-78,-109,-123};
        float[][] dataIn = {{0,0},{0,1},{1,0},{1,1}};
        float[] dataOut;
        NeuralNetwork test = new NeuralNetwork(2,4,1);
        test.setDNA(DNA);

        for(int i = 0; i < dataIn.length; i++)
        {
            dataOut = test.fire(dataIn[i]);

            System.out.println(dataOut[0]);
        }*/

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
        sim.run(100);
    }

    private static void test()
    {
        //Perfect XOR//byte[] DNA = {-126,-25,-45,65,-55,-93,62,-80,97,122,-77,-94,-61,-70,44,-106,-24,-95,-7,-117,69,108,91,-47,-113,-42,-48,-11,-45,-8,43,-10,-81,118,93,-59,62,70,-7,69,-52,44,-103,123,98,109,-122,-100,-107,-93,-120,7,-124,126,7,-121,-36,65,89,115};

        byte[] DNA = {-116,-36,24,111,-54,-55,-102,56,122,74,60,-120,-103,108,-34,-6,103,-12,13,118,-55,63,100,-66,-101,3,72,45,-78,-76,22,33,-47,7,34,102,62,-35,122,-39,-14,-41,-38,-51,63,122,101,91,-84,-104,-28,68,-50,41,90,-49,-65,-105,-19,24};
//{63,-128,0,0,79,-19,-126,-55,63,112,24,-28,62,-31,-7,47,62,-73,-22,56,63,-128,0,0,61,119,99,121,-35,111,4,-52,63,2,52,-34,-15,83,38,-94,63,-128,0,0,-50,-59,-56,75,-128,-55,-116,-125,-85,-4,-46,-22,-75,47,48,-84,63,-128,0,0,-89,-88,91,47,-15,-32,-7,-97,62,-74,58,77,-87,126,-40,-53,63,-128,0,0,-7,-56,42,3,-90,66,-32,16,61,46,-109,61,-62,-68,-110,-117,63,-128,0,0,54,-38,37,83,62,-69,37,-27,-38,3,84,-47,63,-127,116,-35,63,-128,0,0,63,64,91,-28,34,96,-27,112,-62,-43,7,50,63,50,-32,122,63,-128,0,0,-91,-75,-109,73,63,94,99,-110,62,-16,-126,106,-13,118,-65,48,}
        float[][] input = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
        float[] expectedOutput = {1, 0, 0, 1};
        float[] out;
        /*
        float[][] input = {{0, 0, 1, 1}, {0, 1, 0, 1}, {1, 0, 0, 1}, {1, 0, 1, 1}, {1, 0, 1, 0}, {1, 1, 1, 0}, {1, 1, 0, 0}};
        float[][] expectedOutput = {{0, 0, 0, 0}, {0, 0, 0, 1}, {0, 0, 1, 0}, {0, 1, 1, 0}, {0, 1, 0, 0}, {0, 1, 1, 0}, {0, 0, 0, 0}};
        float[] out;*/


        NeuralNetwork NN = new NeuralNetwork(2, 2, 2, 1);
        NN.setDNA(DNA);


        for (int i = 0; i < input.length; i++)
        {
            out = NN.fire(input[i]);
            System.out.println("out: " + out[0]);
            System.out.println("Expected out: " + expectedOutput[i] + "\n_____________________________");
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
