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

    }

    @Override
    public int loop() {
        /*
        if(cutTask.canRun()) {
            if(!cutTask.run()) { return -1; }
        }
        if(bankTask.canRun()) {
            if(!bankTask.run()) { return -1; }
        }
        */

        if(stringTask.canRun()) { if(!stringTask.run()) { return -1; }}
        if(bankTask.canRun()) { if(!bankTask.run()) { return -1; }}
        return Random.nextInt(750,1000);
    }

    @Override
    public void onStart() {
        //cutTask = new CutTask("Yew logs", 2000, 2);
        bankTask = new BankTask("Bow string", 14, "Yew longbow (u)", 14);
        stringTask = new StringTask("Yew longbow (u)", 2000, "Yew longbow");

        startTime = System.currentTimeMillis();
        startXp = Skills.getExperience(Skill.FLETCHING);
        Log.info("Script started");
    }
}
