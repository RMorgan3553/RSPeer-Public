package tasks;

public abstract class Task {

    private String taskName;
    private boolean firstRun;

    public Task() {
        this.firstRun = true;
    }

    /**
     * @return True if the task is able to run. (Not necessarily if it SHOULD be run)
     */
    public abstract boolean canRun();

    /**
     * Runs the given task
     * @return False if the task encounters an error which requires the script to stop.
     */
    public abstract boolean run();


    /**
     * @return True if the task is finished and should be removed from the TaskQueue.
     */
    public abstract boolean isComplete();

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
