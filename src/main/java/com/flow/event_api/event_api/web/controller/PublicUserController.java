package com.flow.event_api.event_api.web.controller;

import com.flow.event_api.event_api.mapper.UserMapper;
import com.flow.event_api.event_api.service.UserService;
import com.flow.event_api.event_api.web.dto.CreateUserRequest;
import com.flow.event_api.event_api.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public/user")
@RequiredArgsConstructor
public class PublicUserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userMapper.toDto(userService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody CreateUserRequest request) {
        var createdUser = userService.registerUser(userMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(createdUser));
    }

}