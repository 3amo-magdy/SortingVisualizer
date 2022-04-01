package ToolsQA;
import com.example.demo.Snapper;

import java.util.*;

public class Heap <T extends Comparable<T>> {
    IHeap<T> heap;
    boolean sorted;
    public Heap(){
        sorted=false;
        this.heap = new MaxHeap<T>();
    }
    public Heap(ArrayList<T> unOrdered){
        this();
        this.heap=new MaxHeap<T>(unOrdered);
    }
    public Heap(ArrayList<T> Ordered,boolean ascending){
        sorted = true;
        if(ascending){
            this.heap = new MinHeap<T>(Ordered);
        }
        else{
            this.heap = new MaxHeap<T>(Ordered);
        }
    }
    public void insert(T e){
        this.heap.insert(e);
        sorted = false;
    }
    public void sort(){
        this.heap=this.heap.sort();
        sorted = true;
    }
    public ArrayList<T> getArray(){
        return this.heap.getArray();
    }

    public static void sort(ArrayList arr) throws ClassNotFoundException{
        if(arr==null||arr.size()==0){
            return;
        }
        boolean flag = false;
        for(int i=0;i<arr.get(0).getClass().getInterfaces().length;i++){
            if(arr.get(0).getClass().getInterfaces()[i].equals(Comparable.class)){
                flag = true;
            }
        }
        if(!flag){
            throw new RuntimeException("Array Elements don't implement Comparable");
        }
        
        IHeap myHeap = new MinHeap(arr);
        myHeap.sort();
        return;
    }
    public static void sort(ArrayList arr,Snapper snapper) throws ClassNotFoundException{
        if(arr==null||arr.size()==0){
            return;
        }
        boolean flag = false;
        for(int i=0;i<arr.get(0).getClass().getInterfaces().length;i++){
            if(arr.get(0).getClass().getInterfaces()[i].equals(Comparable.class)){
                flag = true;
            }
        }
        if(!flag){
            throw new RuntimeException("Array Elements don't implement Comparable");
        }

        IHeap myHeap = new MaxHeap(arr);
        myHeap.sort(snapper);
        return ;
    }
    public void printLevels(){
        int i=0;
        int exp=0;
        int total=0;
        while(i<heap.getHeapSize()){
            System.out.print(heap.getArray().get(i)+", ");
            if((i)==total){
                System.out.print("\n");
                exp++;
                total+=Math.pow(2,exp);
            }
            i++;
        }
        System.out.print("\n");
    }
    public void printArray(){
        System.out.println(Arrays.toString(heap.getArray().toArray()));
    }
    public void printHeap(){
        System.out.println(Arrays.toString(heap.getArray().subList(0, heap.getHeapSize()).toArray()));
    }
    public void printExternals(){
        System.out.println(Arrays.toString(heap.getExtracted().toArray()));
    }
}
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------Min Heap----------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
class MinHeap<T extends Comparable<T>> implements IHeap<T> {
    ArrayList<T> HeapArray;
    int HeapSize;
    int Size;
    boolean Heapified;
    public MinHeap(){
        this.HeapArray = new ArrayList<T>();
        this.HeapSize = 0;
        this.Size = 0;
        Heapified=true;
    }
    public MinHeap(ArrayList<T> unOrdered){
        this.HeapArray = new ArrayList<T>(unOrdered);
        this.HeapSize = HeapArray.size();
        this.Size = HeapSize;
        this.Heapify();
    }
    private void swap(int x, int y){
        T temp = HeapArray.get(x);
        HeapArray.set(x,HeapArray.get(y));
        HeapArray.set(y, temp);
    }

