/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beeriprint.todo.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hilel
 */
public class Project {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private Category category;
    private int priority;
    private boolean active;
    private boolean onDesktop;
    private List<String> notes;
    private List<Task> tasks;

    @Override
    public String toString() {
        return title;
    }

    public Object[] toTableRow() {
        Object[] row = new Object[8];
        row[0] = this;
        row[1] = title;
        row[2] = dateFormat.format(startDate);
        row[3] = endDate == null ? null : dateFormat.format(endDate);
        row[4] = category;
        row[5] = priority;
        row[6] = active;
        row[7] = onDesktop;
        return row;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * @return the on_desktop
     */
    public boolean isOnDesktop() {
        return onDesktop;
    }

    /**
     * @param on_desktop the on_desktop to set
     */
    public void setOnDesktop(boolean on_desktop) {
        this.onDesktop = on_desktop;
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the notes
     */
    public List<String> getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    /**
     * @return the tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * @param tasks the tasks to set
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

}
