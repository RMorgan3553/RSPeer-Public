package tasks;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.scene.Players;

public class CutTask extends Task{

    private String knife = "Knife";
    private String resource;
    private int amount;
    private int option;

    /**
     * Cuts wood into bows/shafts/shields..etc
     * @param resource Log type to use
     * @param amount Amount to make
     * @param option Option to use at the production screen. (0,1,2,3,4..etc)
     */
    public CutTask(String resource, int amount, int option) {
        setTaskName("Cutting");
        this.resource = resource;
        this.amount = amount;
        this.option = option;
    }

    @Override
    public boolean canRun() {
        return !Players.getLocal().isAnimating() && Inventory.containsAll(knife, resource);
    }

    @Override
    public boolean run() {
        Inventory.getFirst(knife).interact("Use");
        Time.sleepUntil(() -> Inventory.getSelectedItem().getName().equals(knife), Random.nextInt(750,1000));
        Inventory.getFirst(resource).interact(ActionOpcodes.ITEM_ON_ITEM);
        Time.sleepUntil(() -> Production.isOpen(), Random.nextInt(750, 1000));
        Production.initiate(this.option);
        Time.sleepUntil(() -> !this.canRun(), Random.nextInt(1250, 1750));
        return true;
    }

    /**
     * @return Amount of actions remanining for this task to be complete.
     */
    public int getRemaining() {
        return this.amount;
    }

    @Override
    public boolean isComplete() {
        return this.amount <= 0;
    }
}
