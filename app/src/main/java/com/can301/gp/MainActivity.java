package com.can301.gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.can301.gp.demos.EffectExampleLoadingEffect;
import com.can301.gp.demos.EffectExampleProgressBarLoadingEffect;
import com.can301.gp.demos.FGServiceExample;

import com.can301.gp.demos.FrameAnimationsExample;
import com.can301.gp.demos.AttributeAnimationsExample;
import com.can301.gp.demos.GoSystemSettingsExample;
import com.can301.gp.demos.InteractCalendarExample;
import com.can301.gp.demos.NavigationExample;
import com.can301.gp.demos.EffectExampleNightMode;
import com.can301.gp.demos.EffectExampleRippleEffect;
import com.can301.gp.demos.ProgressBarWidget;
import com.can301.gp.demos.RadioGroupWidget;
import com.can301.gp.demos.RequestPermissionExample;
import com.can301.gp.demos.SwitchWidget;
import com.can301.gp.searchbar.SearchBarMain;
import com.can301.gp.demos.PopupWindowExample;
import com.can301.gp.demos.BottomSheetDialogExample;
import com.can301.gp.demos.ProgressDialogExample;
import com.can301.gp.demos.TimePickerDialogExample;

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
     * Used for the Home button of other pages.
     * Click that button to go back to this activity.
     * @param context the context of that page.
     */
    static public void goBackToHomePage(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

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
                R.drawable.ic_cat_services
        ));
        categories.put("System", new Category(
                "System",
                "Demonstrates how you can interact with the Android system. " +
                        "For example, asking for permissions",
                R.drawable.ic_cat_system
        ));
        categories.put("Navagation", new Category(
                "Navigation", "navigation", R.drawable.ic_cat_navigation
        ));
        categories.put("Animations", new Category(
                "Animations", "description of Animations", R.drawable.baseline_image_24
        ));
        categories.put("Dialogs", new Category(
                "Dialogs",
                "Explore a variety of dialog styles and notification methods. " +
                        "Learn how to grab user attention with modals, pop-ups, toasts, " +
                        "and more for an engaging user experience.",
                R.drawable.baseline_chat_24
        ));
        categories.put("Effects", new Category(
                "Effects",
                "Shows various effects.",
                R.drawable.ic_cat_effect
        ));
        categories.put("Widgets", new Category(
                "Widgets",
                "Shows various widgets.",
                R.drawable.ic_widgets
        ));

        HashMap<String, Demonstration> catServicesDemos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> catSystemDemos = new HashMap<String, Demonstration>();
        HashMap<String, Demonstration> catDialogsDemos = new HashMap<>();
        HashMap<String, Demonstration> catNavigationDemos = new HashMap<>();
        HashMap<String, Demonstration> catAnimationsDemos = new HashMap<>();
        HashMap<String, Demonstration> catEffectsDemos = new HashMap<>();
        HashMap<String, Demonstration> catWidgetsDemos = new HashMap<>();

        // Set up all demos.
        catEffectsDemos.put("Night Mode", new Demonstration(
                        "Night Mode", getString(R.string.night_mode_example_desc),
                R.drawable.baseline_home_24, EffectExampleNightMode.class,"nightmode"
                )
        );
        catEffectsDemos.put("Ripple Effect", new Demonstration(
                        "Ripple Effect", getString(R.string.ripple_effect_example_desc),
                android.R.drawable.ic_menu_mylocation, EffectExampleRippleEffect.class,"rippleeffect"
                )
        );
        catEffectsDemos.put("Shimmer Loading Effect", new Demonstration(
                        "Shimmer Loading Effect", getString(R.string.loading_effect_example_desc),
                R.drawable.baseline_image_24, EffectExampleLoadingEffect.class,"loadingeffect"
                )
        );
        catEffectsDemos.put("Progress Bar Loading Effect", new Demonstration(
                        "Progress Bar Loading Effect", getString(R.string.progress_bar_effect_example_desc),
                R.drawable.ic_progress_bar, EffectExampleProgressBarLoadingEffect.class,"progressbareffect"
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

        catSystemDemos.put("Request Permissions", new Demonstration(
                "Request Permissions",
                getString(R.string.request_permission_example_desc),
                R.drawable.ic_request_permission, RequestPermissionExample.class, "reqpermission"
        ));
        catSystemDemos.put("Calendar Interaction", new Demonstration(
                "Calendar Interaction",
                getString(R.string.interactcalendar_example_desc),
                R.drawable.baseline_edit_calendar_24, InteractCalendarExample.class, "interactcalendar"
        ));
        catSystemDemos.put("Go To System Settings", new Demonstration(
                "Go To System Settings",
                getString(R.string.go_system_settings_example_desc),
                R.drawable.ic_demo_settings, GoSystemSettingsExample.class, "systemsettings"
        ));

        catDialogsDemos.put("AlertDialog", new Demonstration(
                "AlertDialog",
                "Demonstration entry for an AlertDialog example. " +
                        "This dialog showcases user interaction and informs about " +
                        "important events. Represented by an icon (R.drawable.ic_demo1) " +
                        "and uses AlertDialogExample.class",
                R.drawable.baseline_chat_24, AlertDialogExample.class,"alertdialog"
        ));
        catDialogsDemos.put("DatePickerDialog", new Demonstration(
                        "DatePickerDialog", "Select a date from a calendar view.",
                        R.drawable.baseline_chat_24, DatePickerDialogExample.class,"datepickerdialog"
                )
        );
        catNavigationDemos.put("navigation", new Demonstration(
                "navigation", "Show the effect of bottom navigation bar and switching", R.drawable.ic_cat_navigation, NavigationExample.class, "navigation1"
        ));

        catAnimationsDemos.put("Animations",new Demonstration(
                "Animations","show the alpha, rotate, scale and translate of images",R.drawable.baseline_image_24, AnimationsExample.class,"animation1"
        ));
        catAnimationsDemos.put("FrameAnimations",new Demonstration(
                "FrameAnimations","Show how to create animation through frames-by-frames",R.drawable.baseline_animation_24, FrameAnimationsExample.class,"frameanimation"
        ));
        catAnimationsDemos.put("AttributeAnimations",new Demonstration(
                "AttributeAnimations","Show how to create animation through using different attributes",R.drawable.baseline_keyboard_double_arrow_right_24, AttributeAnimationsExample.class,"attributeanimation"
        ));

        catDialogsDemos.put("PopupWindow", new Demonstration(
                        "PopupWindow",
                        getString(R.string.popupwindow_example_desc),
                        R.drawable.baseline_chat_24, PopupWindowExample.class,"popupwindow"
                )
        );
        catDialogsDemos.put("BottomSheetDialog", new Demonstration(
                        "BottomSheetDialog",
                getString(R.string.bottomsheetdialog_example_desc),
                        R.drawable.baseline_chat_24, BottomSheetDialogExample.class,"bottomsheetdialog"
                )
        );
        catDialogsDemos.put("TimePickerDialog", new Demonstration(
                        "TimePickerDialog",
                        getString(R.string.timepickerdialog_example_desc),
                        R.drawable.baseline_chat_24, TimePickerDialogExample.class,"timepickerdialog"
                )
        );
        catDialogsDemos.put("ProgressDialog", new Demonstration(
                        "ProgressDialog",
                        getString(R.string.progressdialog_example_desc),
                        R.drawable.baseline_chat_24, ProgressDialogExample.class,"progressdialog"
                )
        );

        catWidgetsDemos.put("Button", new Demonstration(
                "Button","A common type of widget which is the simplest way to interact with an application. Users can click it in order to transmit their instructions.",R.drawable.ic_button, ButtonWidget.class, "button1"
        ));
        catWidgetsDemos.put("Switch", new Demonstration(
                "Switch","A common type of widget. It has two states, on and off. The two states of the widget represent two states of the application. The layout will change according to the change of the state of a switch.",R.drawable.ic_switch, SwitchWidget.class, "switch1"
        ));
        catWidgetsDemos.put("Radio Group", new Demonstration(
                "Radio Group","A group of buttons, but only one button of them can be chosen.",R.drawable.ic_radio, RadioGroupWidget.class, "radio1"
        ));
        catWidgetsDemos.put("Progress bar", new Demonstration(
                "Progress bar","A type of widget to show the progress of the program in order to give the users an approximate waiting time.",R.drawable.ic_progress, ProgressBarWidget.class, "progress1"
        ));

        // Decide which demos go into which category.
        demos.put("Services", catServicesDemos);
        demos.put("System", catSystemDemos);
        demos.put("Dialogs", catDialogsDemos);
        demos.put("Navigation", catNavigationDemos);
        demos.put("Animations",catAnimationsDemos);
        demos.put("Effects",catEffectsDemos);
        demos.put("Widgets",catWidgetsDemos);

        // Decided by ourselves
        highlightedCats = new ArrayList<>();
        // highlight xxx
        highlightedCats.add(0);
        // highlight xxx
        highlightedCats.add(1);
        // highlight xxx
        highlightedCats.add(2);
        // highlight xxx
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