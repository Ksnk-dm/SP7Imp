package ua.kiev.prog;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Task, Long> {

    @Query("SELECT c FROM Task c WHERE c.group = :group")
    List<Task> findByGroup(@Param("group") Group group, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Task c WHERE c.group = :group")
    long countByGroup(@Param("group") Group group);

    @Query("SELECT c FROM Task c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :pattern, '%'))")
    List<Task> findByPattern(@Param("pattern") String pattern,
                             Pageable pageable);

    // List<Contact> findByNameOrEmailOrderById(String s);
    // List<Contact> findByNameAndEmail(String name, String email);
}
