package com.company.Utils;

import com.company.NuralNetwork.NeuralNetwork;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class XMLLogger
{
    private ArrayList<NeuralNetwork> population;

    private Document XMLDoc;
    private Element rootElement;
    private String docName;

    private OutputFormat outFormat;
    private File XMLFile;
    private FileOutputStream outStream;
    private XMLSerializer serializer;

    private Element generation;
    private Element individual;
    private Text fitness;

    public XMLLogger(String docName)
    {
        this.docName = docName;

        try
        {
            init();
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public XMLLogger(String docName, ArrayList<NeuralNetwork> population)
    {
        this.docName = docName;
        this.population = population;

        try
        {
            init();
        } catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    private void init() throws ParserConfigurationException
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        XMLDoc = docBuilder.newDocument();

        rootElement = XMLDoc.createElement("Root");
        XMLDoc.appendChild(rootElement);

        outFormat = new OutputFormat(XMLDoc);
        outFormat.setIndenting(true);

        XMLFile = new File(docName);
        outStream = null;

        try
        {
            outStream = new FileOutputStream(XMLFile);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        serializer = new XMLSerializer(outStream, outFormat);
    }

    public void logToXML(int gen, ArrayList<NeuralNetwork> population)
    {
        generation = XMLDoc.createElement("Generation");
        generation.setAttribute("generationNum", Integer.toString(gen));

        for(NeuralNetwork network: population)
        {
            individual = XMLDoc.createElement("Individual");
            fitness = XMLDoc.createTextNode(Integer.toString(network.fitness));
            individual.appendChild(fitness);

            generation.appendChild(individual);
        }

        rootElement.appendChild(generation);
    }

    public void logToXML(int gen)
    {
        generation = XMLDoc.createElement("Generation");
        generation.setAttribute("generationNum", Integer.toString(gen));

        for (NeuralNetwork network : population)
        {
            individual = XMLDoc.createElement("Individual");
            fitness = XMLDoc.createTextNode(Integer.toString(network.fitness));
            individual.appendChild(fitness);

            generation.appendChild(individual);
        }

        rootElement.appendChild(generation);
    }

    public void saveToXML()
    {
        try
        {
            serializer.serialize(XMLDoc);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
