package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.JdTest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the JdTest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JdTestRepository extends JpaRepository<JdTest, Long>, JpaSpecificationExecutor<JdTest> {}
