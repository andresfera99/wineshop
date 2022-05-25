package com.project.wineshop;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Type {
    private @Id
    @GeneratedValue Integer id;
    private String name;

    public Type() {
    }

    Type(String name) {
        this.name = name;

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Type{" + "id=" + this.id + ", name='" + this.name + '\'' + '}';
    }

}
