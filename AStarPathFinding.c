#include <stdio.h>
#include <math.h>   // for sqrt(a) and pow(a, b)

typedef struct path {size_t* length;
                        int* array[100][2];
                        } path_t;


int find_shortest_path(int start_pos[], int end_pos[], int grid_dim[], path_t *mypath){
    // Create cost vectors full of Zeros
    int d_cost[grid_dim[0]][grid_dim[1]];
    int h_cost[grid_dim[0]][grid_dim[1]];
    int f_cost[grid_dim[0]][grid_dim[1]];
    for (int i=0; i<grid_dim[0]; i++){
        for (int j=0; j<grid_dim[1]; j++){
            f_cost[i][j] = -10;
        }
    }
    // Create Variable for used status of Fields
    int used[grid_dim[0]][grid_dim[1]];
    // Store the Parent informations
    int parent[grid_dim[0]][grid_dim[1]][3];
    int set_parent[grid_dim[0]][grid_dim[1]];
    // temp variable cost / Position
    int tmp_pos[2];
    tmp_pos[0] = start_pos[0];
    tmp_pos[1] = start_pos[1];
    int tmp_cost = 0;
    // found var
    int found = 0;
    // var stores the found path
    int Path_found[grid_dim[0] * 2][2];
    int moving_var = 0;
    // observing area
    while (found == 0){
        int around_position[8][2];
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
            if ((0 <= around_position[i][0] < grid_dim[0]) && (0 <= around_position[i][1] < grid_dim[1])){
                if (set_parent[around_position[i][0]][around_position[i][1]] == 0){
                    parent[around_position[i][0]][around_position[i][1]][0] = tmp_pos[0];
                    parent[around_position[i][0]][around_position[i][1]][1] = tmp_pos[1];
                    parent[around_position[i][0]][around_position[i][1]][2] = tmp_cost;
                    set_parent[around_position[i][0]][around_position[i][1]] = 1;
                }
            }
            if ((around_position[i][0] == end_pos[0]) && (around_position[i][1] == end_pos[1])){
                found = 1;
                int pos_ln[2];
                pos_ln[0] = end_pos[0];
                pos_ln[1] = end_pos[1];
                int found_path = 0;
                while (found_path == 0){
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
                Path_found[moving_var][0] = end_pos[0];
                Path_found[moving_var][1] = end_pos[1];
                break;
            }
            if ((0 <= around_position[i][0] < grid_dim[0]) && (0 <= around_position[i][1] < grid_dim[1])){
                // calculate Costs
                d_cost[around_position[i][0]][around_position[i][1]] = (int)sqrt(pow(around_position[i][0] - start_pos[0], 2) + pow(around_position[i][1] - start_pos[1], 2)) * 10;
                h_cost[around_position[i][0]][around_position[i][1]] = (int)sqrt(pow(around_position[i][0] - end_pos[0], 2) + pow(around_position[i][1] - end_pos[1], 2)) * 10;
                f_cost[around_position[i][0]][around_position[i][1]] = (int)d_cost[around_position[i][0]][around_position[i][1]] + h_cost[around_position[i][0]][around_position[i][1]];
            }
        }
        int lowest_cost = 10000;
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
    mypath->length = moving_var - 1;
    while(moving_var > 0){
        moving_var -= 1;
        mypath->array[moving_var][0] = Path_found[moving_var][0];
        mypath->array[moving_var][1] = Path_found[moving_var][1];
        // printf("%d\t%d\n", Path_found[moving_var][0], Path_found[moving_var][1]);
    }
    return 0;
}

int main(){
    // this is just an example on how to use the function
    int dimension[2];
    dimension[0] = 50;
    dimension[1] = 50;
    int start_pos[2];
    start_pos[0] = 10;
    start_pos[1] = 10;
    int end_pos[2];
    end_pos[0] = 20;
    end_pos[1] = 20;

    path_t fullpath = {
        .length = 100,
    };

    find_shortest_path(start_pos, end_pos, dimension, &fullpath);

    for (int i=(fullpath.length); i>0; i--){
        printf("%d\t%d\n", fullpath.array[i][0], fullpath.array[i][1]);
    }
    return 0;
}
