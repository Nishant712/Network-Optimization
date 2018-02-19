# Network Optimization

### Objective
The objective of this application (written in Java) is to compare the performance of various algorithms which try to find the maximum bandwidth path in a network.

- - - -
### Summary
To mimic a network with numerous connections, two graphs each containing 5000 vertices and positive weights are created. One of them is a dense graph and the other is a sparse graph.

Three different algorithms are used to find the maximum bandwidth between two randomly chosen vertices. Ther performance of the algorithm is measured by the time taken by the algorithms to find the maximum bandwidth path.

The algorithms used:
* Finding the Maximum Spanning Tree from th graph using Kruskal's algorithm
* Finding the maximum weighted path between two points by using a modified version of Dijkstra's algorithm which uses an array.
* Finding the maximum weighted path between two points by using a modified version of Dijkstra's algorithm which uses a max heap.

- - - -
### To run
Download this repository to any IDE capable of running Java applications and run the Network_Optimization.java file to obtain the results.
