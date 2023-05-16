#Jajodia-Mutchler-voting-algorithm
This project involves implementing the Jajodia-Mutchler voting algorithm on 
eight servers named A, B, C, D, E, F, G, and H. The algorithm replicates a 
single data object X subject to writes, and the network goes through a sequence 
of partitioning and merges. Two writes in each network component are required, 
with VN, RU, and DS values outputted for each server after each write attempt.


The code must be built first. To do this, use the Makefile by calling the "make" command in the 
working directory (inside the folder containing the code and make file). After that run the code 
from ServerA to ServerH in alphabetical order. Each server A-H will be ran from dc21.utdallas.edu to dc28.utdallas.edu respectively.
In each machine run java Server?/Server where "?" is replaced by the respective Server letter.
