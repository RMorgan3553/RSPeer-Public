import org.rspeer.runetek.adapter.scene.Npc;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.scene.Npcs;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;

import java.awt.*;

@ScriptMeta(developer =  "Morgan3553", name = "M-Barbarian PowerFisher", desc = "Barbarian powerfisher", version = 0.3)
public class Main extends Script implements RenderListener {

    private String[] fishNames = new String[]{"Leaping trout", "Leaping salmon", "Leaping sturgeon"};
    private long startTime;
    private int startXp;

    @Override
    public void onStart() {
        startTime = System.currentTimeMillis();
        startXp = Skills.getExperience(Skill.FISHING);
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
            if(fishingSpot == null) { Log.severe("Can't find fishing spot"); ;return -1; } //Check we are in the correct area
            fishingSpot.interact("Use-rod");
            Time.sleepUntil(() -> Players.getLocal().isAnimating(), Random.nextInt(500,750));
        }else if(!Inventory.contains("Barbarian rod")) {
            Log.severe("Missing ROD");
            return -1;
        }else if(!Inventory.contains("Feather")) {
            Log.severe("Missing FEATHERS");
            return -1;
        }

        return 400;
    }

    @Override
    public void notify(RenderEvent renderEvent) {
        int currentXp = Skills.getExperience(Skill.FISHING);
        Graphics g = renderEvent.getSource();
        int xpGained = currentXp - startXp;
        g.setColor(Color.black);
        g.drawString("Experience gained: " + xpGained, 11, 329);
        g.drawString("Time: " + formatTime(System.currentTimeMillis() - startTime), 11, 315);
    }

    //Thanks Stackoverflow
    public final String formatTime(final long ms) {
        long s = ms / 1000, m = s / 60, h = m / 60;
        s %= 60;
        m %= 60;
        h %= 24;
        return String.format("%02d:%02d:%02d", h, m, s);
    }
}
