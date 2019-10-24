package ru.itis.longpollingtokens.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.longpollingtokens.models.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
