package com.can301.gp;

import android.content.Context;
import android.content.Intent;

/**
 * Represents a demonstration
 *
 * Contains all the information needed to identify a demonstration,
 * all the information needed to navigate to a page about a demonstration,
 * and all the information needed to show a demonstration's effect
 *
 * Each instance will be created by hand and should be immutable,
 * therefore, all fields will be public.
 */
public final class Demonstration {

    Demonstration(
            String title, String description,
            int iconId,
            String effectActivityName, String codeId
    )
    {
        this.title = title;
        this.description = description;
        this.iconId = iconId;
        this.effectActivityName = effectActivityName;
        this.codeId = codeId;
    }

    public final String title;
    public final String description;
    public final int iconId;
    // Used to navigate to its corresponding effect activity
    public final String effectActivityName;
    public final String codeId;

    public void goToEffectActivity(Context ctx) {
//        Intent intent = new Intent();
//        ctx.startActivity(intent);
    }
}
