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
 *
 * One problem that I encountered when implementing the design of the code & effect pages
 * is that each effect has its own activity while there is only one unified code activity.
 * Therefore, the code activity has to somehow know how to get the the corresponding effect activity.
 * (Remember that normally the class of the activity is passed to the constructor of Intent).
 * However, this means each effect activity has to tell the code activity the class of itself
 * through an Intent, which is not straightforward as a Class object cannot be passed as an Intent
 * extra.
 * My solution for this problem is to let each effect activity pass its Activity Name as a String
 * to the code Activity, which uses Intent.setComponentName to navigate to the correct Activity.
 */
public final class Demonstration {

    // These two are the extra keys that are needed by intents to put extras
    public static final String EFFECT_DEMO_TITLE_KEY = "demo title";
    public static final String EFFECT_DEMO_CODE_ID_KEY = "code id";

    public static String codeIdToDocLinkStringId(String id) {
        return "doc_link_" + id;
    }

    Demonstration(
            int title, int description,
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

    // String id for the title
    public final int title;
    // String id for the description.
    public final int description;
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
