import React, { useEffect } from 'react';
import { Button, Col, Row } from 'react-bootstrap';
import { Translate, ValidatedField, ValidatedForm, isNumber, translate } from 'react-jhipster';
import { Link, useNavigate, useParams } from 'react-router';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntities as getJdTests } from 'app/entities/jd-test/jd-test.reducer';

import { createEntity, getEntity, reset, updateEntity } from './jd-test.reducer';

export const JdTestUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const jdTests = useAppSelector(state => state.jdTest.entities);
  const jdTestEntity = useAppSelector(state => state.jdTest.entity);
  const loading = useAppSelector(state => state.jdTest.loading);
  const updating = useAppSelector(state => state.jdTest.updating);
  const updateSuccess = useAppSelector(state => state.jdTest.updateSuccess);

  const handleClose = () => {
    navigate(`/jd-test${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getJdTests({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    if (values.nodeId !== undefined && typeof values.nodeId !== 'number') {
      values.nodeId = Number(values.nodeId);
    }
    if (values.code !== undefined && typeof values.code !== 'number') {
      values.code = Number(values.code);
    }

    const entity = {
      ...jdTestEntity,
      ...values,
      parent: jdTests.find(it => it.id.toString() === values.parent?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...jdTestEntity,
          parent: jdTestEntity?.parent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="jdTestApp.jdTest.home.createOrEditLabel" data-cy="JdTestCreateUpdateHeading">
            <Translate contentKey="jdTestApp.jdTest.home.createOrEditLabel">Create or edit a JdTest</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm mode="all" defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew && (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="jd-test-id"
                  label={translate('jdTestApp.jdTest.id')}
                  validate={{ required: true }}
                />
              )}
              <ValidatedField
                label={translate('jdTestApp.jdTest.name')}
                id="jd-test-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jdTestApp.jdTest.classification')}
                id="jd-test-classification"
                name="classification"
                data-cy="classification"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('jdTestApp.jdTest.nodeId')}
                id="jd-test-nodeId"
                name="nodeId"
                data-cy="nodeId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('jdTestApp.jdTest.node')}
                id="jd-test-node"
                name="node"
                data-cy="node"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField label={translate('jdTestApp.jdTest.code')} id="jd-test-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label={translate('jdTestApp.jdTest.codeDescription')}
                id="jd-test-codeDescription"
                name="codeDescription"
                data-cy="codeDescription"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="jd-test-parent" name="parent" data-cy="parent" label={translate('jdTestApp.jdTest.parent')} type="select">
                <option value="" key="0" />
                {jdTests
                  ? jdTests.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button as={Link as any} id="cancel-save" data-cy="entityCreateCancelButton" to="/jd-test" replace variant="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button variant="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default JdTestUpdate;
