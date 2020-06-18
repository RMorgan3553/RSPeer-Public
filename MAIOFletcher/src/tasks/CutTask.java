package tasks;

import data.cut.CutProduct;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.scene.Players;
import tasks.framework.Task;

public class CutTask extends Task {

    private String knife = "Knife";
    private CutProduct product;
    private int xpRequiredToCompletion;
    private int amount;

    /**
     * Cuts wood into bows/shafts/shields..etc
     * @param product What to make
     * @param amount Amount to make
     */
    public CutTask(CutProduct product, int amount) {
        setTaskName("Cutting" + product.getName());
        this.product = product;
        this.amount = amount;
    }

    @Override
    public boolean canRun() {
        return !Players.getLocal().isAnimating() && Inventory.containsAll(knife, product.getResourceName());
    }

    @Override
    public boolean run() {

        if(!this.hasRun()) {
            this.xpRequiredToCompletion = (amount * product.getXp()) + Skills.getExperience(Skill.FLETCHING);
            this.setHasRun();
        }

        Inventory.getFirst(knife).interact("Use");
        Time.sleepUntil(() -> Inventory.getSelectedItem() != null && Inventory.getSelectedItem().getName().equals(knife), Random.nextInt(750,1000));
        Inventory.getFirst(product.getResourceName()).interact(ActionOpcodes.ITEM_ON_ITEM);
        Time.sleepUntil(() -> Production.isOpen(), Random.nextInt(750, 1000));
        Production.initiate(product.getProductionOption());
        Time.sleepUntil(() -> !this.canRun(), Random.nextInt(1250, 1750));
        return true;
    }

    /**
     * @return Amount of actions remaining for this task to be complete.
     */
    public int getRemaining() {  return (this.xpRequiredToCompletion - Skills.getExperience(Skill.FLETCHING)) / product.getXp(); }

    @Override
    public boolean isComplete() {
        return Skills.getExperience(Skill.FLETCHING) >= xpRequiredToCompletion && this.hasRun();
    }
}
