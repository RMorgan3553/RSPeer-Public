package tasks;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.scene.Players;

public class StringTask extends Task {

    private String resource;
    private int amount;

    private String bowstring = "Bow string";

    public StringTask(String resource, int amount) {
        this.resource = resource;
        this.amount = amount;
        setTaskName("Stringing");
    }
    @Override
    public boolean canRun() {
        return !Players.getLocal().isAnimating() && Inventory.containsAll(bowstring, resource);
    }

    @Override
    public boolean run() {
        Inventory.getFirst(bowstring).interact("Use");
        Time.sleepUntil(() -> Inventory.getSelectedItem() != null && Inventory.getSelectedItem().getName().equals(bowstring), Random.nextInt(750, 1000));
        Inventory.getFirst(this.resource).interact(ActionOpcodes.ITEM_ON_ITEM);
        Time.sleepUntil(() -> Production.isOpen(), Random.nextInt(750,1000));
        Production.initiate(0);
        Time.sleepUntil(() -> !this.canRun(), Random.nextInt(1250,1500));
        return true;
    }

    @Override
    public boolean isComplete() {
        return false;
    }
}
