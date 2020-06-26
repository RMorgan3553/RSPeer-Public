import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.ui.Log;
import tasks.BankTask;
import tasks.ItemOnItemTask;
import tasks.ProductionTask;
import tasks.framework.Task;
import tasks.framework.TaskSet;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

public class Main extends Script {

    private TaskSet t;
    @Override
    public void onStart() {
        Log.fine("API. Script allowed for testing");

        String knife = "Knife";
        String yew_logs = "Yew logs";

        BooleanSupplier[] playerIsBusy = new BooleanSupplier[]{
                () -> Players.getLocal().isAnimating(),
                () -> Players.getLocal().isMoving()
        };

        ItemOnItemTask ioit = new ItemOnItemTask(knife, yew_logs, playerIsBusy);

        ProductionTask pt = new ProductionTask(0);

        BooleanSupplier[] bankingCondition = new BooleanSupplier[] {
                () -> !Inventory.contains(knife, yew_logs),
                () -> !Players.getLocal().isMoving(),
                () -> !Players.getLocal().isAnimating()
        };

        Map<String, Integer> bankItem = new HashMap<>();
        bankItem.put(knife, 1);
        bankItem.put(yew_logs, 27);

        BankTask bt = new BankTask(bankItem, bankingCondition);

        t = new TaskSet(new Task[]{ioit, pt, bt});

    }

    @Override
    public void onStop() {
        Log.fine("API Script finished");
    }

    @Override
    public int loop() {
        if(!t.run()) return 0;
        return 500;
    }
}
