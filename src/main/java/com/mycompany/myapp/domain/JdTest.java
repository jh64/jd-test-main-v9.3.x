package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A JdTest.
 */
@Entity
@Table(name = "jd_test")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JdTest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "classification", nullable = false)
    private String classification;

    @NotNull
    @Column(name = "node_id", nullable = false)
    private Long nodeId;

    @NotNull
    @Column(name = "node", nullable = false)
    private String node;

    @Column(name = "code")
    private Integer code;

    @NotNull
    @Column(name = "code_description", nullable = false)
    private String codeDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "parent", "children" }, allowSetters = true)
    private JdTest parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parent", "children" }, allowSetters = true)
    private Set<JdTest> children = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JdTest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public JdTest name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return this.classification;
    }

    public JdTest classification(String classification) {
        this.setClassification(classification);
        return this;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Long getNodeId() {
        return this.nodeId;
    }

    public JdTest nodeId(Long nodeId) {
        this.setNodeId(nodeId);
        return this;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getNode() {
        return this.node;
    }

    public JdTest node(String node) {
        this.setNode(node);
        return this;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public Integer getCode() {
        return this.code;
    }

    public JdTest code(Integer code) {
        this.setCode(code);
        return this;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getCodeDescription() {
        return this.codeDescription;
    }

    public JdTest codeDescription(String codeDescription) {
        this.setCodeDescription(codeDescription);
        return this;
    }

    public void setCodeDescription(String codeDescription) {
        this.codeDescription = codeDescription;
    }

    public JdTest getParent() {
        return this.parent;
    }

    public void setParent(JdTest jdTest) {
        this.parent = jdTest;
    }

    public JdTest parent(JdTest jdTest) {
        this.setParent(jdTest);
        return this;
    }

    public Set<JdTest> getChildren() {
        return this.children;
    }

    public void setChildren(Set<JdTest> jdTests) {
        if (this.children != null) {
            this.children.forEach(i -> i.setParent(null));
        }
        if (jdTests != null) {
            jdTests.forEach(i -> i.setParent(this));
        }
        this.children = jdTests;
    }

    public JdTest children(Set<JdTest> jdTests) {
        this.setChildren(jdTests);
        return this;
    }

    public JdTest addChild(JdTest jdTest) {
        this.children.add(jdTest);
        jdTest.setParent(this);
        return this;
    }

    public JdTest removeChild(JdTest jdTest) {
        this.children.remove(jdTest);
        jdTest.setParent(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JdTest)) {
            return false;
        }
        return getId() != null && getId().equals(((JdTest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JdTest{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", classification='" + getClassification() + "'" +
            ", nodeId=" + getNodeId() +
            ", node='" + getNode() + "'" +
            ", code=" + getCode() +
            ", codeDescription='" + getCodeDescription() + "'" +
            "}";
    }
}
