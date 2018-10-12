package com.company.NEAT;

import java.util.ArrayList;

public class NodeGene
{
    private float value = 0;
    private int id;
    private Type type;

    public ArrayList<ConnectionGene> incomingConnections = new ArrayList<>();

    enum Type
    {
        INPUT, OUTPUT, HIDDEN
    }

    public NodeGene(Type type, int id)
    {
        this.type = type;
        this.id = id;
    }

    public void setValue(float value)
    {
        this.value = value;
    }

    public float getValue()
    {
        return value;
    }

    public NodeGene copy()
    {
        return new NodeGene(type, id);
    }

    public int getId()
    {
        return id;
    }

    public Type getType()
    {
        return type;
    }
}
