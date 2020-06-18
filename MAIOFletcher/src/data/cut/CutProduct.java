package data.cut;

public enum CutProduct {

    ARROW_SHAFT("Arrow shaft", CutResource.LOG, 0, 5, 1),
    SHORTBOW_U("Shortbow (u)", CutResource.LOG, 1, 5, 5),
    LONGBOW_U("Longbow (u)", CutResource.LOG, 2, 10, 10),
    OAK_SHORTBOW_U("Oak shortbow (u)", CutResource.OAK_LOG, 1, 16, 20),
    OAK_LONGBOW_U("Oak longbow (u)", CutResource.OAK_LOG, 2, 25, 25),
    WILLOW_SHORTBOW_U("Willow shortbow (u)", CutResource.WILLOW_LOG, 1, 33, 35),
    WILLOW_LONGBOW_U("Willow longbow (u)", CutResource.WILLOW_LOG, 2, 41, 40),
    MAPLE_SHORTBOW_U("Maple shortbow (u)", CutResource.MAPLE_LOG, 1, 50, 50),
    MAPLE_LONGBOW_U("Maple longbow (u)", CutResource.MAPLE_LOG, 2, 58, 55),
    YEW_SHORBTOW_U("Yew shortbow (u)", CutResource.YEW_LOG, 1, 67, 65),
    YEW_LONGBOW_U("Yew longbow (u)", CutResource.YEW_LOG, 2, 75, 70),
    MAGIC_SHORTBOW_U("Magic shortbow (u)", CutResource.MAGIC_LOG, 1, 83, 80),
    MAGIC_LONGBOW_U("Magic longbow (u)", CutResource.MAGIC_LOG, 2, 91, 85);

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

    public String getName() {
        return this.name;
    }

    public CutResource getResource() {
        return this.resource;
    }

    public int getProductionOption() {
        return this.productionOption;
    }

    public int getXp() {
        return this.xp;
    }

    public int getLevelReq() {
        return this.levelReq;
    }

    /**
     * @return Name of the required resource (Log)
     */
    public String getResourceName() {
        return this.resource.getName();
    }

}