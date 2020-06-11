package data.cut;

public enum CutProduct {

    SHORTBOW("Shortbow (u)", CutResource.LOG, 1,5,5),
    LONGBOW("Longbow (u)", CutResource.LOG, 2, 10, 10),
    OAK_SHORTBOW("Oak shortbow (u)", CutResource.OAK_LOG, 1, 16, 20),
    OAK_LONGBOW("Oak longbow (u)", CutResource.OAK_LOG, 2, 25, 25),
    WILLOW_SHORTBOW("Willow shortbow (u)", CutResource.WILLOW_LOG, 1, 33, 35),
    WILLOW_LONGBOW("Willow longbow (u)", CutResource.WILLOW_LOG, 2, 41, 40),
    MAPLE_SHORTBOW("Maple shortbow (u)", CutResource.MAPLE_LOG, 1, 50, 50),
    MAPLE_LONGBOW("Maple longbow (u)", CutResource.MAPLE_LOG, 2, 58, 55),
    YEW_SHORBTOW("Yew shortbow (u)", CutResource.YEW_LOG, 1, 67, 65),
    YEW_LONGBOW("Yew longbow (u)", CutResource.YEW_LOG, 2, 75, 70),
    MAGIC_SHORTBOW("Magic shortbow (u)", CutResource.MAGIC_LOG, 1, 83, 80),
    MAGIC_LONGBOW("Magic longbow (u)", CutResource.MAGIC_LOG, 2, 91, 85);

    private final String name;
    private final CutResource resource;
    private final int productionOption;
    private final int xp;
    private final int levelReq;

    CutProduct(final String name, final CutResource resource, final int productionOption, final int xp, final int levelReq) {
        this.name = name;
        this.resource = resource;
        this.productionOption = productionOption;
        this.xp = xp;
        this.levelReq = levelReq;
    }

    public String getName() { return this.name; }

    public CutResource getResource() { return this.resource; }

    public int getProductionOption() { return this.productionOption; }

    public int getXp() { return this.xp; }

    public int getLevelReq() { return this.levelReq; }

    /**
     * @return Name of the required resource (Log)
     */
    public String getResourceName() { return this.resource.getName(); }
}
