import java.lang.Math;

public class AStarPathFinding {

    public static void main(String[] args){
        // please see the main() as an example on how to use the find_shortest_path() function
        int[] start = new int[2];
        start[0] = 10;
        start[1] = 10;
        int[] end = new int[2];
        end[0] = 20;
        end[1] = 20;
        int[] dim = new int[2];
        dim[0] = 50;
        dim[1] = 50;
        int[][] shortest_path;

        shortest_path = find_shortest_path(start, end, dim);

        for (int i=(shortest_path.length - 1); i>=0; --i) {
            if ((shortest_path[i][0] != 0) || (shortest_path[i][1] != 0)) {
                System.out.println(Integer.toString(shortest_path[i][0]) + "       " + Integer.toString(shortest_path[i][1]));
                System.out.println("__ _ _ _ __");
            }
        }
    }

    private static int[][] find_shortest_path(int[] start_pos, int[] end_pos, int[] grid_dim){
        // Create cost vectors full of Zeros
        int[][] d_cost = new int[grid_dim[0]][grid_dim[1]];
        int[][] h_cost = new int[grid_dim[0]][grid_dim[1]];
        int[][] f_cost = new int[grid_dim[0]][grid_dim[1]];
        for (int i=0; i<grid_dim[0]; i++){
            for (int j=0; j<grid_dim[1]; j++){
                f_cost[i][j] = -10;
            }
        }
        // Create Variable for used status of Fields
        int[][] used = new int[grid_dim[0]][grid_dim[1]];
        // Store the Parent informations
        int[][][] parent = new int[grid_dim[0]][grid_dim[1]][3];
        int[][] set_parent = new int[grid_dim[0]][grid_dim[1]];
        // temp variable cost / Position
        int[] tmp_pos = new int[2];
        tmp_pos[0] = start_pos[0];
        tmp_pos[1] = start_pos[1];
        int tmp_cost = 0;
        // found var
        int found = 0;
        // var for recreated Path
        int[][] Path_found = new int[grid_dim[0] * 2][2];
        int moving_var = 0;
        // observing area
        while (found == 0){
            int[][] around_position = new int[8][2];
            around_position[0][0] = tmp_pos[0] - 1;
            around_position[0][1] = tmp_pos[1] - 1;
            around_position[1][0] = tmp_pos[0];
            around_position[1][1] = tmp_pos[1] - 1;
            around_position[2][0] = tmp_pos[0] + 1;
            around_position[2][1] = tmp_pos[1] - 1;
            around_position[3][0] = tmp_pos[0] - 1;
            around_position[3][1] = tmp_pos[1];
            around_position[4][0] = tmp_pos[0] + 1;
            around_position[4][1] = tmp_pos[1];
            around_position[5][0] = tmp_pos[0] - 1;
            around_position[5][1] = tmp_pos[1] + 1;
            around_position[6][0] = tmp_pos[0];
            around_position[6][1] = tmp_pos[1] + 1;
            around_position[7][0] = tmp_pos[0] + 1;
            around_position[7][1] = tmp_pos[1] + 1;
            for (int i=0; i<=7; i++){
                if ((around_position[i][0] < grid_dim[0]) && (around_position[i][1] < grid_dim[1])) {
                    if ((0 <= around_position[i][0]) && (0 <= around_position[i][1])) {
                        if (set_parent[around_position[i][0]][around_position[i][1]] == 0) {
                            parent[around_position[i][0]][around_position[i][1]][0] = tmp_pos[0];
                            parent[around_position[i][0]][around_position[i][1]][1] = tmp_pos[1];
                            parent[around_position[i][0]][around_position[i][1]][2] = tmp_cost;
                            set_parent[around_position[i][0]][around_position[i][1]] = 1;
                        }
                    }
                }
                if ((around_position[i][0] == end_pos[0]) && (around_position[i][1] == end_pos[1])){
                    found = 1;
                    int[] pos_ln = new int[2];
                    pos_ln[0] = end_pos[0];
                    pos_ln[1] = end_pos[1];

                    Path_found[moving_var][0] = end_pos[0];
                    Path_found[moving_var][1] = end_pos[1];
                    moving_var++;

                    int found_path = 0;
                    while (found_path == 0){
                        // recalculate the path from end- to startpoint
                        int px = pos_ln[0];
                        int py = pos_ln[1];
                        pos_ln[0] = parent[px][py][0];
                        pos_ln[1] = parent[px][py][1];
                        if ((pos_ln[0] == start_pos[0]) && (pos_ln[1] == start_pos[1])){
                            found_path = 1;
                        }
                        Path_found[moving_var][0] = pos_ln[0];
                        Path_found[moving_var][1] = pos_ln[1];
                        moving_var++;
                    }

                    break;
                }
                if ((around_position[i][0] < grid_dim[0]) && (around_position[i][1] < grid_dim[1])) {
                    if ((0 <= around_position[i][0]) && (0 <= around_position[i][1])) {
                        // calculate Costs
                        d_cost[around_position[i][0]][around_position[i][1]] = (int) Math.sqrt(Math.pow(around_position[i][0] - start_pos[0], 2) + Math.pow(around_position[i][1] - start_pos[1], 2)) * 10;
                        h_cost[around_position[i][0]][around_position[i][1]] = (int) Math.sqrt(Math.pow(around_position[i][0] - end_pos[0], 2) + Math.pow(around_position[i][1] - end_pos[1], 2)) * 10;
                        f_cost[around_position[i][0]][around_position[i][1]] = (int) d_cost[around_position[i][0]][around_position[i][1]] + h_cost[around_position[i][0]][around_position[i][1]];
                    }
                }
            }
            int lowest_cost = 1000000;
            for (int grid_x=0; grid_x<grid_dim[0]; grid_x++){
                for (int grid_y=0; grid_y<grid_dim[1]; grid_y++){
                    if ((f_cost[grid_x][grid_y] != -10) && (used[grid_x][grid_y] == 0) && ((grid_x != start_pos[0]) || (grid_y != start_pos[1]))){
                        if (f_cost[grid_x][grid_y] < lowest_cost){
                            lowest_cost = f_cost[grid_x][grid_y];
                            tmp_pos[0] = grid_x;
                            tmp_pos[1] = grid_y;
                        }
                    }
                }
            }
            used[tmp_pos[0]][tmp_pos[1]] = 1;
            tmp_cost = f_cost[tmp_pos[0]][tmp_pos[1]];
        }
        return Path_found;
    }
}
