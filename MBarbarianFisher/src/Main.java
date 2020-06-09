import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

@ScriptMeta(developer =  "Morgan3553", name = "M-Barbarian Fisher", desc = "Barbarian fisher", version = 0.1)
public class Main extends Script {

    private String[] fishNames = new String[]{"Leaping trout", "Leaping salmon", "Leaping sturgeon"};

    @Override
    public void onStart() {
        Log.info("Script started");
    }

    @Override
    public int loop() {
        if(Inventory.isFull()) {
            Log.info("Dropping fish");
            while(Inventory.contains(fishNames)) {
                int invSize = Inventory.getCount();
                Inventory.getFirst(fishNames).interact("Drop");
                Time.sleepUntil(() -> Inventory.getCount() < invSize, Random.nextInt(750,1000));
            }
        }else if(Inventory.containsAll("Feather", "Barbarian rod") && !Players.getLocal().isAnimating()) {
            Log.info("Fishing");
            Npc fishingSpot = Npcs.getNearest("Fishing spot");
            fishingSpot.interact("Use-rod");
            Time.sleepUntil(() -> Players.getLocal().isAnimating(), Random.nextInt(500,750));
        }
        return 400;
    }
}
