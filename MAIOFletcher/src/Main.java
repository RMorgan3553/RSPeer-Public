import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.component.tab.Inventory;
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

@ScriptMeta(developer = "Morgan3553", desc = "AIO Fletcher", name = "M ~ AIO Fletcher")
public class Main extends Script implements RenderListener {

    private String knife = "Knife";
    private String resource = "Yew logs";
    private String secondaryResource;

    private CutTask cutTask;
    private BankTask bankTask;
    private StringTask stringTask;

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
        return Random.nextInt(400,600);
    }

    @Override
    public void onStart() {
        //cutTask = new CutTask("Yew logs", 2000, 2);
        bankTask = new BankTask("Bow string", 14, "Yew longbow (u)", 14);
        stringTask = new StringTask("Yew longbow (u)", 2000);
        Log.info("Script started");
    }
}
