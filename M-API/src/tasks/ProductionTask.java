package tasks;


import org.rspeer.runetek.adapter.scene.Player;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Production;
import org.rspeer.runetek.api.scene.Players;
import tasks.framework.Task;

public class ProductionTask extends Task {

    private int productionOption;

    /**
     * Handles the production interface.
     * @param productionOption Production option to use.
     */
    public ProductionTask(int productionOption) {
        this.productionOption = productionOption;
    }

    @Override
    public boolean canRun() {
        Player player = Players.getLocal();
        return !player.isAnimating() && !player.isMoving() && Production.isOpen();
    }

    @Override
    public boolean run() {
        Production.initiate(this.productionOption);
        Time.sleepUntil(()-> !this.canRun(), Random.nextInt(500,750));
        return true;
    }

    @Override
    public boolean isComplete() {
        return false;
    }
}
