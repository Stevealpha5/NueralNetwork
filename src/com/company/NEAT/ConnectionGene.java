package com.company.NEAT;

class ConnectionGene
{
    private int innovation;
    private int inNode;
    private int outNode;
    private float weight;
    private boolean expressed;

    ConnectionGene(int inNode, int outNode, float weight, int innovation, boolean expressed)
    {
        this.inNode = inNode;
        this.outNode = outNode;
        this.weight = weight;
        this.innovation = innovation;
        this.expressed = expressed;
    }

    int getOutNode()
    {
        return outNode;
    }

    int getInNode()
    {
        return inNode;
    }

    float getWeight()
    {
        return weight;
    }

    boolean getExpressed()
    {
        return expressed;
    }

    int getInnovation()
    {
        return innovation;
    }

    void dissable()
    {
        expressed = false;
    }

    void enable()
    {
        expressed = true;
    }

    public void setWeight(float weight)
    {
        this.weight = weight;
    }

    ConnectionGene copy()
    {
        return new ConnectionGene(inNode, outNode, weight, innovation, expressed);
    }
}
