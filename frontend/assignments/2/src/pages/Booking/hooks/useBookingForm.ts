import { useState } from "react";
import { useAppDispatch } from "../../../app/hooks";
import { 
  updateBookingDetails, 
  updatePaymentDetails, 
  updatePersonalDetails 
} from "../../../features/booking";
import { 
  isValidEmail, 
  isValidPhone, 
  isValidZip, 
  isValidCard, 
  isValidCVV 
} from "../../../utils/validation";
import type { BookingDetails, CreditCardDetails, PersonalDetails } from "../../../types";

export const useBookingForm = (
  details: BookingDetails, 
  payment: CreditCardDetails, 
  personal: PersonalDetails
) => {
  const dispatch = useAppDispatch();
  const [errors, setErrors] = useState<Record<string, string>>({});

  const handleBookingUpdate = (field: keyof BookingDetails, value: any) => {
    dispatch(updateBookingDetails({ [field]: value }));
    if (errors[field as string]) {
      setErrors((prev) => ({ ...prev, [field as string]: "" }));
    }
  };

  const handleUserUpdate = (
    field: string, 
    value: string | boolean, 
    section: "payment" | "personal"
  ) => {
    if (section === "payment") {
      dispatch(updatePaymentDetails({ [field]: value }));
    } else {
      dispatch(updatePersonalDetails({ [field]: value }));
    }
    if (errors[field]) {
      setErrors((prev) => ({ ...prev, [field]: "" }));
    }
  };

  const validateForm = () => {
    const newErrs: Record<string, string> = {};
    if (!details.cleaningType) newErrs.cleaningType = "Required";

    if (!details.frequency) newErrs.frequency = "Required";

    if (!details.date) newErrs.date = "Required";

    if (!details.startTime) newErrs.startTime = "Required";

    if (!isValidCard(payment.cardNumber)) newErrs.cardNumber = "Invalid Card Number";

    if (!payment.expiry) newErrs.expiry = "Required";

    if (!isValidCVV(payment.cvv)) newErrs.cvv = "Invalid CVV";

    if (!payment.nameOnCard) newErrs.nameOnCard = "Required";

    if (!isValidEmail(personal.email)) newErrs.email = "Invalid Email";

    if (!isValidPhone(personal.phone)) newErrs.phone = "Invalid Phone";

    if (!personal.address) newErrs.address = "Required";

    if (!isValidZip(personal.zipCode)) newErrs.zipCode = "Invalid Zip";
    
    if (!personal.termsAccepted) newErrs.terms = "You must accept the terms";

    setErrors(newErrs);
    return Object.keys(newErrs).length === 0;
  };

  return { errors, handleBookingUpdate, handleUserUpdate, validateForm };
};