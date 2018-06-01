package com.company;

import com.company.GeneticAlgorithm.Arena;
import com.company.GeneticAlgorithm.Mating;
import com.company.NuralNetwork.NeuralNetwork;

public class Main
{

    public static void main(String[] args)
    {

        float[] in = {1, 2};

        NeuralNetwork NN = new NeuralNetwork(2,2,2,1);
        NeuralNetwork NN2 = new NeuralNetwork(2,2,2,1);
        NeuralNetwork child = Mating.simpleMate(NN, NN2);

        NeuralNetwork[] NNA = {NN, NN2, child};
        Arena area = new Arena(NNA, 0.5f);

        System.out.println("DNA 1: " + NN.getDNA());
        System.out.println("DNA 2: " + NN2.getDNA());
        System.out.println("Child: " + child.getDNA());

        float[] out1 = NN.fire(in);
        float[] out2 = NN2.fire(in);
        float[] out3 = child.fire(in);

        area.evolve();
        System.out.println(NNA[0].getDNA());
        System.out.println(NNA[1].getDNA());
        System.out.println(NNA[2].getDNA());




       /* System.out.println("NN1: " + out1[0]);
        System.out.println("NN2: " + out2[0]);
        System.out.println("Child: " + out3[0]);

        System.out.println("DNA 1: " + NN.getDNA());
        System.out.println("DNA 2: " + NN2.getDNA());
        System.out.println("Child: " + child.getDNA());*/



    }

}