    public void insert(T e){
        HeapArray.add(HeapSize,e);
        HeapSize ++;
        Size ++;
        int i = HeapSize-1;
        T parent,current;
        for (; i > 0 ; i--) {
            parent=HeapArray.get(i/2);
            current=HeapArray.get(i);
            if(parent.compareTo(current)>0){
                swap(i,i/2);
            }
        }
    }
    private T extractRoot(){
        swap(0, HeapSize-1);
        HeapSize--;
        Heapify(0);
        return HeapArray.get(HeapSize);
    }
    private T extractRoot(Snapper<T>snapper){
        swap(0, HeapSize-1);
        var sp = new ArrayList<Integer>();
        sp.add(0);
        sp.add(HeapSize-1);
        snapper.snap((ArrayList<T>) HeapArray.clone(),sp);
        HeapSize--;
        Heapify(0,snapper);
        return HeapArray.get(HeapSize);
    }
    public ArrayList<T> getExtracted(){
        return new ArrayList<T>(HeapArray.subList(HeapSize, Size));
    }
    private void Heapify(int i){
        if(2*i>=HeapSize){
            return;
        }
        T parent=HeapArray.get(i);
        T left=HeapArray.get(i*2);
        T LRMin = left;
        int swappedChild = i*2;
        if((2*i+1)<HeapSize){
            T right=HeapArray.get(i*2+1);
            if(left.compareTo(right)>0){
                LRMin= right;
                swappedChild = i*2+1;
            }
        }
        if(parent.compareTo(LRMin)>0){
            swap(i,swappedChild);
            Heapify(swappedChild);
        }
    }
    private void Heapify(int i,Snapper<T> snapper){
        if(2*i>=HeapSize){
            return;
        }
        T parent=HeapArray.get(i);
        T left=HeapArray.get(i*2);
        T LRMin = left;
        int swappedChild = i*2;
        if((2*i+1)<HeapSize){
            T right=HeapArray.get(i*2+1);
            if(left.compareTo(right)>0){
                LRMin= right;
                swappedChild = i*2+1;
            }
        }
        var sp = new ArrayList<Integer>();
        sp.add(i);
        sp.add(swappedChild);
        snapper.snap((ArrayList<T>) HeapArray.clone(),sp);
        if(parent.compareTo(LRMin)>0){
            swap(i,swappedChild);
            snapper.snap((ArrayList<T>) HeapArray.clone(),sp);
            Heapify(swappedChild,snapper);
            Heapify(swappedChild,snapper);
        }
    }
    private void Heapify(){
        int current = (Size-2)/2;
        while(current>-1){
            Heapify(current);
            current--;
        }
        Heapified = true;
    }
    private void Heapify(Snapper<T> snapper){
        int current = (Size-2)/2;
        while(current>-1){
            Heapify(current,snapper);
            current--;
        }
        Heapified = true;
    }
    public MaxHeap<T> sort(){
        if(!Heapified){
            Heapify();
        }
        while(HeapSize>0){
            extractRoot();
        }
        MaxHeap<T> returned = new MaxHeap<T>();
        returned.setArray(this.HeapArray,true);
        return returned;
    }
    @Override
    public MaxHeap<T> sort(Snapper snapper){
        if(!Heapified){
            Heapify(snapper);
        }
        while(HeapSize>0){
            extractRoot(snapper);
        }
        MaxHeap<T> returned = new MaxHeap<T>();
        returned.setArray(this.HeapArray,true);
        return returned;
    }
    public void setArray(ArrayList<T> arr,boolean ordered){
        this.HeapArray=arr;
        this.HeapSize=arr.size();
        this.Size=this.HeapSize;
        this.Heapified=ordered;
    }
    public ArrayList<T> getArray(){
        return this.HeapArray;
    }
    public int getHeapSize(){
        return this.HeapSize;
    }
    public int getSize(){
        return this.Size;
    }
}
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------Max Heap----------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------
class MaxHeap<T extends Comparable<T>> implements IHeap<T> {
    ArrayList<T> HeapArray;
    int HeapSize;
    int Size;
    boolean Heapified;
    public MaxHeap(){
        this.HeapArray = new ArrayList<T>();
        this.HeapSize = 0;
        this.Size = 0;
        Heapified=true;
    }
    public MaxHeap(ArrayList<T> unOrdered){
        this.HeapArray = unOrdered;
        this.HeapSize = HeapArray.size();
        this.Size = HeapSize;
        this.Heapify();
    }
    private void swap(int x, int y){
        T temp = HeapArray.get(x);
        HeapArray.set(x,HeapArray.get(y));
        HeapArray.set(y, temp);
    }

