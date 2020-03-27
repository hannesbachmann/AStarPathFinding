import math


def find_shortest_path(start_position, goal_position, grid_dim, black_fields):
    """
    Finds the shortest legal path between two points in a grid.

    :param start_position: [x position, y position]
        Starting point (begin there location)
    :param goal_position: [x position, y position]
        End point (go to location)
    :param grid_dim: [x axis, y axis]
        The size of the grid in where the points are
    :param black_fields: [[x position, y position], [...], [...], ...]
        Fields that cant be reached
    :return: path: [[x position, y position], [...], [...], ...]
        The path from start point to end point in the grid
    """
    # init some grids of costs
    d_cost = [[-10 for j in range(grid_dim[1])] for i in range(grid_dim[0])]    # distance to start cost
    h_cost = [[-10 for j in range(grid_dim[1])] for i in range(grid_dim[0])]    # distance to goal cost
    f_cost = [[-10 for j in range(grid_dim[1])] for i in range(grid_dim[0])]    # combined added cost
    # init a grid to store the used fields
    USED = [[False for j in range(grid_dim[1])] for i in range(grid_dim[0])]
    # init grids to declare parent and parent status
    parent = [[[-10, -10, 100000] for j in range(grid_dim[1])] for i in range(grid_dim[0])]
    set_parent = [[False for j in range(grid_dim[1])] for i in range(grid_dim[0])]

    # set black/unreachable fields as used
    for i in range(grid_dim[0]):
        for j in range(grid_dim[1]):
            for black in range(len(black_fields)):
                if i == black_fields[black][0] and j == black_fields[black][1]:
                    USED[i][j] = True
    # init temporary position and cost
    tmp_pos = start_position

    found = False   # tell when a path is found
    path = []       # store the path
    while not found:
        # calc and store all positions around the actual position
        around_position = [[tmp_pos[0] - 1, tmp_pos[1] - 1], [tmp_pos[0], tmp_pos[1] - 1],
                           [tmp_pos[0] + 1, tmp_pos[1] - 1], [tmp_pos[0] - 1, tmp_pos[1]],
                           [tmp_pos[0] + 1, tmp_pos[1]], [tmp_pos[0] - 1, tmp_pos[1] + 1],
                           [tmp_pos[0], tmp_pos[1] + 1], [tmp_pos[0] + 1, tmp_pos[1] + 1]]
        for i in range(7):
            # see if position is inside the grid
            if 0 <= around_position[i][0] < grid_dim[0] and 0 <= around_position[i][1] < grid_dim[1]:
                if set_parent[around_position[i][0]][around_position[i][1]] is False:
                    # set the parent status of the around position
                    parent[around_position[i][0]][around_position[i][1]] = [tmp_pos[0], tmp_pos[1]]
                    set_parent[around_position[i][0]][around_position[i][1]] = True
            if around_position[i][0] == goal_position[0] and around_position[i][1] == goal_position[1]:
                # on of the around positions has reached the endpoint/goal
                found = True
                pos_ln = [goal_position[0], goal_position[1]]
                found_path = False
                while found_path is not True:
                    # recalculate the path from endpoint/goal to startpoint
                    px = pos_ln[0]
                    py = pos_ln[1]
                    pos_ln = [parent[px][py][0], parent[px][py][1]]
                    if pos_ln[0] == start_position[0] and pos_ln[1] == start_position[1]:
                        found_path = True
                    path.append(pos_ln)
                path.append(goal_position)
                break
            if 0 <= around_position[i][0] < grid_dim[0] and 0 <= around_position[i][1] < grid_dim[1]:
                # calculate the costs from the fields around a centerpoint

                # distance to startpoint times ten
                d_cost[around_position[i][0]][around_position[i][1]] = int(math.sqrt(
                    math.pow(around_position[i][0] - start_position[0], 2) + math.pow(
                        around_position[i][1] - start_position[1], 2)) * 10)
                # distance to endpoint/goal times 10
                h_cost[around_position[i][0]][around_position[i][1]] = int(math.sqrt(
                    math.pow(around_position[i][0] - goal_position[0], 2) + math.pow(
                        around_position[i][1] - goal_position[1], 2)) * 10)
                # sum of the two costs
                f_cost[around_position[i][0]][around_position[i][1]] = int(h_cost[around_position[i][0]][
                                                                               around_position[i][1]] + \
                                                                           d_cost[around_position[i][0]][
                                                                               around_position[i][1]])
        lowest_cost = 100000000     # init with a high value which can be easy underscored by a lower cost
        for i in range(grid_dim[0]):
            for j in range(grid_dim[1]):
                if f_cost[i][j] != -10 and (USED[i][j] is False) and (i != start_position[0] or j != start_position[1]):
                    if f_cost[i][j] < lowest_cost:
                        # searching for the lowest cost to find the shortest path
                        # (can also be changed to highest cost to find the longest path)
                        lowest_cost = f_cost[i][j]
                        tmp_pos = [i, j]
        USED[tmp_pos[0]][tmp_pos[1]] = True     # mark the used point to dont go there again
    # give back the shortest path
    return path