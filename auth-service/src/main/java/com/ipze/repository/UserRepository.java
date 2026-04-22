package com.ipze.repository;

import com.ipze.model.postgres.Role;
import com.ipze.model.postgres.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUserID(UUID uuid);
//
//    Optional<User> findByUsername(String username);
//
//    Optional<User> findByUserID(UUID userID);
//
//    Optional<User> findByEmailOrUsername(String email, String username);
//
    Page<User> findAllByRole(Role role, Pageable pageable);
//
//    Page<User> findAllByActive(Boolean active, Pageable pageable);
//
//    Page<User> findAllByRoleAndActive(Role role, Boolean active, Pageable pageable);
//
//    boolean existsByUsername(String username);
//
//    boolean existsByPhoneNumber(String phoneNumber);
//
//    Optional<User> findByEmailAndActiveTrue(String email);
//
//    Optional<User> findByUsernameAndActiveTrue(String username);
//
//    @Modifying
//    @Query("UPDATE User u SET u.password = :password WHERE u.userID = :userID")
//    void updatePassword(UUID userID, String password);
//
//    @Modifying
//    @Query("UPDATE User u SET u.active = :active WHERE u.userID = :userID")
//    void updateActiveStatus(UUID userID, Boolean active);
//
//    @Modifying
//    @Query("""
//        UPDATE User u\s
//        SET u.firstnameUKR = :firstname,
//            u.lastNameUKR = :lastname,
//            u.phoneNumber = :phone,
//            u.updatedAt = CURRENT_TIMESTAMP
//        WHERE u.userID = :userID
//   \s""")
//    void updateProfile(UUID userID, String firstname, String lastname, String phone);
//
//    @Query("SELECT u.role FROM User u WHERE u.userID = :userID")
//    Role findRoleByUserID(UUID userID);
//
//    @Query("SELECT COUNT(u) FROM User u WHERE u.active = true")
//    long countActiveUsers();
//
//    @Query("SELECT u FROM User u WHERE u.createdAt >= :date")
//    Page<User> findAllCreatedAfter(LocalDateTime date, Pageable pageable);
}