    public void insert(T e){
        HeapArray.add(HeapSize,e);
        HeapSize ++;
        Size ++;
        int i = HeapSize-1;
        T parent,current;
        for (; i > 0 ; i--) {
            parent=HeapArray.get(i/2);
            current=HeapArray.get(i);
            if(parent.compareTo(current)<0){
                swap(i,i/2);
            }
        }
    }
    private T extractRoot(){
        swap(0, HeapSize-1);
        HeapSize--;
        this.Heapify(0);
        return HeapArray.get(HeapSize);
    }
    private T extractRoot(Snapper<T> snapper){
        swap(0, HeapSize-1);
        var sp = new ArrayList<Integer>();
        sp.add(0);
        sp.add(HeapSize-1);
        snapper.snap((ArrayList<T>) HeapArray.clone(),sp);
        HeapSize--;
        this.Heapify(0,snapper);
        return HeapArray.get(HeapSize);
    }
    public ArrayList<T> getExtracted(){
        return new ArrayList<T>(HeapArray.subList(HeapSize, Size));
    }
    private void Heapify(int i){
        if(2*i>=HeapSize){
            return;
        }
        T parent=HeapArray.get(i);
        T left=HeapArray.get(i*2);
        T LRMax = left;
        int swappedChild = i*2;
        if((2*i+1)<HeapSize){
            T right=HeapArray.get(i*2+1);
            if(left.compareTo(right)<0){
                LRMax = right;
                swappedChild = i*2+1;
            }
        }
        if(parent.compareTo(LRMax)<0){
            swap(i,swappedChild);
            Heapify(swappedChild);
        }
    }
    private void Heapify(int i,Snapper<T> snapper){
        if(2*i>=HeapSize){
            return;
        }

        T parent=HeapArray.get(i);
        T left=HeapArray.get(i*2);
        T LRMax = left;
        int swappedChild = i*2;
        if((2*i+1)<HeapSize){
            T right=HeapArray.get(i*2+1);
            if(left.compareTo(right)<0){
                LRMax = right;
                swappedChild = i*2+1;
            }
        }
        var sp = new ArrayList<Integer>();
        sp.add(i);
        sp.add(swappedChild);
        snapper.snap((ArrayList<T>) HeapArray.clone(),sp);
        if(parent.compareTo(LRMax)<0){
            swap(i,swappedChild);
            snapper.snap((ArrayList<T>) HeapArray.clone(),sp);
            Heapify(swappedChild,snapper);
        }
    }
    private void Heapify(){
        int current = (Size-2)/2;
        while(current>-1){
            Heapify(current);
            current--;
        }
        Heapified = true;
    }
    private void Heapify(Snapper<T> snapper){
        int current = (Size-2)/2;
        while(current>-1){
            Heapify(current,snapper);
            current--;
        }
        Heapified = true;
    }
    public MinHeap<T> sort(){
        if(!Heapified){
            Heapify();
        }
        while(HeapSize>0){
            extractRoot();
        }
        MinHeap<T> returned = new MinHeap<T>();
        returned.setArray(this.HeapArray,true);
        return returned;
    }
    @Override
    public MinHeap<T> sort(Snapper snapper){
        if(!Heapified){
            Heapify(snapper);
        }
        while(HeapSize>0){
            extractRoot(snapper);
        }
        MinHeap<T> returned = new MinHeap<T>();
        returned.setArray(this.HeapArray,true);
        return returned;
    }
    public void setArray(ArrayList<T> arr,boolean ordered){
        this.HeapArray=arr;
        this.HeapSize=arr.size();
        this.Size=this.HeapSize;
        this.Heapified=ordered;
    }
    public ArrayList<T> getArray(){
        return this.HeapArray;
    }
    public int getHeapSize(){
        return this.HeapSize;
    }
    public int getSize(){
        return this.Size;
    }

}


