package com.company;

import com.company.GeneticAlgorithm.Arena;
import com.company.GeneticAlgorithm.Mating;
import com.company.GeneticAlgorithm.Simulations.MultiplicationSim;
import com.company.NuralNetwork.NeuralNetwork;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Main
{

    public static void main(String[] args)
    {
        //test();

        sim();
    }

    private static void sim()
    {
        MultiplicationSim sim = new MultiplicationSim(50000, 1, 3);//50000
        sim.runUntil(99000);
    }

    private static void test()
    {
        byte[] DNA = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,-7,117,117,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-53,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-55,-27,-27,-27,-27,-27,-27};




        float[] in = {1};
        float[] out;


        NeuralNetwork NN = new NeuralNetwork(1, 3);
        NN.setDNA(DNA);

        out = NN.fire(in);

        for (float anOut : out)
        {
            System.out.println(anOut);
        }
    }

}
