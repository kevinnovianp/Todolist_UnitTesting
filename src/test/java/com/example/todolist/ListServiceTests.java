package com.example.todolist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ListServiceTests {
    
    @Autowired
    private ListService service;

    @Test
    @Rollback(false)
    @Order(1)
    public void testCreateProduct() throws ParseException{
        CustomList list = new CustomList("List 1", new SimpleDateFormat("yyyyMMdd").parse("20200101"), "This is first list", "Ethan Wang");
        CustomList savedList = service.addList(list);
        System.out.println(list);
        assertNotNull(savedList);
    }

    @Test
    @Order(2)
    public void testFindAllList(){
        List<CustomList> lists = (List<CustomList>) service.getAllLists();
        for(CustomList l:lists){
            System.out.println(l);
        }
        Assertions.assertThat(lists.size()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void testFindListById(){
        Integer id = 1;
        boolean currList = service.findList(id).isPresent();
        assertTrue(currList);
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void testUpdateList() throws ParseException{
        String title = "New List";
        Integer id = 1;
        CustomList list = new CustomList(title, new SimpleDateFormat("yyyyMMdd").parse("20200101"), "This is first list", "Ethan Wang");
        list.setId(1);
        service.updateList(list, id);
        Optional<CustomList> updatedList = service.findList(id);
        Assertions.assertThat(updatedList.isPresent());
    }

    @Test
    @Rollback(false)
    @Order(5)
    public void testDeleteList(){
        Integer id = 1;
        assertTrue(service.findList(id).isPresent());
        service.deleteList(id);
        assertFalse(service.findList(id).isPresent());
    }
}
