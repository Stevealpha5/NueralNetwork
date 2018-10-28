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
    intercept 


	


animate(1)
#ani = animation.FuncAnimation(fig, animate, interval = 1000)
plt.show()






