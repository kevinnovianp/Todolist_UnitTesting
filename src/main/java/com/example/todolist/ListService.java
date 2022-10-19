package com.example.todolist;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListService {
    @Autowired
    private ListRepository repo;

    public CustomList addList(CustomList list){
        list.setStatus("undone");
        this.repo.save(list);
        return list;
    }

    public List<CustomList> getAllLists(){
        return this.repo.findAll();
    }

    public Optional<CustomList> findList(int id){
        return this.repo.findById(id);
    }

    public CustomList updateList(CustomList list, int id){
        CustomList currList = repo.findById(id).orElse(null);
        currList.setTitle(list.getTitle());
        currList.setDescription(list.getDescription());
        currList.setAuthor(list.getAuthor());
        currList.setDate(list.getDate());
        currList.setStatus(list.getStatus());
        repo.save(currList);
        return currList;
    }

    public void deleteList(int id){
        CustomList currList = repo.findById(id).orElse(null);
        repo.delete(currList);
    }
}
