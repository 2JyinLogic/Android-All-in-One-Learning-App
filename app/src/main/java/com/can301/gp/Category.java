package com.can301.gp;

/**
 * A category of demonstrations
 *
 * Each instance will be created by hand and should be immutable,
 * therefore, all fields will be public.
 */
public final class Category {
    Category(
            String title, String description,
            int iconId
    )
    {
        this.title = title;
        this.description = description;
        this.iconId = iconId;
    }

    public final String title;
    public final String description;
    public final int iconId;
}
