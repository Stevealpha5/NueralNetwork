package com.company.GeneticAlgorithm.Simulations.Standard.TicTacToe;

import com.company.GeneticAlgorithm.Arena;
import com.company.NuralNetwork.NeuralNetwork;
import com.company.Utils.XMLLogger;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.Random;

public class TicTacToeMisere
{
    private ArrayList<NeuralNetwork> populationP1 = new ArrayList<>();
    private ArrayList<NeuralNetwork> populationP2 = new ArrayList<>();
    private Arena arenaP1;
    private Arena arenaP2;
    private XMLLogger logger1 = new XMLLogger("P1.xml");
    private XMLLogger logger2 = new XMLLogger("P2.xml");
    private TicTacToeMisereGame game = new TicTacToeMisereGame();
    private Random r = new Random();
    private float[] dataIn = new float[9];

    /**
     * @param popSize the size of the populationP1
     */
    public TicTacToeMisere(int popSize, int... neuronCFG)
    {
        for (int i = 0; i < popSize; i++)
        {
            populationP1.add(new NeuralNetwork(neuronCFG));
            populationP2.add(new NeuralNetwork(neuronCFG));
        }

        arenaP1 = new Arena(populationP1, 0.25f);
        arenaP2 = new Arena(populationP2, 0.25f);
    }

    /**
     * Runs the simulation for a set number of generations
     *
     * @param numberOfGenerations the number of generations the simulation will run for
     */
    public void run(int numberOfGenerations)
    {
        try
        {
            logger1.start();
            logger2.start();

        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < numberOfGenerations; i++)
        {
            assignFitness();

            logger1.logToXML(i, arenaP1.getPopulation());
            logger2.logToXML(i, arenaP2.getPopulation());

            arenaP1.evolve();
            arenaP2.evolve();


            System.out.println("P1________________________________________________________________________________");
            System.out.println("Generation: " + i);
            arenaP1.printBestStats();

            System.out.println("P2________________________________________________________________________________");
            System.out.println("Generation: " + i);
            arenaP2.printBestStats();

        }

        logger1.close();
        logger2.close();


    }

    /**
     * Runs the simulation until the most fit network is equal to or grater than the target fitness
     *
     * @param targetFitness ...it's the target fitness...
     */
    public void runUntil(int targetFitness)
    {
        int generation = 0;

        try
        {
            logger1.start();
            logger2.start();
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }

        while (arenaP1.getHighestFitness() < targetFitness)
        {
            assignFitness();

            logger1.logToXML(generation, arenaP1.getPopulation());
            logger2.logToXML(generation, arenaP2.getPopulation());

            arenaP1.evolve();
            arenaP2.evolve();


            System.out.println("P1________________________________________________________________________________");
            System.out.println("Generation: " + generation);
            arenaP1.printBestStats();

            System.out.println("P2________________________________________________________________________________");
            System.out.println("Generation: " + generation);
            arenaP2.printBestStats();

            generation++;


        }

        logger1.close();
        logger2.close();

        System.out.println("P1________________________________________________________________________________");
        System.out.println("Generation: " + generation);
        arenaP1.printBestStats();

        System.out.println("P2________________________________________________________________________________");
        System.out.println("Generation: " + generation);
       arenaP2.printBestStats();

    }

    /**
     * Runs the simulation and assigned a fitness based on the networks performance
     */
    private void assignFitness()
    {
        int numberOfOpponents = (int)(populationP2.size() * 50);
        int p2 = r.nextInt(populationP2.size());
        int p1;
        int p1Win = 0;
        int p2Win = 0;
        int counter;
        float multFactor = 1000;




        for (int i = 0; i < /*populationP1.size()*/ numberOfOpponents; i++)
        {
            p1 = r.nextInt(populationP1.size());
            /*for(int k = 0; k <  numberOfOpponents; k++)
            {*/
                p2 = r.nextInt(populationP2.size());
                /*counter = 0;

                while (populationP2.get(k).fitness > populationP1.get(i).fitness + 100 || populationP2.get(k).fitness <  populationP1.get(i).fitness - 100 )
                {
                    p2 = r.nextInt(populationP2.size());

                    if (counter > populationP2.size())
                    {
                        //System.out.println("No Match Found");
                        break;
                    }

                    counter++;
                }*/


                //System.out.println("P1: " + populationP1.get(i).fitness);
                //System.out.println("P2: " + populationP2.get(p2).fitness);

                while (game.getWinner() == TicTacToeMisereGame.Player.NONE)
                {
                    flatten(game.getBoard());

                    game.turnP1NN(populationP1.get(p1).fire(dataIn));

                    if (game.getWinner() != TicTacToeMisereGame.Player.NONE)
                        break;

                    flatten(game.getBoard());

                    game.turnP2NN(populationP2.get(p2).fire(dataIn));

                }

                //System.out.println("Winner: " + game.getWinner());

                if (game.getWinner() == TicTacToeMisereGame.Player.PLAYER1)
                    p1Win++;
                else
                    p2Win++;

                //System.out.println("P1: " + p1Win + " P2 " + p2Win);

                game.resetBoard();


                populationP2.get(p2).fitness = (int)((((((float)p2Win) * multFactor) + (float) populationP2.get(p2).fitness) / 2.0));
                p2Win = 0;

                populationP1.get(p1).fitness = (int)((((((float)p1Win) * multFactor) + (float)populationP1.get(p1).fitness) / 2.0));
                p1Win = 0;

               // System.out.println("P2: " + p2 + " Fitness: " + populationP2.get(p2).fitness + " Scalar: " + scaler);
                //System.out.println("P1: " + i + " Fitness: " + populationP1.get(i).fitness );
            //}



        }
    }

    private void flatten(int[][] in)
    {
        for (int i = 0; i < 9; i++)
        {
            dataIn[i] = in[i / 3][i % 3];
        }
    }
}
