package com.company.NEAT;

public class ConnectionGene
{
    private int inovation;
    private int inNode;
    private int outNode;
    private float weight;
    private boolean expressed;

    public ConnectionGene(int inNode, int outNode)
    {
        this.inNode = inNode;
        this.outNode = outNode;

        weight = 1;
    }
}
