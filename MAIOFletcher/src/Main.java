import data.ScriptData;
import data.cut.CutProduct;
import data.string.StringProduct;
import gui.GuiMain;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.tab.Skill;
import org.rspeer.runetek.api.component.tab.Skills;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;
import org.rspeer.ui.Log;
import tasks.*;
import tasks.framework.Task;
import tasks.framework.TaskManager;
import tasks.framework.TaskSet;

import java.awt.*;

@ScriptMeta(developer = ScriptData.scriptDev, desc = ScriptData.scriptDesc, name = ScriptData.scriptName, version = ScriptData.scriptVer)
public class Main extends Script implements RenderListener {

    private TaskManager tManager;

    //Timers
    private long startTime;
    private int startXp;

    private GuiMain gui;
    private boolean started;

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
        if(this.started) {
            g.drawString("Current task: " + this.tManager.getRunningTaskName(), 11, 285);
            g.drawString("Time: " + time, 11, 300);
            g.drawString("XP Gained: " + xpGained, 11, 315);
        }
    }

    @Override
    public int loop() {
        if(this.started && !tManager.run()) return -1;

        if(!this.started) {
            loadTasks();
        }
        return Random.nextInt(400,600);
    }

    @Override
    public void onStart() {
        gui = new GuiMain();
        Log.info("Opened GUI");
        startTime = System.currentTimeMillis();
        startXp = Skills.getExperience(Skill.FLETCHING);
        Log.info("Script started");
    }

    @Override
    public void onStop() {
        Log.info("Script stopped");
    }

    //Placeholder for command-line/GUI start logic,
    private void loadTasks() {
        loadTasksFromGUI();
    }

    private void loadTasksFromGUI() {
        if(gui.isStarted()) {
            Log.info("Checking GUI");
            String[][] tasks = gui.getTasks();
            int amountOfTasks = tasks.length;
            //Sanity check
            TaskSet[] taskSets = new TaskSet[amountOfTasks];
            for(int i = 0; i < amountOfTasks; i++ ) {
                String product = tasks[i][1];
                int amount = Integer.valueOf(tasks[i][2]);
                switch (tasks[i][0]) {
                    case "Cut":
                        CutProduct cp = CutProduct.valueOf(product);
                        CutTask ct = new CutTask(cp, amount);
                        BankTask bct = new BankTask("Knife", 1, cp.getResourceName(), 27);
                        taskSets[i] = new TaskSet(new Task[]{ct, bct});
                        break;
                    case "String":
                        StringProduct sp = StringProduct.valueOf(product);
                        StringTask st = new StringTask(sp, amount);
                        BankTask bst = new BankTask(sp.getResourceName(), 14, "Bow string", 14);
                        taskSets[i] = new TaskSet(new Task[]{st, bst});
                }
            }
            if(amountOfTasks == 0) {
                Log.severe("No tasks set!");
                this.setStopping(true);
            }else {
                this.tManager = new TaskManager(taskSets, false);
                this.started = true;
            }
        }
    }
}
