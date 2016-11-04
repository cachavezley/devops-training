package com.thoughtworks;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.URI;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class TodoControllerTest {

    private String someTodoTitle;
    private Todo someTodo;
    private Todo patch;
    private TodoController controller;

    @Before
    public void setUp() throws Exception {
        patch = new Todo();
        someTodoTitle = "a todo";
        someTodo = new Todo();
        someTodo.setTitle(someTodoTitle);
        controller = new TodoController();
    }

    @Test
    public void shouldEchoBackATodo() {
        TodoController controller = new TodoController();

        Todo responseTodo = controller.createTodo(someTodo, new MockHttpServletRequest());
        assertThat(responseTodo.getTitle(), equalTo(someTodoTitle));
    }

    @Test
    public void shouldRespondOkWhenDeletingATodo() {
        TodoController controller = new TodoController();
        Todo responseTodo = controller.createTodo(someTodo, new MockHttpServletRequest());
        assertThat(responseTodo.getTitle(), equalTo(someTodoTitle));
    }

    @Test
    public void shouldHaveTodoPresentInCollectionAfterCreation() {
        someTodo.setTitle("lol i exist forever");

        controller.createTodo(someTodo, new MockHttpServletRequest());

        List<Todo> aBunchOfTodos = controller.getAll();

        assertThat(aBunchOfTodos.size(), is(1));
        assertThat(aBunchOfTodos.get(0).getTitle(), is(someTodo.getTitle()));
    }

    @Test
    public void shouldDeleteAllTodosIfNoIdIsSpecified() {
        someTodo.setTitle("Delete all the things");

        controller.createTodo(someTodo, new MockHttpServletRequest());
        controller.deleteAll();

        List<Todo> aBunchOfTodos = controller.getAll();
        assertThat(aBunchOfTodos.size(), is(0));
    }

    @Test
    public void shouldHavePropertyCompletedAsFalse() {
        someTodo.setTitle("A new todo I'm never going to do");

        controller.createTodo(someTodo, new MockHttpServletRequest());

        assertFalse(controller.getTodoById(0).isCompleted());
    }

    @Test
    public void shouldHaveUrl() {
        someTodo.setTitle("A new todo I'm never going to do");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        controller.createTodo(someTodo, mockRequest);

        URI url = controller.getTodoById(0).getUrl();
        assertThat(url.toString(), is(mockRequest.getRequestURL() + "/0"));
    }

    @Test
    public void shouldReturnTheTodoIdentifiedByItsId() {
        someTodo.setTitle("A new todo I'm definitely going to do");
        controller.createTodo(someTodo, new MockHttpServletRequest());

        Todo savedTodo = controller.getTodoById(0);
        assertThat(savedTodo.getTitle(), is(someTodo.getTitle()));
    }


    @Test
    public void shouldUpdateTheTitleOfAnExistingTodo() {
        someTodo.setTitle("Buy Dan Brunch");
        controller.createTodo(someTodo, new MockHttpServletRequest());

        patch.setTitle("Buy Dan Lunch");
        patch.setCompleted(null);
        patch.setUrl(null);

        someTodo.patchWith(patch);;

        Todo updated = controller.getTodoById(0);
        assertThat(updated.getTitle(), is(patch.getTitle()));
        assertThat(updated.isCompleted(), is(false));
    }

    @Test
    public void shouldUpdateTheCompletednessOfAnExistingTodo() {
        someTodo.setTitle("Get Coffee");
        controller.createTodo(someTodo, new MockHttpServletRequest());

        patch.setTitle(null);
        patch.setCompleted(true);
        patch.setUrl(null);

        someTodo.patchWith(patch);
        ;

        Todo updated = controller.getTodoById(0);
        assertThat(updated.isCompleted(), is(true));
    }

    @Test
    public void shouldUpdateTheOrderOfAnExistingTodo() {
        someTodo.setTitle("Do Laundry");
        controller.createTodo(someTodo, new MockHttpServletRequest());

        patch.setTitle(null);
        patch.setCompleted(null);
        patch.setUrl(null);
        patch.setOrder(100);

        someTodo.patchWith(patch);

        Todo updated = controller.getTodoById(0);
        assertThat(updated.getOrder(), is(100));
    }

    @Test
    public void shouldDeleteById() {
        someTodo.setTitle("Start Vagrant");
        controller.createTodo(someTodo, new MockHttpServletRequest());

        controller.deleteTodo(0);

        assertThat(controller.getAll().size(), is(0));
        assertNull(controller.getTodoById(0));
    }
}