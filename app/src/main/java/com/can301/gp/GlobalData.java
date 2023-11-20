package com.can301.gp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores global data in its static fields
 * that can be accessed by all other activities.
 */
public class GlobalData {

    public static HashMap<String, Category> categories = new HashMap<String, Category>();
    public static HashMap<String, HashMap<String, Demonstration>> demos =
            new HashMap<String, HashMap<String, Demonstration>>();
    // Those that will be displayed on the main page
    public static ArrayList<Integer> highlightedCats = new ArrayList<Integer>();

    // For the sake of the selectors that only know about indices,
    // also store cats and demos in lists, so that they can be accessed in order
    public static ArrayList<Category> catList = new ArrayList<Category>();
    public static ArrayList<Demonstration> demoList = new ArrayList<Demonstration>();

    public static final int numHighlighedCats = 4;
}
