import xml.etree.ElementTree as ET 
import matplotlib.pyplot as plt 
import matplotlib.animation as animation
import numpy as np
import time

from matplotlib import style

#data to be plotted
gen = []
meanFittness = []
medianFittness = []
q1 = []
q3 = []
topFittness = []
lowestFittness = []

#Extracting XML
tree = ET.parse('C:\\Users\\Christopher\\JavaProjects\\MachineLearning\\P2.xml')
root = tree.getroot()

population = []

def best_fit(X, Y, printFormaula=False):

    xbar = sum(X)/len(X)
    ybar = sum(Y)/len(Y)
    n = len(X) 

    numer = sum([xi*yi for xi,yi in zip(X, Y)]) - n * xbar * ybar
    denum = sum([xi**2 for xi in X]) - n * xbar**2

    slope = numer / denum
    intercept = ybar - slope * xbar

    if printFormaula:
    	print('best fit line:\ny =  {:.2f}x + {:.2f}'.format(slope, intercept))

    return slope, intercept

def populateData():
	
	for generation in root:
		individuals = []

		for individual in generation:		
			individuals.append(individual.text)
					
		population.append(individuals)

	counter = 0
	
	for individual in population:
		gen.append(counter)
		temp = np.array(individual).astype(np.float)
		meanFittness.append(np.mean(temp))
		medianFittness.append(np.median(temp))
		q1.append(np.percentile(temp, 25))
		q3.append(np.percentile(temp, 75))
		topFittness.append(np.max(temp))
		lowestFittness.append(np.min(temp))
		counter += 1


def drawGraph():
	style.use('fivethirtyeight')
	fig = plt.figure()

	# bins = [0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100]
	# bargraph = plt.subplot2grid((1,2), (0,0), rowspan=1, colspan=1)
	# bargraph.hist(population[33], bins, histtype='bar', rwidth=0.8)

	fittnessPlt = plt.subplot2grid((1,1), (0,0), rowspan=1, colspan=1)

	fittnessPlt.clear()

	#fittnessPlt.plot(gen, meanFittness, label='Mean Fittness')
	# fittnessPlt.plot(gen, medianFittness,  label='Median Fittness')
	fittnessPlt.plot(gen, topFittness,  label='Top Fittness')
	# fittnessPlt.plot(gen, q1,  label='Q1')
	fittnessPlt.scatter(gen, medianFittness,  label='Q3')
	fittnessPlt.plot(gen, lowestFittness, label='Lowest Fittness')

	best_fit(gen, medianFittness, True)

	fittnessPlt.set_title('Fittness')
	fittnessPlt.set_xlabel('Generation Number')
	fittnessPlt.set_ylabel('Fittness')
	fittnessPlt.legend()

	plt.show()

populateData()
drawGraph()


	
