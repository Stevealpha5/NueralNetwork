package com.company.Utils;

public class ArrayPrinter
{

    public static void printArray(float[] in)
    {
        for (float x : in)
        {
            System.out.print(x + " ");
        }

        System.out.print("\n");
    }

    public static void printArray(byte[] in)
    {
        for (float x : in)
        {
            System.out.print(x + " ");
        }

        System.out.print("\n");
    }

    public static void printArray(int[] in)
    {
        for (float x : in)
        {
            System.out.print(x + " ");
        }

        System.out.print("\n");
    }

    public static void print3dArray(float[][][] weights)
    {
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

    }

    public static void print3dArray(byte[][][] weights)
    {
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

    }

    public static void print2dArray(float[][] baises)
    {
        System.out.println("------------------------------------------------");
        for(int i = 0; i < baises.length; i++)
        {

            for(int j = 0; j < baises[i].length; j++)
            {
                System.out.print("Bais of neuron " + j + " in layer " + i + ": " + baises[i][j] + '\n');
            }

        }
    }

    public static void print2dArray(byte[][] baises)
    {
        System.out.println("------------------------------------------------");
        for(int i = 0; i < baises.length; i++)
        {

            for(int j = 0; j / 4 < baises[i].length; j++)
            {
                System.out.print("Bais of neuron " + j + " in layer " + i + ": " + baises[i][j] + '\n');
            }

        }
    }
}
