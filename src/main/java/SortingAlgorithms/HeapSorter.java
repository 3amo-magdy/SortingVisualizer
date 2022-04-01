package SortingAlgorithms;

import ToolsQA.Heap;
import com.example.demo.Snapper;

import java.util.ArrayList;

public class HeapSorter<T extends Comparable<T>> implements ISort<T> {

    @Override
    public void sort(ArrayList<T> collection){
        try {
            Heap.sort(collection);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sort(ArrayList<T> collection, Snapper<T> snapper) {
        try {
            Heap.sort(collection,snapper);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return;
    }
}
