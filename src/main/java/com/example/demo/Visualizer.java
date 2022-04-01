package com.example.demo;

import SortingAlgorithms.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Visualizer extends Application {
    int SIZE = 10;                  //number of elements
    long MAXTIME = 1000;            //to change the total duration


    ArrayList<ArrayList<Integer>> snaps;
    ArrayList<ArrayList<Integer>> specials;
    int index = 0;
    int size = 0;
    final int WIDTH=600;
    final int Height=300;
    int maxHeight = 0;
    public Visualizer(){
//        this.snaps=new ArrayList<ArrayList<Integer>>();
//        this.specials=new ArrayList<ArrayList<Integer>>();
//
//        ArrayGenerator g = new ArrayGenerator();
//        for (int i = 0; i < 100; i++) {
//            var x = new ArrayList<Integer>();
//            x.add(i);
//            x.add((i+3)%100);
//            addSnap(g.generate(100,0,500),x);
//        }
    }

    public Visualizer(ArrayList<ArrayList<Integer>> snaps, ArrayList<ArrayList<Integer>> specials) {
        this.snaps=snaps;
        this.specials=specials;
    }

    public void setMaxHeight(int max){
        maxHeight=max;
    }
    public void computeMaxHeight(){
        for (int i = 0; i < snaps.get(0).size(); i++) {
            if(snaps.get(0).get(i)>maxHeight){
                maxHeight = snaps.get(0).get(i);
            }
        }
    }
    public void addSnap(ArrayList<Integer>x,ArrayList<Integer> specials ){
        this.snaps.add(x);
        this.specials.add(specials);
        size++;
    }
    int size(){
        return size;
    }
    @Override
    public void start(Stage stage) throws IOException {
        ListView<String> listView = new ListView<>();
        listView.getItems().add("Heap Sort");
        listView.getItems().add("Bubble Sort");
        listView.getItems().add("Insertion Sort");
        listView.getItems().add("Selection Sor");
        listView.getItems().add("Merge Sort");
        listView.getItems().add("Quick Sort");
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        AtomicBoolean stopped = new AtomicBoolean(true);
        Button begin = new Button("Start Sorting");
        Button stop = new Button("stop");

        listView.setLayoutX(WIDTH);
        listView.setLayoutY(0);
        listView.setMaxHeight(Height*1/2);
        begin.setLayoutX(WIDTH);
        begin.setLayoutY(Height*1/2);
        begin.setMaxHeight(Height*1/2);
        stop.setLayoutX(WIDTH+100);
        stop.setLayoutY(Height*1/2);
        begin.setLayoutY(Height*1/2);
        begin.setMaxHeight(Height*1/2);
        Group root = new Group();
        root.getChildren().add(begin);
        root.getChildren().add(stop);
        root.getChildren().add(listView);
        AtomicReference<Group> array = new AtomicReference<>(new Group());
        root.getChildren().add(array.get());
        AtomicReference<ArrayList<Rectangle>> rectangles = new AtomicReference<ArrayList<Rectangle>>();
        Scene scene = new Scene(root, WIDTH+300, 2*Height);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        AnimationTimer timer = new AnimationTimer() {
//            long last=System.currentTimeMillis();
            long last;
            @Override
            public void handle(long l) {
                if(!stopped.get() && l-last>(MAXTIME/snaps.size())) {
                    stopped.set(!updateHeights(rectangles.get()));
                    if(stopped.get()){
                        begin.setDisable(false);
                    }
                    stage.show();
                    last = l;
                }

            }
        };
        timer.start();
        begin.setOnAction(event -> {
            if(stopped.get()) {
                root.getChildren().remove(array.get());
                array.set(new Group());
                int selected = listView.getSelectionModel().getSelectedIndex();
                rectangles.setRelease(new ArrayList<>());
                switch (selected) {
                    case 1:
                        array.get().getChildren().clear();
                        rectangles.set(prepare(new BubbleSort<Integer>()));
                        array.get().getChildren().addAll(rectangles.get());
                        break;
                    case 2:
                        array.get().getChildren().clear();
                        rectangles.set(prepare(new InsertionSort<Integer>()));
                        array.get().getChildren().addAll(rectangles.get());
                        break;
                    case 4:
                        array.get().getChildren().clear();
                        rectangles.set(prepare(new MergeSort<Integer>()));
                        array.get().getChildren().addAll(rectangles.get());
                        break;
                    case 5:
                        array.get().getChildren().clear();
                        rectangles.set(prepare(new QuickSort<Integer>()));
                        array.get().getChildren().addAll(rectangles.get());
                        break;
                    case 3:
                        array.get().getChildren().clear();
                        rectangles.set(prepare(new SelectionSort<Integer>()));
                        array.get().getChildren().addAll(rectangles.get());
                        break;
                    default:
                        array.get().getChildren().clear();
                        rectangles.set(prepare(new HeapSorter<Integer>()));
                        array.get().getChildren().addAll(rectangles.get());
                        break;
                }
                root.getChildren().add(array.get());
                stopped.set(false);
                begin.setDisable(true);
            }
        });
        stop.setOnAction(event -> {
            array.get().getChildren().clear();
            rectangles.set(new ArrayList<Rectangle>());
            rectangles.setOpaque(new ArrayList<Rectangle>());
            begin.setDisable(false);
            stopped.set(true);
        });
    }
    public ArrayList<Rectangle> prepare(ISort sorter){
        index = 0;
        Snapper<Integer> snapper = new Snapper<Integer>();
        ArrayGenerator generator = new ArrayGenerator();
//        HeapSorter sorter = new HeapSorter<Integer>();
        ArrayList<Integer> tobeSorted = generator.generate(SIZE,0,120);
        sorter.sort(tobeSorted,snapper);
        System.out.println(Arrays.toString(snapper.getSnaps().toArray()));

        this.snaps=snapper.getSnaps();
        this.specials=snapper.getSpecials();
        this.size= snaps.size();
        computeMaxHeight();
        int n = snaps.isEmpty()? 0: snaps.get(0).size();
        int width = WIDTH/n;

        Group root = new Group();
        ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
        for (int i = 0; i < snaps.get(0).size(); i++) {
            int x=i*width;
            int y=0;
            int height = (snaps.get(index).get(i)*Height/maxHeight);
            if(height<0){
                y+=Height;
            }
            else{
                y=(Height-height);
            }
            Rectangle R = new Rectangle(x, y, width, height);
            R.setFill(Color.CYAN);
            R.setStroke(Color.valueOf("black"));
            R.setStrokeWidth(0.5);
            rectangles.add(R);
            root.getChildren().add(R);
        }
        for (int i = 0; i < specials.get(index).size(); i++) {
            rectangles.get(specials.get(index).get(i)).setFill(Color.RED);
        }
        return rectangles;
    }
    public boolean updateHeights(ArrayList<Rectangle> rectangles){
        if(index >= snaps.size()){
            return false;
        }
        index=(index+1);
        for (int i = 0; i < snaps.get(index).size(); i++) {
            int height = (snaps.get(index).get(i)*Height/maxHeight);
            if(height>0){
                rectangles.get(i).setY(Height-height);
            }
            else{
                rectangles.get(i).setY(Height);
            }
            rectangles.get(i).setHeight(Math.abs(height));
            rectangles.get(i).setFill(Color.CYAN);

        }
        for (int i = 0; i < specials.get(index).size(); i++) {
            rectangles.get(specials.get(index).get(i)).setFill(Color.RED);
        }
        return true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}