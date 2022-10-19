package com.example.todolist;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ListDatabaseTests {
    
    @Autowired
    private ListRepository repo;

    @Test
    @Rollback(false)
    @Order(1)
    public void testCreateProduct() throws ParseException{
        List<CustomList> lists = new ArrayList<CustomList>();
        CustomList list = new CustomList("List 1", new SimpleDateFormat("yyyyMMdd").parse("20200101"), "This is first list", "Ethan Wang");
        lists.add(list);
        List<CustomList> savedList = repo.saveAll(lists);
        assertNotNull(savedList);
    }

    @Test
    @Order(2)
    public void testFindListByTitle(){
        String title = "List 1";
        CustomList list = repo.findByTitle(title);
        Assertions.assertThat(list.getTitle()).isEqualTo(title);
    }

    @Test
    @Order(3)
    public void testFindListByTitleNoExist(){
        String title = "List 2";
        CustomList list = repo.findByTitle(title);
        assertNull(list);
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void testUpdateList() throws ParseException{
        String title = "List 11";
        CustomList list = new CustomList(title, new SimpleDateFormat("yyyyMMdd").parse("20200101"), "This is first list", "Ethan Wang");
        list.setId(1);
        repo.save(list);
        CustomList updatedList = repo.findByTitle(title);
        Assertions.assertThat(updatedList.getTitle()).isEqualTo(title);
    }

    @Test
    @Order(5)
    public void testFindAllList(){
        List<CustomList> lists = (List<CustomList>) repo.findAll();
        for(CustomList l:lists){
            System.out.println(l);
        }
        Assertions.assertThat(lists.size()).isGreaterThan(0);
    }

    @Test
    @Rollback(false)
    @Order(6)
    public void testDeleteList(){
        Integer id = 1;
        boolean isExistsBeforeDelete = repo.findById(id).isPresent();
        repo.deleteById(id);
        boolean isExistsAfterDelete = repo.findById(id).isPresent();
        assertTrue(isExistsBeforeDelete);
        assertFalse(isExistsAfterDelete);
    }
}
