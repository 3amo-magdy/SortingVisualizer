package com.example.demo;

import SortingAlgorithms.HeapSorter;
import SortingAlgorithms.ISort;
import SortingAlgorithms.SortingFactory;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Analyzer {
    public static void main(String[] args){
        ArrayGenerator generator= new ArrayGenerator();
        File heapf= new File("heap.txt");
        File n2f= new File("n2.txt");
        File nlgnf= new File("nlgn.txt");

        try {
            if (heapf.createNewFile() && n2f.createNewFile() && nlgnf.createNewFile()) {
                System.out.println("Files created: ");
            } else {
                System.out.println("Files already exists.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        SortingFactory<Integer> factory = new SortingFactory<>();
        ISort<Integer> heapSorter = new HeapSorter<>();
        ISort<Integer> n2Sorter = factory.getN2SortingAlgorithm();
        ISort<Integer> nlgnSorter = factory.getNlgNSortingAlgorithm();


        for(int i = 10 ; i <= 2000; i+=10){
               ArrayList<Integer> arr = generator.generate(i,0,5000);
               Object arrCopy1=arr.clone();
               Object arrCopy2=arr.clone();
               Object arrCopy3=arr.clone();

               Long start1=System.nanoTime();
               heapSorter.sort((ArrayList<Integer>) arrCopy1);
               Long end1 =System.nanoTime();
               appendToFile(heapf,end1-start1);

               Long start2=System.nanoTime();
               n2Sorter.sort((ArrayList<Integer>) arrCopy2);
               Long end2 =System.nanoTime();
               appendToFile(n2f,end2-start2);


               Long start3=System.nanoTime();
               nlgnSorter.sort((ArrayList<Integer>) arrCopy3);
               Long end3 =System.nanoTime();
               appendToFile(nlgnf,end3-start3);
        }
    }




    private static void appendToFile(File file, Long num){
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            br.write(String.valueOf(num));
            br.write("\n");
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}



