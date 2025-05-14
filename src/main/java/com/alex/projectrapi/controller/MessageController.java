package com.alex.projectrapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    @GetMapping("/messages")
    public List<String> getMessages() {
        return List.of("Hola desde el backend", "Mensaje secreto protegido");
    }
}

