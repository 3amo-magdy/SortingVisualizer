package com.example.demo;

import java.util.ArrayList;

public class Snapper<E> {

    ArrayList<ArrayList<E>>snaps;

    public Snapper() {
        this.snaps = new ArrayList<ArrayList<E>>();
        this.specials = new ArrayList<ArrayList<Integer>>();
    }

    ArrayList<ArrayList<Integer>> specials;


    public void snap(ArrayList<E> s,ArrayList<Integer>special){
        snaps.add(s);
        specials.add(special);
    }

    public ArrayList<ArrayList<E>> getSnaps() {
        return snaps;
    }
    public ArrayList<ArrayList<Integer>> getSpecials() {
        return specials;
    }
}
