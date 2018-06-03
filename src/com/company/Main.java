package com.company;

import com.company.GeneticAlgorithm.Arena;
import com.company.GeneticAlgorithm.Mating;
import com.company.GeneticAlgorithm.Simulations.MultiplicationSim;
import com.company.NuralNetwork.NeuralNetwork;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main
{

    public static void main(String[] args)
    {
        //double[] DNA = {0.46804607, 0.50677437, 0.1966173, 0.500886, 0.07874715, 0.5506147, 0.21678516, 0.117215216, 0.8378199, 0.6165313, 0.74250615, 0.6325553, 0.6496482, 0.32115138, 0.25492692, 0.13550752, 0.40903765, 0.5515076, 0.42293164, 0.08989966, 0.30034047, 0.6300599, 0.08989966, 0.22719389, 0.67701954, 0.62771696, 0.40059614, 0.49608684, 0.5532269, 0.6031528, 0.39853466, 0.0668585, 0.022655785, 0.089894235, 0.03726536, 0.030961432, 0.70517147, 0.3197276, 0.24503607, 0.08517504, 0.052985787, 0.08517504, 0.008749127, 0.002706468, 0.034476936, 0.79850006, 0.989767, 0.44782174, 0.87740886, 0.91292834, 0.027550757, 0.09286553, 0.14338845, 0.2773177, 0.2122495, 0.06696278, 0.033994436, 0.22563125, 0.06696278, 0.45369};

       /* ArrayList<Float> nDNA = new ArrayList<Float>();
        float[] in = {1, 0, 1, 0};
        float[] out;

        for (int i  = 0; i < DNA.length; i++)
        {
            nDNA.add((float)DNA[i]);
        }

        NeuralNetwork NN = new NeuralNetwork(4, 4, 4, 4);
        NN.setDNA(nDNA);

        out = NN.fire(in);

        for (float anOut : out)
        {
            System.out.println(anOut);
        }*/



        MultiplicationSim sim = new MultiplicationSim(50000, 4, 4);
        sim.runUntil(95000);




    }

}
