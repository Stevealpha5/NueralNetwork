package com.company.Utils;

import com.company.NuralNetwork.NeuralNetwork;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CustomCSVLogger
{
    private String fileName;
    private ArrayList<NeuralNetwork> population;
    private PrintWriter writerFitness;
    private PrintWriter writerAge;
    private PrintWriter writerDNA;

    public CustomCSVLogger(ArrayList<NeuralNetwork> population, String fileName)
    {
        this.fileName = fileName;
        this.population = population;

        init();
    }

    private void init()
    {
        try
        {
            writerFitness = new PrintWriter(fileName + "Fitness", "UTF-8");
            writerAge = new PrintWriter(fileName + "Age", "UTF-8");
            writerDNA = new PrintWriter(fileName + "DNA", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    public void log()
    {
        logFitness();
        logAge();
        logFitness();
    }

    private void logFitness()
    {
        for (NeuralNetwork net: population)
        {
            writerFitness.print(net.fitness + ",");
        }

        writerFitness.print("\n");
    }

    private void logAge()
    {
        for (NeuralNetwork net: population)
        {
            writerAge.print(net.age + ",");
        }

        writerAge.print("\n");
    }

    private void logDNA()
    {
        for (NeuralNetwork net: population)
        {
            writerDNA.print(net.fitness + ",");
        }

        writerDNA.print("\n");
    }

    public void close()
    {
        writerFitness.close();
        writerAge.close();
        writerFitness.close();
    }
}
