package com.can301.gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.can301.gp.catviewer.CatViewMain;
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

    // For convenience, also store cats and demos in lists
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

        // Title -> Cat
        categories = new HashMap<String, Category>();
        // CatTitle -> {DemoTitle -> Demo}
        demos = new HashMap<String, HashMap<String, Demonstration>>();

        categories.put("Cat1", new Category("Cat1","Cat1", R.drawable.ic_cat1));
        categories.put("Cat2", new Category("Cat2","Cat2", R.drawable.ic_cat2));
        categories.put("Cat3", new Category("Cat3","Cat3", R.drawable.ic_cat1));
        categories.put("Cat4", new Category("Cat4","Cat4", R.drawable.ic_cat2));

        HashMap<String, Demonstration> cat1Demos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> cat2Demos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> cat3Demos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> cat4Demos = new HashMap<String, Demonstration>();

        cat1Demos.put("Demo1", new Demonstration(
                "Demo1", "Demo1", R.drawable.ic_demo1,"TBD","1"
                )
        );
        cat1Demos.put("Demo2", new Demonstration(
                        "Demo2", "Demo2", R.drawable.ic_demo1,"TBD","2"
                )
        );
        cat2Demos.put("Demo3", new Demonstration(
                        "Demo3", "Demo3", R.drawable.ic_demo1,"TBD","3"
                )
        );
        cat2Demos.put("Demo4", new Demonstration(
                        "Demo4", "Demo4", R.drawable.ic_demo1,"TBD","4"
                )
        );
        cat3Demos.put("Demo5", new Demonstration(
                        "Demo5", "Demo5", R.drawable.ic_demo1,"TBD","5"
                )
        );
        cat3Demos.put("Demo6", new Demonstration(
                        "Demo6", "Demo6", R.drawable.ic_demo1,"TBD","6"
                )
        );
        cat4Demos.put("Demo7", new Demonstration(
                        "Demo7", "Demo7", R.drawable.ic_demo1,"TBD","7"
                )
        );
        cat4Demos.put("Demo8", new Demonstration(
                        "Demo8", "Demo8", R.drawable.ic_demo1,"TBD","8"
                )
        );

        demos.put("Cat1", cat1Demos);
        demos.put("Cat2", cat2Demos);
        demos.put("Cat3", cat3Demos);
        demos.put("Cat4", cat4Demos);

        highlightedCats = new ArrayList<>();
        highlightedCats.add(0);
        highlightedCats.add(1);
        highlightedCats.add(2);
        highlightedCats.add(3);

        catList = new ArrayList<Category>(categories.values());
        demoList = new ArrayList<Demonstration>();
        for (HashMap<String, Demonstration> h : demos.values()) {
            demoList.addAll(h.values());
        }
    }

    void goToSearchActivity() {
        Intent intent = new Intent(this, SearchBarMain.class);
        startActivity(intent);
    }

    void goToCategoriesPage() {
        Intent intent = new Intent(this, CategoriesPage.class);
        startActivity(intent);
    }

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