package data.string;

public enum StringResource {

    SHORTBOW("Shortbow (u)"),
    LONGBOW("Longbow (u)"),
    OAK_SHORTBOW("Oak shortbow (u)"),
    OAK_LONGBOW("Oak longbow (u)"),
    WILLOW_LONGBOW("Willow longbow(u)"),
    WILLOW_SHORTBOW("Willow shortbow (u)"),
    MAPLE_LONGBOW("Maple longbow (u)"),
    MAPLE_SHORTBOW("Maple shortbow(u)"),
    YEW_LONGBOW("Yew longbow (u)"),
    YEW_SHORTBOW("Yew shortbow(u)"),
    MAGIC_SHORTBOW("Magic shortbow (u)"),
    MAGIC_LONGBOW("Magic longbow(u)");

    private final String name;

    StringResource(final String name) {
        this.name = name;
    }

    public String getName() { return this.name; }
}
