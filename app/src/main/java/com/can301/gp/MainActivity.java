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
import com.can301.gp.demos.AlertDialogExample;
import com.can301.gp.demos.ButtonWidget;
import com.can301.gp.demos.DatePickerDialogExample;
import com.can301.gp.demos.AnimationsExample;
import com.can301.gp.demos.FGServiceExample;
import com.can301.gp.demos.NavigationExample;
import com.can301.gp.demos.EffectExampleNightMode;
import com.can301.gp.demos.EffectExampleRippleEffect;
import com.can301.gp.demos.RadioGroupWidget;
import com.can301.gp.demos.SwitchWidget;
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
    public void setupCatsAndDemos() {
        // Only initialise them once
        if (categories != null) {
            return;
        }

        // CatTitle -> Cat
        categories = new HashMap<String, Category>();
        // CatTitle -> {DemoTitle -> Demo}
        demos = new HashMap<String, HashMap<String, Demonstration>>();

        // Set up all categories.
        categories.put("Services", new Category(
                "Services",
                "Demonstrates how you can implement different kinds of system services," +
                        "including foreground, bound, and background services",
                R.drawable.ic_services
        ));
        categories.put("Navagation", new Category(
                "Navigation", "navigation", R.drawable.ic_cat2
        ));
        categories.put("Animations", new Category(
                "Animations", "description of Animations", R.drawable.baseline_music_note_24
        ));
        categories.put("Dialogs", new Category(
                "Dialogs",
                "Explore a variety of dialog styles and notification methods. " +
                        "Learn how to grab user attention with modals, pop-ups, toasts, " +
                        "and more for an engaging user experience.",
                R.drawable.ic_cat1
        ));
        categories.put("Effects", new Category(
                "Effects",
                "Shows various effects.",
                R.drawable.ic_cat_effect
        ));
        categories.put("Widgets", new Category(
                "Widgets",
                "Shows various widgets.",
                R.drawable.ic_cat_effect
        ));

        HashMap<String, Demonstration> catServicesDemos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> catDialogsDemos = new HashMap<>();
        HashMap<String, Demonstration> catNavigationDemos = new HashMap<>();
        HashMap<String, Demonstration> catAnimationsDemos = new HashMap<>();
        HashMap<String, Demonstration> catEffectsDemos = new HashMap<>();
        HashMap<String, Demonstration> catWidgetsDemos = new HashMap<>();

        // Set up all demos.
        catEffectsDemos.put("Night Mode", new Demonstration(
                        "Night Mode", "Light and night mode",
                        R.drawable.ic_demo1, EffectExampleNightMode.class,"nightmode"
                )
        );
        catEffectsDemos.put("Ripple Effect", new Demonstration(
                        "Ripple Effect", "Ripple effect",
                        R.drawable.ic_demo1, EffectExampleRippleEffect.class,"rippleeffect"
                )
        );
        catServicesDemos.put("Background Service", new Demonstration(
                "Background Service",
                getString(R.string.bg_service_example_desc),
                R.drawable.ic_bg_service, BGServiceExample.class, "bgservice"
        ));
        catServicesDemos.put("Bound Service", new Demonstration(
                "Bound Service",
                getString(R.string.bound_service_example_desc),
                R.drawable.ic_bound_service, BoundServiceExample.class, "boundservice"
        ));
        catServicesDemos.put("Foreground Service", new Demonstration(
                "Foreground Service",
                getString(R.string.fg_service_example_desc),
                R.drawable.ic_fg_service, FGServiceExample.class, "fgservice"
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
        catNavigationDemos.put("navigation", new Demonstration(
                "navigation", "navigation description", R.drawable.ic_demo1, NavigationExample.class, "navigation1"
        ));

        catAnimationsDemos.put("Animations",new Demonstration(
                "Animations","description of Animations",R.drawable.baseline_music_note_24, AnimationsExample.class,"animation1"
        ));
        catWidgetsDemos.put("Button", new Demonstration(
                "Button","button",R.drawable.ic_demo1, ButtonWidget.class, "button1"
        ));
        catWidgetsDemos.put("Switch", new Demonstration(
                "Switch","switch",R.drawable.ic_demo1, SwitchWidget.class, "switch1"
        ));
        catWidgetsDemos.put("Radio Group", new Demonstration(
                "Radio Group","radio group",R.drawable.ic_demo1, RadioGroupWidget.class, "radio1"
        ));

        // Decide which demos go into which category.
        demos.put("Services", catServicesDemos);
        demos.put("Dialogs", catDialogsDemos);
        demos.put("Navigation", catNavigationDemos);
        demos.put("Animations",catAnimationsDemos);
        demos.put("Effects",catEffectsDemos);
        demos.put("Widgets",catWidgetsDemos);

        // Decided by ourselves
        highlightedCats = new ArrayList<>();
        // highlight services
        highlightedCats.add(0);
        // highlight dialogs
        highlightedCats.add(1);
        // highlight navigation
        highlightedCats.add(2);
        // highlight animation
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