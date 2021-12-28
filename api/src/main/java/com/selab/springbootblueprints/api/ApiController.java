package com.selab.springbootblueprints.api;

import com.selab.springbootblueprints.web.service.UserService;
import com.selab.springbootblueprints.web.model.entity.projection.UserPageableInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;

    @GetMapping("/user/list")
    public ResponseEntity<Page<UserPageableInfoVO>> getUserList( @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserPageableInfoVO> userPage = userService.getAllUserList(pageable);

        return new ResponseEntity<>(userPage, HttpStatus.OK);
    }

}
