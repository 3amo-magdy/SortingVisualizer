package com.example.demo;

import java.util.ArrayList;

public class ArrayGenerator {
    public ArrayList<Integer> generate(int N,int MIN,int MAX){
        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (int i = 0; i < N; i++) {
            int x = ((int)(Math.random()*(MAX-MIN-2)+MIN+1));
            if(Math.random()<0.5){
                x*=-1;
            }
            arr.add(x);
        }
        return arr;
    }
}
