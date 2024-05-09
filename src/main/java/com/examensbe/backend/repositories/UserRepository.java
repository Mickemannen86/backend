package com.examensbe.backend.repositories;

import com.examensbe.backend.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // UserEntity could be an OPTIONAL as well if you want it to be
    UserEntity findByUsername(String username);

}
