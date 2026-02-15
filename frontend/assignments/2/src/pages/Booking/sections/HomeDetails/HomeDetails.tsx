import { BedIcon, BathIcon, getExtraIcon } from "../../../../components/Icons";
import { updateBookingDetails } from "../../../../features/booking";
import type { AppConfig, BookingDetails, ExtraOption } from "../../../../types";
import styles from "./HomeDetails.module.scss";

interface Home_Props {
  config: AppConfig;
  details: BookingDetails;
  sender: any;
}

export const HomeDetails = ({ config, details, sender }: Home_Props) => (
  <section className={styles.section}>
    <div className={styles.sectionHeader}>Tell us about your home</div>
    <div className={styles.sectionBody}>
      <div className={styles.counters}>
        <div className={styles.counterCard}>
          <div className={styles.cardTop}>
            <BedIcon />
            <span className={styles.cardLabel}>BEDROOMS</span>
          </div>
          <div className={styles.cardBottom}>
            <button
              onClick={() =>
                sender(
                  updateBookingDetails({
                    bedrooms: Math.max(1, details.bedrooms - 1),
                  }),
                )
              }
            >
              -
            </button>
            <span className={styles.cardValue}>{details.bedrooms}</span>
            <button
              onClick={() =>
                sender(updateBookingDetails({ bedrooms: details.bedrooms + 1 }))
              }
            >
              +
            </button>
          </div>
        </div>

        <div className={styles.counterCard}>
          <div className={styles.cardTop}>
            <BathIcon />
            <span className={styles.cardLabel}>BATHROOMS</span>
          </div>
          <div className={styles.cardBottom}>
            <button
              onClick={() =>
                sender(
                  updateBookingDetails({
                    bathrooms: Math.max(1, details.bathrooms - 1),
                  }),
                )
              }
            >
              -
            </button>
            <span className={styles.cardValue}>{details.bathrooms}</span>
            <button
              onClick={() =>
                sender(
                  updateBookingDetails({ bathrooms: details.bathrooms + 1 }),
                )
              }
            >
              +
            </button>
          </div>
        </div>
      </div>

      <div className={styles.subHeader}>Need any extras?</div>
      <div className={styles.verticalStack}>
        {config.extras.map((extra_obj: ExtraOption) => (
          <button
            key={extra_obj.id}
            className={`${styles.extraBtn} ${details.extras.includes(extra_obj.id) ? styles.active : ""}`}
            onClick={() => {
              const updated_list = details.extras.includes(extra_obj.id)
                ? details.extras.filter((e: string) => e !== extra_obj.id)
                : [...details.extras, extra_obj.id];
              sender(updateBookingDetails({ extras: updated_list }));
            }}
          >
            <div className={styles.icon}>{getExtraIcon(extra_obj.id)}</div>
            <span>{extra_obj.label}</span>
          </button>
        ))}
      </div>
      <div className={styles.specialReq}>
        <label>
          Do you have any special requirements?{" "}
          <span className={styles.optional}>(optional)</span>
        </label>
        <textarea
          rows={4}
          value={details.specialRequirements}
          onChange={(e) =>
            sender(
              updateBookingDetails({ specialRequirements: e.target.value }),
            )
          }
        />
      </div>
    </div>
  </section>
);
