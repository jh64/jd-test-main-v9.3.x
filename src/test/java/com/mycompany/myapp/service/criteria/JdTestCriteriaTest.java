package com.mycompany.myapp.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class JdTestCriteriaTest {

    @Test
    void newJdTestCriteriaHasAllFiltersNullTest() {
        var jdTestCriteria = new JdTestCriteria();
        assertThat(jdTestCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void jdTestCriteriaFluentMethodsCreatesFiltersTest() {
        var jdTestCriteria = new JdTestCriteria();

        setAllFilters(jdTestCriteria);

        assertThat(jdTestCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void jdTestCriteriaCopyCreatesNullFilterTest() {
        var jdTestCriteria = new JdTestCriteria();
        var copy = jdTestCriteria.copy();

        assertThat(jdTestCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(copyFiltersAre(copy, (a, b) -> a == null || a instanceof Boolean ? a == b : a != b && a.equals(b))),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(jdTestCriteria)
        );
    }

    @Test
    void jdTestCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var jdTestCriteria = new JdTestCriteria();
        setAllFilters(jdTestCriteria);

        var copy = jdTestCriteria.copy();

        assertThat(jdTestCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(copyFiltersAre(copy, (a, b) -> a == null || a instanceof Boolean ? a == b : a != b && a.equals(b))),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(jdTestCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var jdTestCriteria = new JdTestCriteria();

        assertThat(jdTestCriteria).hasToString("JdTestCriteria{}");
    }

    private static void setAllFilters(JdTestCriteria jdTestCriteria) {
        jdTestCriteria.id();
        jdTestCriteria.name();
        jdTestCriteria.classification();
        jdTestCriteria.nodeId();
        jdTestCriteria.node();
        jdTestCriteria.code();
        jdTestCriteria.codeDescription();
        jdTestCriteria.parentId();
        jdTestCriteria.childId();
        jdTestCriteria.distinct();
    }

    private static Condition<JdTestCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getName()) &&
                condition.apply(criteria.getClassification()) &&
                condition.apply(criteria.getNodeId()) &&
                condition.apply(criteria.getNode()) &&
                condition.apply(criteria.getCode()) &&
                condition.apply(criteria.getCodeDescription()) &&
                condition.apply(criteria.getParentId()) &&
                condition.apply(criteria.getChildId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<JdTestCriteria> copyFiltersAre(JdTestCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getName(), copy.getName()) &&
                condition.apply(criteria.getClassification(), copy.getClassification()) &&
                condition.apply(criteria.getNodeId(), copy.getNodeId()) &&
                condition.apply(criteria.getNode(), copy.getNode()) &&
                condition.apply(criteria.getCode(), copy.getCode()) &&
                condition.apply(criteria.getCodeDescription(), copy.getCodeDescription()) &&
                condition.apply(criteria.getParentId(), copy.getParentId()) &&
                condition.apply(criteria.getChildId(), copy.getChildId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
