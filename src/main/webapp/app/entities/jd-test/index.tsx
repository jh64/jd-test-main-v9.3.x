import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import JdTest from './jd-test';
import JdTestDeleteDialog from './jd-test-delete-dialog';
import JdTestDetail from './jd-test-detail';
import JdTestUpdate from './jd-test-update';

const JdTestRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<JdTest />} />
    <Route path="new" element={<JdTestUpdate />} />
    <Route path=":id">
      <Route index element={<JdTestDetail />} />
      <Route path="edit" element={<JdTestUpdate />} />
      <Route path="delete" element={<JdTestDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default JdTestRoutes;
