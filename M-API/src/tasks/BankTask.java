package tasks;

import org.rspeer.runetek.api.commons.BankLocation;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import tasks.framework.Task;

import java.util.Map;
import java.util.function.BooleanSupplier;

public class BankTask extends Task {

    private Map<String, Integer> inventory;

    /**
     * Handles banking @ nearest bank. Will set inventory up as provided in Map.
     * @param inventory Map<String, Integer> (Item, Amount) i.e, Knife:1, Yew log:27
     * @param runConditions When to run this task
     * @param completionConditions Set conditions upon which this task should be marked as complete.
     */
    public BankTask(Map<String, Integer> inventory, BooleanSupplier[] runConditions, BooleanSupplier[] completionConditions) {
        this.inventory = inventory;

        setRunConditions(runConditions);
        addRunCondition(()-> !Players.getLocal().isAnimating() && !Players.getLocal().isMoving());
        setCompletionConditions(completionConditions);
    }

    /**
     * Handles banking @ nearest bank. Will set inventory up as provided in Map
     * @param inventory Map<String, Integer> (Item , Amount) i.e Knife:1, Yew log:27
     * @param runConditions When to run this task.
     */
    public BankTask(Map<String, Integer> inventory, BooleanSupplier[] runConditions) {
        this(inventory, runConditions, null);
    }

    @Override
    public boolean run() {
        if(Bank.isOpen()) {
            String[] reqItems = (String[])inventory.keySet().toArray();

            if(Inventory.containsAnyExcept(reqItems)) {
                Bank.depositAllExcept(reqItems);
                Time.sleepUntil(()-> !Inventory.containsAnyExcept(reqItems), Random.nextInt(500,750));
                return true;
            }

            for(Map.Entry<String, Integer> e : inventory.entrySet()) {
                String name = e.getKey();
                final int amount = e.getValue();
                int amount2 = amount;

                if(Inventory.contains(name)) {
                    amount2 = amount - Inventory.getCount(name);
                }


                if(amount2 > 0) {
                    if(bankActuallyContainsAmount(name, amount2)) {
                        Bank.withdraw(name, amount2);
                        Time.sleepUntil(()-> Inventory.getCount(name) == amount, Random.nextInt(500,750));
                        return true;
                    }
                }else if(amount2 < 0) {
                    Bank.deposit(name, amount2);
                    Time.sleepUntil(()->Inventory.getCount(name) == amount, Random.nextInt(500,750));
                    return true;
                }
            }

            Bank.close();
        } else {
            Bank.open(BankLocation.getNearest());
            Time.sleepUntil(()->Bank.isOpen() || !this.canRun(), Random.nextInt(500,750));
        }

        return true;
    }

    private boolean bankActuallyContains(String name) {
        //Bank.contains occassionally returns false when it should be true. So we double check to account for latency.
        if(!Bank.contains(name)) {
            Time.sleep(100);
            return Bank.contains(name);
        }
        return true;
    }

    private boolean bankActuallyContainsAmount(String name, int amount) {
        return bankActuallyContains(name) && Bank.getCount(name) >= amount;
    }

}
