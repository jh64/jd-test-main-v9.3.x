package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.JdTestRepository;
import com.mycompany.myapp.service.JdTestQueryService;
import com.mycompany.myapp.service.JdTestService;
import com.mycompany.myapp.service.criteria.JdTestCriteria;
import com.mycompany.myapp.service.dto.JdTestDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.JdTest}.
 */
@RestController
@RequestMapping("/api/jd-tests")
public class JdTestResource {

    private static final Logger LOG = LoggerFactory.getLogger(JdTestResource.class);

    private static final String ENTITY_NAME = "jdTest";

    @Value("${jhipster.clientApp.name:jdTest}")
    private String applicationName;

    private final JdTestService jdTestService;

    private final JdTestRepository jdTestRepository;

    private final JdTestQueryService jdTestQueryService;

    public JdTestResource(JdTestService jdTestService, JdTestRepository jdTestRepository, JdTestQueryService jdTestQueryService) {
        this.jdTestService = jdTestService;
        this.jdTestRepository = jdTestRepository;
        this.jdTestQueryService = jdTestQueryService;
    }

    /**
     * {@code POST  /jd-tests} : Create a new jdTest.
     *
     * @param jdTestDTO the jdTestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jdTestDTO, or with status {@code 400 (Bad Request)} if the jdTest has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JdTestDTO> createJdTest(@Valid @RequestBody JdTestDTO jdTestDTO) throws URISyntaxException {
        LOG.debug("REST request to save JdTest : {}", jdTestDTO);
        if (jdTestDTO.getId() != null) {
            throw new BadRequestAlertException("A new jdTest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        jdTestDTO = jdTestService.save(jdTestDTO);
        return ResponseEntity.created(new URI("/api/jd-tests/" + jdTestDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, jdTestDTO.getId().toString()))
            .body(jdTestDTO);
    }

    /**
     * {@code PUT  /jd-tests/:id} : Updates an existing jdTest.
     *
     * @param id the id of the jdTestDTO to save.
     * @param jdTestDTO the jdTestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jdTestDTO,
     * or with status {@code 400 (Bad Request)} if the jdTestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jdTestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JdTestDTO> updateJdTest(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody JdTestDTO jdTestDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update JdTest : {}, {}", id, jdTestDTO);
        if (jdTestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jdTestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jdTestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jdTestDTO = jdTestService.update(jdTestDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jdTestDTO.getId().toString()))
            .body(jdTestDTO);
    }

    /**
     * {@code PATCH  /jd-tests/:id} : Partial updates given fields of an existing jdTest, field will ignore if it is null
     *
     * @param id the id of the jdTestDTO to save.
     * @param jdTestDTO the jdTestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jdTestDTO,
     * or with status {@code 400 (Bad Request)} if the jdTestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jdTestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jdTestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JdTestDTO> partialUpdateJdTest(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody JdTestDTO jdTestDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update JdTest partially : {}, {}", id, jdTestDTO);
        if (jdTestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jdTestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jdTestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JdTestDTO> result = jdTestService.partialUpdate(jdTestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jdTestDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /jd-tests} : get all the Jd Tests.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of Jd Tests in body.
     */
    @GetMapping("")
    public ResponseEntity<List<JdTestDTO>> getAllJdTests(
        JdTestCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get JdTests by criteria: {}", criteria);

        Page<JdTestDTO> page = jdTestQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jd-tests/count} : count all the jdTests.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countJdTests(JdTestCriteria criteria) {
        LOG.debug("REST request to count JdTests by criteria: {}", criteria);
        return ResponseEntity.ok().body(jdTestQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /jd-tests/:id} : get the "id" jdTest.
     *
     * @param id the id of the jdTestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jdTestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JdTestDTO> getJdTest(@PathVariable("id") Long id) {
        LOG.debug("REST request to get JdTest : {}", id);
        Optional<JdTestDTO> jdTestDTO = jdTestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jdTestDTO);
    }

    /**
     * {@code DELETE  /jd-tests/:id} : delete the "id" jdTest.
     *
     * @param id the id of the jdTestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJdTest(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete JdTest : {}", id);
        jdTestService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
