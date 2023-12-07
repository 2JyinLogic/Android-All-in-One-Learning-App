package com.can301.gp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores global data in its static fields
 * that can be accessed by all other activities.
 */
public class GlobalData {

    // String title id -> cat
    public static HashMap<Integer, Category> categories = new HashMap<Integer, Category>();
    // String title id -> { String title id -> demo }
    public static HashMap<Integer, HashMap<Integer, Demonstration>> demos =
            new HashMap<Integer, HashMap<Integer, Demonstration>>();
    // Those that will be displayed on the main page
    // Each element is the index of the category in catList.
    public static ArrayList<Integer> highlightedCats = new ArrayList<Integer>();

    // For the sake of the selectors that only know about indices,
    // also store cats and demos in lists, so that they can be accessed in order
    public static ArrayList<Category> catList = new ArrayList<Category>();
    public static ArrayList<Demonstration> demoList = new ArrayList<Demonstration>();

    public static final int numHighlighedCats = 6;
}
