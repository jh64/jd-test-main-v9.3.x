package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.JdTest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JdTestDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String classification;

    @NotNull
    private Long nodeId;

    @NotNull
    private String node;

    private Integer code;

    @NotNull
    private String codeDescription;

    private JdTestDTO parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

    public JdTestDTO getParent() {
        return parent;
    }

    public void setParent(JdTestDTO parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JdTestDTO)) {
            return false;
        }

        JdTestDTO jdTestDTO = (JdTestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jdTestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JdTestDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", classification='" + getClassification() + "'" +
            ", nodeId=" + getNodeId() +
            ", node='" + getNode() + "'" +
            ", code=" + getCode() +
            ", codeDescription='" + getCodeDescription() + "'" +
            ", parent=" + getParent() +
            "}";
    }
}
