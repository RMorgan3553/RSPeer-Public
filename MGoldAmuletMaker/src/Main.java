import data.ScriptData;
import org.rspeer.runetek.api.component.Bank;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import org.rspeer.script.task.Task;
import tasks.BankTask;
import tasks.SmeltTask;

@ScriptMeta(name = ScriptData.name,
        developer = ScriptData.developer,
        version = ScriptData.version,
        category = ScriptCategory.MONEY_MAKING,
        desc = ScriptData.desc)
public class Main extends Script implements RenderListener {

    private long startTime;

    private BankTask bankTask;
    private SmeltTask smeltTask;

    public Main() {
        bankTask = new BankTask("Amulet mould", 1, "Gold bar", 27);
        smeltTask = new SmeltTask(new Position(3110,3499,0),
                "Smelt",
                446,
                34,
                "Make",
                1800);
    }

    @Override
    public void onStart() {
        startTime = System.currentTimeMillis();
    }

    @Override
    public int loop(){
        if(bankTask.canRun()) {
            if(!bankTask.run()) return -1;
        }
        if(smeltTask.canRun()) {
            if(!smeltTask.run()) return -1;
        }
        return 600;
    }

    @Override
    public void onStop() {

    }

    @Override
    public void notify(RenderEvent renderEvent) {

    }
}
