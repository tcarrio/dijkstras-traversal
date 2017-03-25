import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tom on 3/23/17.
 */
public class Paths{

    Path[] tentativePaths;
    Boolean[] vertexVisited;
    PriorityQueue<Integer> searchQueue;
    Graph g;
    Integer startVertex;

    public Paths(Graph g, Integer startVertex){

        //System.out.printf("The graph has %d vertices",g.getVertexCount());

        // create a list of tentative distances for each vertex
        tentativePaths = new Path[g.getVertexCount()+1];

        // create a set of visited nodes, which I will use an array of booleans for since O(1) access
        vertexVisited = new Boolean[g.getVertexCount()+1];

        // create a priority queue for sorted by lowest tentative distance
        searchQueue = new PriorityQueue<Integer>(g.getVertexCount()+1,new VertexComp());

        // fill em up
        for(int i = 0; i< tentativePaths.length; i++){
            tentativePaths[i]=new Path();
            vertexVisited[i]=false;
        }

        // set the default values on starting vertex
        tentativePaths[startVertex]=new Path(0);
        vertexVisited[startVertex]=true;

        for(int i=0; i<tentativePaths.length; i++){
            searchQueue.add(i);
        }

        this.g = g;
        this.startVertex = startVertex;
    }

    public void applyRelaxation(Integer thisVertexId){
        //System.out.printf("Checking for %d...\n",thisVertexId);
        for(Graph.Vertex targetVertex : g.getAdj(thisVertexId)){

            // put data in local variables
            Integer targetDistance = targetVertex.mDistance;
            Integer targetId = targetVertex.mVertId;
            Integer tempDistance;

            // avoid integer overflow by detecting an unvisited node
            if(tentativePaths[thisVertexId].getDistance()==Integer.MAX_VALUE)
                tempDistance = Integer.MAX_VALUE;
            else
                tempDistance = targetDistance + tentativePaths[thisVertexId].getDistance();

            if(tempDistance<tentativePaths[targetId].getDistance()){
                // make a new object from this to avoid modifying the referenced list
                LinkedList<Integer> newPath = (LinkedList<Integer>)tentativePaths[thisVertexId].getPath().clone();
                newPath.add(thisVertexId);
                tentativePaths[targetId]=new Path(tempDistance,newPath);

                // replace the old vertex location in the priority queue since it may have moved up
                searchQueue.remove(targetId);
                searchQueue.add(targetId);
            }
        }
    }

    public Integer getNextVertex(){
        return searchQueue.poll();
    }

    public void printShortestPath(Integer vId){
        // let's throw the target node in the list and print it all
        LinkedList<Integer> thisPath = (LinkedList<Integer>)tentativePaths[vId].getPath().clone();
        thisPath.add(vId);
        System.out.println(thisPath.stream()
                .map(n->String.valueOf(n))
                .collect(Collectors.joining(" --> ")));
        System.out.printf("Shortest path from %d to %d is %d.0 units\n",
                startVertex,
                vId,
                tentativePaths[vId].getDistance());
    }

    public class Path {

        private Integer distance;
        private LinkedList<Integer> path;

        public Path(Integer distance, LinkedList<Integer> path){
            this.distance = distance;
            this.path = path;
        }

        public Path(Integer distance){
            this(distance, new LinkedList<Integer>());
        }

        public Path(){
            this(Integer.MAX_VALUE);
        }

        public Integer getDistance(){
            return distance;
        }

        public void setDistance(Integer distance){
            this.distance = distance;
        }

        public LinkedList<Integer> getPath(){
            return path;
        }

        public void setPath(LinkedList<Integer> path){
            this.path = path;
        }
    }

    public class VertexComp implements Comparator<Integer>{

        public VertexComp(){
            super();
        }

        public int compare(Integer v1, Integer v2){
            return tentativePaths[v1].getDistance() - tentativePaths[v2].getDistance();
        }

    }

}
