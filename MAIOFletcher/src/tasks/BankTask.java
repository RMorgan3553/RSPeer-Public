package tasks;

import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.ui.Log;
import tasks.framework.Task;

import java.util.List;

public class BankTask extends Task {

    private String item1;
    private String item2;
    private int amount;
    private int amount2;
    private String[] depositExclude;

    /**
     * Contains all necessary interaction with the bank including depositing/withdrawing required items.
     * @param item1 Item to withdraw (i.e unstrung bow or knife)
     * @param amount Amount of item1 to withdraw
     * @param item2 Item to withdraw (i.e bowstring or logs)
     * @param amount2 Amount of item2 to withdraw
     * @param depositExclude Items which shouldn't be deposited.
     */
    public BankTask(String item1, int amount, String item2, int amount2, String[] depositExclude) {
        this.item1 = item1;
        this.amount = amount;
        this.item2 = item2;
        this.amount2 = amount2;

        List<String> de = List.of(depositExclude);
        de.add(item1);
        de.add(item2);
        this.depositExclude = (String[]) de.toArray();

        setTaskName("Banking");
    }

    public BankTask(String item1, int amount, String item2, int amount2) {
        this(item1, amount, item2, amount2, new String[0]);
    }

    @Override
    public boolean canRun() {
        return !Inventory.containsAll(item1, item2);
    }

    @Override
    public boolean run() {
        if(Bank.isOpen()) {
            if (Inventory.containsAnyExcept(item1, item2) ) {
                Bank.depositAllExcept(item1, item2);
                Time.sleepUntil(() -> !Inventory.containsAnyExcept(item1, item2), Random.nextInt(750, 1000));
                if(Inventory.containsAnyExcept(item1,item2)) { return true; }
            }

            if(!Bank.Tab.MAIN_TAB.isOpen()) {
                Bank.Tab.MAIN_TAB.open();
                Time.sleepUntil(() -> Bank.Tab.MAIN_TAB.isOpen(), Random.nextInt(750,1000));
            }

            int item1Inv = Inventory.getCount(item1);
            int item2Inv = Inventory.getCount(item2);
            int neededItems1 = amount - item1Inv;
            int neededItems2 = amount2 - item2Inv;

            //Sanity check
            if (neededItems1 >= 28 || neededItems2 >= 28) {
                Log.severe("Bank task req items >= 28. Error");
                return false;
            }

            if (neededItems1 > 0) {
                if (!Bank.contains(item1)) {
                    //The Bank.contains method rarely returns false, when it should be true. So we double check here.
                    //Am unsure why this happens, best guess is latency, so a small static sleep is implemented.
                    Time.sleep(100);
                    if(!Bank.contains(item1)) {
                        logNotFound(item1);
                        return false;
                    }
                }

                int invSlots = Inventory.getFreeSlots();
                if (neededItems1 == invSlots) {
                    Bank.withdrawAll(item1);
                } else {
                    Bank.withdraw(item1, neededItems1);
                }
                Time.sleepUntil(() -> Inventory.getCount(item1) == amount, Random.nextInt(750, 1000));
            }

            if (neededItems2 > 0) {
                if(!Bank.contains(item2)) {
                    //The Bank.contains method rarely returns false, when it should be true. So we double check here.
                    //Am unsure why this happens, best guess is latency, so a small static sleep is implemented.
                    Time.sleep(100);
                    if(!Bank.contains(item2)) {
                        logNotFound(item2);
                        return false;
                    }
                }

                int invSlots = Inventory.getFreeSlots();
                if (neededItems2 == invSlots) {
                    Bank.withdrawAll(item2);
                } else {
                    Bank.withdraw(item2, neededItems2);
                }
                Time.sleepUntil(() -> Inventory.getCount(item2) == amount2, Random.nextInt(750, 1000));
            }

            Bank.close();
            Time.sleepUntil(() -> Bank.isClosed(), Random.nextInt(750,1000));
        }else {
            Bank.open();
            Time.sleepUntil(() -> Bank.isOpen(), Random.nextInt(750,1000));
        }
        return true;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    private void logNotFound(String item) {
        Log.severe("Item: " + item + " was not found in Bank/Inventory. Stopping script");
    }
}
