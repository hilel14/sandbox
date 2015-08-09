/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.controller;

import beeriprint.todo.model.Category;
import beeriprint.todo.model.Project;
import beeriprint.todo.model.Status;
import beeriprint.todo.model.Task;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author hilel
 */
public class JdbcController implements AutoCloseable {

    static final Logger logger = Logger.getLogger(JdbcController.class.getName());
    Connection connection;
    PreparedStatement findAllProjects;
    PreparedStatement findAllCategories;
    PreparedStatement findAllStatuses;
    PreparedStatement findCategoryById;
    PreparedStatement findStatusById;
    PreparedStatement findNotesByProjectId;
    PreparedStatement findTasksByProjectId;
    PreparedStatement updateProject;

    public JdbcController() throws IOException, ClassNotFoundException, SQLException {
        initConnection();
        preparedStatements();
    }

    private void initConnection() throws IOException, ClassNotFoundException, SQLException {
        Properties p = new Properties();
        p.load(JdbcController.class.getResourceAsStream("/jdbc.properties"));
        // init connection
        Class.forName(p.getProperty("driver"));
        connection = DriverManager.getConnection(
                p.getProperty("url"),
                p.getProperty("user"),
                p.getProperty("password"));
        //connection.setAutoCommit(false);
    }

    private void preparedStatements() throws SQLException {
        findAllProjects = connection.prepareStatement("SELECT * FROM project ORDER BY id;");
        findAllCategories = connection.prepareStatement("SELECT * FROM category ORDER BY id;");
        findCategoryById = connection.prepareStatement("SELECT * FROM category WHERE id = ?;");
        findStatusById = connection.prepareStatement("SELECT * FROM status WHERE id = ?;");
        findNotesByProjectId = connection.prepareStatement("SELECT * FROM note WHERE project_id = ?;");
        findTasksByProjectId = connection.prepareStatement("SELECT * FROM task WHERE project_id = ?;");
        updateProject = connection.prepareStatement("UPDATE project SET "
                + "title = ?, start_date = ?, end_date = ?, category =  ?, priority = ?, on_desktop = ?, active = ? "
                + "WHERE id = ?;");
    }

    public List<Project> findAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        ResultSet resultSet = findAllProjects.executeQuery();
        while (resultSet.next()) {
            Project project = new Project();
            project.setId(resultSet.getInt("id"));
            project.setTitle(resultSet.getString("title"));
            project.setDescription(resultSet.getString("description"));
            project.setStartDate(resultSet.getDate("start_date"));
            project.setEndDate(resultSet.getDate("end_date"));
            project.setPriority(resultSet.getInt("priority"));
            project.setActive(resultSet.getBoolean("active"));
            project.setOnDesktop(resultSet.getBoolean("on_desktop"));
            project.setCategory(findCategoryById(resultSet.getInt("category")));
            project.setNotes(findNotesByProjectId(project.getId()));
            project.setTasks(findTasksByProjectId(project.getId()));
            projects.add(project);
        }
        return projects;
    }

    public List<Category> findAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();
        ResultSet resultSet = findAllCategories.executeQuery();
        while (resultSet.next()) {
            Category category = new Category();
            category.setId(resultSet.getInt("id"));
            category.setDescription(resultSet.getString("description"));
            categories.add(category);
        }
        return categories;
    }

    public Category findCategoryById(int id) throws SQLException {
        Category category = new Category();
        findCategoryById.setInt(1, id);
        ResultSet resultSet = findCategoryById.executeQuery();
        if (resultSet.next()) {
            category.setId(resultSet.getInt("id"));
            category.setDescription(resultSet.getString("description"));
        }
        return category;
    }

    public List<String> findNotesByProjectId(int projectId) throws SQLException {
        List<String> notes = new ArrayList<>();
        findNotesByProjectId.setInt(1, projectId);
        ResultSet resultSet = findNotesByProjectId.executeQuery();
        while (resultSet.next()) {
            notes.add(resultSet.getString("description"));
        }
        return notes;
    }

    public List<Task> findTasksByProjectId(int projectId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        findTasksByProjectId.setInt(1, projectId);
        ResultSet resultSet = findTasksByProjectId.executeQuery();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setDescription(resultSet.getString("description"));
            task.setStatus(findStatusById(resultSet.getInt("status")));
            tasks.add(task);
        }
        return tasks;
    }

    public Status findStatusById(int id) throws SQLException {
        Status status = new Status();
        findStatusById.setInt(1, id);
        ResultSet resultSet = findStatusById.executeQuery();
        if (resultSet.next()) {
            status.setId(resultSet.getInt("id"));
            status.setDescription(resultSet.getString("description"));
        }
        return status;
    }

    public void updateProject(Project project) throws SQLException {
        updateProject.setString(1, project.getTitle());
        java.sql.Date startDate = new java.sql.Date(project.getStartDate().getTime());
        updateProject.setDate(2, startDate);
        java.sql.Date endDate = project.getEndDate() == null ? null : new java.sql.Date(project.getEndDate().getTime());
        updateProject.setDate(3, endDate);
        updateProject.setInt(4, project.getCategory().getId());
        updateProject.setInt(5, project.getPriority());
        updateProject.setBoolean(6, project.isOnDesktop());
        updateProject.setBoolean(7, project.isActive());
        updateProject.setInt(8, project.getId());
        updateProject.execute();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
