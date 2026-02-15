import { createSlice, type PayloadAction } from '@reduxjs/toolkit';
import type { BookingDetails, CreditCardDetails, PersonalDetails } from '../../types';



interface FormState {
  details: BookingDetails;
  payment: CreditCardDetails;
  personal: PersonalDetails;
}

const initialState: FormState = {
  details: {
    cleaningType: '',
    frequency: '',
    bedrooms: 1,
    bathrooms: 1,
    extras: [],
    hours: 2,
    date: '',
    startTime: '',
    specialRequirements: ''
  },
  payment: {
    cardNumber: '',
    expiry: '',
    cvv: '',
    nameOnCard: ''
  },
  personal: {
    email: '',
    phone: '',
    address: '',
    zipCode: '',
    termsAccepted: false
  },
};

const bookingSlice = createSlice({
  name: 'booking',
  initialState,
  reducers: {
    updateBookingDetails: (state, action: PayloadAction<Partial<BookingDetails>>) => {state.details = { ...state.details, ...action.payload };
    },
    updatePaymentDetails: (state, action: PayloadAction<Partial<CreditCardDetails>>) => {
      state.payment = { ...state.payment, ...action.payload };
    },
    updatePersonalDetails: (state, action: PayloadAction<Partial<PersonalDetails>>) => {
      state.personal = { ...state.personal, ...action.payload };
    },
    resetBooking: () => initialState
  },
});

export const { updateBookingDetails, updatePaymentDetails, updatePersonalDetails, resetBooking } = bookingSlice.actions;
export default bookingSlice.reducer;