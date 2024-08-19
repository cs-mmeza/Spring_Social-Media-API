package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.UsernameAlreadyExistsException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody Account newAccount) {
        try {
            accountService.register(newAccount) ;
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Successfully registered");
        } catch (UsernameAlreadyExistsException e) {
            if (e.getMessage().equals("Username already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.badRequest().build();
            }
        }
        
    }
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account loginRequest) {
        try {
            Account authenticatedAccount = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(authenticatedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    
    @PostMapping("messages")
    public @ResponseBody ResponseEntity<Message> createPie(@RequestBody Message newMessage){
        try {
            messageService.addNewMessage(newMessage);
                return ResponseEntity.status(HttpStatus.OK)
                            .body(newMessage);
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
        }
    }
    @GetMapping("messages")
    public @ResponseBody ResponseEntity<List<Message>> getMessageList(){
        return new ResponseEntity<>(messageService.getMessageList(), HttpStatus.OK);
    }  

    @GetMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        try {
            return new ResponseEntity<>(messageService.getMessageById(messageId), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }
    @DeleteMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<String> deleteMessageById(@PathVariable Integer messageId) {
        boolean isDeleted = messageService.deleteMessageById(messageId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body("1"); // Message existed and was deleted
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(""); // Message did not exist, return empty body
        }
    }

    @PatchMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<String> patchMessageById(@PathVariable Integer messageId, 
                                                                 @RequestBody Map<String, String> updates){
        try {
            String newText = updates.get("messageText");
            messageService.updateMessageById(messageId, newText);
            return ResponseEntity.status(HttpStatus.OK).body("1");
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
 
    @GetMapping("accounts/{accountId}/messages")
    public @ResponseBody ResponseEntity<List<Message>> getAllMessagesByUserId(@PathVariable Integer accountId){
        return new ResponseEntity<>(messageService.getAllMessagesByUserId(accountId), HttpStatus.OK);
    }  

}
