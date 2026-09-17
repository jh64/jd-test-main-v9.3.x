import React, { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { JhiItemCount, JhiPagination, Translate, getPaginationState } from 'react-jhipster';
import { Link, useLocation, useNavigate } from 'react-router';

import { faSort, faSortDown, faSortUp } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';

import { getEntities } from './jd-test.reducer';

export const JdTest = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );

  const jdTestList = useAppSelector(state => state.jdTest.entities);
  const loading = useAppSelector(state => state.jdTest.loading);
  const totalItems = useAppSelector(state => state.jdTest.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(pageLocation.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [pageLocation.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const { order } = paginationState;
    if (sortFieldName !== fieldName) {
      return faSort;
    }
    return order === ASC ? faSortUp : faSortDown;
  };

  return (
    <div>
      <h2 id="jd-test-heading" data-cy="JdTestHeading">
        <Translate contentKey="jdTestApp.jdTest.home.title">Jd Tests</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" variant="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jdTestApp.jdTest.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/jd-test/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jdTestApp.jdTest.home.createLabel">Create new Jd Test</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {jdTestList?.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="jdTestApp.jdTest.id">Id</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="jdTestApp.jdTest.name">Name</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('name')} />
                </th>
                <th className="hand" onClick={sort('classification')}>
                  <Translate contentKey="jdTestApp.jdTest.classification">Classification</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('classification')} />
                </th>
                <th className="hand" onClick={sort('nodeId')}>
                  <Translate contentKey="jdTestApp.jdTest.nodeId">Node Id</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('nodeId')} />
                </th>
                <th className="hand" onClick={sort('node')}>
                  <Translate contentKey="jdTestApp.jdTest.node">Node</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('node')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="jdTestApp.jdTest.code">Code</Translate> <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('codeDescription')}>
                  <Translate contentKey="jdTestApp.jdTest.codeDescription">Code Description</Translate>{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('codeDescription')} />
                </th>
                <th>
                  <Translate contentKey="jdTestApp.jdTest.parent">Parent</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {jdTestList.map(jdTest => (
                <tr key={`entity-${jdTest.id}`} data-cy="entityTable">
                  <td>
                    <Button as={Link as any} to={`/jd-test/${jdTest.id}`} variant="link" size="sm">
                      {jdTest.id}
                    </Button>
                  </td>
                  <td>{jdTest.name}</td>
                  <td>{jdTest.classification}</td>
                  <td>{jdTest.nodeId}</td>
                  <td>{jdTest.node}</td>
                  <td>{jdTest.code}</td>
                  <td>{jdTest.codeDescription}</td>
                  <td>{jdTest.parent ? <Link to={`/jd-test/${jdTest.parent.id}`}>{jdTest.parent.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button as={Link as any} to={`/jd-test/${jdTest.id}`} variant="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        as={Link as any}
                        to={`/jd-test/${jdTest.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        variant="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        as={Link as any}
                        to={`/jd-test/${jdTest.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        variant="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jdTestApp.jdTest.home.notFound">No Jd Tests found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={jdTestList && jdTestList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default JdTest;
