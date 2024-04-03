package org.zerock.apiserver.service;

import jakarta.transaction.Transactional;
import org.zerock.apiserver.domain.Todo;
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.PageResponseDTO;
import org.zerock.apiserver.dto.TodoDTO;

@Transactional
public interface TodoService {

    TodoDTO get(Long tno);

    //등록
    Long register(TodoDTO dto);

    //수정
    void modify(TodoDTO dto);

    void remove(Long tno);

    PageResponseDTO<TodoDTO> getList(PageRequestDTO pageRequestDTO);

    default  TodoDTO entityToDTO(Todo todo){

      return
                TodoDTO.builder()
                        .tno(todo.getTno())
                        .title(todo.getTitle())
                        .content(todo.getContent())
                        .dueDate(todo.getDueDate())
                        .build();
    }

    default  Todo dtoToEntity(TodoDTO todoDTO){

        return
                Todo.builder()
                        .tno(todoDTO.getTno())
                        .title(todoDTO.getTitle())
                        .content(todoDTO.getContent())
                        .dueDate(todoDTO.getDueDate())
                        .build();
    }
}
