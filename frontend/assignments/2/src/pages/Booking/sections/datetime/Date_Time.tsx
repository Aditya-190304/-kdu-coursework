import styles from "./DateTime.module.scss";
import { updateBookingDetails } from "../../../../features/booking";
import type { AppConfig, BookingDetails, TimeSlot } from "../../../../types";

interface time_data_interface {
  config: AppConfig;
  details: BookingDetails;
  errors: Record<string, string>;
  dispatch: any;
  PickTime: (field: "startTime", value: string) => void;
}

export const DateTime = ({
  config,
  details,
  errors,
  dispatch,
  PickTime,
}: time_data_interface) => (
  <section className={styles.section}>
    <div className={styles.sectionHeader}>Choose hours and dates</div>
    <div className={styles.sectionBody}>
      <div className={styles.dateTime}>
        <div className={styles.dateBlock}>
          <label>How many hours?</label>
          <div className={styles.hoursControl}>
            <button
              onClick={() =>
                dispatch(
                  updateBookingDetails({
                    hours: Math.max(1, details.hours - 1),
                  }),
                )
              }
            >
              -
            </button>
            <span className={styles.cardValue}>{details.hours}</span>
            <button
              onClick={() =>
                dispatch(updateBookingDetails({ hours: details.hours + 1 }))
              }
            >
              +
            </button>
          </div>
        </div>
        <div className={styles.dateBlock}>
          <label>Choose a date</label>
          <input
            type="date"
            className={styles.dateInput}
            style={{ borderColor: errors.date ? "#e74c3c" : "" }}
            onChange={(e) =>
              dispatch(updateBookingDetails({ date: e.target.value }))
            }
          />
          {errors.date && <span className={styles.RequiredText}>Required</span>}
        </div>
      </div>

      <div className={styles.subHeader}>When do you like to start?</div>
      <div className={styles.timeGrid}>
        {config.timeSlots.map((slot_item: TimeSlot) => (
          <button
            key={slot_item.id}
            className={`${styles.optionBtn} ${details.startTime === slot_item.time ? styles.active : ""}`}
            onClick={() => PickTime("startTime", slot_item.time)}
          >
            {slot_item.time}
          </button>
        ))}
      </div>
      {errors.startTime && (
        <span className={styles.RequiredText}>Required</span>
      )}
    </div>
  </section>
);
