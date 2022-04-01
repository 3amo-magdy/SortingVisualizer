package SortingAlgorithms;

import com.example.demo.Snapper;

import java.util.ArrayList;

public class BubbleSort<T extends Comparable<T>> implements ISort<T>{
    public void sort(ArrayList<T> collection){
        for(int i=0;i<collection.size();i++){
            for(int j=0;j<collection.size()-i-1;j++){
                if(collection.get(j).compareTo(collection.get(j+1))<0){
                    swap(collection,j,j+1);
                }
            }
        }
    }
    public void sort(ArrayList<T> collection, Snapper<T> snapper){
        for(int i=0;i<collection.size();i++){
            for(int j=0;j<collection.size()-i-1;j++){
                if(collection.get(j).compareTo(collection.get(j+1))<0){
                    swap(collection,j,j+1);
                    var sp = new ArrayList<Integer>();
                    sp.add(i);
                    sp.add(j);
                    snapper.snap((ArrayList<T>) collection.clone(),sp);
                }
            }
        }
    }
    private void swap(ArrayList<T> arr,int first,int second){
        T temp= arr.get(first);
        arr.set(first,arr.get(second));
        arr.set(second,temp);
    }
}