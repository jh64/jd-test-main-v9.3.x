package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.JdTest;
import com.mycompany.myapp.service.dto.JdTestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JdTest} and its DTO {@link JdTestDTO}.
 */
@Mapper(componentModel = "spring")
public interface JdTestMapper extends EntityMapper<JdTestDTO, JdTest> {
    @Mapping(target = "parent", source = "parent", qualifiedByName = "jdTestId")
    JdTestDTO toDto(JdTest s);

    @Named("jdTestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JdTestDTO toDtoJdTestId(JdTest jdTest);
}
