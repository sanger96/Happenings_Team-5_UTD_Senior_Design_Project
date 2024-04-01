package com.services.api.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.services.api.entity.Interest;
import com.services.api.service.InterestService;

@RestController
@RequestMapping("interest")
public class InterestController {
    
    @Autowired
    private InterestService service;

    @PostMapping("/add")
    public Interest add(@RequestBody Interest interest) {
        return service.add(interest);
    }

    @PostMapping("/addMany")
    public List<Interest> addMany(@RequestBody List<Interest> interests) {
        return service.addMany(interests);
    }

    @GetMapping("/getById/{id}")
    public Interest getById(@PathVariable int id) {
        return service.getById(id);
    }

    @GetMapping("/getByName/{name}")
    public Interest getByName(@PathVariable String name) {
        return service.getByName(name);
    }

    @GetMapping("/getAll")
    public List<Interest> getAll() {
        return service.getAll();
    }

    @PutMapping("/update")
    public Interest update(@RequestBody Interest interest) {
        return service.add(interest);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable int id) {
        return service.deleteById(id);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody Interest interest) {
        return service.delete(interest);
    }

    @PostMapping("/populateInterests")
    public List<Interest> populateInterests() {
      
        List<Interest> interests = new ArrayList<>();

        HashSet<String> list = new HashSet<>(Arrays.asList(
            "Accounting",
            "Accounting And Analytics",
            "Actuarial Science",
            "American Studies",
            "Applied Cognition And Neuroscience",
            "Applied Sociology",
            "Art History",
            "Arts Technology And Emerging Communication",
            "Audiology",
            "Biochemistry",
            "Bioinformatics And Computational Biology",
            "Biology",
            "Biomedical Engineering",
            "Biotechnology",
            "Business Administration",
            "Business Analytics",
            "Chemistry",
            "Child Learning And Development",
            "Cognition And Neuroscience",
            "Cognitive Science",
            "Computer Engineering",
            "Computer Information Systems And Technology",
            "Computer Science",
            "Criminology",
            "Cyber Security, Technology And Policy",
            "Data Science",
            "Economics",
            "Education",
            "Electrical Engineering",
            "Energy Management",
            "Finance",
            "Financial Technology And Analytics",
            "Geosciences",
            "Geospatial Information Sciences",
            "Global Business",
            "Healthcare Leadership And Management",
            "Healthcare Management",
            "Healthcare Studies",
            "History",
            "History Of Ideas",
            "Human Development And Early Childhood Disorders",
            "Human Resource Management",
            "Humanities",
            "Information Technology And Management",
            "Innovation And Entrepreneurship",
            "Interdisciplinary Studies",
            "International Management Studies",
            "International Political Economy",
            "Latin American Studies",
            "Leadership And Organizational Development",
            "Literature",
            "Management Science",
            "Marketing",
            "Materials Science And Engineering",
            "Mathematics",
            "Mathematics Education",
            "Mechanical Engineering",
            "Molecular And Cell Biology",
            "Molecular Biology",
            "Neuroscience",
            "Philosophy",
            "Physics",
            "Political Science",
            "Psychology",
            "Public Affairs",
            "Public Health",
            "Public Policy",
            "Public Policy And Political Economy",
            "Science Education",
            "Social Data Analytics And Research",
            "Sociology",
            "Software Engineering",
            "Speech Language And Hearing Sciences",
            "Speech-Language Pathology",
            "Statistics",
            "Supply Chain Management",
            "Supply Chain Management And Analytics",
            "Systems Engineering And Management",
            "Telecommunications Engineering",
            "Visual And Performing Arts" 
        ));

        for(String s : list)
            interests.add(new Interest(s));

        return service.addMany(interests);

    }
}
