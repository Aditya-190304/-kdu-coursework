export interface CleaningType {
  id: string;
  label: string;
  basePrice: number;
}

export interface CleaningFrequency {
  id: string;
  label: string;
  multiplier: number;
}

export interface ExtraOption {
  id: string;
  label: string;
  price: number;
}

export interface TimeSlot {
  id: string;
  time: string;
}

export interface AppConfig {
  cleaningTypes: CleaningType[];
  frequencies: CleaningFrequency[];
  extras: ExtraOption[];
  timeSlots: TimeSlot[];
}

export interface BookingDetails {
  cleaningType: string;
  frequency: string;
  bedrooms: number;
  bathrooms: number;
  extras: string[];
  hours: number;
  date: string;
  startTime: string;
  specialRequirements?: string;
}

export interface CreditCardDetails {
  cardNumber: string;
  expiry: string;
  cvv: string;
  nameOnCard: string;
}

export interface PersonalDetails {
  email: string;
  phone: string;
  address: string;
  zipCode: string;
  termsAccepted: boolean;
}

export interface BookingState {
  config: AppConfig | null;
  details: BookingDetails;
  payment: CreditCardDetails;
  personal: PersonalDetails;
  status: 'idle' | 'loading' | 'succeeded' | 'failed';
  totalPrice: number;
}