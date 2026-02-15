import { configureStore } from '@reduxjs/toolkit';

import { api } from '../features/booking/bookingApi'; 
import bookingReducer from '../features/booking/bookingSlice';
export const store = configureStore({
  reducer: {
    booking: bookingReducer,

    [api.reducerPath]: api.reducer, 
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(api.middleware),
});

export type AppDispatch = typeof store.dispatch;
export type RootState = ReturnType<typeof store.getState>;