package dn.onlinelibrary.repository;

import com.sun.codemodel.util.UnicodeEscapeWriter;
import dn.onlinelibrary.entity.UserEntity;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {


    boolean existsByUsername(String username);

    boolean existsByEmail(String email);




}
