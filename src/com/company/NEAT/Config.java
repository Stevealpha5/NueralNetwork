package com.company.NEAT;

class Config
{
    static final int   INPUTS                        = 2;
    static final int   OUTPUTS                       = 1;
    static final int   HIDDEN_NODES                  = 1_000_000;

    static final float ADD_NODE_CHANCE               = 0.03f;
    static final float ADD_CONNECTION_CHANCE         = 0f;//1.05f;
    static final float WEIGH_MUTATION_CHANCE         = 0.8f;
    static final float PERTURB_CHANCE                = 0.9f;

    static final float PERTURB_PERCENT               = 0.05f;
    static final float RANDOM_WEIGHT_UPPER_BOUND     = 2.0f;
    static final float RANDOM_WEIGHT_LOWER_BOUND     = -2.0f;

    static final float C1                            = 0.0f;
    static final float C2                            = 0.0f;
    static final float C3                            = 0.0f;
    static final int SAFE_SPECIES_SIZE               = 15;
    static final float SPECIES_THREASHOLD            = 3.0f;
}
