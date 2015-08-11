/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.model;

/**
 *
 * @author hilel
 */
public class Task {

    private int id;
    private String description;
    private boolean completed;

    public Object[] toTableRow() {
        Object[] row = new Object[3];
        row[0] = this;
        row[1] = isCompleted();
        row[2] = description;
        return row;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @param completed the completed to set
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
