import type { RootState } from '../../app/store';
import type { AppConfig, BookingDetails } from '../../types';

export const selectBookingDetails = (state: RootState) => state.booking.details;
export const selectPaymentDetails = (state: RootState) => state.booking.payment;
export const selectPersonalDetails = (state: RootState) => state.booking.personal;


export const calculateTotal = (details: BookingDetails, config: AppConfig | undefined): number => {
  if (!config) return 0;

  const typePrice = config.cleaningTypes.find(t => t.id === details.cleaningType)?.basePrice || 0;
  
  const extrasPrice = details.extras.reduce((acc, extraId) => {
    const extra = config.extras.find(e => e.id === extraId);
    return acc + (extra ? extra.price : 0);
  }, 0);
  
  const roomCost = (details.bedrooms * 10) + (details.bathrooms * 15);

  const hourCost = details.hours * 20;
  
  const subtotal = typePrice + extrasPrice + roomCost + hourCost;
  
  const freqMult = config.frequencies.find(f => f.id === details.frequency)?.multiplier || 1;
  
  return Math.round(subtotal * freqMult);
};