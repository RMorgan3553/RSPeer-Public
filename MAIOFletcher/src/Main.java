import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.api.input.menu.ActionOpcodes;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;
import tasks.BankTask;
import tasks.CutTask;
import tasks.StringTask;

import java.awt.*;

@ScriptMeta(developer = "Morgan3553", desc = "AIO Fletcher", name = "M ~ AIO Fletcher", version = 0.2)
public class Main extends Script implements RenderListener {

    private String knife = "Knife";
    private String resource = "Yew logs";
    private String secondaryResource;

    //Tasks
    private CutTask cutTask;
    private BankTask bankTask;
    private StringTask stringTask;

    //Timers
    private long startTime;
    private int startXp;


    @Override
    public void notify(RenderEvent renderEvent) {
        int currentXp = Skills.getExperience(Skill.FLETCHING);
        int xpGained = currentXp - startXp;

        long s = (System.currentTimeMillis() - startTime) / 1000, m = s / 60, h = m / 60;
        s %= 60;
        m %= 60;
        h %= 24;
        String time = String.format("%02d:%02d:%02d", h, m, s);

        Graphics g = renderEvent.getSource();
        g.setColor(Color.black);
        g.drawString("Time: " + time, 11, 300);
        g.drawString("XP Gained: " + xpGained, 11, 315);
    }

    @Override
    public int loop() {

        if(cutTask.canRun()) {
            if(!cutTask.run()) { return -1; }
        }
        if(bankTask.canRun()) {
            if(!bankTask.run()) { return -1; }
        }


        //if(stringTask.canRun()) { if(!stringTask.run()) { return -1; }}
        //if(bankTask.canRun()) { if(!bankTask.run()) { return -1; }}
        return Random.nextInt(400,600);
    }

    @Override
    public void onStart() {
        cutTask = new CutTask("Yew logs", 2000, 2);
        bankTask = new BankTask("Knife", 1, "Yew logs", 27);
        //stringTask = new StringTask("Yew longbow (u)", 2000, "Yew longbow");

        startTime = System.currentTimeMillis();
        startXp = Skills.getExperience(Skill.FLETCHING);
        Log.info("Script started");
    }
}
