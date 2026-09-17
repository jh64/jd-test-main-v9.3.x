import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Translate } from 'react-jhipster';
import { Link, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './jd-test.reducer';

export const JdTestDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id!));
  }, []);

  const jdTestEntity = useAppSelector(state => state.jdTest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="jdTestDetailsHeading">
          <Translate contentKey="jdTestApp.jdTest.detail.title">JdTest</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="jdTestApp.jdTest.id">Id</Translate>
            </span>
          </dt>
          <dd>{jdTestEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="jdTestApp.jdTest.name">Name</Translate>
            </span>
          </dt>
          <dd>{jdTestEntity.name}</dd>
          <dt>
            <span id="classification">
              <Translate contentKey="jdTestApp.jdTest.classification">Classification</Translate>
            </span>
          </dt>
          <dd>{jdTestEntity.classification}</dd>
          <dt>
            <span id="nodeId">
              <Translate contentKey="jdTestApp.jdTest.nodeId">Node Id</Translate>
            </span>
          </dt>
          <dd>{jdTestEntity.nodeId}</dd>
          <dt>
            <span id="node">
              <Translate contentKey="jdTestApp.jdTest.node">Node</Translate>
            </span>
          </dt>
          <dd>{jdTestEntity.node}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="jdTestApp.jdTest.code">Code</Translate>
            </span>
          </dt>
          <dd>{jdTestEntity.code}</dd>
          <dt>
            <span id="codeDescription">
              <Translate contentKey="jdTestApp.jdTest.codeDescription">Code Description</Translate>
            </span>
          </dt>
          <dd>{jdTestEntity.codeDescription}</dd>
          <dt>
            <Translate contentKey="jdTestApp.jdTest.parent">Parent</Translate>
          </dt>
          <dd>{jdTestEntity.parent ? jdTestEntity.parent.id : ''}</dd>
        </dl>
        <Button as={Link as any} to="/jd-test" replace variant="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button as={Link as any} to={`/jd-test/${jdTestEntity.id}/edit`} replace variant="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default JdTestDetail;
