import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';
import type { AppConfig, BookingDetails, CreditCardDetails, PersonalDetails } from '../../types';


export interface BookingSubmission {
  details: BookingDetails;
  payment: CreditCardDetails;
  personal: PersonalDetails;
  totalPrice: number;
}

export const api = createApi({
  reducerPath: 'api',

  baseQuery: fetchBaseQuery({ baseUrl: import.meta.env.VITE_API_BASE_URL }),
  tagTypes: ['Config', 'Booking'],
  endpoints: (builder) => ({

    getConfig: builder.query<AppConfig, void>({
      query: () => 'config',
      providesTags: ['Config'],
    }),
    

    submitBooking: builder.mutation<{ success: boolean; bookingId: string }, BookingSubmission>({
      query: (bookingData) => ({
        url: 'booking',
        method: 'POST',
        body: bookingData,
      }),
      invalidatesTags: ['Booking'],
    }),
  }),
});

export const { useGetConfigQuery, useSubmitBookingMutation } = api;