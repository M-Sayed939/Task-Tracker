public class TaskCLIApp {
    public static void main(String[]args){
        TaskTracker taskTracker = new TaskTracker();
        if (args.length < 1) {
            System.out.println("Usage: TaskCLIApp <command> [arguments]");
            return;
        }
        String command = args[0];
        switch (command) {
            case "add":
                if (args.length < 2) {
                    System.out.println("Usage: TaskClIApp add <description>");
                    return;
                }
                taskTracker.addTask(args[1]);
                break;
            case "update":
                if (args.length < 3) {
                    System.out.println("Usage: TaskClIApp update <id> <new description>");
                    return;
                }
                taskTracker.updateTask(args[1], args[2]);
                System.out.println("Task updated successfully (ID: " + args[1] + ")");
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println("Usage: TaskClIApp delete <id>");
                    return;
                }
                taskTracker.deleteTask(args[1]);
                System.out.println("Task deleted successfully (ID: " + args[1] + ")");
                break;
            case "mark-in-progress":
                if (args.length < 2) {
                    System.out.println("Usage: TaskClIApp mark-in-progress <id>");
                    return;
                }
                taskTracker.markInProgress(args[1]);
                System.out.println("Task marked as in progress (ID: " + args[1] + ")");
                break;

            case "mark-done":
                if (args.length < 2) {
                    System.out.println("Usage: TaskClIApp mark-done <id>");
                    return;
                }
                taskTracker.markDone(args[1]);
                System.out.println("Task marked as done (ID: " + args[1] + ")");
                break;

            case "list":
                if (args.length < 2) {
                    taskTracker.listTasks("All");
                } else {
                    Status filterStatus;
                    try {
                        filterStatus = Status.valueOf(args[1].toUpperCase().replace("-", "_"));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid status: " + args[1]);
                        return;
                    }
                    taskTracker.listTasks(filterStatus.toString());
                }
                break;

            default:
                System.out.println("Unknown command: " + command);
                break;
        }
        taskTracker.saveTasks();
    }
}
