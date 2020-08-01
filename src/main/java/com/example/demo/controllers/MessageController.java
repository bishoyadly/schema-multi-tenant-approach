package com.example.demo.controllers;

import com.example.demo.entities.Message;
import com.example.demo.repositories.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tenants/{tenantId}/messages")
public class MessageController {

    private MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Page<Message> getMessages(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message createMessage(@RequestBody Message message) {
        return repository.save(message);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(@RequestParam String uuid) {
        repository.deleteById(uuid);
    }
}
