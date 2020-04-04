# AStarPathFinding
an algorithm to find the shortest legal path between two points.
Languages: Python       ->          .py
            C           ->          .c
            Java        ->          .java

Inside the AStarPathFinding.py file exists a function called 'find_shortest_path(start_position, goal_position, grid_dim, black_fields)'.                                                                   
It takes:
            start_position    |     [x, y]                  
            goal_position     |     [x, y]                  
            grid_dim          |     [width, height]                     
            black_fields      |     [[x1, y1], [x2, y2], ..., [xn, yn]]                                                                
            
            
_________________________________________________________________________________
The AStarPathFinding.c file also calculate the shortest path between two points.
It do not now have a option to ignore barriers (black_fields).
                                  

_________________________________________________________________________________
There is now also a .java file.
Take care the path returned by the find_shortest_path() function is 
from        endpoint    to          startpoint (!).
Example on how to use this:

shortest_path = find_shortest_path(start, end, dim);
for (int i=(shortest_path.length - 1); i>=0; --i) {
   if ((shortest_path[i][0] != 0) || (shortest_path[i][1] != 0)) {
        System.out.println(Integer.toString(shortest_path[i][0]) + "       " + Integer.toString(shortest_path[i][1]));
        System.out.println("_ _ _ _ _ _ _");
   }
}   


HOPE THIS HELPS



 
            
