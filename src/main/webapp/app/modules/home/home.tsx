import './home.scss';

import React, { useState } from 'react';
import { Alert, Col, Row } from 'react-bootstrap';
import { Translate } from 'react-jhipster';
import { Link } from 'react-router';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  const nodeId = '041';
  const [nodeSelected, setNodeSelected] = useState({ id: nodeId, isExpanded: true, isSelected: true, name: 'Fishing' });

  const handleCodeFromChild = node => {
    setNodeSelected(node);
  };

  function CodeSelectionTree() {
    // 1. Mock Data Structure
    const initialTreeData = [
      {
        id: '1',
        isExpanded: true,
        name: 'Agriculture, Forestry and Fishing',
        children: [
          {
            id: '51',
            isExpanded: false,
            name: 'Agriculture',
            children: [
              { id: '52', isExpanded: true, name: 'Agriculture child 1' },
              { id: '53', isExpanded: true, name: 'Agriculture child 2' },
            ],
          },
          { id: '5', isExpanded: true, name: 'Agriculture' },
          { id: '6', isExpanded: true, name: 'Forestry and Logging' },
          {
            id: '2',
            isExpanded: true,
            name: 'Fishing, Hunting and Trapping',
            children: [
              { id: '041', isExpanded: true, isSelected: true, name: 'Fishing' },
              { id: '4', isExpanded: true, isSelected: false, name: 'Hunting and Trapping' },
            ],
          },
        ],
      },
      {
        id: '7',
        isExpanded: false,
        name: 'Mining',
        children: [
          {
            id: '71',
            isExpanded: true,
            name: 'Mining child 1',
          },
          {
            id: '72',
            isExpanded: true,
            name: 'Mining child 2',
          },
        ],
      },
    ];

    const initialTreeDataCheckbox = [
      {
        id: '1',
        isExpanded: true,
        name: 'Agriculture, Forestry and Fishing',
        children: [
          {
            id: '51',
            isExpanded: false,
            name: 'Agriculture',
            children: [
              { id: '52', isExpanded: true, name: 'Agriculture child 1' },
              { id: '53', isExpanded: true, name: 'Agriculture child 2' },
            ],
          },
          { id: '5', isExpanded: true, name: 'Agriculture' },
          { id: '6', isExpanded: true, name: 'Forestry and Logging' },
          {
            id: '2',
            isExpanded: true,
            name: 'Fishing, Hunting and Trapping',
            children: [
              { id: '041', isExpanded: true, isSelected: true, name: 'Fishing' },
              { id: '4', isExpanded: true, isSelected: false, name: 'Hunting and Trapping' },
            ],
          },
        ],
      },
      {
        id: '7',
        isExpanded: false,
        name: 'Mining',
        children: [
          {
            id: '71',
            isExpanded: true,
            name: 'Mining child 1',
          },
          {
            id: '72',
            isExpanded: true,
            name: 'Mining child 2',
          },
        ],
      },
    ];

    // TODO: try to initialise all generations parenNode.isExpanded to the expected positions in the initialTreeData
    //       depending on the selected id/code.
    return (
      <div style={{ padding: '20px' }}>
        {initialTreeData.map(rootNode => (
          <TreeNode onCodeSelected={handleCodeFromChild} key={rootNode.id} node={rootNode} parenNode={rootNode} />
        ))}
        {initialTreeDataCheckbox.map(rootNode => (
          <TreeNodeCheckbox onCodeSelected={handleCodeFromChild} key={rootNode.id} node={rootNode} parenNode={rootNode} />
        ))}
      </div>
    );

    // 2. Individual Tree Node Component
    function TreeNode({ node, parenNode, onCodeSelected }) {
      // const [codeSelected, setCodeSelected] = useState('');
      const hasChildren = node.children && node.children.length > 0;
      // const [isExpanded, setIsExpanded] = useState(false);
      const [isExpanded, setIsExpanded] = useState(hasChildren && parenNode.isExpanded && node.isExpanded);

      // parenNode.isExpanded = true;

      const handleToggle = () => {
        setIsExpanded(!isExpanded);
        // TODO: see if resetting initialTreeData is needed, for the isExpanded condition.
        //       may need to check if the node is a leave object or not.
        //       Looks like I have to let it re-render.
        //       As long as all parenNode.isExpanded + node.isExpanded can be set with the expected condition, it should be ok!
        // node.isExpanded = !isExpanded;
        // parenNode.isExpanded = true;
      };

      const handleCodePick = () => {
        /*
          This error occurs because your project's ESLint configuration enforces the no-console rule,
          allowing only console.warn and console.error while blocking other console methods like console.log.

          alternative solution: // eslint-disable-next-line no-console
         */
        console.warn('handleCodePick- ID: ' + node.id + ', has children: ' + node.children + ', parenNode: ' + parenNode.id);
        console.warn('isSelected: ' + node.isSelected);
        if (!node.children) {
          /* TODO: this may just set the object in the scope, the initialTreeData is still immutable!
                   So, work out some code to go through the whole tree and set the nodes to the expected value
                   1. isSelected
                   2. All parents to be expended in all levels.
                   3. All others to be reset
                   4. Consider cloning mechanism, may need multiple loops or walking back with pointer to parent object in all levels.
          */
          node.isSelected = true;

          // TODO: please confirm - setNodeSelected(node);
          onCodeSelected(node);
        }
      };

      return (
        <div style={{ marginLeft: '16px', fontFamily: 'sans-serif', userSelect: 'none' }}>
          <div
            onClick={handleToggle}
            style={{
              cursor: hasChildren ? 'pointer' : 'default',
              padding: '4px 0',
              display: 'flex',
              alignItems: 'center',
            }}
          >
            {/* Toggle indicator / Icon */}
            {/* TODO: work out the colour/style for the selected leave node. */}
            {hasChildren ? (isExpanded ? '▼ 📁 ' : '▶ 📁 ') : node.isSelected ? '>📄 ' : '📄 '}
            <span onClick={handleCodePick} style={{ marginLeft: '4px' }}>
              {node.name}
            </span>
          </div>

          {/* Recursive rendering step */}
          {hasChildren && isExpanded && (
            <div style={{ borderLeft: '1px dashed #ccc', marginLeft: '6px' }}>
              {node.children.map(childNode => (
                <TreeNode onCodeSelected={handleCodeFromChild} key={childNode.id} node={childNode} parenNode={node} />
              ))}
            </div>
          )}
        </div>
      );
    }

    function TreeNodeCheckbox({ node, parenNode, onCodeSelected }) {
      const [isChecked, setIsChecked] = useState<boolean>(false);

      // Type the event as React.ChangeEvent<HTMLInputElement>
      const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setIsChecked(event.target.checked);
      };

      // const [codeSelected, setCodeSelected] = useState('');
      const hasChildren = node.children && node.children.length > 0;
      // const [isExpanded, setIsExpanded] = useState(false);
      const [isExpanded, setIsExpanded] = useState(hasChildren && parenNode.isExpanded && node.isExpanded);

      // parenNode.isExpanded = true;

      const handleToggle = () => {
        setIsExpanded(!isExpanded);
        // TODO: see if resetting initialTreeData is needed, for the isExpanded condition.
        //       may need to check if the node is a leave object or not.
        //       Looks like I have to let it re-render.
        //       As long as all parenNode.isExpanded + node.isExpanded can be set with the expected condition, it should be ok!
        // node.isExpanded = !isExpanded;
        // parenNode.isExpanded = true;
      };

      const handleCodePick = () => {
        /*
          This error occurs because your project's ESLint configuration enforces the no-console rule,
          allowing only console.warn and console.error while blocking other console methods like console.log.

          alternative solution: // eslint-disable-next-line no-console
         */
        console.warn('handleCodePick- ID: ' + node.id + ', has children: ' + node.children + ', parenNode: ' + parenNode.id);
        console.warn('isSelected: ' + node.isSelected);
        if (!node.children) {
          /* TODO: this may just set the object in the scope, the initialTreeData is still immutable!
                   So, work out some code to go through the whole tree and set the nodes to the expected value
                   1. isSelected
                   2. All parents to be expended in all levels.
                   3. All others to be reset
                   4. Consider cloning mechanism, may need multiple loops or walking back with pointer to parent object in all levels.
          */
          node.isSelected = true;

          // TODO: please confirm - setNodeSelected(node);
          onCodeSelected(node);
        }
      };

      return (
        <div style={{ marginLeft: '16px', fontFamily: 'sans-serif', userSelect: 'none' }}>
          <div
            onClick={handleToggle}
            style={{
              cursor: hasChildren ? 'pointer' : 'default',
              padding: '4px 0',
              display: 'flex',
              alignItems: 'center',
            }}
          >
            {/* Toggle indicator / Icon */}
            {/* TODO: work out the colour/style for the selected leave node. */}
            {hasChildren ? (isExpanded ? '▼ 📁 ' : '▶ 📁 ') : node.isSelected ? '>📄 ' : '📄 '}

            {/* TODO: how to remove the errors from prettier/prettier. */}
            {hasChildren ? (
              <span style={{ marginLeft: '4px' }}>{node.name}</span>
            ) : (
              <label style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                <input type="checkbox" checked={isChecked} onChange={handleCheckboxChange} />
                <span style={{ marginLeft: '4px' }}>{node.name}</span>
              </label>
              /* TODO: may use the Checkbox from the DAFF lib
              <Checkbox
                // value={node.name}
                // checked={selectedDevices.includes('phone')}
                // onChange={e => {
                // setSelectedDevices(e.target.checked ? [...selectedDevices, 'phone'] : selectedDevices.filter(d => d !== 'phone'));
                // }}
              >
                {node.name}
              </Checkbox>
              */
            )}
          </div>

          {/* Recursive rendering step */}
          {hasChildren && isExpanded && (
            <div style={{ borderLeft: '1px dashed #ccc', marginLeft: '6px' }}>
              {node.children.map(childNode => (
                <TreeNodeCheckbox onCodeSelected={handleCodeFromChild} key={childNode.id} node={childNode} parenNode={node} />
              ))}
            </div>
          )}
        </div>
      );
    }
  }

  return (
    <Row>
      <Col md="9">
        <div>
          <h3>Tree view sample</h3>
          <h6>Code: {nodeSelected.id}</h6>
        </div>
        <CodeSelectionTree />
      </Col>

      <Col md="9">
        <div>
          <h3>JHipster original</h3>
        </div>
        {account?.login ? (
          <div>
            <Alert variant="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div>
            <Alert variant="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>

              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </Alert>

            <Alert variant="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </div>
        )}
      </Col>
    </Row>
  );
};

export default Home;
