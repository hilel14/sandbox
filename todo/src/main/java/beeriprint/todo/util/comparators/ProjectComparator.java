/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.util.comparators;

import beeriprint.todo.model.Project;
import java.util.Comparator;
import java.util.Date;

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
            case "start":
                return (int) (p1.getStartDate().getTime() - p2.getStartDate().getTime());
            case "end":
                return compareEndDate(p1.getEndDate(), p2.getEndDate());
            case "category":
                return p1.getCategory().getDescription().compareTo(p2.getCategory().getDescription());
            case "priority":
                return p1.getPriority() - p2.getPriority();
            case "status":
                return p1.getStatus().getDescription().compareTo(p2.getStatus().getDescription());
        }
        throw new IllegalArgumentException(sortKey + " is not a valid sort key");
    }

    private int compareEndDate(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            return (int) (d2.getTime() - d2.getTime());
        }
        if (d1 != null && d2 == null) {
            return 1;
        }
        if (d1 == null && d2 != null) {
            return -1;
        }
        return 0;
    }

}
