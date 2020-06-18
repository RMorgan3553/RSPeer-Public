package tasks;

import data.string.StringProduct;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.scene.Players;
import tasks.framework.Task;

public class StringTask extends Task {

    private StringProduct product;
    private int xpRequiredToCompletion;
    private int amount;

    private String bowstring = "Bow string";

    /**
     * Strings bows/crossbows.
     * @param product End product to be made.
     * @param amount Amount of bows/crossbows to make.
     */
    public StringTask(StringProduct product, int amount) {
        this.product = product;
        this.amount = amount;
        setTaskName("Stringing " + product.getName());
    }

    @Override
    public boolean canRun() {
        return !Players.getLocal().isAnimating() && Inventory.containsAll(bowstring, product.getResource().getName());
    }

    @Override
    public boolean run() {
        if(!this.hasRun()) {
            this.xpRequiredToCompletion = (amount * product.getXp()) + Skills.getExperience(Skill.FLETCHING);
            this.setHasRun();
        }

        //Bug found. Every 5 bows the animation pauses for a second and triggers the canRun() method. Somewhat hacky fix.
        if(Inventory.contains(product.getName()) && Inventory.getCount(product.getName()) % 5 == 0) {
            Time.sleepUntil(() -> !this.canRun(), Random.nextInt(2000,2800));
            if(!this.canRun()) return true;
        }

        Inventory.getFirst(bowstring).interact("Use");
        Time.sleepUntil(() -> Inventory.getSelectedItem() != null && Inventory.getSelectedItem().getName().equals(bowstring), Random.nextInt(750, 1000));
        Inventory.getFirst(product.getResource().getName()).interact(ActionOpcodes.ITEM_ON_ITEM);
        Time.sleepUntil(() -> Production.isOpen(), Random.nextInt(750,1000));
        Production.initiate(0);
        Time.sleepUntil(() -> !this.canRun(), Random.nextInt(1250,1500));
        return true;
    }

    @Override
    public boolean isComplete() {
        return Skills.getExperience(Skill.FLETCHING) >= xpRequiredToCompletion && this.hasRun();
    }

    /**
     * @return Amount of bows left to string until task completion.
     */
    public int getRemaining() { return (Skills.getExperience(Skill.FLETCHING) - xpRequiredToCompletion) / product.getXp(); }
}
