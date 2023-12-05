package com.can301.gp;

import static com.can301.gp.GlobalData.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import com.can301.gp.catviewer.CatViewMain;
import com.can301.gp.demos.AppSpecificFilesExample;
import com.can301.gp.demos.BGServiceExample;
import com.can301.gp.demos.BoundServiceExample;
import com.can301.gp.demos.AlertDialogExample;
import com.can301.gp.demos.ButtonWidget;
import com.can301.gp.demos.DatePickerDialogExample;
import com.can301.gp.demos.AnimationsExample;
import com.can301.gp.demos.EffectExampleDragView;
import com.can301.gp.demos.EffectExampleFancyLoadingEffect;
import com.can301.gp.demos.EffectExampleLoadingEffect;
import com.can301.gp.demos.EffectExampleProgressBarLoadingEffect;
import com.can301.gp.demos.EffectExampleZooming;
import com.can301.gp.demos.FGServiceExample;

import com.can301.gp.demos.FrameAnimationsExample;
import com.can301.gp.demos.AttributeAnimationsExample;
import com.can301.gp.demos.GoSystemSettingsExample;
import com.can301.gp.demos.NavigationDrawerExample;
import com.can301.gp.demos.InteractCalendarExample;
import com.can301.gp.demos.NavigationExample;
import com.can301.gp.demos.EffectExampleNightMode;
import com.can301.gp.demos.EffectExampleRippleEffect;
import com.can301.gp.demos.ProgressBarWidget;
import com.can301.gp.demos.QRcodeGenerateExample;
import com.can301.gp.demos.RadioGroupWidget;
import com.can301.gp.demos.RatingBarWidget;
import com.can301.gp.demos.RequestPermissionExample;
import com.can301.gp.demos.SeekBarWidget;
import com.can301.gp.demos.SensorsExampleActivity;
import com.can301.gp.demos.SwitchWidget;
import com.can301.gp.demos.TimerExample;
import com.can301.gp.searchbar.SearchBarMain;
import com.can301.gp.demos.PopupWindowExample;
import com.can301.gp.demos.BottomSheetDialogExample;
import com.can301.gp.demos.ProgressDialogExample;
import com.can301.gp.demos.TimePickerDialogExample;
import com.can301.gp.demos.InteractCameraExample;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Drawable drawableFromId(int Id) {
        return this.getResources().getDrawable(Id);
    }

    /**
     * Init global data when this launcher activity starts
     * if the global data has not been inited.
     */
    public void setupGlobalData() {
        // Only initialise them once
        if (!categories.isEmpty()) {
            return;
        }
        

        // Set up all categories.
        categories.put(R.string.cat_title_services, new Category(
                R.string.cat_title_services,
                R.string.cat_desc_services,
                R.drawable.ic_cat_services
        ));
        categories.put(R.string.cat_title_qrcode, new Category(
                R.string.cat_title_qrcode,
                R.string.cat_desc_qrcode,
                R.drawable.baseline_qr_code_24
        ));
        categories.put(R.string.cat_title_sys_interaction, new Category(
                R.string.cat_title_sys_interaction,
                R.string.cat_desc_sys_interaction,
                R.drawable.ic_cat_system
        ));
        categories.put(R.string.cat_title_storage, new Category(
                R.string.cat_title_storage,
                R.string.cat_desc_storage,
                R.drawable.ic_cat_storage
        ));
        categories.put(R.string.cat_title_navigation, new Category(
                R.string.cat_title_navigation,
                R.string.cat_desc_navigation,
                R.drawable.ic_cat_navigation
        ));
        categories.put(R.string.cat_title_animations, new Category(
                R.string.cat_title_animations,
                R.string.cat_desc_animations,
                R.drawable.baseline_image_24
        ));
        categories.put(R.string.cat_title_dialogs, new Category(
                R.string.cat_title_dialogs,
                R.string.cat_desc_dialogs,
                R.drawable.baseline_chat_24
        ));
        categories.put(R.string.cat_title_effects, new Category(
                R.string.cat_title_effects,
                R.string.cat_desc_effects,
                R.drawable.ic_cat_effect
        ));
        categories.put(R.string.cat_title_widgets, new Category(
                R.string.cat_title_widgets,
                R.string.cat_desc_widgets,
                R.drawable.ic_widgets
        ));
        categories.put(R.string.cat_title_gestures, new Category(
                R.string.cat_title_gestures,
                R.string.cat_desc_gestures,
                R.drawable.ic_gesture
        ));

        HashMap<Integer, Demonstration> catServicesDemos = new HashMap<Integer, Demonstration>();
        HashMap<Integer, Demonstration> catSystemDemos = new HashMap<Integer, Demonstration>();
        HashMap<Integer, Demonstration> catStorageDemos = new HashMap<Integer, Demonstration>();
        HashMap<Integer, Demonstration> catDialogsDemos = new HashMap<>();
        HashMap<Integer, Demonstration> catNavigationDemos = new HashMap<>();
        HashMap<Integer, Demonstration> catAnimationsDemos = new HashMap<>();
        HashMap<Integer, Demonstration> catEffectsDemos = new HashMap<>();
        HashMap<Integer, Demonstration> catWidgetsDemos = new HashMap<>();
        HashMap<Integer, Demonstration> catGesturesDemos = new HashMap<>();
        HashMap<Integer, Demonstration> catQrcodeDemos = new HashMap<>();

        // Set up all demos.
        catEffectsDemos.put(R.string.demo_title_nightmode, new Demonstration(
                R.string.demo_title_nightmode, R.string.night_mode_example_desc,
                R.drawable.ic_night, EffectExampleNightMode.class,"nightmode"
                )
        );
        catEffectsDemos.put(R.string.demo_title_rippleeffect, new Demonstration(
                R.string.demo_title_rippleeffect, R.string.ripple_effect_example_desc,
                R.drawable.ic_water, EffectExampleRippleEffect.class,"rippleeffect"
                )
        );
        catEffectsDemos.put(R.string.demo_title_loadingeffect, new Demonstration(
                R.string.demo_title_loadingeffect, R.string.loading_effect_example_desc,
                        R.drawable.baseline_image_24, EffectExampleLoadingEffect.class,"loadingeffect"
                )
        );
        catEffectsDemos.put(R.string.demo_title_progressbareffect, new Demonstration(
                R.string.demo_title_progressbareffect, R.string.progress_bar_effect_example_desc,
                        R.drawable.ic_progress_bar, EffectExampleProgressBarLoadingEffect.class,"progressbareffect"
                )
        );
        catEffectsDemos.put(R.string.demo_title_fancyloadingeffect, new Demonstration(
                R.string.demo_title_fancyloadingeffect, R.string.fancy_loading_effect_example_desc,
                        R.drawable.ic_progress_bar, EffectExampleFancyLoadingEffect.class,"fancyloadingeffect"
                )
        );
        catGesturesDemos.put(R.string.demo_title_zooming, new Demonstration(
                R.string.demo_title_zooming, R.string.zooming_example_desc,
                R.drawable.ic_zooming, EffectExampleZooming.class,"zooming"
                )
        );
        catGesturesDemos.put(R.string.demo_title_dragging, new Demonstration(
                R.string.demo_title_dragging, R.string.dragging_example_desc,
                R.drawable.ic_dragging, EffectExampleDragView.class,"dragging"
                )
        );

        catServicesDemos.put(R.string.demo_title_bgservice, new Demonstration(
                R.string.demo_title_bgservice,
                R.string.bg_service_example_desc,
                R.drawable.ic_bg_service, BGServiceExample.class, "bgservice"
        ));
        catServicesDemos.put(R.string.demo_title_boundservice, new Demonstration(
                R.string.demo_title_boundservice,
                R.string.bound_service_example_desc,
                R.drawable.ic_bound_service, BoundServiceExample.class, "boundservice"
        ));
        catServicesDemos.put(R.string.demo_title_fgservice, new Demonstration(
                R.string.demo_title_fgservice,
                R.string.fg_service_example_desc,
                R.drawable.ic_fg_service, FGServiceExample.class, "fgservice"
        ));

        catSystemDemos.put(R.string.demo_title_reqpermission, new Demonstration(
                R.string.demo_title_reqpermission,
                R.string.request_permission_example_desc,
                R.drawable.ic_request_permission, RequestPermissionExample.class, "reqpermission"
        ));
        catSystemDemos.put(R.string.demo_title_interactcalendar, new Demonstration(
                R.string.demo_title_interactcalendar,
                R.string.interactcalendar_example_desc,
                R.drawable.baseline_edit_calendar_24, InteractCalendarExample.class, "interactcalendar"
        ));
        catSystemDemos.put(R.string.demo_title_systemsettings, new Demonstration(
                R.string.demo_title_systemsettings,
                R.string.go_system_settings_example_desc,
                R.drawable.ic_demo_settings, GoSystemSettingsExample.class, "systemsettings"
        ));
        catSystemDemos.put(R.string.demo_title_interactcamera, new Demonstration(
                R.string.demo_title_interactcamera,
                R.string.interactcamera_example_desc,
                R.drawable.baseline_photo_camera_24, InteractCameraExample.class, "interactcamera"
        ));
        catSystemDemos.put(R.string.demo_title_sensors, new Demonstration(
                R.string.demo_title_sensors,
                R.string.sensors_example_desc,
                R.drawable.ic_sensors, SensorsExampleActivity.class, "sensors"
        ));

        catStorageDemos.put(R.string.demo_title_appspecific, new Demonstration(
                R.string.demo_title_appspecific,
                R.string.app_specific_files_example_desc,
                R.drawable.ic_app_specific_files, AppSpecificFilesExample.class, "appspecific"
        ));
        catDialogsDemos.put(R.string.demo_title_alertdialog, new Demonstration(
                R.string.demo_title_alertdialog,
                R.string.alertdialog_example_desc,
                R.drawable.baseline_chat_24, AlertDialogExample.class,"alertdialog"
        ));
        catDialogsDemos.put(R.string.demo_title_datepickerdialog, new Demonstration(
                R.string.demo_title_datepickerdialog, R.string.datepickerdialog_example_desc,
                        R.drawable.baseline_chat_24, DatePickerDialogExample.class,"datepickerdialog"
                )
        );
        catNavigationDemos.put(R.string.demo_title_navigation1, new Demonstration(
                R.string.demo_title_navigation1, R.string.navigation_example_desc, R.drawable.baseline_navigation_24, NavigationExample.class, "navigation1"
        ));
        catNavigationDemos.put(R.string.demo_title_navigation2, new Demonstration(
                R.string.demo_title_navigation2, R.string.navigation_drawer_example_desc, R.drawable.baseline_navigation_24, NavigationDrawerExample.class, "navigation2"
        ));

        catAnimationsDemos.put(R.string.demo_title_animation1,new Demonstration(
                R.string.demo_title_animation1,R.string.animations_example_desc, R.drawable.baseline_image_24, AnimationsExample.class,"animation1"
        ));
        catAnimationsDemos.put(R.string.demo_title_frameanimation,new Demonstration(
                R.string.demo_title_frameanimation,R.string.frame_animations_example_desc,R.drawable.baseline_animation_24, FrameAnimationsExample.class,"frameanimation"
        ));
        catAnimationsDemos.put(R.string.demo_title_attributeanimation,new Demonstration(
                R.string.demo_title_attributeanimation,R.string.attribute_animations_example_desc,R.drawable.baseline_keyboard_double_arrow_right_24, AttributeAnimationsExample.class,"attributeanimation"
        ));

        catDialogsDemos.put(R.string.demo_title_popupwindow, new Demonstration(
                R.string.demo_title_popupwindow,
                        R.string.popupwindow_example_desc,
                        R.drawable.baseline_chat_24, PopupWindowExample.class,"popupwindow"
                )
        );
        catDialogsDemos.put(R.string.demo_title_bottomsheetdialog, new Demonstration(
                R.string.demo_title_bottomsheetdialog,
                        R.string.bottomsheetdialog_example_desc,
                        R.drawable.baseline_chat_24, BottomSheetDialogExample.class,"bottomsheetdialog"
                )
        );
        catDialogsDemos.put(R.string.demo_title_timepickerdialog, new Demonstration(
                        R.string.demo_title_timepickerdialog,
                        R.string.timepickerdialog_example_desc,
                        R.drawable.baseline_chat_24, TimePickerDialogExample.class,"timepickerdialog"
                )
        );
        catDialogsDemos.put(R.string.demo_title_timer, new Demonstration(
                R.string.demo_title_timer,
                        R.string.timer_example_desc,
                        R.drawable.baseline_hourglass_top_24, TimerExample.class,"timer"
                )
        );

        catDialogsDemos.put(
                R.string.demo_title_progressdialog,
                new Demonstration(
                R.string.demo_title_progressdialog,
                R.string.progressdialog_example_desc,
                R.drawable.baseline_chat_24, ProgressDialogExample.class,"progressdialog"
                )
        );

        catWidgetsDemos.put(R.string.demo_title_button1, new Demonstration(
                R.string.demo_title_button1,
                R.string.button_desc, R.drawable.ic_button, ButtonWidget.class, "button1"
        ));
        catWidgetsDemos.put(R.string.demo_title_switch1, new Demonstration(
                R.string.demo_title_switch1,
                R.string.switch_desc,R.drawable.ic_switch, SwitchWidget.class, "switch1"
        ));
        catWidgetsDemos.put(R.string.demo_title_radio1, new Demonstration(
                R.string.demo_title_radio1,
                R.string.radio_desc,R.drawable.ic_radio, RadioGroupWidget.class, "radio1"
        ));
        catWidgetsDemos.put(R.string.demo_title_progress1, new Demonstration(
                R.string.demo_title_progress1,
                 R.string.progress_desc,R.drawable.ic_progress, ProgressBarWidget.class, "progress1"
        ));
        catWidgetsDemos.put(R.string.demo_title_seek1, new Demonstration(
                R.string.demo_title_seek1,
                R.string.seek_desc,R.drawable.ic_seek_bar, SeekBarWidget.class, "seek1"
        ));
        catWidgetsDemos.put(R.string.demo_title_rating1, new Demonstration(
                R.string.demo_title_rating1,
                R.string.rating_desc,R.drawable.ic_rating_bar, RatingBarWidget.class, "rating1"
        ));
        catQrcodeDemos.put(R.string.demo_title_qrcode1,
                new Demonstration(
                        R.string.demo_title_qrcode1,
                        R.string.qrcode_generate_desc,
                        R.drawable.baseline_qr_code_24, QRcodeGenerateExample.class,"qrcode1"
        ));

        // Decide which demos go into which category.
        demos.put(R.string.cat_title_services, catServicesDemos);
        demos.put(R.string.cat_title_sys_interaction, catSystemDemos);
        demos.put(R.string.cat_title_storage, catStorageDemos);
        demos.put(R.string.cat_title_dialogs, catDialogsDemos);
        demos.put(R.string.cat_title_navigation, catNavigationDemos);
        demos.put(R.string.cat_title_animations, catAnimationsDemos);
        demos.put(R.string.cat_title_effects, catEffectsDemos);
        demos.put(R.string.cat_title_widgets, catWidgetsDemos);
        demos.put(R.string.cat_title_gestures, catGesturesDemos);
        demos.put(R.string.cat_title_qrcode, catQrcodeDemos);

        // Decided by ourselves
        highlightedCats = new ArrayList<>();
        // highlight Services
        highlightedCats.add(0);
        // highlight System Interaction
        highlightedCats.add(9);
        // highlight Animation
        highlightedCats.add(4);
        // highlight Effects
        highlightedCats.add(8);
        // highlight Dialogs
        highlightedCats.add(5);
        // highlight Widgets
        highlightedCats.add(2);

        // Fill in the lists
        catList = new ArrayList<Category>(categories.values());
        demoList = new ArrayList<Demonstration>();
        for (HashMap<Integer, Demonstration> h : demos.values()) {
            demoList.addAll(h.values());
        }
    }

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

        setupGlobalData();

        setContentView(R.layout.activity_main);

        // Set up the cat buttons
        {
            assert highlightedCats.size() >= numHighlighedCats;
            Category cat1 = catList.get(highlightedCats.get(0));
            Category cat2 = catList.get(highlightedCats.get(1));
            Category cat3 = catList.get(highlightedCats.get(2));
            Category cat4 = catList.get(highlightedCats.get(3));
            Category cat5 = catList.get(highlightedCats.get(4));
            Category cat6 = catList.get(highlightedCats.get(5));

            Button bt1 = findViewById(R.id.mainCatButton1);
            Button bt2 = findViewById(R.id.mainCatButton2);
            Button bt3 = findViewById(R.id.mainCatButton3);
            Button bt4 = findViewById(R.id.mainCatButton4);
            Button bt5 = findViewById(R.id.mainCatButton5);
            Button bt6 = findViewById(R.id.mainCatButton6);

            // Button icons (on top)
            bt1.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat1.iconId), null, null);
            bt2.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat2.iconId), null, null);
            bt3.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat3.iconId), null, null);
            bt4.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat4.iconId), null, null);
            bt5.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat5.iconId), null, null);
            bt6.setCompoundDrawablesWithIntrinsicBounds(
                    null, drawableFromId(cat6.iconId), null, null);

            // Button texts
            bt1.setText(cat1.title);
            bt2.setText(cat2.title);
            bt3.setText(cat3.title);
            bt4.setText(cat4.title);
            bt5.setText(cat5.title);
            bt6.setText(cat6.title);

            // Redirections
            bt1.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(0)));
            bt2.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(1)));
            bt3.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(2)));
            bt4.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(3)));
            bt5.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(4)));
            bt6.setOnClickListener(v -> goToCatViewPage(highlightedCats.get(5)));
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