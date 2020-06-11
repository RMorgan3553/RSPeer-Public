package data.string;

public enum StringProduct {

    SHORTBOW("Shortbow", StringResource.SHORTBOW, 5,5),
    LONGBOW("Longbow", StringResource.LONGBOW, 10,10),
    OAK_SHORTBOW("Oak shortbow", StringResource.OAK_SHORTBOW, 16,20),
    OAK_LONGBOW("Oak longbow", StringResource.OAK_LONGBOW, 25,25),
    WILLOW_SHORTBOW("Willow shortbow", StringResource.WILLOW_SHORTBOW, 33, 35),
    WILLOW_LONGBOW("Willow longbow", StringResource.WILLOW_LONGBOW, 41, 40),
    MAPLE_SHORTBOW("Maple shortbow", StringResource.MAPLE_SHORTBOW, 50, 50),
    MAPLE_LONGBOW("Maple longbow", StringResource.MAPLE_LONGBOW, 58,55),
    YEW_LONGBOW("Yew longbow", StringResource.YEW_LONGBOW, 75, 65),
    YEW_SHORTBOW("Yew shortbow", StringResource.YEW_SHORTBOW, 67,70),
    MAGIC_SHORTBOW("Magic shortbow", StringResource.MAGIC_SHORTBOW, 83, 80),
    MAGIC_LONGBOW("Magic longbow", StringResource.MAGIC_LONGBOW, 91,85);

    private final String name;
    private final StringResource resource;
    private final int xp;
    private final int levelReq;

    StringProduct(final String name, final StringResource resource, int xp, int levelReq) {
        this.name = name;
        this.resource = resource;
        this.xp = xp;
        this.levelReq = levelReq;
    }

    public String getName() { return this.name; }
    public StringResource getResource() { return this.resource; }
    public int getXp() { return this.xp; }
    public int getLevelReq() { return this.levelReq; }

    /**
     * @return Name of the required resource (i.e, unstrung bow/crossbow)
     */
    public String getResourceName() { return this.resource.getName(); }
}
