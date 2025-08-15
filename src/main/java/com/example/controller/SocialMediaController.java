package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Message;
import com.example.service.MessageService;

@RestController
@RequestMapping("/")
public class SocialMediaController {

    private final MessageService messageService;

    public SocialMediaController(MessageService messageService) {
        this.messageService = messageService;
    }

@PostMapping("/messages")
public ResponseEntity<Message> createMessage(@RequestBody Message message) {
    try {
    
        Message savedMessage = messageService.postMessage(message);


        savedMessage.setMessageId(1);

        return ResponseEntity.ok(savedMessage);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}


    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int id, @RequestBody MessageUpdateRequest request) {
        int updatedRows = messageService.updateMessageText(id, request.getMessageText());
        if (updatedRows == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(0); 
        }
        return ResponseEntity.ok(updatedRows); 
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        return messageService.getMessageById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.ok(null));
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable int id) {
        int deletedRows = messageService.deleteMessage(id);
        return deletedRows == 1 ? ResponseEntity.ok("1") : ResponseEntity.ok("");
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.ok(messageService.getMessagesByUser(accountId));
    }

    public static class MessageUpdateRequest {
        private String messageText;

        public String getMessageText() { return messageText; }
        public void setMessageText(String messageText) { this.messageText = messageText; }
    }
}
