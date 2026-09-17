import { Storage } from 'react-jhipster';

import axios, { type AxiosError } from 'axios';

import { AUTHENTICATION_TOKEN_KEY } from 'app/shared/jhipster/constants';

const TIMEOUT = 1 * 60 * 1000;
axios.defaults.timeout = TIMEOUT;
axios.defaults.baseURL = SERVER_API_URL;

const setupAxiosInterceptors = onUnauthenticated => {
  const onRequestSuccess = config => {
    const token = Storage.local.get(AUTHENTICATION_TOKEN_KEY) || Storage.session.get(AUTHENTICATION_TOKEN_KEY);
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  };
  const onResponseSuccess = response => response;
  const onResponseError = (err: AxiosError) => {
    const status = err.status ?? err.response?.status ?? 0;
    if (status === 401) {
      onUnauthenticated();
    }
    return Promise.reject(err);
  };
  axios.interceptors.request.use(onRequestSuccess);
  axios.interceptors.response.use(onResponseSuccess, onResponseError);
};

export default setupAxiosInterceptors;
