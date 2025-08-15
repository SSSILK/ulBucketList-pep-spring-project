package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message postMessage(Message message) throws Exception {
        // Validate message text
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            throw new Exception("Bad request");
        }

        // Validate postedBy exists
        if (message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy())) {
            throw new Exception("Account does not exist");
        }

        // Ensure ID is null for auto-generation
        message.setMessageId(null);

        // Save and return
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int messageId) {
        return messageRepository.findById(messageId);
    }

    public int updateMessageText(int messageId, String newText) {
        if (newText == null || newText.isBlank() || newText.length() > 255) {
            return 0;
        }

        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isEmpty()) return 0;

        Message message = optionalMessage.get();
        message.setMessageText(newText);
        messageRepository.save(message);
        return 1;
    }

    public int deleteMessage(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
