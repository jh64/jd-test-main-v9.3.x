import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  loading: false,
  errorMessage: null as string | null,
  totalItems: 0,
};

export type AdministrationState = Readonly<typeof initialState>;

// Actions

export const AdministrationSlice = createSlice({
  name: 'administration',
  initialState,
  reducers: {},
  extraReducers() {},
});

// Reducer
export default AdministrationSlice.reducer;
