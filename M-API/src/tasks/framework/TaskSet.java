package tasks.framework;

public class TaskSet {

    private Task[] taskList;
    private boolean isComplete;
    private String currentTask;

    /**
     * Allows for multiple tasks to be grouped together. When one is found complete, the entire set will be registered as complete.
     * @param taskList List of tasks to execute within the set.
     */
    public TaskSet(Task[] taskList) {
        this.isComplete = false;
        this.taskList = taskList;
    }

    /**
     * @return False if one of the tasks has encountered an error which required the script to stop.
     */
    public boolean run() {
        for(Task t : this.taskList) {

            if (t.isComplete()) {
                this.isComplete = true;
                break;
            }

            if (t.canRun()) {
                this.currentTask = t.getTaskName();
                if (!t.run()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return The currently running task name.
     */
    public String getCurrentTaskName() {
        return this.currentTask;
    }

    /**
     * @return True if any of the tasks has registerd as complete.
     */
    public boolean isComplete() {
        return this.isComplete;
    }
}
