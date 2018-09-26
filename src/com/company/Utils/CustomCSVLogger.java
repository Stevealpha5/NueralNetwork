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
    private PrintWriter writer;

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
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }

    public void log()
    {
        logFitness();
    }

    private void logFitness()
    {
        for (NeuralNetwork net: population)
        {
            writer.print(net.fitness + ",");
        }

        writer.print("\n");
    }

    public void close()
    {
        writer.close();
    }
}
