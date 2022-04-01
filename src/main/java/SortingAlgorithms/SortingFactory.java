package SortingAlgorithms;

public class SortingFactory<T extends Comparable<T>> {
    public ISort<T> getN2SortingAlgorithm(){
//        return new SelectionSort<T>();
        return new BubbleSort<T>();
//        return new InsertionSort<T>();
    }

    public ISort<T> getNlgNSortingAlgorithm(){
//        return new QuickSort<T>();
        return new MergeSort<T>();
    }

}
