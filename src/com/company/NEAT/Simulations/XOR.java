package com.company.NEAT.Simulations;

import com.company.NEAT.Network;
import com.company.NEAT.Simulation;
import com.company.NuralNetwork.NeuralNetwork;

public class XOR extends Simulation
{
    public XOR(int popSize)
    {
        super(popSize);
    }

    @Override
    protected void assingeFittness()
    {
        for(Network net : population)
        {
            float fitness = 0;
            net.fitness = 0;
            for (int i = 0; i < 2; i++)
                for (int j = 0; j < 2; j++)
                {
                    float inputs[] = {i, j};
                    float output[] = net.fire(inputs);
                    int expected = i^j;
                    fitness +=  (1 - Math.abs(expected - output[0]));
                }
            fitness = fitness * fitness;

            net.fitness = (int)(fitness * (1000));

        }
    }
}
