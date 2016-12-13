package com.acbetter;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * Created by Reign on 16/6/24.
 * getMaze
 */
public class Maze {

    Grid[][] maze;
    int startX;
    int startY;
    int exitX;
    int exitY;
    int row;
    int column;

    // 调用算法产生随机迷宫
    public Maze() {
        this(5, 5);
    }

    public Maze(int row, int column) {
        this(row, column, 0, 0, column - 1, row - 1);
    }

    public Maze(int row, int column, int startX, int startY, int exitX, int exitY) {

        // 初始化各项数据
        maze = new Grid[row][column];
        this.row = row;
        this.column = column;
        this.startX = startX;
        this.startY = startY;
        this.exitX = exitX;
        this.exitY = exitY;

        // 初始迷宫都为 15
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[i].length; j++)
                maze[i][j] = new Grid(i, j);

        // 把入口和出口的墙拆了
        maze[startY][startX].destroyWall(maze.length, maze[0].length);


        // 递归回溯生成随机迷宫
        Stack stack = new Stack();
        stack.push(maze[startY][startX]);
        int x, y;
        int[] arr = null;
        while (stack.getSize() != 0) {
            x = stack.peek().x;
            y = stack.peek().y;
            arr = stack.peek().getRandom();
            if (arr != null) {
                switch (arr[(int) (Math.random() * arr.length)]) {
                    case 2:
                        maze[y][x].destroyWall(2);
                        maze[--y][x].destroyWall(8);
                        stack.push(maze[y][x]);
                        break;
                    case 4:
                        maze[y][x].destroyWall(4);
                        maze[y][--x].destroyWall(6);
                        stack.push(maze[y][x]);
                        break;
                    case 6:
                        maze[y][x].destroyWall(6);
                        maze[y][++x].destroyWall(4);
                        stack.push(maze[y][x]);
                        break;
                    case 8:
                        maze[y][x].destroyWall(8);
                        maze[++y][x].destroyWall(2);
                        stack.push(maze[y][x]);
                        break;
                }
            } else {
                stack.pop();
            }
        }

        maze[exitY][exitX].destroyWall(maze.length, maze[0].length);

    }


    // 解迷宫
    public static boolean solve() {
        return false;
    }

