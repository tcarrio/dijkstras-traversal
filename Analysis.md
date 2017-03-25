Tom Carrio

CSE-361

03/24/2017

# Dijkstra's Algorithm

The code required to complete this algorithm was provided entirely within the Paths class. This class uses an existing Graph class, and since it does not involve the traversal of the graph that is Dijkstra's algorithm, that Graph generation will not be included in the complexity of this algorithm. This Path uses a starting vertex ID to build the shortest path from for all other vertexes, which is done in the `applyRelaxation(int vertexId)` method. The `getNextVertex()` method returns the next shortest path vertex which has not yet been visited by the `applyRelaxation` method yet. This will use the `startVertex` given to the Path class, since this is the only vertex whose distance is not set to the maximum integer value. Each progression of `applyRelaxation()` on the next vertex will use the next shortest path vertex until all vertexes have been visited, where the entire graph would then have been traversed, since every node and all of its arcs have been visited with the shortest path available at each step. With this description of this Dijkstra's algorithm given, the time and space complexity can be more thoroughly explored.

### Time Complexity

The Paths class takes an existing graph and starting vertex in its constructor to generate the initial collection of path distances for every vertex ID. In the scope of the constructor, an array is generated containing `Path` objects which contain the current shortest distance and a `LinkedList` of vertexes that path is composed of, and a `PriorityQueue` is generated using the vertex ID. The priority queue uses a `VertexComp` object which implements `Comparator<Integer>` to allow the 