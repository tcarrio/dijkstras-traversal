import  java.util.*;
import  java.io.*;

import java.lang.reflect.Array;

/**
 * Created by singh on 3/10/17.
 */

public class Graph {

     public class Vertex{
        Integer     mVertId;
        Integer     mDistance;
    }

    Vector<LinkedList<Vertex>>      mGraph;
    int                             mVertexCount;
    int                             mEdgeCount;


    Vector<LinkedList<Vertex>>      getGraph() {return mGraph;}
    LinkedList<Vertex>              getAdj(Integer vertex) { return mGraph.get(vertex.intValue()); }
    Integer                         getVertexCount() {return mVertexCount;}

    // Constructor that uses an adjacency specified as a GR file

    public Graph (String fileName)
    {
        try{
            Scanner inFile = new Scanner(new FileReader(fileName));
            while (inFile.hasNextLine())
            {
                String  tok = inFile.next();
                tok.trim();

                if (tok.equals("c")) {
                    inFile.nextLine();
                }

                else if (tok.equals("p"))
                {
                    String code = inFile.next();
                    mVertexCount = inFile.nextInt();
                    mEdgeCount = inFile.nextInt();
                    //System.out.println ("Vertex Count " + mVertexCount);
                    mGraph  = new Vector<LinkedList<Vertex>>(mVertexCount+1);

                    for (int i = 0; i <= mVertexCount; i++) {

                        //System.out.println (i);
                        mGraph.add(i, new LinkedList<Vertex>());
                    }
                    //System.out.println ("Size of Vector = " + mGraph.size());
                    inFile.nextLine();
                }
                else if (tok.equals("a"))
                {
                    Integer fromVertex  = inFile.nextInt();
                    Integer toVertex    = inFile.nextInt();
                    Integer distance    = inFile.nextInt();
                    Vertex  v           = new Vertex();
                    v.mVertId           = toVertex;
                    v.mDistance         = distance;

                    //System.out.println ("From -> " + fromVertex + " to " + toVertex + " Dist " + distance);
                    LinkedList<Vertex>      adj = mGraph.get(fromVertex);
                    adj.add(v);
                    inFile.nextLine();
                }
                else
                {
                    System.out.println ("Found an illegal code: " + tok);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println ("Caught Exception " + e.getMessage());
        }
    }

    // Print Graph - Utility Function

    void PrintGraph ()
    {
        // Go through the Adjacency Matrix

        for (int vert = 1; vert <= mVertexCount; vert++)
        {
           LinkedList<Vertex> adj = mGraph.get(vert);
           System.out.print ("From Vertex: " + vert);
           for (Iterator<Vertex> vertEnum = adj.iterator();
                   vertEnum.hasNext();)
            {
                Vertex     toVert = vertEnum.next();
                System.out.print (" " + toVert.mVertId + " (" + toVert.mDistance + ") ");
            }
            System.out.println();
        }
    }
}
