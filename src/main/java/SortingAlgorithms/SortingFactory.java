package SortingAlgorithms;

public class SortingFactory<T extends Comparable<T>> {
    public ISort getN2SortingAlgorithm(){
//        return new SelectionSort<T>();
//        return new BubbleSort<T>();
        return new InsertionSort<T>();
    }

}
