package com.company.NEAT;

public class NodeGene
{
    private Type type;
    private int id;

    enum Type
    {
        INPUT, OUTPUT, HIDDEN
    }

    public NodeGene(int id, Type type)
    {
        this.type = type;
        this.id = id;
    }
}
