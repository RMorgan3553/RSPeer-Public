import data.cut.CutProduct;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;
import tasks.*;

import java.awt.*;

@ScriptMeta(developer = "Morgan3553", desc = "AIO Fletcher", name = "M ~ AIO Fletcher", version = 0.2)
public class Main extends Script implements RenderListener {

    private TaskManager tManager;

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
        g.drawString("Current task: " + this.tManager.getRunningTaskName(), 11, 285);
        g.drawString("Time: " + time, 11, 300);
        g.drawString("XP Gained: " + xpGained, 11, 315);
    }

    @Override
    public int loop() {
        if(!tManager.run()) return -1;
        return Random.nextInt(400,600);
    }

    @Override
    public void onStart() {

        CutProduct c = CutProduct.YEW_LONGBOW;
        CutTask ct = new CutTask(c, 12);
        BankTask bt = new BankTask("Knife", 1, c.getResourceName(), 27);
        TaskSet ts = new TaskSet(new Task[]{ct, bt});

        CutProduct c2 = CutProduct.YEW_SHORBTOW;
        CutTask ct2 = new CutTask(c2, 12);
        BankTask bt2 = new BankTask("Knife", 1, c2.getResourceName(), 27);
        TaskSet ts2 = new TaskSet(new Task[]{ct2, bt2});

        this.tManager = new TaskManager(new TaskSet[]{ts, ts2}, true);

        startTime = System.currentTimeMillis();
        startXp = Skills.getExperience(Skill.FLETCHING);
        Log.info("Script started");
    }

    @Override
    public void onStop() {
        Log.info("Script stopped");
    }
}
