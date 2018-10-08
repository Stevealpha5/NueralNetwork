import matplotlib.pyplot as plt 
import matplotlib.animation as animation
import numpy as np
from matplotlib import style


style.use('fivethirtyeight')
fig = plt.figure()

#data to be plotted  


def best_fit(X, Y, printFormaula=False):

    xbar = sum(X)/len(X)
    ybar = sum(Y)/len(Y)
    n = len(X) # or len(Y)

    numer = sum([xi*yi for xi,yi in zip(X, Y)]) - n * xbar * ybar
    denum = sum([xi**2 for xi in X]) - n * xbar**2

    slope = numer / denum
    intercept = ybar - slope * xbar

    if printFormaula:
    	print('best fit line:\ny =  {:.2f}x + {:.2f}'.format(slope, intercept))

    return slope, intercept


def animate(i):
		raw_data_Fitness = open('C:\\Users\\Christopher\\JavaProjects\\MachineLearning\\P1Fitness', 'r').read()
		generations = raw_data_Fitness.split('\n')

		gen = []
		meanFittness = []
		medianFittness = []
		q1 = []
		q3 = []
		topFittness = []
		lowestFittness = []
	counter = 0

	for genration in generations:
		if len(genration) > 1:
			individuals = genration.split(',')
			gen.append(counter)
			counter += 1

			
			temp = []
			for individual in individuals:
				if individual != "":
					temp.append(float(individual))

			meanFittness.append(np.mean(temp))
			medianFittness.append(np.median(temp))
			q1.append(np.percentile(temp, 25))
			q3.append(np.percentile(temp, 75))
			topFittness.append(np.max(temp))
			lowestFittness.append(np.min(temp))

	fittnessPlt = plt.subplot2grid((2,2), (0,0), rowspan=1, colspan=2)

	fittnessPlt.clear()

	fittnessPlt.plot(gen, meanFittness, label='Mean Fittness')
	fittnessPlt.plot(gen, medianFittness,  label='Median Fittness')
	fittnessPlt.plot(gen, topFittness,  label='Top Fittness')
	# fittnessPlt.plot(gen, q1,  label='Q1')
	# fittnessPlt.plot(gen, medianFittness,  label='Q3')
	fittnessPlt.plot(gen, lowestFittness, label='Lowest Fittness')

	best_fit(gen, medianFittness, True)

	fittnessPlt.set_title('Fittness')
	fittnessPlt.set_xlabel('Generation Number')
	fittnessPlt.set_ylabel('Fittness')
	fittnessPlt.legend()

	raw_data_Age = open('C:\\Users\\Christopher\\JavaProjects\\MachineLearning\\P1Age', 'r').read()
	generations = raw_data_Age.split('\n')

	gen2 = []
	topAge = []
	counter2 = 0

	for genration in generations:
		if len(genration) > 1:
			individuals = genration.split(',')
			gen2.append(counter2)
			counter2 += 1

			
			temp = []
			for individual in individuals:
				if individual != "":
					temp.append(float(individual))

			topAge.append(np.max(temp))
			
	age = plt.subplot2grid((2,2), (1,0), rowspan=1, colspan=2)
	age.plot(gen2, topAge)
	age.set_title('Age')
	age.set_xlabel('Generation Number')
	age.set_ylabel('Age')


	


animate(1)
#ani = animation.FuncAnimation(fig, animate, interval = 1000)
plt.show()






