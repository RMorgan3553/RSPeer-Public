package tasks;

import org.rspeer.runetek.adapter.component.InterfaceComponent;
import org.rspeer.runetek.adapter.scene.SceneObject;
import org.rspeer.runetek.api.commons.Time;
import org.rspeer.runetek.api.commons.math.Random;
import org.rspeer.runetek.api.component.Interfaces;
import org.rspeer.runetek.api.component.tab.Inventory;
import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.runetek.api.movement.position.Position;
import org.rspeer.runetek.api.scene.Players;
import org.rspeer.runetek.api.scene.SceneObjects;
import tasks.framework.Task;

import java.util.function.BooleanSupplier;

public class SceneObjectProductionTask extends Task {

    private Position objectPosition;
    private String objectInteraction;
    private int interfaceGroup;
    private int interfaceComponent;
    private String interfaceInteraction;
    private int animationTime;

    private int inventoryCount;
    private long time;

    /**
     * Production task which interacts with a SceneObject and requires interface interaction outside of Production.class. i.e, Smithing on an Anvil or Making gold amulets
     * @param objectPosition The position of the object to interact with
     * @param objectInteraction The interaction option for Object.interact.
     * @param interfaceGroup The interface group for the production interface.
     * @param interfaceComponent The interface component ID for the production interface
     * @param interfaceInteraction The interaction string for the production interface option (i.e, 'Make/Smith..etc')
     *                             
     * @param animationTime The animation time of the action (optional) for animations which frequently reset to 0.
     */
    public SceneObjectProductionTask(Position objectPosition, String objectInteraction, int interfaceGroup, int interfaceComponent, String interfaceInteraction, BooleanSupplier runCondition, int animationTime) {
        this.objectPosition = objectPosition;
        this.objectInteraction = objectInteraction;
        this.interfaceGroup = interfaceGroup;
        this.interfaceComponent = interfaceComponent;
        this.interfaceInteraction = interfaceInteraction;
        this.animationTime = animationTime;
    }

    public SceneObjectProductionTask(Position objectPosition, String objectInteraction, int interfaceGroup, int interfaceComponent, String interfaceInteraction) {
        this(objectPosition, objectInteraction, interfaceGroup, interfaceComponent, interfaceInteraction, 0);
    }

    @Override
    public boolean canRun() {
        return !Players.getLocal().isAnimating() &&
                !Players.getLocal().isMoving() &&
                Inventory.containsAll("Hammer", "Bronze bar");

    }

    @Override
    public boolean run() {
        if(interfaceIsOpen()) {
            getInterface().interact(interfaceInteraction);
            Time.sleepUntil(() -> !interfaceIsOpen() && !canRun(), Random.nextInt(600,750));
        }else {
            SceneObject furnace = SceneObjects.getFirstAt(objectPosition);
            if(furnace != null) {
                Time.sleepUntil(() -> !canRun(), Random.nextInt(animationTime, animationTime + (animationTime / 10)));
                if(!canRun()) return true;
                furnace.interact(objectInteraction);
                Time.sleepUntil(() -> interfaceIsOpen(), Random.nextInt(600,750));
            }else {
                Movement.walkTo(objectPosition);
                Time.sleepUntil(() -> !canRun(), Random.nextInt(650,750));
            }
        }

        return true;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    /**
     * Returns a fresh InterfaceComponent using the Object variables interfaceGroup and interfaceComponent
     * @return Can be null.
     */
    private InterfaceComponent getInterface() {
        return Interfaces.getComponent(interfaceGroup, interfaceComponent);
    }

    /**
     * @return True if the production interface is open.
     */
    private boolean interfaceIsOpen() {
        InterfaceComponent ic = getInterface();
        return ic != null && ic.isVisible();
    }
}
