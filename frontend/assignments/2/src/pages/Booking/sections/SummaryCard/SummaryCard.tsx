import {
  SummaryBrushIcon,
  SummaryCalendarIcon,
  SummaryClockIcon,
  SummaryCycleIcon,
  SummaryPinIcon,
} from "../../../../components/Icons";
import styles from "./SummaryCard.module.scss";

interface Summary_Props {
  config: any;
  details: any;
  personal: any;
  price_val: number;
}

export const SummaryCard = ({
  config,
  details,
  personal,
  price_val,
}: Summary_Props) => (
  <div className={styles.summarySection}>
    <div className={styles.summaryCard}>
      <h3>Booking Summary</h3>
      <ul>
        <li>
          <span className={styles.icon}>
            <SummaryBrushIcon />
          </span>
          <span>
            {config.cleaningTypes.find(
              (t: any) => t.id === details.cleaningType,
            )?.label || "..."}{" "}
            Cleaning
          </span>
        </li>
        <li>
          <span className={styles.icon}>
            <SummaryCalendarIcon />
          </span>

          <span>
            {details.date || "..."}@{details.startTime || "..."}
          </span>
        </li>
        <li>
          <span className={styles.icon}>
            <SummaryClockIcon />
          </span>
          <span>{details.hours} Hours</span>
        </li>
        <li>
          <span className={styles.icon}>
            <SummaryCycleIcon />
          </span>
          <span>
            {config.frequencies.find(
              (f_item: any) => f_item.id === details.frequency,
            )?.label || "..."}
          </span>
        </li>
        <li>
          <span className={styles.icon}>
            <SummaryPinIcon />
          </span>
          <span>{personal.address || "Address not entered"}</span>
        </li>
      </ul>
      <div className={styles.total}>
        <span>Total cost</span>
        <span className={styles.price}>${price_val}</span>
      </div>
    </div>
  </div>
);
