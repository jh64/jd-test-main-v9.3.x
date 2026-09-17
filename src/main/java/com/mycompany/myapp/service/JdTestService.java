package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.JdTest;
import com.mycompany.myapp.repository.JdTestRepository;
import com.mycompany.myapp.service.dto.JdTestDTO;
import com.mycompany.myapp.service.mapper.JdTestMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.JdTest}.
 */
@Service
@Transactional
public class JdTestService {

    private static final Logger LOG = LoggerFactory.getLogger(JdTestService.class);

    private final JdTestRepository jdTestRepository;

    private final JdTestMapper jdTestMapper;

    public JdTestService(JdTestRepository jdTestRepository, JdTestMapper jdTestMapper) {
        this.jdTestRepository = jdTestRepository;
        this.jdTestMapper = jdTestMapper;
    }

    /**
     * Save a jdTest.
     *
     * @param jdTestDTO the entity to save.
     * @return the persisted entity.
     */
    public JdTestDTO save(JdTestDTO jdTestDTO) {
        LOG.debug("Request to save JdTest : {}", jdTestDTO);
        JdTest jdTest = jdTestMapper.toEntity(jdTestDTO);
        jdTest = jdTestRepository.save(jdTest);
        return jdTestMapper.toDto(jdTest);
    }

    /**
     * Update a jdTest.
     *
     * @param jdTestDTO the entity to save.
     * @return the persisted entity.
     */
    public JdTestDTO update(JdTestDTO jdTestDTO) {
        LOG.debug("Request to update JdTest : {}", jdTestDTO);
        JdTest jdTest = jdTestMapper.toEntity(jdTestDTO);
        jdTest = jdTestRepository.save(jdTest);
        return jdTestMapper.toDto(jdTest);
    }

    /**
     * Partially update a jdTest.
     *
     * @param jdTestDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JdTestDTO> partialUpdate(JdTestDTO jdTestDTO) {
        LOG.debug("Request to partially update JdTest : {}", jdTestDTO);

        return jdTestRepository
            .findById(jdTestDTO.getId())
            .map(existingJdTest -> {
                jdTestMapper.partialUpdate(existingJdTest, jdTestDTO);

                return existingJdTest;
            })
            .map(jdTestRepository::save)
            .map(jdTestMapper::toDto);
    }

    /**
     * Get one jdTest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<JdTestDTO> findOne(Long id) {
        LOG.debug("Request to get JdTest : {}", id);
        return jdTestRepository.findById(id).map(jdTestMapper::toDto);
    }

    /**
     * Delete the jdTest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete JdTest : {}", id);
        jdTestRepository.deleteById(id);
    }
}
