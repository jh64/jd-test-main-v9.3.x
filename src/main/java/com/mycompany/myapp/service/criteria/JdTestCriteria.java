package com.mycompany.myapp.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.JdTest} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.JdTestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /jd-tests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JdTestCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter classification;

    private LongFilter nodeId;

    private StringFilter node;

    private IntegerFilter code;

    private StringFilter codeDescription;

    private LongFilter parentId;

    private LongFilter childId;

    private Boolean distinct;

    public JdTestCriteria() {}

    public JdTestCriteria(JdTestCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.name = other.optionalName().map(StringFilter::copy).orElse(null);
        this.classification = other.optionalClassification().map(StringFilter::copy).orElse(null);
        this.nodeId = other.optionalNodeId().map(LongFilter::copy).orElse(null);
        this.node = other.optionalNode().map(StringFilter::copy).orElse(null);
        this.code = other.optionalCode().map(IntegerFilter::copy).orElse(null);
        this.codeDescription = other.optionalCodeDescription().map(StringFilter::copy).orElse(null);
        this.parentId = other.optionalParentId().map(LongFilter::copy).orElse(null);
        this.childId = other.optionalChildId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public JdTestCriteria copy() {
        return new JdTestCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public Optional<StringFilter> optionalName() {
        return Optional.ofNullable(name);
    }

    public StringFilter name() {
        if (name == null) {
            setName(new StringFilter());
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getClassification() {
        return classification;
    }

    public Optional<StringFilter> optionalClassification() {
        return Optional.ofNullable(classification);
    }

    public StringFilter classification() {
        if (classification == null) {
            setClassification(new StringFilter());
        }
        return classification;
    }

    public void setClassification(StringFilter classification) {
        this.classification = classification;
    }

    public LongFilter getNodeId() {
        return nodeId;
    }

    public Optional<LongFilter> optionalNodeId() {
        return Optional.ofNullable(nodeId);
    }

    public LongFilter nodeId() {
        if (nodeId == null) {
            setNodeId(new LongFilter());
        }
        return nodeId;
    }

    public void setNodeId(LongFilter nodeId) {
        this.nodeId = nodeId;
    }

    public StringFilter getNode() {
        return node;
    }

    public Optional<StringFilter> optionalNode() {
        return Optional.ofNullable(node);
    }

    public StringFilter node() {
        if (node == null) {
            setNode(new StringFilter());
        }
        return node;
    }

    public void setNode(StringFilter node) {
        this.node = node;
    }

    public IntegerFilter getCode() {
        return code;
    }

    public Optional<IntegerFilter> optionalCode() {
        return Optional.ofNullable(code);
    }

    public IntegerFilter code() {
        if (code == null) {
            setCode(new IntegerFilter());
        }
        return code;
    }

    public void setCode(IntegerFilter code) {
        this.code = code;
    }

    public StringFilter getCodeDescription() {
        return codeDescription;
    }

    public Optional<StringFilter> optionalCodeDescription() {
        return Optional.ofNullable(codeDescription);
    }

    public StringFilter codeDescription() {
        if (codeDescription == null) {
            setCodeDescription(new StringFilter());
        }
        return codeDescription;
    }

    public void setCodeDescription(StringFilter codeDescription) {
        this.codeDescription = codeDescription;
    }

    public LongFilter getParentId() {
        return parentId;
    }

    public Optional<LongFilter> optionalParentId() {
        return Optional.ofNullable(parentId);
    }

    public LongFilter parentId() {
        if (parentId == null) {
            setParentId(new LongFilter());
        }
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    public LongFilter getChildId() {
        return childId;
    }

    public Optional<LongFilter> optionalChildId() {
        return Optional.ofNullable(childId);
    }

    public LongFilter childId() {
        if (childId == null) {
            setChildId(new LongFilter());
        }
        return childId;
    }

    public void setChildId(LongFilter childId) {
        this.childId = childId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final JdTestCriteria that = (JdTestCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(classification, that.classification) &&
            Objects.equals(nodeId, that.nodeId) &&
            Objects.equals(node, that.node) &&
            Objects.equals(code, that.code) &&
            Objects.equals(codeDescription, that.codeDescription) &&
            Objects.equals(parentId, that.parentId) &&
            Objects.equals(childId, that.childId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, classification, nodeId, node, code, codeDescription, parentId, childId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JdTestCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalName().map(f -> "name=" + f + ", ").orElse("") +
            optionalClassification().map(f -> "classification=" + f + ", ").orElse("") +
            optionalNodeId().map(f -> "nodeId=" + f + ", ").orElse("") +
            optionalNode().map(f -> "node=" + f + ", ").orElse("") +
            optionalCode().map(f -> "code=" + f + ", ").orElse("") +
            optionalCodeDescription().map(f -> "codeDescription=" + f + ", ").orElse("") +
            optionalParentId().map(f -> "parentId=" + f + ", ").orElse("") +
            optionalChildId().map(f -> "childId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
