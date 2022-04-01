package ToolsQA;
import com.example.demo.Snapper;

import java.util.*;

public interface IHeap<T> {
    ArrayList<T> getArray();
    int getHeapSize();
    int getSize();
    void insert(T e);
    IHeap<T> sort();
    IHeap<T> sort(Snapper s);

    ArrayList<T> getExtracted();
}
