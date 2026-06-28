package com.geetalibrary.backend.member;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByOwnerIdOrderByNameAsc(Long ownerId);
    Optional<Member> findByIdAndOwnerId(Long id, Long ownerId);
}
