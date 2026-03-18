package com.student.student_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.aot.generate.GeneratedTypeReference;

@Entity
@Table(name="STUDENT")
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @Min(value=18, message = "Age should be greater than 18")
    @Max(value=30, message = "Age should be lesser than 30")
    private int age;

    public Student() {
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }

}
