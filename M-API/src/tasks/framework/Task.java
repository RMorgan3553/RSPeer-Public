package tasks.framework;

import java.util.function.BooleanSupplier;

public abstract class Task {

    private String taskName;
    private boolean firstRun;

    private BooleanSupplier[] runConditions;

    private BooleanSupplier[] completionConditions;


    protected Task() {
        if(runConditions == null) { setDefaultRunConditions(); }
        if(completionConditions == null) { setDefaultCompletionConditions(); }
    }

    private void setDefaultRunConditions() {
        this.runConditions = new BooleanSupplier[] {()->true};
    }

    private void setDefaultCompletionConditions() {
        this.completionConditions = new BooleanSupplier[] {()->false};
    }

    /**
     * Sets the run conditions for the task
     * @param conditions Conditions to set.
     */
    protected void setRunConditions(BooleanSupplier[] conditions) {
        runConditions = conditions;
    }

    /**
     * Adds a run condition.
     * @param condition Condition to add.
     */
    protected void addRunCondition(BooleanSupplier condition) {
        BooleanSupplier[] b = new BooleanSupplier[runConditions.length + 1];
        System.arraycopy(runConditions,0,b,0,runConditions.length);
        b[runConditions.length] = condition;
    }
    /**
     * @return True if the task is able to run. (Not necessarily if it SHOULD be run)
     */
    public boolean canRun() {
        for(BooleanSupplier b : runConditions) {
            if(!b.getAsBoolean()) return false;
        }
        return true;
    }

    /**
     * Runs the given task
     * @return False if the task encounters an error which requires the script to stop.
     */
    public abstract boolean run();

    /**
     * @return True if the task is finished and should be removed from the TaskQueue.
     */
    public boolean isComplete() {
        for(BooleanSupplier b : completionConditions) {
            if(!b.getAsBoolean()) return false;
        }
        return true;
    }

    /**
     * @param condition The condition upon which the task should be marked as complete.
     */
    protected void setCompletionConditions(BooleanSupplier[] condition) {
        this.completionConditions = condition;
    }

    /**
     * @return Task name
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * Sets the task name.
     * @param taskName Task name
     */
    protected void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return True if the task has  run at least once.
     */
    protected boolean hasRun() { return !this.firstRun; }

    /**
     * Used to set the hasRun variable to false, should be called after the first complete run();
     */
    protected void setHasRun() { this.firstRun = false; }
}
