package com.can301.gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.can301.gp.catviewer.CatViewMain;
import com.can301.gp.demos.BGServiceExample;
import com.can301.gp.demos.BoundServiceExample;
import com.can301.gp.demos.EffectExample1;
import com.can301.gp.demos.EffectExample2;
import com.can301.gp.demos.EffectExample3;
import com.can301.gp.demos.EffectExample4;
import com.can301.gp.demos.EffectExample5;
import com.can301.gp.demos.AlertDialogExample;
import com.can301.gp.demos.DatePickerDialogExample;
import com.can301.gp.searchbar.SearchBarMain;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Drawable drawableFromId(int Id) {
        return this.getResources().getDrawable(Id);
    }

    public static HashMap<String, Category> categories = null;
    public static HashMap<String, HashMap<String, Demonstration>> demos = null;
    // Those that will be displayed on the main page
    public static ArrayList<Integer> highlightedCats = null;

    // For the sake of the selectors that only know about indices,
    // also store cats and demos in lists, so that they can be accessed in order
    public static ArrayList<Category> catList = null;
    public static ArrayList<Demonstration> demoList = null;

    public static final int numHighlighedCats = 4;

    /**
     * What we will need to do by hand,
     */
    public static void setupCatsAndDemos() {
        // Only initialise them once
        if(categories != null) {
            return;
        }

        // CatTitle -> Cat
        categories = new HashMap<String, Category>();
        // CatTitle -> {DemoTitle -> Demo}
        demos = new HashMap<String, HashMap<String, Demonstration>>();

        // Set up all categories.
        categories.put("Cat1", new Category("Cat1","Cat1", R.drawable.ic_cat1));
        categories.put("Cat2", new Category("Cat2","Cat2", R.drawable.ic_cat2));
        categories.put("Cat3", new Category("Cat3","Cat3", R.drawable.ic_cat1));
        categories.put("Cat4", new Category("Cat4","Cat4", R.drawable.ic_cat2));
        categories.put("Cat5", new Category("Cat5", "Cat5 desc", R.drawable.ic_cat1));
        categories.put("Services", new Category(
                "Services",
                "Foreground and background services",
                R.drawable.ic_services
            ));

        categories.put("Dialogs", new Category(
                "Dialogs",
                "Explore a variety of dialog styles and notification methods. " +
                        "Learn how to grab user attention with modals, pop-ups, toasts, " +
                        "and more for an engaging user experience.",
                R.drawable.ic_cat1
        ));
        HashMap<String, Demonstration> cat1Demos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> cat2Demos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> cat3Demos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> cat4Demos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> cat5Demos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> catServicesDemos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> catDialogsDemos = new HashMap<>();
        
        // Set up all demos.
        cat1Demos.put("Demo1", new Demonstration(
                "Demo1", "Demo1",
                R.drawable.ic_demo1, EffectExample1.class,"1"
                )
        );
        cat1Demos.put("Demo2", new Demonstration(
                "Demo2", "Demo2",
                R.drawable.ic_demo1, EffectExample2.class,"2"
                )
        );
        cat2Demos.put("Demo3", new Demonstration(
                "Demo3", "Demo3",
                R.drawable.ic_demo1, EffectExample3.class,"3"
                )
        );
        cat2Demos.put("Demo4", new Demonstration(
                "Demo4", "Demo4",
                R.drawable.ic_demo1, EffectExample4.class,"4"
                )
        );
        cat3Demos.put("Demo5", new Demonstration(
                "Demo5", "Demo5",
                R.drawable.ic_demo1, EffectExample1.class,"5"
                )
        );
        cat3Demos.put("Demo6", new Demonstration(
                "Demo6", "Demo6",
                R.drawable.ic_demo1, EffectExample2.class,"6"
                )
        );
        cat4Demos.put("Demo7", new Demonstration(
                "Demo7", "Demo7",
                R.drawable.ic_demo1, EffectExample3.class,"7"
                )
        );
        cat4Demos.put("Demo8", new Demonstration(
                "Demo8", "Demo8",
                R.drawable.ic_demo1, EffectExample4.class,"8"
                )
        );
        cat5Demos.put("Demo9", new Demonstration(
                        "Demo9", "Demo9 desc",
                        R.drawable.ic_demo1, EffectExample5.class,"9"
                )
        );
        catServicesDemos.put("Background Services", new Demonstration(
                "Background Services",
                "A background service performs an operation that isn\\'t directly noticed by the user.\n" +
                "        In this example, a BG service will be created to repeatedly increase the value of an integer by 1 and sleep for a few seconds.\n" +
                "        Note how running the service in the UI thread will freeze the UI and eventually leads to the app being killed.",
                R.drawable.ic_bg_service, BGServiceExample.class,"bgservice"
        ));
        catServicesDemos.put("Bound Services", new Demonstration(
                "Bound Services",
                "A bound service is an implementation of the Service class that lets other applications bind to it and interact with it.\n" +
                        "To provide binding for a service, you implement the onBind() callback method.\n" +
                        "This method returns an IBinder object that defines the programming interface that clients can use to interact with the service.\n" +
                        "In this example, a bound and started service will be created to play music in the background.\n" +
                        "You can bind to the service to control the music play, but even after you unbind, the music is still being played,\n" +
                        "unless you stop the service.",
                R.drawable.ic_bound_service, BoundServiceExample.class,"boundservice"
        ));

        catDialogsDemos.put("AlertDialog", new Demonstration(
                "AlertDialog",
                "Demonstration entry for an AlertDialog example. " +
                        "This dialog showcases user interaction and informs about " +
                        "important events. Represented by an icon (R.drawable.ic_demo1) " +
                        "and uses AlertDialogExample.class",
                R.drawable.ic_demo1, AlertDialogExample.class,"alertdialog"
        ));
        catDialogsDemos.put("DatePickerDialog", new Demonstration(
                        "DatePickerDialog", "Select a date from a calendar view.",
                        R.drawable.ic_demo1, DatePickerDialogExample.class,"datepickerdialog"
                )
        );
        // Decide which demos go into which category.
        demos.put("Cat1", cat1Demos);
        demos.put("Cat2", cat2Demos);
        demos.put("Cat3", cat3Demos);
        demos.put("Cat4", cat4Demos);
        demos.put("Cat5", cat5Demos);
        demos.put("Services", catServicesDemos);
        demos.put("Dialogs", catDialogsDemos);

        // Decided by ourselves
        highlightedCats = new ArrayList<>();
        // highlight services
        highlightedCats.add(5);
        // highlight dialogs
        highlightedCats.add(6);
        highlightedCats.add(2);
        highlightedCats.add(3);

        // Fill in the lists
        catList = new ArrayList<Category>(categories.values());
        demoList = new ArrayList<Demonstration>();
        for (HashMap<String, Demonstration> h : demos.values()) {
            demoList.addAll(h.values());
        }
    }

    /**
     * Called when the user clicks the search bar on the main activity.
     */
    void goToSearchActivity() {
        Intent intent = new Intent(this, SearchBarMain.class);
        startActivity(intent);
    }

    /**
     * Called when the user clicks the menu on the main activity.
     */
    void goToCategoriesPage() {
        Intent intent = new Intent(this, CategoriesPage.class);
        startActivity(intent);
    }

    /**
     * Called when the user clicks any of the highlighted categories on the main activity.
     */
    void goToCatViewPage(int catIndex) {
        Intent intent = new Intent(this, CatViewMain.class);
        intent.putExtra(CatViewMain.CAT_VIEW_INDEX_KEY, catIndex);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupCatsAndDemos();

        setContentView(R.layout.activity_main);

        // Set up the cat buttons
        {
            assert highlightedCats.size() >= numHighlighedCats;
            Category cat1 = catList.get(highlightedCats.get(0));
            Category cat2 = catList.get(highlightedCats.get(1));
            Category cat3 = catList.get(highlightedCats.get(2));
            Category cat4 = catList.get(highlightedCats.get(3));

            Button bt1 = findViewById(R.id.mainCatButton1);
            Button bt2 = findViewById(R.id.mainCatButton2);
            Button bt3 = findViewById(R.id.mainCatButton3);
            Button bt4 = findViewById(R.id.mainCatButton4);

            // Button icons (on top)
            bt1.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat1.iconId), null, null);
            bt2.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat2.iconId), null, null);
            bt3.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat3.iconId), null, null);
            bt4.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat4.iconId), null, null);

            // Button texts
            bt1.setText(cat1.title);
            bt2.setText(cat2.title);
            bt3.setText(cat3.title);
            bt4.setText(cat4.title);

            // Redirections
            bt1.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(0)));
            bt2.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(1)));
            bt3.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(2)));
            bt4.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(3)));
        }

        // Search button
        {
            ImageButton sb = findViewById(R.id.searchButton);
            sb.setOnClickListener(v -> goToSearchActivity());
        }

        // Menu button
        {
            Button mb = findViewById(R.id.menuButton);
            mb.setOnClickListener(v -> goToCategoriesPage());
        }
    }
}