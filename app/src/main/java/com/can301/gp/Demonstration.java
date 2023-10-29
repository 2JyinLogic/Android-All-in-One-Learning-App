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

    // These two are the extra keys that are needed by intents to put extras
    public static final String EFFECT_DEMO_TITLE_KEY = "demo title";
    public static final String EFFECT_DEMO_CODE_ID_KEY = "code id";

    Demonstration(
            String title, String description,
            int iconId,
            Class<?> effectActivityClass, String codeId
    )
    {
        this.title = title;
        this.description = description;
        this.iconId = iconId;
        this.codeId = codeId;
        this.effectActivityClass = effectActivityClass;
    }

    public final String title;
    public final String description;
    // An icon for this demo
    public final int iconId;
    // codeId is needed for the code activity to load the corresponding code
    // and for the effect activity to load the documentation link
    // Each effect and its source code has a unique codeId.
    public final String codeId;

    // Because each effect has an independent activity,
    // has to record its activity class to launch its activity.
    public final Class<?> effectActivityClass;

    public void goToEffectActivity(Context ctx) {
        Intent intent = new Intent(ctx, effectActivityClass);

        intent.putExtra(EFFECT_DEMO_TITLE_KEY, title);
        intent.putExtra(EFFECT_DEMO_CODE_ID_KEY, codeId);

        ctx.startActivity(intent);
    }
}
