package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.JdTestTestSamples.*;
import static com.mycompany.myapp.domain.JdTestTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JdTestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JdTest.class);
        JdTest jdTest1 = getJdTestSample1();
        JdTest jdTest2 = new JdTest();
        assertThat(jdTest1).isNotEqualTo(jdTest2);

        jdTest2.setId(jdTest1.getId());
        assertThat(jdTest1).isEqualTo(jdTest2);

        jdTest2 = getJdTestSample2();
        assertThat(jdTest1).isNotEqualTo(jdTest2);
    }

    @Test
    void parentTest() {
        JdTest jdTest = getJdTestRandomSampleGenerator();
        JdTest jdTestBack = getJdTestRandomSampleGenerator();

        jdTest.setParent(jdTestBack);
        assertThat(jdTest.getParent()).isEqualTo(jdTestBack);

        jdTest.parent(null);
        assertThat(jdTest.getParent()).isNull();
    }

    @Test
    void childTest() {
        JdTest jdTest = getJdTestRandomSampleGenerator();
        JdTest jdTestBack = getJdTestRandomSampleGenerator();

        jdTest.addChild(jdTestBack);
        assertThat(jdTest.getChildren()).containsOnly(jdTestBack);
        assertThat(jdTestBack.getParent()).isEqualTo(jdTest);

        jdTest.removeChild(jdTestBack);
        assertThat(jdTest.getChildren()).doesNotContain(jdTestBack);
        assertThat(jdTestBack.getParent()).isNull();

        jdTest.children(new HashSet<>(Set.of(jdTestBack)));
        assertThat(jdTest.getChildren()).containsOnly(jdTestBack);
        assertThat(jdTestBack.getParent()).isEqualTo(jdTest);

        jdTest.setChildren(new HashSet<>());
        assertThat(jdTest.getChildren()).doesNotContain(jdTestBack);
        assertThat(jdTestBack.getParent()).isNull();
    }
}
