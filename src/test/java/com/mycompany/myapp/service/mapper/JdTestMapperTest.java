package com.mycompany.myapp.service.mapper;

import static com.mycompany.myapp.domain.JdTestAsserts.*;
import static com.mycompany.myapp.domain.JdTestTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JdTestMapperTest {

    private JdTestMapper jdTestMapper;

    @BeforeEach
    void setUp() {
        jdTestMapper = new JdTestMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getJdTestSample1();
        var actual = jdTestMapper.toEntity(jdTestMapper.toDto(expected));
        assertJdTestAllPropertiesEquals(expected, actual);
    }
}
