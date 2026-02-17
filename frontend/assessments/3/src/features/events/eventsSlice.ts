import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import type { RegData, EventState, Status_Result } from '../../types'; 

const initialState: EventState = {
  regId: null,
  activeRegistration: null,
  loadingState: 'idle',
  errorMessage: null
};



const API_BASE_URL = "https://158l7fr9gf.execute-api.ap-southeast-1.amazonaws.com/prod";

export const submit_Registration = createAsyncThunk( 
  'events/register',
  async (payload: RegData) => {

    const awsPayload = {
      userName: payload.fullName,
      userEmail: payload.email_address,
      eventName: payload.selectedEvent,
      message: payload.user_msg
    };

    const response = await fetch(`${API_BASE_URL}/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(awsPayload)
    });
    return (await response.json()); 
  }
);

export const fetchStatusData = createAsyncThunk( 
  'events/fetchStatus',
  async (id: string) => {
    const res = await fetch(`${API_BASE_URL}/registration/${id}`);
    return (await res.json()) as Status_Result;
  }
);

const eventSlice = createSlice({
  name: 'event_slice',
  initialState,
  reducers: {
    reset_reg: (state) => { state.regId = null; }
  },
  extraReducers: (builder) => {
    builder
      .addCase(submit_Registration.pending, (state) => { 
        state.loadingState = 'loading'; 
      })
      .addCase(submit_Registration.fulfilled, (state, action) => {
        state.loadingState = 'succeeded';
        state.regId = action.payload.registrationId; 
        state.activeRegistration = {
            registrationId: action.payload.registrationId,
            current_status: 'Queued'
        };
      })
      .addCase(fetchStatusData.fulfilled, (state, action) => {
        state.activeRegistration = action.payload;
        state.loadingState = 'succeeded';
      });
  }
});

export const { reset_reg } = eventSlice.actions;
export default eventSlice.reducer;