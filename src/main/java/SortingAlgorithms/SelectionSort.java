package SortingAlgorithms;

import com.example.demo.Snapper;

import java.util.ArrayList;

public class SelectionSort<T extends Comparable<T>>  implements ISort<T>{
    public void sort(ArrayList<T> collection){
        for(int i=0;i<collection.size();i++){
            int max=i;
            for(int j=i;j<collection.size();j++){
                if(collection.get(j).compareTo(collection.get(max))>0){
                    max=j;
                }
            }
            swap(collection,i,max);
        }
    }
    public void sort(ArrayList<T> collection, Snapper<T> snapper){
        for(int i=0;i<collection.size();i++){
            int max=i;
            for(int j=i;j<collection.size();j++){
                var sp = new ArrayList<Integer>();
                sp.add(i);
                sp.add(j);
                snapper.snap((ArrayList<T>) collection.clone(),sp);
                if(collection.get(j).compareTo(collection.get(max))>0){
                    max=j;
                }
            }
            var sp = new ArrayList<Integer>();
            sp.add(i);
            sp.add(max);
            sp.add(max);
            snapper.snap((ArrayList<T>) collection.clone(),sp);
            swap(collection,i,max);
        }
    }


    private void swap(ArrayList<T> arr,int first,int second){
        T temp= arr.get(first);
        arr.set(first,arr.get(second));
        arr.set(second,temp);
    }

}
