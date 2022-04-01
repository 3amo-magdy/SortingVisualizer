package SortingAlgorithms;

import com.example.demo.Snapper;

import java.util.ArrayList;

public class QuickSort<T extends Comparable<T>> implements ISort<T> {

    @Override
    public void sort(ArrayList<T> collection) {
        quicksort(collection,0,collection.size()-1);
    }

    @Override
    public void sort(ArrayList<T> collection, Snapper<T> snapper) {
        quicksort(collection,0,collection.size()-1,snapper);
    }


    private void quicksort(ArrayList<T> collection,int low,int high){
        if(low<high){
            int p=partition(collection,low,high);
            if(p-1>low)  quicksort(collection,low,p-1);
            if(p+1<high) quicksort(collection,p+1,high);
        }
    }
    private void quicksort(ArrayList<T> collection,int low,int high,Snapper<T> snapper){
        if(low<high){
            int p=partition(collection,low,high,snapper);
            if(p-1>low)  quicksort(collection,low,p-1,snapper);
            if(p+1<high) quicksort(collection,p+1,high,snapper);
        }
    }

    //returns the index of the pivot element which is an element guarenteed to be in its position
    private int partition(ArrayList<T> collection,int low,int high){
        int left=low;
        int right=high;
        //pick a pivot
        //place elements in their position
        //put pivot in its place
        T pivot=collection.get(low);
        while(left<right){
            left++;
            while (left<=right && collection.get(left).compareTo(pivot)>=0) left++;
            while (left<=right && pivot.compareTo(collection.get(right))>0) right--;
            if(left<right) {
                swap(collection, left, right);
                right--;
            }
        }
        swap(collection,right,low);
        return right;
    }

    //returns the index of the pivot element which is an element guarenteed to be in its position
    private int partition(ArrayList<T> collection,int low,int high,Snapper<T> snapper){
        int left=low;
        int right=high;
        //pick a pivot
        //place elements in their position
        //put pivot in its place
        T pivot=collection.get(low);
        while(left<right){
            left++;
            while (left<=right && collection.get(left).compareTo(pivot)>=0) left++;
            while (left<=right && pivot.compareTo(collection.get(right))>0) right--;
            if(left<right) {
                swap(collection, left, right);
                var sp = new ArrayList<Integer>();
                sp.add(left);
                sp.add(right);
                snapper.snap(collection,sp);
                right--;
            }
        }
        swap(collection,right,low);
        var sp = new ArrayList<Integer>();
        sp.add(low);
        sp.add(right);
        snapper.snap(collection,sp);
        return right;
    }



    private void swap(ArrayList<T> arr,int first,int second){
        T temp= arr.get(first);
        arr.set(first,arr.get(second));
        arr.set(second,temp);
    }
}
