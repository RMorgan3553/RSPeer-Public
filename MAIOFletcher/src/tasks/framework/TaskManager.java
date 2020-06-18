package tasks.framework;

import org.rspeer.runetek.api.movement.Movement;
import org.rspeer.ui.Log;

public class TaskManager {

    private TaskSet[] tasks;
    private boolean loop; //TODO: Add infinite looping.
    private int currentTask;

    /**
     * Task queueing system. Tasks are completed from index 0 to end.
     * TaskSets are completed one by one, Task[1] will only be run once Task[0] has completed.
     * If looping is enabled, once the last TaskSet has completed the first will run again
     * If looping is disabled, the script will end once the last TaskSet has completed.
     * @param tasks Set of tasks to execute, in order of which to be executed.
     * @param loop True if the TaskSets should loop
     */
    public TaskManager(TaskSet[] tasks, boolean loop) {
        this.tasks = tasks;
        this.loop = loop;
        this.currentTask = 0;
    }

    /**
     * Task queueing system. Tasks are completed from index 0 to end.
     * TaskSets are completed one by one, Task[1] will only be run once Task[0] has completed.
     * If looping is enabled, once the last TaskSet has completed the first will run again
     * If looping is disabled, the script will end once the last TaskSet has completed.
     * @param tasks Set of tasks to execute, in order of which to be executed.
     * @param loop True if the TaskSets should loop
     */
    public TaskManager(Task[] tasks, boolean loop) {
        TaskSet[] t = new TaskSet[tasks.length];
        int i = 0;
        for(Task task : tasks) {
            t[i] = new TaskSet(new Task[]{task});
        }
        this.tasks = t;
        this.loop = loop;
        this.currentTask = 0;
    }

    /**
     * Runs the current task in the queue.
     * @return False if a task encountered an error which requires the script to stop
     */
    public boolean run() {

        if(this.tasks[currentTask].isComplete()) {
            Log.info("Task set " + this.currentTask + " is complete");
            if (this.tasks.length - 1 <= this.currentTask) {
                Log.fine("All tasks complete. Script ending...");
                return false;
            }else {
                Log.info("Moving to next TaskSet");
                Movement.toggleRun(!Movement.isRunEnabled()); //Stops the current character action.
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
}
