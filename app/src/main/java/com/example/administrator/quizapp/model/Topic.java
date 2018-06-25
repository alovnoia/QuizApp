package com.example.administrator.quizapp.model;

public class Topic {

    String id, name, desc;
    boolean status;

    public Topic(String id, String name, String desc, boolean status) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.status = status;
    }

    public Topic(Topic topic) {
        this.id = topic.getId();
        this.name = topic.getName();
        this.desc = topic.getDesc();
        this.status = topic.isStatus();
    }

    public Topic() {
        this.id = null;
        this.name = null;
        this.desc = null;
        this.status = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return getName();
    }
}
