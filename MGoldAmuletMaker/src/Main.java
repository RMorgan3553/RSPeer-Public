import data.ScriptData;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.event.listeners.RenderListener;
import org.rspeer.runetek.event.types.RenderEvent;
import org.rspeer.script.Script;
import org.rspeer.script.ScriptCategory;
import org.rspeer.script.ScriptMeta;
import tasks.BankTask;
import tasks.SceneObjectProductionTask;

@ScriptMeta(name = ScriptData.name,
        developer = ScriptData.developer,
        version = ScriptData.version,
        category = ScriptCategory.MONEY_MAKING,
        desc = ScriptData.desc)
public class Main extends Script implements RenderListener {

    private long startTime;

    private BankTask bankTask;
    private SceneObjectProductionTask smeltTask;

    public Main() {
        bankTask = new BankTask("Hammer", 1, "Bronze bar", 27);
        smeltTask = new SceneObjectProductionTask(new Position(3188,3426,0),
                "Smith",
                312,
                9,
                "Smith",
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
