import type { AppConfig, BookingDetails } from "../../../../types";
import styles from "./CleaningPreferences.module.scss";

interface props_interface {
  config: AppConfig;
  details: BookingDetails;
  errors: Record<string, string>;
  on_select: (field: "cleaningType" | "frequency", value: string) => void;
}

export const CleaningPreferences = ({
  config,
  details,
  errors,
  on_select,
}: props_interface) => (
  <section className={styles.section}>
    <div className={styles.sectionHeader}>Cleaning Preferences</div>
    <div className={styles.sectionBody}>
      <div className={styles.subHeader}>What type of cleaning?</div>
      <div className={styles.verticalStack}>
        {config.cleaningTypes.map((Type_Item) => (
          <button
            key={Type_Item.id}
            className={`${styles.optionBtn} ${details.cleaningType === Type_Item.id ? styles.active : ""}`}
            style={{ borderColor: errors.cleaningType ? "#e74c3c" : "" }}
            onClick={() => on_select("cleaningType", Type_Item.id)}
          >
            {Type_Item.label}
          </button>
        ))}
      </div>
      {errors.cleaningType && (
        <span className={styles.RequiredText}>Required</span>
      )}

      <div className={styles.subHeader}>How often would you like cleaning?</div>
      <div className={styles.verticalStack}>
        {config.frequencies.map((freq: any) => (
          <button
            key={freq.id}
            className={`${styles.optionBtn} ${details.frequency === freq.id ? styles.active : ""}`}
            style={{ borderColor: errors.frequency ? "#e74c3c" : "" }}
            onClick={() => on_select("frequency", freq.id)}
          >
            {freq.label}
          </button>
        ))}
      </div>
      {errors.frequency && (
        <span className={styles.RequiredText}>Required</span>
      )}
    </div>
  </section>
);
