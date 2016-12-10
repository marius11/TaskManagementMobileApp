package com.example.marius.taskmanager.Model;

import java.util.Date;

public class Task
{
    private long taskId;
    private String userName;
    private int isAdmin;
    private String taskText;
    private String taskDate;
    private int isActive;

    public Task(long taskId, String userName, int isAdmin, String taskText, String taskDate, int isActive)
    {
        this.taskId = taskId;
        this.userName = userName;
        this.isAdmin = isAdmin;
        this.taskText = taskText;
        this.taskDate = taskDate;
        this.isActive = isActive;
    }

    public Task() {}

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long id) { taskId = id; }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String name) {
        userName = name;
    }

    public int getIsAdmin() { return isAdmin; }

    public void setIsAdmin(int status) { isAdmin = status; }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String text) {
        taskText = text;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String date) {
        taskDate = date;
    }

    public int getIsActive() { return isActive; }

    public void setIsActive(int activity) { isActive = activity; }

    @Override
    public String toString() {
        return "Task id: " + getTaskId() + "\n" +
                "Username: " + getUserName() + "\n" +
                "Is admin: " + getIsAdmin() + "\n" +
                "Text: " + getTaskText() + "\n" +
                "Date: " + getTaskDate() + "\n" +
                "Is active: " + getIsActive();
    }
}