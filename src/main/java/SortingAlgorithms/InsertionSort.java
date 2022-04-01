package SortingAlgorithms;

import com.example.demo.Snapper;

import java.util.ArrayList;

public class InsertionSort<T extends Comparable<T>> implements ISort<T> {
    public void sort(ArrayList<T> collection) {
        for(int i=1;i<collection.size();i++){
            int j=i;
            T temp=collection.get(j);
            while (j>0 && collection.get(j-1).compareTo(temp)<0 ){
                collection.set(j,collection.get(j-1));
                j--;
            }
            collection.set(j,temp);
        }
    }
    public void sort(ArrayList<T> collection, Snapper<T> snapper) {
        for(int i=1;i<collection.size();i++){
            int j=i;
            T temp=collection.get(j);
            while (j>0 && collection.get(j-1).compareTo(temp)<0 ){
                collection.set(j,collection.get(j-1));
                j--;
                var sp = new ArrayList<Integer>();
                sp.add(i);
                sp.add(j);
                snapper.snap((ArrayList<T>) collection.clone(),sp);
            }
            collection.set(j,temp);
            var sp = new ArrayList<Integer>();
            sp.add(i);
            sp.add(j);
            snapper.snap((ArrayList<T>) collection.clone(),sp);

        }
    }
}
