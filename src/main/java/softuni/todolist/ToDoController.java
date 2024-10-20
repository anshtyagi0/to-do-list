package softuni.todolist;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoController {

    private static final String FILE_PATH = "src/main/resources/softuni/todolist/tasks.txt";

    @FXML
    private TextField taskInput;

    @FXML
    private ListView<String> taskList;

    @FXML
    public void initialize() {
        // Load tasks from the text file during initialization
        loadTasksFromFile();
    }

    @FXML
    protected void handleAddTask() {
        String task = taskInput.getText();
        if (!task.isEmpty()) {
            taskList.getItems().add(task);
            saveTaskToFile(task);
            taskInput.clear();
        }
    }

    @FXML
    protected void handleRemoveTask() {
        String selectedTask = taskList.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            taskList.getItems().remove(selectedTask);
            removeTaskFromFile(selectedTask);
        }
    }

    private void loadTasksFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            List<String> tasks = reader.lines().collect(Collectors.toList());
            taskList.getItems().addAll(tasks);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveTaskToFile(String task) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(task);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeTaskFromFile(String taskToRemove) {
        try {
            List<String> tasks = taskList.getItems().stream().collect(Collectors.toList());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (String task : tasks) {
                    writer.write(task);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
