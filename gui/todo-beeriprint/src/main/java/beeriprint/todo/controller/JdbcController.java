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
import org.h2.jdbcx.JdbcConnectionPool;

/**
 *
 * @author hilel
 */
public class JdbcController implements AutoCloseable {

    static final Logger LOGGER = Logger.getLogger(JdbcController.class.getName());
    Connection connection;
    PreparedStatement findAllProjects;
    PreparedStatement findAllCategories;
    PreparedStatement findAllStatuses;
    PreparedStatement findProjectById;
    PreparedStatement findTaskById;
    PreparedStatement findCategoryById;
    PreparedStatement findStatusById;
    PreparedStatement findTasksByProjectId;
    PreparedStatement updateProject;
    PreparedStatement updateTask;
    PreparedStatement insertProject;
    PreparedStatement insertTask;
    PreparedStatement deleteProject;
    PreparedStatement deleteTask;

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

    private void initConnectionPool() throws SQLException, IOException {
        Properties p = new Properties();
        p.load(JdbcController.class.getResourceAsStream("/jdbc.properties"));
        JdbcConnectionPool pool = JdbcConnectionPool.create(
                p.getProperty("url"),
                p.getProperty("user"),
                p.getProperty("password"));
        connection = pool.getConnection();
        //conn.close();
        //pool.dispose();
    }

    private void preparedStatements() throws SQLException {
        findAllProjects = connection.prepareStatement("SELECT * FROM project ORDER BY id;");
        findAllCategories = connection.prepareStatement("SELECT * FROM category ORDER BY id;");
        findAllStatuses = connection.prepareStatement("SELECT * FROM status ORDER BY id;");

        findProjectById = connection.prepareStatement("SELECT * FROM project WHERE id = ?;");
        findTaskById = connection.prepareStatement("SELECT * FROM task WHERE id = ?;");
        findCategoryById = connection.prepareStatement("SELECT * FROM category WHERE id = ?;");
        findStatusById = connection.prepareStatement("SELECT * FROM status WHERE id = ?;");
        findTasksByProjectId = connection.prepareStatement("SELECT * FROM task WHERE project_id = ?;");

        updateProject = connection.prepareStatement("UPDATE project SET "
                + "title = ?, remarks = ?, start_date = ?, end_date = ?, category =  ?, priority = ?, status = ? "
                + "WHERE id = ?;");
        updateTask = connection.prepareStatement("UPDATE task SET description = ?, completed = ? WHERE id = ?");
        insertProject = connection.prepareStatement("INSERT INTO project (title) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS);
        insertTask = connection.prepareStatement("INSERT INTO task (project_id, description) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        deleteProject = connection.prepareStatement("DELETE FROM project WHERE id = ?");
        deleteTask = connection.prepareStatement("DELETE FROM task WHERE id = ?");
    }

    public List<Project> findAllProjects() throws SQLException {
        List<Project> projects = new ArrayList<>();
        ResultSet resultSet = findAllProjects.executeQuery();
        while (resultSet.next()) {
            Project project = recordToProject(resultSet);
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

    public List<Status> findAllStatuses() throws SQLException {
        List<Status> statuses = new ArrayList<>();
        ResultSet resultSet = findAllStatuses.executeQuery();
        while (resultSet.next()) {
            Status status = new Status();
            status.setId(resultSet.getInt("id"));
            status.setDescription(resultSet.getString("description"));
            statuses.add(status);
        }
        return statuses;
    }

    public List<Integer> findAllPriorities() {
        List<Integer> priorities = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            priorities.add(i);
        }
        return priorities;
    }

    public Project findProjectById(int id) throws SQLException {
        findProjectById.setInt(1, id);
        ResultSet resultSet = findProjectById.executeQuery();
        return resultSet.next() ? recordToProject(resultSet) : null;
    }

    public Task findTaskById(int id) throws SQLException {
        findTaskById.setInt(1, id);
        ResultSet resultSet = findTaskById.executeQuery();
        Task task = null;
        if (resultSet.next()) {
            task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setDescription(resultSet.getString("description"));
            task.setCompleted(resultSet.getBoolean("completed"));
        }
        return task;
    }

    private Project recordToProject(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("id"));
        project.setTitle(resultSet.getString("title"));
        project.setRemarks(resultSet.getString("remarks"));
        project.setStartDate(resultSet.getDate("start_date"));
        project.setEndDate(resultSet.getDate("end_date"));
        project.setPriority(resultSet.getInt("priority"));
        project.setStatus(findStatusById(resultSet.getInt("status")));
        project.setCategory(findCategoryById(resultSet.getInt("category")));
        project.setTasks(findTasksByProjectId(project.getId()));
        return project;
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

    public List<Task> findTasksByProjectId(int projectId) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        findTasksByProjectId.setInt(1, projectId);
        ResultSet resultSet = findTasksByProjectId.executeQuery();
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setDescription(resultSet.getString("description"));
            task.setCompleted(resultSet.getBoolean("completed"));
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
        updateProject.setString(2, project.getRemarks());
        java.sql.Date startDate = new java.sql.Date(project.getStartDate().getTime());
        updateProject.setDate(3, startDate);
        java.sql.Date endDate = project.getEndDate() == null ? null : new java.sql.Date(project.getEndDate().getTime());
        updateProject.setDate(4, endDate);
        updateProject.setInt(5, project.getCategory().getId());
        updateProject.setInt(6, project.getPriority());
        updateProject.setInt(7, project.getStatus().getId());
        updateProject.setInt(8, project.getId());
        updateProject.execute();
    }

    public void updateTask(Task task) throws SQLException {
        updateTask.setString(1, task.getDescription());
        updateTask.setBoolean(2, task.isCompleted());
        updateTask.setInt(3, task.getId());
        updateTask.execute();
    }

    public int insertProject(String title) throws SQLException {
        insertProject.setString(1, title);
        insertProject.executeUpdate();
        ResultSet rs = insertProject.getGeneratedKeys();
        return rs.next() ? rs.getInt(1) : null;
    }

    public int insertTask(int projectId, String description) throws SQLException {
        insertTask.setInt(1, projectId);
        insertTask.setString(2, description);
        insertTask.executeUpdate();
        ResultSet rs = insertTask.getGeneratedKeys();
        return rs.next() ? rs.getInt(1) : null;
    }

    public void deleteProject(int id) throws SQLException {
        deleteProject.setInt(1, id);
        deleteProject.executeUpdate();
    }

    public void deleteTask(int id) throws SQLException {
        deleteTask.setInt(1, id);
        deleteTask.executeUpdate();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
