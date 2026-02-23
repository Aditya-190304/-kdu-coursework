import { createSlice, type PayloadAction } from '@reduxjs/toolkit';
import type { UserState } from '../types';

const initialState: UserState = {
  name: localStorage.getItem('username') || "", 
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setUsername: (state, action: PayloadAction<string>) => {
      state.name = action.payload;
      localStorage.setItem('username', action.payload);
    },
  },
});

export const { setUsername } = userSlice.actions;
export default userSlice.reducer;