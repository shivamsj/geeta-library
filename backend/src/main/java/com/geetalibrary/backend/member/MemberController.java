package com.geetalibrary.backend.member;

import com.geetalibrary.backend.member.dto.MemberRequest;
import com.geetalibrary.backend.member.dto.MemberResponse;
import com.geetalibrary.backend.user.User;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService service;
    public MemberController(MemberService service) { this.service = service; }

    @GetMapping public List<MemberResponse> findAll(@AuthenticationPrincipal User user) { return service.findAll(user); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public MemberResponse create(@AuthenticationPrincipal User user, @Valid @RequestBody MemberRequest request) {
        return service.create(user, request);
    }
    @PutMapping("/{id}")
    public MemberResponse update(@AuthenticationPrincipal User user, @PathVariable Long id,
                                 @Valid @RequestBody MemberRequest request) { return service.update(user, id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal User user, @PathVariable Long id) { service.delete(user, id); }
}
