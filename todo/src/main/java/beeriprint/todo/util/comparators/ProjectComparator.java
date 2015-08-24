/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.util.comparators;

import beeriprint.todo.model.Project;
import java.util.Comparator;

/**
 *
 * @author hilel
 */
public class ProjectComparator implements Comparator<Project> {

    String sortKey;

    public ProjectComparator(String sortKey) {
        this.sortKey = sortKey;
    }

    @Override
    public int compare(Project p1, Project p2) {
        switch (sortKey) {
            case "id":
                return p1.getId() - p2.getId();
            case "title":
                return p1.getTitle().compareTo(p2.getTitle());
        }
        throw new IllegalArgumentException(sortKey + " is not a valid sort key");
    }

}
