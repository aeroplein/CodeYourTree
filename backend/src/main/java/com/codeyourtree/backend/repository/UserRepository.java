package com.codeyourtree.backend.repository;

import com.codeyourtree.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Default olarak JpaRepository bize findById verir.
    //Biz kullanıcıları username ile bulmak istediğim için buna uygun emtot eklicez.
    //Bu optional kısımları da mesela girdiğimiz username tabloda yoksa java nullPointerException atar, program çöker.
    //Optional bir kutudur o username varsa içinden çıkar yoksa boş döner ama program çökmez.
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
