package com.flow.event_api.event_api.repository;

import com.flow.event_api.event_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select distinct u.email from User u " +
           "left join u.subscribedCategories c " +
           "left join u.subscribedOrganizations o " +
           "where c.id IN :categoryIds OR o.id = :organizationId")
    Set<String> getEmailsBySubscriptions(@Param("categoryIds") Collection<Long> categoryIds,
                                         @Param("organizationId") Long organizationId);

    boolean existsByIdAndSubscribedCategoriesId(Long userId, Long categoryId);

    boolean existsByIdAndSubscribedOrganizationsId(Long userId, Long organizationId);

    boolean existsByUsernameOrEmail(String username, String email);
}
