package com.sayjong.backend.user.controller;

import com.sayjong.backend.user.dto.MyPageResponseDto;
import com.sayjong.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //마이페이지 조회
    @GetMapping("/me")
    public ResponseEntity<MyPageResponseDto> getMyPageInfo(
            @AuthenticationPrincipal UserDetails userDetails) {
        String loginId = userDetails.getUsername();
        MyPageResponseDto myPageInfo = userService.getMyPageInfo(loginId);

        return ResponseEntity.ok(myPageInfo);

    }
}
