package br.com.todolist.desafiotodolist.repository;

import br.com.todolist.desafiotodolist.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}