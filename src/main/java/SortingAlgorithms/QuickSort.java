package SortingAlgorithms;

import com.example.demo.Snapper;

import java.util.ArrayList;

public class QuickSort<T extends Comparable<T>> implements ISort<T> {

    @Override
    public void sort(ArrayList<T> collection) {

    }

    @Override
    public void sort(ArrayList<T> collection, Snapper<T> snapper) {

    }


    public void partition(ArrayList<T> collection,int high,int low){

    }



    private void swap(ArrayList<T> arr,int first,int second){
        T temp= arr.get(first);
        arr.set(first,arr.get(second));
        arr.set(second,temp);
    }
}
