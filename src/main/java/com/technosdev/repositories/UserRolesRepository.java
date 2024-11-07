package com.technosdev.repositories;


import com.technosdev.entities.RoleEntity;
import com.technosdev.entities.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRolesEntity, Long> {

    @Query("SELECT re FROM RoleEntity re JOIN UserRolesEntity ure ON ure.roleEntity.id = re.id JOIN UserEntity ue ON ue.id = ure.userEntity.id WHERE ue.id = :userId")
    List<RoleEntity> findRoleByUserId(@Param("userId") Long id);

}
