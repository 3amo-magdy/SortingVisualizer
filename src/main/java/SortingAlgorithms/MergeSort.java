package SortingAlgorithms;

import com.example.demo.Snapper;

import java.util.ArrayList;

public class MergeSort <T extends Comparable<T>> implements ISort<T>{
    @Override
    public void sort(ArrayList<T> collection) {
        mergeSort(collection,0,collection.size()-1);
    }

    @Override
    public void sort(ArrayList<T> collection, Snapper<T> snapper) {
        mergeSort(collection,0,collection.size()-1,snapper);
    }

    private void mergeSort(ArrayList<T> collection,int low,int high){
        if(low==high) return;
        int mid=(low+high)/2;

        mergeSort(collection,low,mid);
        mergeSort(collection,mid+1,high);

        merge(collection,low,mid,high);
    }


    private void mergeSort(ArrayList<T> collection,int low,int high,Snapper<T> snapper){
        if(low==high) return;
        int mid=(low+high)/2;

        mergeSort(collection,low,mid,snapper);
        mergeSort(collection,mid+1,high,snapper);

        merge(collection,low,mid,high,snapper);
    }

    private void merge(ArrayList<T> collection,int low,int mid, int high){
        ArrayList<T> arr=new ArrayList<T>();
        int rp=low, lp=mid+1;
        while (rp<=mid && lp<=high){
            int max;
            if(collection.get(rp).compareTo(collection.get(lp))>=0) {
                max=rp;
                rp++;
            }
            else {
                max=lp;
                lp++;
            }
            arr.add(collection.get(max));
        }
        while (rp<=mid){
            arr.add(collection.get(rp++));
        }
        while (lp<=high){
            arr.add(collection.get(lp++));
        }
        int k=0;
        for(int i=low;i<=high;i++){
            collection.set(i, arr.get(k++));
        }
    }
    private void merge(ArrayList<T> collection,int low,int mid, int high,Snapper<T> snapper){
        ArrayList<T> arr=new ArrayList<T>();
        int rp=low, lp=mid+1;
        while (rp<=mid && lp<=high){
            var sp = new ArrayList<Integer>();
            sp.add(rp);
            sp.add(lp);
            snapper.snap(collection,sp);
            int max;
            if(collection.get(rp).compareTo(collection.get(lp))>=0) {
                max=rp;
                rp++;
            }
            else {
                max=lp;
                lp++;
            }
            arr.add(collection.get(max));
        }
        while (rp<=mid){
            arr.add(collection.get(rp++));
        }
        while (lp<=high){
            arr.add(collection.get(lp++));
        }
        int k=0;
        for(int i=low;i<=high;i++){
            collection.set(i, arr.get(k++));
            var sp = new ArrayList<Integer>();
            sp.add(low);
            sp.add(high);
            snapper.snap(collection,sp);
        }
    }
}

