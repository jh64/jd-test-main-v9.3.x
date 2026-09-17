package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JdTestDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JdTestDTO.class);
        JdTestDTO jdTestDTO1 = new JdTestDTO();
        jdTestDTO1.setId(1L);
        JdTestDTO jdTestDTO2 = new JdTestDTO();
        assertThat(jdTestDTO1).isNotEqualTo(jdTestDTO2);
        jdTestDTO2.setId(jdTestDTO1.getId());
        assertThat(jdTestDTO1).isEqualTo(jdTestDTO2);
        jdTestDTO2.setId(2L);
        assertThat(jdTestDTO1).isNotEqualTo(jdTestDTO2);
        jdTestDTO1.setId(null);
        assertThat(jdTestDTO1).isNotEqualTo(jdTestDTO2);
    }
}
