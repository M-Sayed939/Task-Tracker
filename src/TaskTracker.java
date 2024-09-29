import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskTracker {
  private final Path FILE_PATH = Path.of("tasks.json");
  private List<Task> tasks;
  public TaskTracker(){
      this.tasks=loadTask();
  }

    private List<Task> loadTask() {
      List<Task> storedTasks = new ArrayList<>();
      if ((!Files.exists(FILE_PATH))) {
          return storedTasks;
      }
      try {
          String jsonContent = Files.readString(FILE_PATH);
          String[] taskList = jsonContent.replace("[", "").replace("]", "").split("},");
          for (String taskJson : taskList) {
              if (!taskJson.endsWith("}")) {
                  taskJson += "}";
                  storedTasks.add(Task.fromJson(taskJson));
              } else {
                  storedTasks.add(Task.fromJson(taskJson + "}"));
              }
          }
      } catch (Exception e) {
          e.printStackTrace();
      }
        return storedTasks;
    }
    public void saveTasks(){
      StringBuilder sb = new StringBuilder();
      sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).toJson());
            if(i < tasks.size()-1){
                sb.append(",\n");
            }

        }
        sb.append("\n]");
        String jsonContent = sb.toString();
        try {
            Files.writeString(FILE_PATH, jsonContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addTask(String description){
      Task newTask = new Task(description);
      tasks.add(newTask);
        System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
    }
    public void updateTask(String id, String newDescription){
      Task task = findTask(id).orElseThrow(()->new IllegalArgumentException("Invalid task id"));
      task.updateDescription(newDescription);
        System.out.println("Task updated successfully");
}
public void deleteTask(String id){
      Task task = findTask(id).orElseThrow(()->new IllegalArgumentException("Invalid task id"));
      task.remove(task);
}
public void markInProgress(String id) {
    Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Invalid task id"));
    task.markInProgress();
}
public void markDone(String id) {
      Task task = findTask(id).orElseThrow(()->new IllegalArgumentException("Invalid task id"));
        task.markDone();
}
public void listTasks(String type){
      for(Task task:tasks){
          String status = task.getStatus().toString().strip();
          if(type.equals("All")||status.equalsIgnoreCase(type)){
              System.out.println(task.toString());
          }
      }
}

    private Optional<Task> findTask(String id) {
      return tasks.stream().filter((task)->task.getId()==Integer.parseInt(id)).findFirst();
    }
    }
