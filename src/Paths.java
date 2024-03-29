import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tom on 3/23/17.
 */
public class Paths{

    Path[] tentativePaths;
    PriorityQueue<Integer> searchQueue;
    Graph g;
    Integer startVertex;

    public Paths(Graph g, Integer startVertex){
        // create a list of tentative distances for each vertex
        tentativePaths = new Path[g.getVertexCount()+1];

        // create a priority queue for sorted by lowest tentative distance
        searchQueue = new PriorityQueue<Integer>(g.getVertexCount()+1,new VertexComp());

        // fill em up
        for(int i = 0; i< tentativePaths.length; i++){
            tentativePaths[i]=new Path();
        }

        // set the default values on starting vertex
        tentativePaths[startVertex]=new Path(0);

        for(int i=0; i<tentativePaths.length; i++){
            searchQueue.add(i);
        }

        this.g = g;
        this.startVertex = startVertex;
    }

    public void applyRelaxation(Integer thisVertexId){
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
                tentativePaths[targetId].setDistance(tempDistance);
                tentativePaths[targetId].setPreviousVertex(thisVertexId);

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
        private Integer previousVertex;

        public Path(Integer distance, Integer previousId){
            this.distance = distance;
            this.previousVertex = previousId;
        }

        public Path(Integer distance){
            this(distance, null);
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
            Integer prev = previousVertex;
            LinkedList<Integer> pathOfVertexes = new LinkedList<>();
            while(prev != null){
                pathOfVertexes.addFirst(prev);
                prev = tentativePaths[prev].getPreviousVertex();
            }
            return pathOfVertexes;
        }

        public Integer getPreviousVertex(){
            return previousVertex;
        }

        public void setPreviousVertex(Integer previousVertex){
            this.previousVertex = previousVertex;
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
