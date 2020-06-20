package data.cut;

public enum CutResource {

    LOG("Logs"),
    OAK_LOG("Oak logs"),
    WILLOW_LOG("Willow logs"),
    MAPLE_LOG("Maple logs"),
    YEW_LOG("Yew logs"),
    MAGIC_LOG("Magic logs"),
    REDWOOD_LOG("Redwood logs");

    private final String name;

    CutResource(final String name) {
        this.name = name;
    }

    public String getName() { return this.name; }
}
