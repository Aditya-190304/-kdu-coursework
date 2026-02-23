import { configureStore } from '@reduxjs/toolkit';
import userReducer from '../slice/userSlice';
import stockReducer from '../slice/stockSlice';

export const store = configureStore({
  reducer: {
    user: userReducer,
    stocks: stockReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;