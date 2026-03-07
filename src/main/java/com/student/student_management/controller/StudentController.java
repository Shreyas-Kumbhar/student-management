package com.student.student_management.controller;
//changed
import com.student.student_management.model.Student;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.student.student_management.repository.StudentRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    private final StudentRepository repo;

    public StudentController(StudentRepository repo) {
        this.repo = repo;
    }

    // Home page - list students
    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("students", repo.findAll());
        return "index";
    }

    // Show Add Form
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    // Save student (POST)
    @PostMapping("/save")
    public String saveStudent(@Valid Student student,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "add-student";
        }
        repo.save(student);
        return "success";
    }

    // Show Update Form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable long id, Model model) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        model.addAttribute("student", student);
        return "update";
    }

    // Update student (POST)
    @PostMapping("/update")
    public String updateStudent(@Valid Student student,
                                BindingResult result) {
        if (result.hasErrors()) {
            return "update";
        }
        repo.save(student);
        return "redirect:/";
    }

    // Delete student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable long id) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        repo.delete(student);
        return "redirect:/";
    }

    // Success page (optional)
    @GetMapping("/success")
    public String successPage() {
        return "success";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable int pageNo, Model model) {
        int pageSize = 5; // show 5 students per page
        Page<Student> page = repo.findAll(PageRequest.of(pageNo - 1, pageSize));
        model.addAttribute("students", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        return "index";
    }

    @GetMapping("/search")
    public String searchStudents(@RequestParam("keyword") String keyword, Model model) {
        List<Student> result = repo.findByNameContainingIgnoreCase(keyword);
        model.addAttribute("students", result);
        model.addAttribute("currentPage", 1);
        model.addAttribute("totalPages", 1); // No pagination for search
        return "index";
    }


}