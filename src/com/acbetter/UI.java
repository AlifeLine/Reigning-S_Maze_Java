package com.acbetter;
/*
  Created by Reign on 16/6/24.
  UI
 */

import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class UI extends Application {

    private boolean 有迷宫 = false;

    private Button 生成迷宫 = new Button("生成迷宫");
    private Button 寻找路径 = new Button("寻找路径");
    private Button 遍历迷宫 = new Button("遍历迷宫");
    private Button 最短路径 = new Button("最短路径");

    private Circle circle;
    private double SIZE;

    private PathTransition pt;
    private SequentialTransition sequentialTransition;
    private SequentialTransition sequentialTransition1;
    private SequentialTransition sequentialTransition2;

    private ArrayList<Integer> arrayList;
    private ArrayList<Integer> arrayList1;
    private ArrayList<Integer> arrayList2;
    private ArrayList<Integer> arrayList3;
    private ArrayList<Integer> arrayList5;
    private ArrayList<Integer> arrayList6;
    private Line line;

    private Queue queue;
    private Stack stack;
    private Maze maze;
    private Pane pane;

    private Text text = new Text("SIZE: ");
    private ComboBox<String> cbo = new ComboBox<>();
    private Text text1 = new Text("GAP: ");
    private ComboBox<String> cbo1 = new ComboBox<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        HBox hBox = new HBox(生成迷宫, 寻找路径, 遍历迷宫, 最短路径);
        寻找路径.setDisable(true);
        遍历迷宫.setDisable(true);
        最短路径.setDisable(true);
        hBox.setPadding(new Insets(5));
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        borderPane.setBottom(hBox);


        VBox vBox = new VBox();
        vBox.setPadding(new Insets(0, 30, 10, 10));


        cbo.getItems().addAll(Settings.getSIZE());
        cbo1.getItems().addAll(Settings.getTIME());

        HBox hbo1 = new HBox();
        hbo1.setAlignment(Pos.TOP_RIGHT);
        hbo1.setPadding(new Insets(10, 10, 10, 30));
        hbo1.setSpacing(4.45);
        hbo1.getChildren().addAll(text, cbo, text1, cbo1);


        borderPane.setTop(hbo1);
        borderPane.setLeft(vBox);

        Scene scene = new Scene(borderPane, 600, 620);

        primaryStage.setTitle("Reigning'S Maze");
        primaryStage.setScene(scene);
        primaryStage.show();


        生成迷宫.setOnAction(event -> { // TODO
            int a = 0, b = 0;
            try {
                for (int i = 0; i < Settings.getSIZE().length; i++) {
                    if (Settings.getSIZE()[i].compareTo(cbo.getValue()) == 0) {
                        a = i;
                        break;
                    }
                }
            } catch (Exception ex) {
                a = 0;
            }
            try {
                for (int i = 0; i < Settings.getTIME().length; i++) {
                    if (Settings.getTIME()[i].compareTo(cbo1.getValue()) == 0) {
                        b = i;
                        break;
                    }
                }
            } catch (Exception ex) {
                b = 0;
            }
            Settings.setSIZE(a);
            Settings.setTIME(b);

            if (!有迷宫) {
                生成迷宫.setText("重置迷宫");
                寻找路径.setDisable(false);
                遍历迷宫.setDisable(true);
                最短路径.setDisable(true);
                maze = new Maze(Settings.SIZE, Settings.SIZE);
                pane = maze.getPane();
                borderPane.setCenter(pane);
                有迷宫 = true;
            } else {
                生成迷宫.setText("生成迷宫");
                寻找路径.setDisable(true);
                遍历迷宫.setDisable(true);
                最短路径.setDisable(true);
                pane.getChildren().clear();
                有迷宫 = false;
            }
        });

        寻找路径.setOnAction(event -> {
            long start = System.currentTimeMillis();
            寻找路径.setDisable(true);

            stack = new Stack();
            queue = new Queue();

            arrayList = new ArrayList<>();
            arrayList1 = new ArrayList<>();


            stack.push(maze.maze[maze.startY][maze.startX]);

            int x, y;
            SIZE = 500 / maze.maze.length;
            circle = new Circle(SIZE / 2, SIZE / 2, SIZE / 2);
            circle.setFill(new Color(1.0, 0.5, 0, 0.5));
            pane.getChildren().add(circle);

            sequentialTransition = new SequentialTransition();

            pt = new PathTransition();
            pt.setDuration(Duration.millis(Settings.TIME));
            pt.setPath(new Line(0, 0, circle.getRadius(), circle.getRadius()));
            pt.setNode(circle);
            pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

            sequentialTransition.getChildren().add(pt);

            int[] arr = null;
            while (!(stack.peek().y == Settings.SIZE - 1 && stack.peek().x == Settings.SIZE - 1)) {

                x = stack.peek().x;
                y = stack.peek().y;

                queue.push(maze.maze[y][x]);

                arrayList.add(y);
                arrayList1.add(x);

                System.out.println(y + " " + x);
                arr = stack.peek().getDirection();
                if (arr != null) {
                    switch (arr[(int) (Math.random() * arr.length)]) {
                        case 2:
                            maze.maze[y][x].last.add(2);
                            stack.push(maze.maze[y - 1][x]);
                            maze.maze[y - 1][x].last.add(8);
                            break;
                        case 4:
                            maze.maze[y][x].last.add(4);
                            stack.push(maze.maze[y][x - 1]);
                            maze.maze[y][x - 1].last.add(6);
                            break;
                        case 6:
                            maze.maze[y][x].last.add(6);
                            stack.push(maze.maze[y][x + 1]);
                            maze.maze[y][x + 1].last.add(4);
                            break;
                        case 8:
                            maze.maze[y][x].last.add(8);
                            stack.push(maze.maze[y + 1][x]);
                            maze.maze[y + 1][x].last.add(2);
                            break;
                    }
                } else {
                    queue.popnew();
                    queue.popnew();
                    stack.pop();
                }
            }
            long time = System.currentTimeMillis() - start;
            System.out.println(time + " ms");

            moveCircle(arrayList, arrayList1, sequentialTransition, true, Color.BLUE);
            sequentialTransition.play();
            遍历迷宫.setDisable(false);
        });

        遍历迷宫.setOnAction(event -> {
            遍历迷宫.setDisable(true);

            int x, y;
            int[] arr;
            arrayList2 = new ArrayList<>();
            arrayList3 = new ArrayList<>();

            sequentialTransition1 = new SequentialTransition();

            while (!stack.isEmpty()) {
                x = stack.peek().x;
                y = stack.peek().y;
                System.out.println(y + " " + x);

                arrayList2.add(y);
                arrayList3.add(x);

                arr = stack.peek().getDirection();
                if (arr != null) {
                    switch (arr[(int) (Math.random() * arr.length)]) {
                        case 2:
                            maze.maze[y][x].last.add(2);
                            stack.push(maze.maze[y - 1][x]);
                            maze.maze[y - 1][x].last.add(8);
                            break;
                        case 4:
                            maze.maze[y][x].last.add(4);
                            stack.push(maze.maze[y][x - 1]);
                            maze.maze[y][x - 1].last.add(6);
                            break;
                        case 6:
                            maze.maze[y][x].last.add(6);
                            stack.push(maze.maze[y][x + 1]);
                            maze.maze[y][x + 1].last.add(4);
                            break;
                        case 8:
                            maze.maze[y][x].last.add(8);
                            stack.push(maze.maze[y + 1][x]);
                            maze.maze[y + 1][x].last.add(2);
                            break;
                    }
                } else {
                    stack.pop();
                }
            }
            moveCircle(arrayList2, arrayList3, sequentialTransition1, false, Color.ORANGE);
            sequentialTransition1.play();
            最短路径.setDisable(false);

        });

        最短路径.setOnAction(event -> {
            最短路径.setDisable(true);

            sequentialTransition2 = new SequentialTransition();

            arrayList5 = new ArrayList<>();
            arrayList6 = new ArrayList<>();

            while (!queue.isEmpty()) {
                arrayList5.add(queue.peek().y);
                arrayList6.add(queue.pop().x);
            }

            moveCircle(arrayList5, arrayList6, sequentialTransition2, true, Color.WHITE);
            sequentialTransition2.play();


        });
    }


    private void moveCircle(ArrayList<Integer> arrayList, ArrayList<Integer> arrayList1, SequentialTransition sequentialTransition, boolean bool, Color color) {

        int[] arr = new int[arrayList.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arrayList.get(i);
        }
        int[] arr1 = new int[arrayList1.size()];
        for (int i = 0; i < arr.length; i++) {
            arr1[i] = arrayList1.get(i);
        }

        int x, y;
        int x1 = 0, y1 = 0;
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            pt = new PathTransition();
            pt.setDuration(Duration.millis(Settings.TIME));
            pt.setNode(circle);

            y = arr[i];
            x = arr1[i];
            y1 = arr[i + 1];
            x1 = arr1[i + 1];
            if (x1 - x > 0)
                temp = 6;
            else if (x1 - x < 0)
                temp = 4;
            else if (y1 - y > 0)
                temp = 8;
            else
                temp = 2;

            switch (temp) {
                case 2:
                    pt.setPath(line = new Line((x * 2 + 1) * circle.getRadius(), (y * 2 + 1) * circle.getRadius(), (x * 2 + 1) * circle.getRadius(), (y * 2 - 1) * circle.getRadius()));
                    break;
                case 4:
                    pt.setPath(line = new Line((x * 2 + 1) * circle.getRadius(), (y * 2 + 1) * circle.getRadius(), (x * 2 - 1) * circle.getRadius(), (y * 2 + 1) * circle.getRadius()));
                    break;
                case 6:
                    pt.setPath(line = new Line((x * 2 + 1) * circle.getRadius(), (y * 2 + 1) * circle.getRadius(), (x * 2 + 3) * circle.getRadius(), (y * 2 + 1) * circle.getRadius()));
                    break;
                case 8:
                    pt.setPath(line = new Line((x * 2 + 1) * circle.getRadius(), (y * 2 + 1) * circle.getRadius(), (x * 2 + 1) * circle.getRadius(), (y * 2 + 3) * circle.getRadius()));
                    break;
            }
            line.setStroke(color);
            pane.getChildren().add(line);
            sequentialTransition.getChildren().add(pt);
        }
        if (bool) {
            pt = new PathTransition();
            pt.setDuration(Duration.millis(Settings.TIME));
            pt.setNode(circle);
            pt.setPath(new Line((x1 * 2 + 1) * circle.getRadius(), (y1 * 2 + 1) * circle.getRadius(), (Settings.SIZE * 2 - 1) * circle.getRadius(), (Settings.SIZE * 2 - 1) * circle.getRadius()));
            sequentialTransition.getChildren().add(pt);

        }
    }

}
