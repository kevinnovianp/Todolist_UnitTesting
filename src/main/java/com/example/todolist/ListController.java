package com.example.todolist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ListController {
    @Autowired
    private ListService service;

    @PostMapping("/add")
    public String addList(@RequestBody CustomList list){
        service.addList(list);
        return "New list added!";
    }

    @GetMapping("/all")
    public List<CustomList> getAllLists(){
        return service.getAllLists();
    }

    @GetMapping("/find/{id}")
    public Optional<CustomList> findList(@PathVariable int id){
        return service.findList(id);
    }

    @PutMapping("/update/{id}")
    public String updateList(@RequestBody CustomList list, @PathVariable int id){
        service.updateList(list, id);
        return "Exist list updated!";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteList(@PathVariable int id){
        service.deleteList(id);
        return "Chosen list deleted!";
    }
}
