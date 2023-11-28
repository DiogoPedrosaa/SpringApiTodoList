package br.com.todolist.desafiotodolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.todolist.desafiotodolist.entity.Todo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DesafioTodolistApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	@Transactional
	@Commit
	void testCreateTodoSuccess() {
		var todo = new Todo("Todo 1", "Desc todo 1", false, 1);
		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(todo)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody()
				.jsonPath("$").isArray()
				.jsonPath("$.length()").isEqualTo(1)
				.jsonPath("$[0].name()").isEqualTo(todo.getName())
				.jsonPath("$[0].description()").isEqualTo(todo.getDescription())
				.jsonPath("$[0].done()").isEqualTo(todo.isDone())
				.jsonPath("$[0].priority()").isEqualTo(todo.getPriority());
	}

	@Test
	@Transactional
	@Commit
	void testCreateTodoFailure() {
		webTestClient
				.post()
				.uri("/todos")
				.bodyValue(new Todo("", "", false, 0))
				.exchange()
				.expectStatus()
				.isBadRequest();
	}
}
