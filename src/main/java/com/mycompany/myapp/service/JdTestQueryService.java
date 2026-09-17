package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.JdTest;
import com.mycompany.myapp.repository.JdTestRepository;
import com.mycompany.myapp.service.criteria.JdTestCriteria;
import com.mycompany.myapp.service.dto.JdTestDTO;
import com.mycompany.myapp.service.mapper.JdTestMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link JdTest} entities in the database.
 * The main input is a {@link JdTestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link JdTestDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class JdTestQueryService extends QueryService<JdTest> {

    private static final Logger LOG = LoggerFactory.getLogger(JdTestQueryService.class);

    private final JdTestRepository jdTestRepository;

    private final JdTestMapper jdTestMapper;

    public JdTestQueryService(JdTestRepository jdTestRepository, JdTestMapper jdTestMapper) {
        this.jdTestRepository = jdTestRepository;
        this.jdTestMapper = jdTestMapper;
    }

    /**
     * Return a {@link Page} of {@link JdTestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<JdTestDTO> findByCriteria(JdTestCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<JdTest> specification = createSpecification(criteria);
        return jdTestRepository.findAll(specification, page).map(jdTestMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(JdTestCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<JdTest> specification = createSpecification(criteria);
        return jdTestRepository.count(specification);
    }

    /**
     * Function to convert {@link JdTestCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<JdTest> createSpecification(JdTestCriteria criteria) {
        Specification<JdTest> specification = Specification.unrestricted();
        specification = specification.and((root, query, builder) -> {
            if (Long.class != query.getResultType()) {
                root.fetch(JdTest_.parent, JoinType.LEFT);
            }
            return null;
        });
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = specification.and(
                Specification.allOf(
                    Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : Specification.unrestricted(),
                    buildRangeSpecification(criteria.getId(), JdTest_.id),
                    buildStringSpecification(criteria.getName(), JdTest_.name),
                    buildStringSpecification(criteria.getClassification(), JdTest_.classification),
                    buildRangeSpecification(criteria.getNodeId(), JdTest_.nodeId),
                    buildStringSpecification(criteria.getNode(), JdTest_.node),
                    buildRangeSpecification(criteria.getCode(), JdTest_.code),
                    buildStringSpecification(criteria.getCodeDescription(), JdTest_.codeDescription),
                    buildSpecification(criteria.getParentId(), root -> root.join(JdTest_.parent, JoinType.LEFT).get(JdTest_.id)),
                    buildSpecification(criteria.getChildId(), root -> root.join(JdTest_.children, JoinType.LEFT).get(JdTest_.id))
                )
            );
        }
        return specification;
    }
}
