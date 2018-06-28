package com.company.Utils;

import java.util.Random;

public class DNAManager
{
    public static byte[] getNeuronCfgByte(int[] neuronCfg)
    {
        float[] neuronCfgFloat = new float[neuronCfg.length];

        for (int i = 0; i < neuronCfg.length; i++)
            neuronCfgFloat[i] = neuronCfg[i];

        return Utils.floatArrayToByteArray(neuronCfgFloat);
    }

    public static byte[][][] getWeightsByte(float[][][] weights)
    {
        byte[][][] weightsBytes = new byte[weights.length][][];

        for (int i = 0; i < weights.length; i++)
        {
            weightsBytes[i] = new byte[weights[i].length][];

            for (int j = 0; j < weights[i].length; j++)
            {
                weightsBytes[i][j] = Utils.floatArrayToByteArray(weights[i][j]);
            }
        }

        return weightsBytes;
    }

    public static byte[][] getBaisesBytes(float[][] baises)
    {
        byte[][] baisesBytes = new byte[baises.length][];

        for (int i = 0; i < baises.length; i++)
        {
            baisesBytes[i] = Utils.floatArrayToByteArray(baises[i]);
        }

        return baisesBytes;
    }

    public static int[] getNeuronCfg(byte[] neuronCfgByte)
    {
        float[] neuronCfgFloat = Utils.floatArrayFromByteArray(neuronCfgByte);
        int[] neuronCfgInt = new int[neuronCfgByte.length];

        for(int i = 0; i < neuronCfgFloat.length; i++)
            neuronCfgInt[i] = (int)neuronCfgFloat[i];


        return Utils.removeZeros(neuronCfgInt);
    }

    public static int[] getNeuronCfgClean(byte[] neuronCfgByte, int inputLayer, int outputLayer, int depthLimit)
    {
        Random r = new Random();

        if(inputLayer <= 0 )
        {
            try
            {
                throw new InvalidNeuronConfigurationExeption("Input layer must be greater than 0, the input layer is: " + inputLayer);
            } catch (InvalidNeuronConfigurationExeption invalidNeuronConfigurationExeption)
            {
                invalidNeuronConfigurationExeption.printStackTrace();
            }
        }

        if(outputLayer <= 0)
        {
            try
            {
                throw new InvalidNeuronConfigurationExeption("Output layer must be greater than 0, the out put layer is: " + outputLayer);
            } catch (InvalidNeuronConfigurationExeption invalidNeuronConfigurationExeption)
            {
                invalidNeuronConfigurationExeption.printStackTrace();
            }
        }

        int[] neuronCfg = getNeuronCfg(neuronCfgByte);

        neuronCfg[0] = inputLayer;
        neuronCfg[neuronCfg.length - 1] = outputLayer;

        for(int i = 0; i < neuronCfg.length; i++)
        {
            if(neuronCfg[i] <= 0 || neuronCfg[i] > depthLimit)
                neuronCfg[i] = r.nextInt(depthLimit - 1) + 1;
        }

        return neuronCfg;
    }

    public static float[][][] getWeights(byte[][][] weightsByte, int[] neuronCfg)
    {
        byte defaultWeight = 0;

        float[][][] weightsFloat = new float[neuronCfg.length][][];

        for(int i = 0; i < (neuronCfg.length); i++)
        {
            weightsFloat[i] = new float[neuronCfg[i]][];

            for(int j = 0; j < (neuronCfg[i]); j++)
            {

                try
                {
                    if(i == 0)
                    {
                        weightsFloat[i][j] = Utils.floatArrayFromByteArray(weightsByte[i][j], (neuronCfg[i]));
                    }else
                    {
                        weightsFloat[i][j] = Utils.floatArrayFromByteArray(weightsByte[i][j], (neuronCfg[i - 1]));
                    }
                }catch (Exception e)
                {
                    weightsByte[i][j] = new byte[neuronCfg [i] * 4];

                    for(int k = 0; k < weightsByte[i][j].length; k++)
                    {
                        weightsByte[i][j][k] = defaultWeight;
                    }
                }


            }
        }

        return weightsFloat;
    }
}
