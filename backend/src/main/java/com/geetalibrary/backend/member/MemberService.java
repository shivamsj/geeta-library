package com.geetalibrary.backend.member;

import com.geetalibrary.backend.common.ResourceNotFoundException;
import com.geetalibrary.backend.member.dto.MemberRequest;
import com.geetalibrary.backend.member.dto.MemberResponse;
import com.geetalibrary.backend.user.User;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository members;
    public MemberService(MemberRepository members) { this.members = members; }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll(User owner) {
        return members.findAllByOwnerIdOrderByNameAsc(owner.getId()).stream().map(this::response).toList();
    }

    @Transactional
    public MemberResponse create(User owner, MemberRequest request) {
        Member member = new Member(owner);
        apply(member, request);
        return response(members.save(member));
    }

    @Transactional
    public MemberResponse update(User owner, Long id, MemberRequest request) {
        Member member = owned(owner, id);
        apply(member, request);
        return response(member);
    }

    @Transactional
    public void delete(User owner, Long id) { members.delete(owned(owner, id)); }

    private Member owned(User owner, Long id) {
        return members.findByIdAndOwnerId(id, owner.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
    }

    private void apply(Member member, MemberRequest request) {
        member.update(request.name().trim(), request.mobile().trim(), clean(request.address()),
            clean(request.seatNumber()), clean(request.planName()), date(request.joiningDate()),
            date(request.expiryDate()), request.dueAmount(), request.status());
    }

    private LocalDate date(String value) {
        if (value == null || value.isBlank()) return null;
        try { return LocalDate.parse(value); }
        catch (DateTimeParseException exception) { throw new IllegalArgumentException("Dates must use YYYY-MM-DD format"); }
    }

    private String clean(String value) { return value == null ? "" : value.trim(); }

    private MemberResponse response(Member member) {
        return new MemberResponse(member.getId().toString(), member.getName(), member.getMobile(), member.getAddress(),
            member.getSeatNumber(), member.getPlanName(), text(member.getJoiningDate()), text(member.getExpiryDate()),
            member.getDueAmount(), member.getStatus(), member.getUpdatedAt().toEpochMilli());
    }

    private String text(LocalDate date) { return date == null ? "" : date.toString(); }
}
