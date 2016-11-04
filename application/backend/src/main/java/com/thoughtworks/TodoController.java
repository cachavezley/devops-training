package com.thoughtworks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TodoController {
    Map<Integer, Todo> todos;

    int currentId;

    public TodoController() {
        todos = new HashMap<Integer, Todo>();
        currentId = 0;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public @ResponseBody List<Todo> getAll() {
        return new ArrayList<Todo>(todos.values());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public @ResponseBody Todo getTodoById(@PathVariable int id) {
        return todos.get(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public @ResponseBody Todo createTodo(@RequestBody final Todo todo, HttpServletRequest request) {
        todo.setUrl(URI.create(request.getRequestURL() + "/" + currentId));
        todos.put(currentId++, todo);
        return todo;
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/{id}")
    public @ResponseBody Todo update(@PathVariable int id, @RequestBody Todo todo) {
        return todos.get(id).patchWith(todo);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/")
    public @ResponseBody void deleteAll() {
        todos.clear();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public @ResponseBody void deleteTodo(@PathVariable int id) {
        todos.remove(id);
    }
}
