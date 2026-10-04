import './home.scss';

import React, { useState } from 'react';
import { Alert, Col, Row } from 'react-bootstrap';
import { Translate } from 'react-jhipster';
import { Link } from 'react-router';

import { useAppSelector } from 'app/config/store';

import { Autocomplete } from '@ag.ds-next/react/autocomplete';
import { Checkbox } from '@ag.ds-next/react/checkbox';
import { ControlGroup } from '@ag.ds-next/react/control-group';

// For the simulated async API request - loadOptions()
import { DefaultComboboxOption } from '@ag.ds-next/react/combobox';
// export type DefaultComboboxOption = { label: string; value: string };

export const AutocompleteExample = () => {
  const [value, setValue] = useState(null);
  const resolveCountryOptionListForPromise = [
    { label: 'Australia', value: 'Australia - value' },
    { label: 'Canada', value: 'Canada - value' },
    { label: 'Japan', value: 'Japan - value' },
  ];

  return (
    <Autocomplete
      label="Find your country"
      hint="Start typing to get a list of country prompt for selection"
      value={value}
      onChange={setValue}
      loadOptions={async function loadOptions() {
        // Simulate an asynchronous API request
        await new Promise(resolve => setTimeout(resolve, 1500));
        return resolveCountryOptionListForPromise;
      }}
    />
  );
};

export const CheckboxExample = () => {
  // State for a single controlled checkbox
  const [isAccepted, setIsAccepted] = useState(false);

  // State for tracking grouped checkboxes
  const [selectedDevices, setSelectedDevices] = useState<string[]>([]);

  const handleSingleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setIsAccepted(event.target.checked);
  };

  return (
    <div style={{ padding: '2rem' }}>
      {/* Single Controlled Checkbox */}
      <div style={{ marginBottom: '2rem' }}>
        <Checkbox checked={isAccepted} onChange={handleSingleChange} name="I agree to the terms and conditions" />
        <p>Current Status: {isAccepted ? 'Checked' : 'Unchecked'}</p>
      </div>

      {/* Grouped Checkboxes */}
      <ControlGroup label="Select your devices" hint="Please select all that apply" block>
        <Checkbox
          value="phone"
          checked={selectedDevices.includes('phone')}
          onChange={e => {
            setSelectedDevices(e.target.checked ? [...selectedDevices, 'phone'] : selectedDevices.filter(d => d !== 'phone'));
          }}
        >
          Phone
        </Checkbox>
        <Checkbox
          value="tablet"
          checked={selectedDevices.includes('tablet')}
          onChange={e => {
            setSelectedDevices(e.target.checked ? [...selectedDevices, 'tablet'] : selectedDevices.filter(d => d !== 'tablet'));
          }}
        >
          Tablet
        </Checkbox>
        <Checkbox
          value="laptop"
          checked={selectedDevices.includes('laptop')}
          onChange={e => {
            setSelectedDevices(e.target.checked ? [...selectedDevices, 'laptop'] : selectedDevices.filter(d => d !== 'laptop'));
          }}
        >
          Laptop
        </Checkbox>
      </ControlGroup>
    </div>
  );
};

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="9">
        <div>
          <h3>DAFF React component library - Autocomplete sample</h3>
        </div>
        <AutocompleteExample />
      </Col>
      <p />
      <p />
      <p />
      <p />
      <p />
      <p />
      <p />
      <p />
      <p />
      <p />
      <p />
      <p />

      <Col md="9">
        <div>
          <h3>DAFF React component library - Checkbox sample</h3>
        </div>
        <CheckboxExample />
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
