package com.careconnect.nursinghome.domain.chat.repository;

import com.careconnect.nursinghome.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByMemberIdCustomerOrMemberIdFacility(Long memberIdCustomer, Long memberIdFacility);
}