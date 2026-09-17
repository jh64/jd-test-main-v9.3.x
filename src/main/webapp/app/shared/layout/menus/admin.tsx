import React from 'react';
import { DropdownItem } from 'react-bootstrap';
import { Translate, translate } from 'react-jhipster';

import { faDatabase } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

const adminMenuItems = () => (
  <>
    <MenuItem icon="users" to="/admin/user-management">
      <Translate contentKey="global.menu.admin.userManagement">User management</Translate>
    </MenuItem>
    {/* jhipster-needle-add-element-to-admin-menu - JHipster will add entities to the admin menu here */}
  </>
);

const openAPIItem = () => (
  <MenuItem icon="book" to="/admin/docs">
    <Translate contentKey="global.menu.admin.apidocs">API</Translate>
  </MenuItem>
);

const databaseItem = () => (
  <DropdownItem as="a" href="./h2-console/" target="_tab">
    <FontAwesomeIcon icon={faDatabase} /> <Translate contentKey="global.menu.admin.database">Database</Translate>
  </DropdownItem>
);

export const AdminMenu = ({ showOpenAPI, showDatabase }) => (
  <NavDropdown icon="users-cog" name={translate('global.menu.admin.main')} id="admin-menu" data-cy="adminMenu">
    {adminMenuItems()}
    {showOpenAPI && openAPIItem()}

    {showDatabase && databaseItem()}
  </NavDropdown>
);
