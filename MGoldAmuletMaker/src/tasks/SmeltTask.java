package tasks;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import tasks.framework.Task;

public class SmeltTask extends Task {

    private String resource;
    private int productionOption;
    private int smeltTickTime;

    public SmeltTask(String resource, int productionOption) {
        this.resource = resource;
        this.productionOption = productionOption;
        setTaskName("Smelting gold amulets");
        this.smeltTickTime = 1800;
    }

    @Override
    public boolean canRun() {
        return !Players.getLocal().isAnimating() &&
                !Players.getLocal().isMoving() &&
                Inventory.containsAll("Amulet mould", "Gold bar");
    }

    @Override
    public boolean run() {
        SceneObject furnace = SceneObjects.getNearest("Furnace");
        Item resourceItem = Inventory.getFirst(resource);
        InterfaceComponent ic = getInterface();
        int smeltTime = Inventory.getCount(resource) * smeltTickTime;

        if(ic != null && ic.isVisible()) {
            ic.interact("Make");
            Time.sleepUntil(() -> getInterface() == null, Random.nextInt(600,750));
            if(getInterface() == null && !canRun()) {
                Time.sleepUntil(() -> !Inventory.contains(resource), Random.nextInt(smeltTime, smeltTime + 1000));
            }
        }else {
            if(furnace != null) {
                furnace.interact("Smelt");
                Time.sleepUntil(() -> getInterface() != null, Random.nextInt(650,750));
                return true;
            }
        }
        return true;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    private InterfaceComponent getInterface() {
        InterfaceComponent b = Interfaces.getComponent(446, 34);
        return Interfaces.getComponent(446, 34);
    }
}
