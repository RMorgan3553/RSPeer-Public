package tasks;

public abstract class Task {

    private String taskName;

    public abstract boolean canRun();
    public abstract boolean run();

    /**
     * @return Returns true if the task is complete.
     */
    public abstract boolean isComplete();

    public String getTaskName() {
        return this.taskName;
    }

    protected void setTaskName(String s) {
        this.taskName = s;
    }
}
