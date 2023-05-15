/**
*   path search algorithm implementation.
* This class finds the shortest path between two FieldClass instances in the game maze.
* It uses the FieldClass.nextField method to explore the neighboring fields and
* the canMove method of MazeObject interface to determine if a move can be made.
* @author xdomra00 xdomra00@stud.fit.vutbr.cz
* @author xgarip00 xgarip00@stud.fit.vutbr.cz
*/
package xdomra00.src.logic.game;

import xdomra00.src.logic.common.Field;
import xdomra00.src.logic.common.MazeObject;

import java.util.*;
/**
 * The BFS class provides an implementation of the breadth-first search algorithm to find the shortest path between two FieldClass instances in the game maze.
 * It uses the FieldClass.nextField method to explore the neighboring fields and the canMove method of MazeObject interface to determine if a move can be made.
 * It maintains a search tree of BFSNode objects representing the visited fields and the shortest path to the current node from the starting node.
 * The find method returns the list of Field.Direction instances representing the path to the goal node.
 * @see FieldClass
 * @see MazeObject
 * @see BFSNode
 */
public class BFS
{
    /**
    * Inner class representing a node in the BFS search tree.
    */
    class BFSNode
    {
        public BFSNode parent;

        public FieldClass core;

        public BFSNode(FieldClass core)
        {
            this.core = core;
            parent = null;
        }
    }

    private Set<BFSNode> toVisit;

    private List<BFSNode> visited;

    private BFSNode goal;
    private boolean ready = false;
    /**
    * Constructs a new BFS instance with the specified start and goal nodes.
    * @param start The starting FieldClass node.
    * @param goal The goal FieldClass node.
    */
    public BFS(FieldClass start, FieldClass goal)
    {
        toVisit = new HashSet<>();
        visited = new ArrayList<>();
        toVisit.add(new BFSNode(start));
        this.goal = new BFSNode(goal);
        ready = true;
    }
    /**
     * Helper method to check if a BFSNode instance has already been visited.
     * @param node The BFSNode instance to check.
     * @return True if the node has already been visited; False otherwise.
     */
    private boolean findVisited(BFSNode node)
    {
        for(BFSNode n : visited)
            if(n.core.getRow() == node.core.getRow() && n.core.getCol() == node.core.getCol())
                return true;
        return false;
    }
    /**
     * Finds the shortest path between the start and goal nodes using the BFS algorithm. 
     * @return The list of Field.Direction instances representing the path to the goal node.
     */
    public List<Field.Direction> find()
    {
        if(!ready)
            return null;
        ready = false;
        if(!goal.core.canMove())
            return null;
        List<Field.Direction> path = new ArrayList<>();

        boolean found = false;
        while(!toVisit.isEmpty())
        {
            BFSNode visiting = (BFSNode) toVisit.toArray()[0];
            toVisit.remove(visiting);
            visited.add(visiting);
            if(visiting.core.getCol() == goal.core.getCol() && visiting.core.getRow() == goal.core.getRow())
            {
                found = true;
                goal.parent = visiting.parent;
                break;
            }
            for(Field.Direction d : Field.Direction.values())
            {
                BFSNode child = new BFSNode((FieldClass) visiting.core.nextField(d));
                if(child.core == null)
                    continue;
                if(!child.core.canMove())
                    continue;
                if(findVisited(child))
                    continue;
                child.parent = visiting;
                toVisit.add(child);
            }
        }

        if(!found)
            return null;

        BFSNode current = goal;
        while(current.parent != null)
        {
            int rc = current.core.getRow();
            int rp = current.parent.core.getRow();
            int cc = current.core.getCol();
            int cp = current.parent.core.getCol();

            if(rc != rp)
                path.add(0, rc < rp ? Field.Direction.U : Field.Direction.D);
            if(cc != cp)
                path.add(0, cc < cp ? Field.Direction.L : Field.Direction.R);

            current = current.parent;
        }

        return path;
    }

}
