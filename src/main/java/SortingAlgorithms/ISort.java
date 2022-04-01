//package com.example.demo;


package SortingAlgorithms;

import com.example.demo.Snapper;

import java.util.ArrayList;

public interface ISort<T extends Comparable<T>> {
    public void sort(ArrayList<T> collection);
    public void sort(ArrayList<T> collection, Snapper<T> snapper);

}
