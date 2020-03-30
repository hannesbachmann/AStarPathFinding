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
            
            

The AStarPathFinding.c file also calculate the shortest path between two points.
It do not now have a option to ignore barriers (black_fields).
The int main() method is just there to give an example on how to use the function.                                       


There is now also a .java file.
The main() should be seen as an example on how to use the find_shortest_path() function.
Take care the path returned by the find_shortest_path() function is 
from        endpoint    to          startpoint (!).

HOPE THIS HELPS



 
            