//    // 测试函数
//    public static void main(String[] args) {
//        Maze maze = new Maze();
//        System.out.println(maze);
//    }

    // 获取迷宫图(UI)
    public Pane getPane() {
        Pane pane = new Pane();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j].getLine(maze) != null)
                    pane.getChildren().addAll(maze[i][j].getLine(maze));
            }
        }
        return pane;
    }


    // toString

    @Override
    public String toString() {
        return super.toString();
    }


    //getter and setter

    public int getRow() {
        return maze.length;
    }

    public int getColumn() {
        return maze[0].length;
    }

    class Grid {
        // up left down right
        // 1 -> has wall
        // 0 -> not wall

        int x;
        int y;
        int wall;
        ArrayList<Integer> last = new ArrayList<>();
        final int SIZE = 500 / maze.length;

        Grid(int y, int x) {
            this.x = x;
            this.y = y;
            this.wall = 15;
        }

        // 获取随机方向 2->up, 4->left, 8->down, 6->right

        public int[] getRandom() {
            int[] array = null;
            if (x == 0 && y == 0)
                array = new int[]{6, 8};
            else if (x == 0 && y == row - 1)
                array = new int[]{2, 6};
            else if (x == column - 1 && y == 0)
                array = new int[]{4, 8};
            else if (x == column - 1 && y == row - 1)
                array = new int[]{2, 4};
            else if (x == 0)
                array = new int[]{2, 6, 8};
            else if (y == 0)
                array = new int[]{4, 6, 8};
            else if (x == column - 1)
                array = new int[]{2, 4, 8};
            else if (y == row - 1)
                array = new int[]{2, 4, 6};
            else
                array = new int[]{2, 4, 6, 8};

            int[] arr = new int[array.length];
            int num = 0;
            for (int i = 0; i < array.length; i++) {
                switch (array[i]) {
                    case 2:
                        if (maze[y - 1][x].wall == 15)
                            arr[num++] = 2;
                        break;
                    case 4:
                        if (maze[y][x - 1].wall == 15)
                            arr[num++] = 4;
                        break;
                    case 6:
                        if (maze[y][x + 1].wall == 15)
                            arr[num++] = 6;
                        break;
                    case 8:
                        if (maze[y + 1][x].wall == 15)
                            arr[num++] = 8;
                        break;
                }
            }
            if (num == 0)
                return null;
            else {
                int[] arr1 = new int[num];
                System.arraycopy(arr, 0, arr1, 0, num);
                return arr1;
            }
        }

        // 获取可用方向
        public int[] getDirection() {
            int[] array = null;
            if (x == 0 && y == 0)
                array = new int[]{6, 8};
            else if (x == 0 && y == row - 1)
                array = new int[]{2, 6};
            else if (x == column - 1 && y == 0)
                array = new int[]{4, 8};
            else if (x == column - 1 && y == row - 1)
                array = new int[]{2, 4};
            else if (x == 0)
                array = new int[]{2, 6, 8};
            else if (y == 0)
                array = new int[]{4, 6, 8};
            else if (x == column - 1)
                array = new int[]{2, 4, 8};
            else if (y == row - 1)
                array = new int[]{2, 4, 6};
            else
                array = new int[]{2, 4, 6, 8};

            int[] arr = new int[array.length];
            int num = 0;
            int dir;
            for (int i = 0; i < array.length; i++) {
                dir = array[i];
                if (!last.contains(dir))
                    if (!isWall(wall, dir))
                        arr[num++] = dir;
            }
            if (num == 0)
                return null;
            else {
                int[] arr1 = new int[num];
                System.arraycopy(arr, 0, arr1, 0, num);
                return arr1;
            }
        }

        // 判断某墙是否存在

        public boolean isWall(int source, int direction) {
            int pos;
            switch (direction) {
                case 2:
                    pos = 3;
                    break;
                case 4:
                    pos = 2;
                    break;
                case 8:
                    pos = 1;
                    break;
                case 6:
                    pos = 0;
                    break;
                default:
                    return true;
            }
            int a = ((source >> pos) & 1);
            return a == 1;
        }

        // 拆墙 -> 常规

        public void destroyWall(int direction) {
            switch (direction) {
                case 2:
                    this.wall = wall & ~(1 << 3);
                    break;
                case 4:
                    this.wall = wall & ~(1 << 2);
                    break;
                case 8:
                    this.wall = wall & ~(1 << 1);
                    break;
                case 6:
                    this.wall = wall & ~(1);
                    break;
                default:
                    break;
            }
        }

        // 拆墙 -> 边界
        public void destroyWall(int row, int column) {
            if (x == 0)
                destroyWall(4);
            if (y == 0)
                destroyWall(2);
            if (y == row - 1)
                destroyWall(8);
            if (x == column - 1)
                destroyWall(6);
        }

        public Line[] getLineAll(int y, int x) {
            return new Line[]{new Line(x, y, x + SIZE, y), new Line(x, y, x, y + SIZE), new Line(x, y + SIZE, x + SIZE, y + SIZE), new Line(x + SIZE, y, x + SIZE, y + SIZE)};
        }

//    public static final String s0 = "0000";
//    public static final String s1 = "0001";
//    public static final String s2 = "0010";
//    public static final String s3 = "0011";
//    public static final String s4 = "0100";
//    public static final String s5 = "0101";
//    public static final String s6 = "0110";
//    public static final String s7 = "0111"; 3
//    public static final String s8 = "1000";
//    public static final String s9 = "1001";
//    public static final String s10 = "1010";
//    public static final String s11 = "1011"; 2
//    public static final String s12 = "1100";
//    public static final String s13 = "1101"; 1
//    public static final String s14 = "1110"; 0
//    public static final String s15 = "1111";

        public Line[] getLine(Grid[][] maze) {
            Line[] lineAll = getLineAll(this.y * SIZE, this.x * SIZE);
            switch (this.wall) {
                case 0:
                    return null;
                case 1:
                    return new Line[]{lineAll[3]};
                case 2:
                    return new Line[]{lineAll[2]};
                case 3:
                    return new Line[]{lineAll[2], lineAll[3]};
                case 4:
                    return new Line[]{lineAll[1]};
                case 5:
                    return new Line[]{lineAll[1], lineAll[3]};
                case 6:
                    return new Line[]{lineAll[1], lineAll[2]};
                case 7:
                    return new Line[]{lineAll[1], lineAll[2], lineAll[3]};
                case 8:
                    return new Line[]{lineAll[0]};
                case 9:
                    return new Line[]{lineAll[0], lineAll[3]};
                case 10:
                    return new Line[]{lineAll[0], lineAll[2]};
                case 11:
                    return new Line[]{lineAll[0], lineAll[2], lineAll[3]};
                case 12:
                    return new Line[]{lineAll[0], lineAll[1]};
                case 13:
                    return new Line[]{lineAll[0], lineAll[1], lineAll[3]};
                case 14:
                    return new Line[]{lineAll[0], lineAll[1], lineAll[2]};
                case 15:
                    return lineAll;
                default:
                    return null;
            }
        }

    }
}



