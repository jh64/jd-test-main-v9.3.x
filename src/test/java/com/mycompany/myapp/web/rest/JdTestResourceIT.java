package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.JdTestAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.JdTest;
import com.mycompany.myapp.domain.JdTest;
import com.mycompany.myapp.repository.JdTestRepository;
import com.mycompany.myapp.service.dto.JdTestDTO;
import com.mycompany.myapp.service.mapper.JdTestMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

/**
 * Integration tests for the {@link JdTestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JdTestResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLASSIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_CLASSIFICATION = "BBBBBBBBBB";

    private static final Long DEFAULT_NODE_ID = 1L;
    private static final Long UPDATED_NODE_ID = 2L;
    private static final Long SMALLER_NODE_ID = 1L - 1L;

    private static final String DEFAULT_NODE = "AAAAAAAAAA";
    private static final String UPDATED_NODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;
    private static final Integer SMALLER_CODE = 1 - 1;

    private static final String DEFAULT_CODE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CODE_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/jd-tests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + 2L * Integer.MAX_VALUE);

    @Autowired
    private ObjectMapper om;

    @Autowired
    private JdTestRepository jdTestRepository;

    @Autowired
    private JdTestMapper jdTestMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJdTestMockMvc;

    private JdTest jdTest;

    private JdTest insertedJdTest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JdTest createEntity() {
        return new JdTest()
            .name(DEFAULT_NAME)
            .classification(DEFAULT_CLASSIFICATION)
            .nodeId(DEFAULT_NODE_ID)
            .node(DEFAULT_NODE)
            .code(DEFAULT_CODE)
            .codeDescription(DEFAULT_CODE_DESCRIPTION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JdTest createUpdatedEntity() {
        return new JdTest()
            .name(UPDATED_NAME)
            .classification(UPDATED_CLASSIFICATION)
            .nodeId(UPDATED_NODE_ID)
            .node(UPDATED_NODE)
            .code(UPDATED_CODE)
            .codeDescription(UPDATED_CODE_DESCRIPTION);
    }

    @BeforeEach
    void initTest() {
        jdTest = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedJdTest != null) {
            jdTestRepository.delete(insertedJdTest);
            insertedJdTest = null;
        }
    }

    @Test
    @Transactional
    void createJdTest() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the JdTest
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);
        var returnedJdTestDTO = om.readValue(
            restJdTestMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            JdTestDTO.class
        );

        // Validate the JdTest in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedJdTest = jdTestMapper.toEntity(returnedJdTestDTO);
        assertJdTestUpdatableFieldsEquals(returnedJdTest, getPersistedJdTest(returnedJdTest));

        insertedJdTest = returnedJdTest;
    }

    @Test
    @Transactional
    void createJdTestWithExistingId() throws Exception {
        // Create the JdTest with an existing ID
        jdTest.setId(1L);
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJdTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JdTest in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jdTest.setName(null);

        // Create the JdTest, which fails.
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        restJdTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClassificationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jdTest.setClassification(null);

        // Create the JdTest, which fails.
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        restJdTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNodeIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jdTest.setNodeId(null);

        // Create the JdTest, which fails.
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        restJdTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jdTest.setNode(null);

        // Create the JdTest, which fails.
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        restJdTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeDescriptionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        jdTest.setCodeDescription(null);

        // Create the JdTest, which fails.
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        restJdTestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllJdTests() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList
        restJdTestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jdTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].classification").value(hasItem(DEFAULT_CLASSIFICATION)))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID.intValue())))
            .andExpect(jsonPath("$.[*].node").value(hasItem(DEFAULT_NODE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeDescription").value(hasItem(DEFAULT_CODE_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getJdTest() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get the jdTest
        restJdTestMockMvc
            .perform(get(ENTITY_API_URL_ID, jdTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jdTest.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.classification").value(DEFAULT_CLASSIFICATION))
            .andExpect(jsonPath("$.nodeId").value(DEFAULT_NODE_ID.intValue()))
            .andExpect(jsonPath("$.node").value(DEFAULT_NODE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.codeDescription").value(DEFAULT_CODE_DESCRIPTION));
    }

    @Test
    @Transactional
    void getJdTestsByIdFiltering() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        Long id = jdTest.getId();

        defaultJdTestFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultJdTestFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultJdTestFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllJdTestsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where name equals to
        defaultJdTestFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJdTestsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where name in
        defaultJdTestFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJdTestsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where name is not null
        defaultJdTestFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllJdTestsByNameContainsSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where name contains
        defaultJdTestFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllJdTestsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where name does not contain
        defaultJdTestFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllJdTestsByClassificationIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where classification equals to
        defaultJdTestFiltering("classification.equals=" + DEFAULT_CLASSIFICATION, "classification.equals=" + UPDATED_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllJdTestsByClassificationIsInShouldWork() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where classification in
        defaultJdTestFiltering(
            "classification.in=" + DEFAULT_CLASSIFICATION + "," + UPDATED_CLASSIFICATION,
            "classification.in=" + UPDATED_CLASSIFICATION
        );
    }

    @Test
    @Transactional
    void getAllJdTestsByClassificationIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where classification is not null
        defaultJdTestFiltering("classification.specified=true", "classification.specified=false");
    }

    @Test
    @Transactional
    void getAllJdTestsByClassificationContainsSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where classification contains
        defaultJdTestFiltering("classification.contains=" + DEFAULT_CLASSIFICATION, "classification.contains=" + UPDATED_CLASSIFICATION);
    }

    @Test
    @Transactional
    void getAllJdTestsByClassificationNotContainsSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where classification does not contain
        defaultJdTestFiltering(
            "classification.doesNotContain=" + UPDATED_CLASSIFICATION,
            "classification.doesNotContain=" + DEFAULT_CLASSIFICATION
        );
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where nodeId equals to
        defaultJdTestFiltering("nodeId.equals=" + DEFAULT_NODE_ID, "nodeId.equals=" + UPDATED_NODE_ID);
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where nodeId in
        defaultJdTestFiltering("nodeId.in=" + DEFAULT_NODE_ID + "," + UPDATED_NODE_ID, "nodeId.in=" + UPDATED_NODE_ID);
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where nodeId is not null
        defaultJdTestFiltering("nodeId.specified=true", "nodeId.specified=false");
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where nodeId is greater than or equal to
        defaultJdTestFiltering("nodeId.greaterThanOrEqual=" + DEFAULT_NODE_ID, "nodeId.greaterThanOrEqual=" + UPDATED_NODE_ID);
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where nodeId is less than or equal to
        defaultJdTestFiltering("nodeId.lessThanOrEqual=" + DEFAULT_NODE_ID, "nodeId.lessThanOrEqual=" + SMALLER_NODE_ID);
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where nodeId is less than
        defaultJdTestFiltering("nodeId.lessThan=" + UPDATED_NODE_ID, "nodeId.lessThan=" + DEFAULT_NODE_ID);
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where nodeId is greater than
        defaultJdTestFiltering("nodeId.greaterThan=" + SMALLER_NODE_ID, "nodeId.greaterThan=" + DEFAULT_NODE_ID);
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where node equals to
        defaultJdTestFiltering("node.equals=" + DEFAULT_NODE, "node.equals=" + UPDATED_NODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where node in
        defaultJdTestFiltering("node.in=" + DEFAULT_NODE + "," + UPDATED_NODE, "node.in=" + UPDATED_NODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where node is not null
        defaultJdTestFiltering("node.specified=true", "node.specified=false");
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeContainsSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where node contains
        defaultJdTestFiltering("node.contains=" + DEFAULT_NODE, "node.contains=" + UPDATED_NODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByNodeNotContainsSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where node does not contain
        defaultJdTestFiltering("node.doesNotContain=" + UPDATED_NODE, "node.doesNotContain=" + DEFAULT_NODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where code equals to
        defaultJdTestFiltering("code.equals=" + DEFAULT_CODE, "code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where code in
        defaultJdTestFiltering("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE, "code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where code is not null
        defaultJdTestFiltering("code.specified=true", "code.specified=false");
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where code is greater than or equal to
        defaultJdTestFiltering("code.greaterThanOrEqual=" + DEFAULT_CODE, "code.greaterThanOrEqual=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where code is less than or equal to
        defaultJdTestFiltering("code.lessThanOrEqual=" + DEFAULT_CODE, "code.lessThanOrEqual=" + SMALLER_CODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where code is less than
        defaultJdTestFiltering("code.lessThan=" + UPDATED_CODE, "code.lessThan=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where code is greater than
        defaultJdTestFiltering("code.greaterThan=" + SMALLER_CODE, "code.greaterThan=" + DEFAULT_CODE);
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where codeDescription equals to
        defaultJdTestFiltering("codeDescription.equals=" + DEFAULT_CODE_DESCRIPTION, "codeDescription.equals=" + UPDATED_CODE_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where codeDescription in
        defaultJdTestFiltering(
            "codeDescription.in=" + DEFAULT_CODE_DESCRIPTION + "," + UPDATED_CODE_DESCRIPTION,
            "codeDescription.in=" + UPDATED_CODE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where codeDescription is not null
        defaultJdTestFiltering("codeDescription.specified=true", "codeDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeDescriptionContainsSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where codeDescription contains
        defaultJdTestFiltering(
            "codeDescription.contains=" + DEFAULT_CODE_DESCRIPTION,
            "codeDescription.contains=" + UPDATED_CODE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllJdTestsByCodeDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        // Get all the jdTestList where codeDescription does not contain
        defaultJdTestFiltering(
            "codeDescription.doesNotContain=" + UPDATED_CODE_DESCRIPTION,
            "codeDescription.doesNotContain=" + DEFAULT_CODE_DESCRIPTION
        );
    }

    @Test
    @Transactional
    void getAllJdTestsByParentIsEqualToSomething() throws Exception {
        JdTest parent;
        if (TestUtil.findAll(em, JdTest.class).isEmpty()) {
            jdTestRepository.saveAndFlush(jdTest);
            parent = JdTestResourceIT.createEntity();
        } else {
            parent = TestUtil.findAll(em, JdTest.class).getFirst();
        }
        em.persist(parent);
        em.flush();
        jdTest.setParent(parent);
        jdTestRepository.saveAndFlush(jdTest);
        Long parentId = parent.getId();
        // Get all the jdTestList where parent equals to parentId
        defaultJdTestShouldBeFound("parentId.equals=" + parentId);

        // Get all the jdTestList where parent equals to (parentId + 1)
        defaultJdTestShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    private void defaultJdTestFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultJdTestShouldBeFound(shouldBeFound);
        defaultJdTestShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultJdTestShouldBeFound(String filter) throws Exception {
        restJdTestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jdTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].classification").value(hasItem(DEFAULT_CLASSIFICATION)))
            .andExpect(jsonPath("$.[*].nodeId").value(hasItem(DEFAULT_NODE_ID.intValue())))
            .andExpect(jsonPath("$.[*].node").value(hasItem(DEFAULT_NODE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].codeDescription").value(hasItem(DEFAULT_CODE_DESCRIPTION)));

        // Check, that the count call also returns 1
        restJdTestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultJdTestShouldNotBeFound(String filter) throws Exception {
        restJdTestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restJdTestMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingJdTest() throws Exception {
        // Get the jdTest
        restJdTestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJdTest() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jdTest
        JdTest updatedJdTest = jdTestRepository.findById(jdTest.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedJdTest are not directly saved in db
        em.detach(updatedJdTest);
        updatedJdTest
            .name(UPDATED_NAME)
            .classification(UPDATED_CLASSIFICATION)
            .nodeId(UPDATED_NODE_ID)
            .node(UPDATED_NODE)
            .code(UPDATED_CODE)
            .codeDescription(UPDATED_CODE_DESCRIPTION);
        JdTestDTO jdTestDTO = jdTestMapper.toDto(updatedJdTest);

        restJdTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jdTestDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO))
            )
            .andExpect(status().isOk());

        // Validate the JdTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedJdTestToMatchAllProperties(updatedJdTest);
    }

    @Test
    @Transactional
    void putNonExistingJdTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jdTest.setId(longCount.incrementAndGet());

        // Create the JdTest
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJdTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jdTestDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JdTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJdTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jdTest.setId(longCount.incrementAndGet());

        // Create the JdTest
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJdTestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(jdTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JdTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJdTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jdTest.setId(longCount.incrementAndGet());

        // Create the JdTest
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJdTestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(jdTestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JdTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJdTestWithPatch() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jdTest using partial update
        JdTest partialUpdatedJdTest = new JdTest();
        partialUpdatedJdTest.setId(jdTest.getId());

        partialUpdatedJdTest.classification(UPDATED_CLASSIFICATION).codeDescription(UPDATED_CODE_DESCRIPTION);

        restJdTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJdTest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJdTest))
            )
            .andExpect(status().isOk());

        // Validate the JdTest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJdTestUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedJdTest, jdTest), getPersistedJdTest(jdTest));
    }

    @Test
    @Transactional
    void fullUpdateJdTestWithPatch() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the jdTest using partial update
        JdTest partialUpdatedJdTest = new JdTest();
        partialUpdatedJdTest.setId(jdTest.getId());

        partialUpdatedJdTest
            .name(UPDATED_NAME)
            .classification(UPDATED_CLASSIFICATION)
            .nodeId(UPDATED_NODE_ID)
            .node(UPDATED_NODE)
            .code(UPDATED_CODE)
            .codeDescription(UPDATED_CODE_DESCRIPTION);

        restJdTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJdTest.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedJdTest))
            )
            .andExpect(status().isOk());

        // Validate the JdTest in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertJdTestUpdatableFieldsEquals(partialUpdatedJdTest, getPersistedJdTest(partialUpdatedJdTest));
    }

    @Test
    @Transactional
    void patchNonExistingJdTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jdTest.setId(longCount.incrementAndGet());

        // Create the JdTest
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJdTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jdTestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jdTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JdTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJdTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jdTest.setId(longCount.incrementAndGet());

        // Create the JdTest
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJdTestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(jdTestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JdTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJdTest() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        jdTest.setId(longCount.incrementAndGet());

        // Create the JdTest
        JdTestDTO jdTestDTO = jdTestMapper.toDto(jdTest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJdTestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(jdTestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the JdTest in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJdTest() throws Exception {
        // Initialize the database
        insertedJdTest = jdTestRepository.saveAndFlush(jdTest);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the jdTest
        restJdTestMockMvc
            .perform(delete(ENTITY_API_URL_ID, jdTest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return jdTestRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected JdTest getPersistedJdTest(JdTest jdTest) {
        return jdTestRepository.findById(jdTest.getId()).orElseThrow();
    }

    protected void assertPersistedJdTestToMatchAllProperties(JdTest expectedJdTest) {
        assertJdTestAllPropertiesEquals(expectedJdTest, getPersistedJdTest(expectedJdTest));
    }

    protected void assertPersistedJdTestToMatchUpdatableProperties(JdTest expectedJdTest) {
        assertJdTestAllUpdatablePropertiesEquals(expectedJdTest, getPersistedJdTest(expectedJdTest));
    }
}
