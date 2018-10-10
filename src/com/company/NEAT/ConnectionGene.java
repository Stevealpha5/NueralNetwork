package com.company.NEAT;

public class ConnectionGene
{
    private int innovation;
    private int inNode;
    private int outNode;
    private float weight;
    private boolean expressed;

    public ConnectionGene(int inNode, int outNode, float weight, int innovation, boolean expressed)
    {
        this.inNode = inNode;
        this.outNode = outNode;
        this.weight = weight;
        this.innovation = innovation;
        this.expressed = expressed;
    }

    public int getOutNode()
    {
        return outNode;
    }

    public int getInNode()
    {
        return inNode;
    }

    public float getWeight()
    {
        return weight;
    }

    public boolean getExpressed()
    {
        return expressed;
    }

    public int getInnovation()
    {
        return innovation;
    }

    public ConnectionGene copy()
    {
        return new ConnectionGene(inNode, outNode, weight, innovation, expressed);
    }
}
