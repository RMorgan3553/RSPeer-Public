package tasks;

import org.rspeer.runetek.adapter.component.Item;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import tasks.framework.Task;

import java.util.function.BooleanSupplier;

public class ItemOnItemTask extends Task {

    private String item;
    private String item2;

    /**
     * Uses an item on another item in the inventory as long as it has both items in the inventory.
     * @param item Item to use
     * @param item2 Item to use
     * @param runConditions Additional run conditions outside of any that would stop this task from being CAPABLE of running.
     */
    public ItemOnItemTask(String item, String item2, BooleanSupplier[] runConditions, BooleanSupplier[] completionCondition) {
        this(item,item2, runConditions);
        setCompletionConditions(completionCondition);
    }

    /**
     * Uses an item on another item in the inventory as long as it has both items in the inventory.
     * @param item Item to use
     * @param item2 Item to use
     * @param runConditions Additional run conditions outside of any that would stop this task from being CAPABLE of running.
     */
    public ItemOnItemTask(String item, String item2, BooleanSupplier[] runConditions) {
        this(item, item2);
        setRunConditions(runConditions);
        addRunCondition(()->Inventory.contains(item, item2));
    }

    /**
     * Uses an item on another item in the inventory as long as it has both items in the inventory.
     * @param item Item to use
     * @param item2 Item to use
     */
    public ItemOnItemTask(String item, String item2) {
        this.item = item;
        this.item2 = item2;
        setTaskName("Using " + item + " on " + item2);
    }

    @Override
    public boolean run() {
        Item invItem = Inventory.getSelectedItem();
        Item useItem = Inventory.getFirst(item);
        if(invItem != null) {
            if(invItem == useItem) {
                Inventory.getFirst(item2).interact(ActionOpcodes.ITEM_ON_ITEM);
            }else {
                Inventory.deselectItem();
            }
        }else {
            useItem.interact(ActionOpcodes.USE_ITEM);
        }
        return true;
    }
}
