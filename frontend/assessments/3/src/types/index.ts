

export interface RegData { 
  fullName: string; 
  email_address: string;
  selectedEvent: string;
  user_msg?: string; 
}

export interface Register_Response {
  registrationId: string;
  msg: string;
}

export interface Status_Result {
  registrationId: string;
  current_status: 'Queued' | 'Successful' | 'Failed'; 
  userName?: string; 
  userEmail?: string;
  eventName?: string;
}

export interface EventState {
  regId: string | null;
  activeRegistration: Status_Result | null;
  loadingState: 'idle' | 'loading' | 'succeeded' | 'failed';
  errorMessage: string | null;
}