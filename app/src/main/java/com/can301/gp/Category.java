package com.can301.gp;

/**
 * A category of demonstrations
 *
 * Each instance will be created by hand and should be immutable,
 * therefore, all fields will be public.
 */
public final class Category {
    Category(
            int title, int description,
            int iconId
    )
    {
        this.title = title;
        this.description = description;
        this.iconId = iconId;
    }

    // String id
    public final int title;
    // String id
    public final int description;
    public final int iconId;
}
