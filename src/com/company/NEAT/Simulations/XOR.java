package com.company.NEAT.Simulations;

import com.company.NEAT.Network;
import com.company.NEAT.Simulation;

import java.util.Random;


public class XOR extends Simulation
{
    Random r = new Random();
    public XOR(int popSize)
    {
        super(popSize);
    }

    @Override
    protected void assignFitness()
    {
        for(Network net : population)
        {
            float fitness = 0;
            net.fitness = 0;

            for (int i = 0; i < 2; i++)
            {
                for (int j = 0; j < 2; j++)
                {
                    float inputs[] = {i, j};
                    float output[] = net.fire(inputs);
                    int expected = i ^ j;
                    fitness += (Math.abs(expected - output[0]));
                }
            }

            net.fitness = (int)(fitness * (1000));

        }
    }
}
