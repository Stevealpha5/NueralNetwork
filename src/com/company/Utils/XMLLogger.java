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
    Document XMLDoc;
    Element rootElement;

    public XMLLogger()
    {

    }

    public void start() throws ParserConfigurationException
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        XMLDoc = docBuilder.newDocument();

        rootElement = XMLDoc.createElement("Root");
        XMLDoc.appendChild(rootElement);

    }

    public void logToXML(int gen, ArrayList<NeuralNetwork> population)
    {


        Element generation = XMLDoc.createElement("Generation");
        generation.setAttribute("generationNum", Integer.toString(gen));

        for(NeuralNetwork network: population)
        {
            Element individual = XMLDoc.createElement("Individual");
            Text fitness = XMLDoc.createTextNode(Integer.toString(network.fitness));
            individual.appendChild(fitness);

            generation.appendChild(individual);
        }

        rootElement.appendChild(generation);

    }

    public void close()
    {
        OutputFormat outFormat = new OutputFormat(XMLDoc);
        outFormat.setIndenting(true);

        File XMLFile = new File("Log.xml");
        FileOutputStream outStream = null;

        try
        {
            outStream = new FileOutputStream(XMLFile);
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        XMLSerializer serializer = new XMLSerializer(outStream, outFormat);

        try
        {
            serializer.serialize(XMLDoc);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
