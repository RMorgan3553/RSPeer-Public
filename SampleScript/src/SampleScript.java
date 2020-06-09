import org.rspeer.script.Script;
import org.rspeer.script.ScriptMeta;

@ScriptMeta(developer = "Morgan3553", name = "Sample Script", desc = "Script template")
public class SampleScript extends Script {

    @Override
    public int loop() {
        return 400;
    }
}
