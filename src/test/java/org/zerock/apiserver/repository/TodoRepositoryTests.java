package org.zerock.apiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.apiserver.domain.Todo;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test1(){

        Assertions.assertNotNull(todoRepository);

        log.info(todoRepository.getClass().getName());
    }


    @Test
    public void testInsert(){

      for(int i = 0;i< 100; i++) {

          Todo todo = Todo.builder()
                  .title("Title"+i)
                  .content("Content..."+i)
                  .dueDate(LocalDate.of(2024, 04, 01))
                  .build();

          Todo result = todoRepository.save(todo);

          log.info(result);
      }
    }

    @Test
    public void testRead(){

        Long tno = 1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        log.info(todo);

    }

    @Test
    public void testUpdate(){
        //먼저 로딩 하고 엔티티 객체를 변경 /setter
        Long tno = 1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        todo.setTitle("Update Title");
        todo.setContent("Updated Content");
        todo.setComplete(true);

        todoRepository.save(todo);
    }

    @Test
    public void testPaging(){
        //페이지 번호는 0부터
        Pageable pageable = PageRequest.of(0,10, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info(result.getTotalElements());

        log.info(result.getContent());

    }

   /* @Test
    public void testSearch1(){
        todoRepository.search1();
    }*/
}
