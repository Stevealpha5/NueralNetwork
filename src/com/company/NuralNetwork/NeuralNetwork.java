package com.company.NuralNetwork;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork
{
    /**
     * NN (Neural Network) = the array of layers that make up the neural network
     *
     * neuronCfg = the configuration of the neurons in the network where index 0 is the input layer and the last index is the output layer, the value at each index is the number of
     *                neurons in that layer
     *
     * fitness = the fitness score
     */
    Random r = new Random();//temp, I'm using it to randomize
    // the fitness for arena testing
    private Layer[] NN;
    private int[] neuronCfg;
    public int fitness;// = r.nextInt(13);
    public float percentRight = 0;

    /**
     * @param neurons saves the input into the array 'neurons' where index 0 is the input layer and the last index is the output layer, the value at each index is the number of
     *                neurons in that layer
     */
    public NeuralNetwork(int ... neurons)//5.3.3
    {
        neuronCfg = neurons;

        //the - 1 is because the output layer is not a layer it will be returned as a float[]
        NN = new Layer[neurons.length -1];

        for(int i = 0; i < NN.length; i++)
        {
            NN[i] = new Layer(neurons[i + 1], neurons[i]);
        }

    }

    /**
     * @param dataIn the data that is fed to the input layer of the network
     * @return the values of the output layer
     */
    public float[] fire(float[] dataIn)
    {
        float[] data = dataIn;

        for (int i = 0; i < NN.length; i++)
        {
            data = NN[i].fire(data);
        }

        return data;
    }

    /**
     * Oh boy this is a method... It cycles through all of the neurons layer by layer to save the bais value and the weights to 'DNA'
     * that will be used in the genetic algorithm
     * @return A float array list that will be used by the genetic algorithm
     */
    public byte[] getDNA()
    {
        ArrayList<Float> DNAFloat = new ArrayList<Float>();
        byte[] DNAByte;

        for(int i = 0; i < NN.length; i++)//layers in NN
        {
            for(int j = 0; j < NN[i].getNeurons().length; j++)//Neurons in layer
            {
                DNAFloat.add(NN[i].getNeuron(j).bais); //saves the bais value
                for (int k = 0; k < NN[i].getNeuronWeights(j).length; k++)//weights in neurons
                {
                    DNAFloat.add(NN[i].getNeuronWeights(j)[k]);//saves the weights to the DNA

                }
            }
        }

        DNAByte = floatArrayToByteArray(DNAFloat);

        return DNAByte;
    }

    /**
     * This sets the DNA of this neural net equal to that of the DNA being passed in
     * @param inputDNA The new DNA values for this neural net NOTE: the new DNA <b>MUST</>
     *            have come from a neural net with identical layer configuration
     */
    public void setDNA(byte[] inputDNA)
    {
        float[] DNA = floatArrayFromByteArray(inputDNA);

        int DNACounter = 0;

        for(int i = 0; i < NN.length; i++)//layers in NN
        {
            for(int j = 0; j < NN[i].getNeurons().length; j++)//Neurons in layer
            {
                NN[i].getNeuron(j).bais = DNA[DNACounter];
                DNACounter++;

                for (int k = 0; k < NN[i].getNeuronWeights(j).length; k++)//weights in neurons
                {
                    NN[i].getNeuronWeights(j)[k] = DNA[DNACounter];
                    DNACounter++;
                }
            }
        }
    }

    /**
     * @return The configuration of the neurons in the network where index 0 is the input layer and the last index is the output layer, the value at each index is the number of
     *                neurons in that layer
     */
    public int[] getNeuronCfg()
    {
        return neuronCfg;
    }

    private byte[] floatArrayToByteArray(ArrayList<Float> values)
    {
        ByteBuffer buffer = ByteBuffer.allocate(4 * values.size());

        for (float value : values){
            buffer.putFloat(value);
        }

        return buffer.array();
    }

    private static float[] floatArrayFromByteArray(byte[] buffer)
    {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        float[] floatArray = new float[buffer.length / 4];  // 4 bytes per float
        for (int i = 0; i < floatArray.length; i++)
        {
            try
            {
                floatArray[i] = dataInputStream.readFloat();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return  floatArray;
    }

}
