package com.can301.gp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.can301.gp.catviewer.CatViewMain;

public class CategoriesPage extends AppCompatActivity {

    Drawable drawableFromId(int Id) {
        return this.getResources().getDrawable(Id);
    }

    void goToCatViewPage(int catInd) {
        Intent intent = new Intent(this, CatViewMain.class);
        intent.putExtra(CatViewMain.CAT_VIEW_INDEX_KEY, catInd);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_page);

        // Fill in the categories
        {
            LinearLayout catsContainer = findViewById(R.id.categoryContainer);

            for (int i = 0; i < GlobalData.catList.size(); ++i) {
                Category c = GlobalData.catList.get(i);

                Button catBtn = new Button(this);
                catBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                catBtn.setText(c.title);
                catBtn.setCompoundDrawablesWithIntrinsicBounds(
                        drawableFromId(c.iconId), null, null, null
                );
                int finalI = i; // lambda restriction
                catBtn.setOnClickListener(v -> {
                    goToCatViewPage(finalI);
                });

                // Add Button to LinearLayout
                catsContainer.addView(catBtn);

            }
        }
    }
}