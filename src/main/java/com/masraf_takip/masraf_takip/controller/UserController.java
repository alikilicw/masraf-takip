package com.masraf_takip.masraf_takip.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masraf_takip.masraf_takip.model.User;
import com.masraf_takip.masraf_takip.service.UserService;
import com.masraf_takip.masraf_takip.util.ApiError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    @ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all users"),
    @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.findAll());
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a single user by their ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval of user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            User user = userService.findById(id);
            if(user == null) throw new ApiError(HttpStatus.NOT_FOUND, "User not found.");
            return ResponseEntity.ok(user);
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @Operation(summary = "Add a new user", description = "Create a new user.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful addition of user"),
        @ApiResponse(responseCode = "400", description = "Bad request, missing required fields"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping()
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {

            if(user.getName() == null) throw new ApiError(HttpStatus.BAD_REQUEST, "Please send a name");
            if(user.getEmail() == null) throw new ApiError(HttpStatus.BAD_REQUEST, "Please send an email");
            if(user.getPassword() == null) throw new ApiError(HttpStatus.BAD_REQUEST, "Please send a password");

            return ResponseEntity.ok(userService.add(user));
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @Operation(summary = "Update an existing user", description = "Update the details of an existing user by their ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful update of user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User userDetails) {
        try {
            User user = userService.update(id, userDetails);
            if(user == null) throw new ApiError(HttpStatus.NOT_FOUND, "User not found.");
            return ResponseEntity.ok(user);
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

    @Operation(summary = "Delete a user by ID", description = "Delete a user by their ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful deletion of user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        try {
            User user = userService.deleteById(id);
            if(user == null) throw new ApiError(HttpStatus.NOT_FOUND, "User not found.");
            return ResponseEntity.ok().build();
        } catch(ApiError e) {
            return ResponseEntity.status(e.geHttpStatus()).body(e.getMessage());    
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());    
        }
    }

}
