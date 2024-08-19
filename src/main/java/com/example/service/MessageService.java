package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    // private AccountService accountService;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        // this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    public void addNewMessage(Message newMessage) {

        if (newMessage.getMessageText() == null || newMessage.getMessageText().isBlank()) {
            throw new IllegalArgumentException("Message text cannot be blank");
        }
        if (newMessage.getMessageText().length() > 255) {
            throw new IllegalArgumentException("Message text cannot be over 255 characters");
        }
        if (newMessage.getPostedBy() == null ) { 
            throw new IllegalArgumentException("Posted by account is required");
        }
        
        if (!accountRepository.existsById(newMessage.getPostedBy())) { 
            throw new IllegalArgumentException("Posted by account is required");
        }
        messageRepository.save(newMessage);
    }

    public List<Message> getMessageList(){
        return (List<Message>) messageRepository.findAll();
    }

    public Message getMessageById(Integer messagId)throws ResourceNotFoundException{
        return  (Message) messageRepository.findById(messagId)
                                    .orElseThrow(()->new ResourceNotFoundException(messagId + " Message was not found."));
    }

    // had issues that could not solve with a void method.
    public boolean deleteMessageById(Integer messageId){
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true; // Message existed and was deleted
        } else {
            return false; // Message did not exist
        }
        
    }

    public void updateMessageById(Integer messageId, String newMessageText) throws ResourceNotFoundException {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new ResourceNotFoundException("Message with ID " + messageId + " was not found."));
    
        if (newMessageText == null || newMessageText.isBlank()) {
            throw new IllegalArgumentException("Message text cannot be blank");
        }
        if (newMessageText.length() > 255) {
            throw new IllegalArgumentException("Message text cannot be over 255 characters");
        }
    
        message.setMessageText(newMessageText);
        messageRepository.save(message);
    }

    public List<Message> getAllMessagesByUserId(Integer postedBy)throws ResourceNotFoundException{
        return  messageRepository.findAllByPostedBy(postedBy);
                                    
    }
    
}
