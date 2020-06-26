package tasks.framework;

public class TaskManager {

    private TaskSet[] tasks;
    private int currentTask;

    /**
     * Task queueing system. Tasks are completed from index 0 to end.
     * TaskSets are completed one by one, Task[1] will only be run once Task[0] has completed.
     * @param tasks Set of tasks to execute, in order of which to be executed.
     */
    public TaskManager(TaskSet[] tasks) {
        this.tasks = tasks;
        this.currentTask = 0;
    }

    /**
     * Task queueing system. Tasks are completed from index 0 to end.
     * TaskSets are completed one by one, Task[1] will only be run once Task[0] has completed.
     * @param tasks Set of tasks to execute, in order of which to be executed
     */
    public TaskManager(Task[] tasks) {
        TaskSet[] t = new TaskSet[tasks.length];
        int i = 0;
        for(Task task : tasks) {
            t[i] = new TaskSet(new Task[]{task});
        }
        this.tasks = t;
        this.currentTask = 0;
    }

    /**
     * Runs the current task in the queue.
     * @return False if a task encountered an error which requires the script to stop
     */
    public boolean run() {

        if(this.tasks[currentTask].isComplete()) {
            if (this.tasks.length - 1 <= this.currentTask) {
                return false;
            }else {
                this.currentTask++;
                return true;
            }
        }
        return this.tasks[currentTask].run();
    }

    /**
     * @return The name of the currently running task
     */
    public String getRunningTaskName() {
        return this.tasks[this.currentTask].getCurrentTaskName();
    }

    /**
     * @return The currently running task set.
     */
    public TaskSet getRunningTaskSet() {
        return this.tasks[this.currentTask];
    }


}
