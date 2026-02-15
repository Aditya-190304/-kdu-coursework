import { useMemo } from "react";
import { useNavigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../../app/hooks";
import {
  selectBookingDetails,
  selectPaymentDetails,
  selectPersonalDetails,
  calculateTotal,
} from "../../features/booking";
import {
  useGetConfigQuery,
  useSubmitBookingMutation,
} from "../../features/booking/bookingApi";

import { Loader } from "../../components/Loader/Loader";
import { useScrollToTop } from "../../hooks/useScrollToTop";
import { useBookingForm } from "./hooks/useBookingForm"; 

import { CleaningPreferences } from "./sections/cleaning_preferences/CleaningPreferences";
import { HomeDetails } from "./sections/HomeDetails/HomeDetails";
import { DateTime } from "./sections/datetime/Date_Time";
import { PaymentMethod } from "./sections/PaymentMethod/PaymentMethod";
import { PersonalDetails } from "./sections/PersonalDetails/personalDetails";
import { SummaryCard } from "./sections/SummaryCard/SummaryCard";

import styles from "./Booking.module.scss";

export const Booking = () => { 
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  useScrollToTop();

  const { data: config, isLoading: isConfigLoading, isError: configError } = useGetConfigQuery();
  const [submitBooking, { isLoading: isSubmitting }] = useSubmitBookingMutation();

  const details = useAppSelector(selectBookingDetails);
  const payment = useAppSelector(selectPaymentDetails);
  const personal = useAppSelector(selectPersonalDetails);

  const { errors, handleBookingUpdate, handleUserUpdate, validateForm } = 
    useBookingForm(details, payment, personal);

  const totalPrice = useMemo(
    () => (config ? calculateTotal(details, config) : 0),
    [details, config]
  );

  const handleFinishBooking = async () => {
    if (!validateForm()) return window.scrollTo({ top: 0, behavior: "smooth" });

    try {
      const result = await submitBooking({
        details,
        payment: { ...payment, cvv: "***" },
        personal,
        totalPrice,
      }).unwrap();

      if (result?.success || result?.bookingId) {
        navigate("/confirmation", {
          state: {
            bookingId: result.bookingId,
            details: {
              date: details.date,
              startTime: details.startTime,
              hours: details.hours || 0,
            },
            totalPrice,
          },
        });
      }
    } catch (err) {
      alert("Booking failed. Check console for details.");
    }
  };

  if (isConfigLoading || isSubmitting) return <Loader />;
  if (configError || !config) return <div className={styles.error}>Failed to load configuration.</div>;

  return (
    <div className={styles.bookingPage}>
      <header className={styles.headerTitle}>
        <h1>Book your cleaning</h1>
        <p>It's time to book our professional cleaning service for your home.</p>
      </header>

      <main className={styles.container}>
        <div className={styles.formSection}>
          <CleaningPreferences
            config={config}
            details={details}
            errors={errors}
            on_select={handleBookingUpdate}
          />
          <HomeDetails
            config={config}
            details={details}
            sender={dispatch}
          />
          <DateTime
            config={config}
            details={details}
            errors={errors}
            dispatch={dispatch}
            PickTime={handleBookingUpdate as any}
          />
          <PaymentMethod
            payment={payment}
            errors={errors}
            change_data={handleUserUpdate as any}
          />
          <PersonalDetails
            personal={personal}
            errors={errors}
            dispatch={dispatch}
            doUpdate={handleUserUpdate as any}
            complete_btn_click={handleFinishBooking}
          />
        </div>
        
        <aside className={styles.summarySticky}>
          <SummaryCard
            config={config}
            details={details}
            personal={personal}
            price_val={totalPrice}
          />
        </aside>
      </main>
    </div>
  );
}; 