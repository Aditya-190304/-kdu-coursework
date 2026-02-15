import { useLocation, useNavigate } from "react-router-dom";
import styles from "./Confirmation.module.scss";

interface LocationState {
  bookingId: string;
  details: {
    date: string;
    startTime: string;
    hours: number;
  };
  totalPrice: number;
}

export const Confirmation = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const state = location.state as LocationState;

  if (!state || !state.details) {
    return (
      <div className={styles.pageWrapper}>
        <div className={styles.container}>
          <div className={styles.errorCard}>
            <h1>No Booking Found</h1>
            <button onClick={() => navigate("/")}>Return Home</button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className={styles.pageWrapper}>
      {/* REMOVED <Header /> FROM HERE 
         Since it is already rendered by your global Layout/App wrapper
      */}

      <main className={styles.container}>
        <div className={styles.successCard}>
          <div className={styles.iconContainer}>
            <div className={styles.checkmark}>✓</div> 
          </div>
          
          <h1 className={styles.mainTitle}>BOOKING CONFIRMED</h1>
          <p className={styles.subtext}>
            Your professional cleaning has been scheduled!
          </p>

          <div className={styles.bookingRef}>
            <span>Order Reference:</span>
            <strong>{state.bookingId}</strong>
          </div>

          <hr className={styles.divider} />

          <div className={styles.detailsGrid}>
            <div className={styles.detailItem}>
              <label>DATE & TIME</label>
              <p>{state.details.date} at {state.details.startTime}</p>
            </div>
            <div className={styles.detailItem}>
              <label>DURATION</label>
              <p>{state.details.hours} Hours</p>
            </div>
            <div className={styles.detailItem}>
              <label>TOTAL PRICE</label>
              <p className={styles.price}>${state.totalPrice}</p>
            </div>
          </div>

          <button className={styles.homeBtn} onClick={() => navigate("/")}>
            Back to Home
          </button>
        </div>
      </main>
    </div>
  );
};