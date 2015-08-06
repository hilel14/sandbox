/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo;

import beeriprint.todo.controller.JdbcController;
import beeriprint.todo.model.Project;
import beeriprint.todo.model.Task;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author hilel
 */
public class JdbcControllerTest {

    /**
     * Test of close method, of class JdbcController.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void test1() throws Exception {
        try (JdbcController controller = new JdbcController();) {
            List<Project> projects = controller.findAllProjects();
            for (Project project : projects) {
                System.out.println(project);
                for (String note : project.getNotes()) {
                    System.out.println(note);
                }
                for (Task task : project.getTasks()) {
                    System.out.print(task.getDescription());
                    System.out.print(": ");
                    System.out.println(task.getStatus().getDescription());
                }
            }
        }
    }
}
