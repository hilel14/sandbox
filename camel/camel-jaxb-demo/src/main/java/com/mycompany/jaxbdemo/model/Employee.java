package com.mycompany.jaxbdemo.model;

/**
 *
 * @author hilel
 */
public class Employee {

    private String jobTitle;
    private String employeeName;

    @Override
    public String toString() {
        return employeeName + " is the " + jobTitle;
    }

    /**
     * @return the jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle the jobTitle to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
