import xml.etree.ElementTree as ET 
import matplotlib.pyplot as plt 
from matplotlib import style
import numpy as np

#data to be plotted
gen = []
meanFittness = []
topFittness = []
percentageOfNAN = []

#extracting XML
tree = ET.parse('Log.xml')
root = tree.getroot()

def populateData():
	population = []

	for generation in root:
		individuals = []
		NANCounter = 0
		counter = 0

		for individual in generation:
			if individual.text != '-1000':			
				individuals.append(individual.text)
			else:
				NANCounter += 1
			counter += 1
			
		percentageOfNAN.append(NANCounter / counter)			
		population.append(individuals)

	counter = 0

	for individual in population:
		gen.append(counter)
		temp = np.array(individual).astype(np.float)
		meanFittness.append(np.mean(temp))
		topFittness.append(np.max(temp))
		counter += 1



def drawGraph():
	style.use('fivethirtyeight')

	fittnessPlt = plt.subplot2grid((9,1), (0,0), rowspan=4, colspan=1)

	fittnessPlt.plot(gen, meanFittness, label='Mean Fittness')
	fittnessPlt.plot(gen, topFittness,  label='Top Fittness')

	fittnessPlt.set_title('Fittness')
	fittnessPlt.set_xlabel('Generation Number')
	fittnessPlt.set_ylabel('Fittness')
	fittnessPlt.legend()

	NANPlot = plt.subplot2grid((9,1), (5,0), rowspan=4, colspan=1)
	NANPlot.plot(gen, percentageOfNAN)

	NANPlot.set_title('NAN Values')
	NANPlot.set_xlabel('Generation Number')
	NANPlot.set_ylabel('Percantage of NAN Vals')

	plt.show()

populateData()
drawGraph()
