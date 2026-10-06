import './home.scss';

import React from 'react';
import { Alert, Col, Row } from 'react-bootstrap';
import { Translate } from 'react-jhipster';
import { Link } from 'react-router';

import { useAppSelector } from 'app/config/store';

import { Tabletojson } from 'tabletojson';

const printImageFromTable = (firstTable): string => {
  const arrayFromHtmlTable = firstTable.slice(0, firstTable.length);

  let xLength = 0;
  let yLength = 0;

  // scan the arrayFromHtmlTable to set the dynamic dimension
  for (let i = 1; i < arrayFromHtmlTable.length; i++) {
    {
      let x = Number(arrayFromHtmlTable[i][0]);
      let y = Number(arrayFromHtmlTable[i][2]);
      if (x > xLength) {
        xLength = x;
      }
      if (y > yLength) {
        yLength = y;
      }
    }
  }

  // initialize the 2D imageArray with empty string
  const rows: number = yLength + 1;
  const cols: number = xLength + 1;
  const imageArray2D: string[][] = Array(rows)
    .fill(null)
    .map(() => Array(cols).fill(''));

  // fill in the empty image if any found
  for (let i = 1; i < arrayFromHtmlTable.length; i++) {
    {
      let x = arrayFromHtmlTable[i][0];
      let y = arrayFromHtmlTable[i][2];
      imageArray2D[Number(y)][Number(x)] = arrayFromHtmlTable[i][1];
    }
  }

  // construct the image for printing/rendering
  let imageStringFull = '';
  for (let x = 2; x > -1; x--) {
    let imageString = '';
    for (let y = 0; y < 4; y++) {
      if (imageArray2D[x][y]) {
        imageString += imageArray2D[x][y];
      }
    }
    imageStringFull += '\n' + imageString;
  }

  console.log(imageStringFull);

  return imageStringFull;
};

const getTableByTabletojson = async () => {
  const url =
    'https://docs.google.com/document/u/0/d/e/2PACX-1vTMOmshQe8YvaRXi6gEPKKlsC6UpFJSMAk4mQjLm_u1gmHdVVTaeh7nBNFBRlui0sTZ-snGwZM4DBCT/pub?pli=1';

  try {
    console.log(`Fetching and parsing tables from: ${url}...`);

    // Fetch and convert all tables on the webpage
    const tables = await Tabletojson.convertUrl(url);

    // Check if any tables were found
    if (!tables || tables.length === 0) {
      console.error('No tables found on this page.');
      return;
    }

    console.log(`Successfully parsed ${tables.length} tables!\n`);

    // Extract the first table found on the page
    const firstTable = tables[0];

    console.log(`firstTable.length ${firstTable.length}\n`);

    return printImageFromTable(firstTable);
  } catch (error) {
    console.error('Error fetching or parsing the HTML table:', error);
  }
};

const PrintImageFromTableElement = () => {
  getTableByTabletojson().then(r => {
    console.log(`jd ok\n`);
  });

  const imageStringFull = 'jd testing';

  return <div style={{ whiteSpace: 'pre-line' }}>{imageStringFull}</div>;
};

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="9">
        <PrintImageFromTableElement />
      </Col>

      <Col md="9">
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
