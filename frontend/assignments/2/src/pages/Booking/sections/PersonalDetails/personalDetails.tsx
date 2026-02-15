import styles from "./personalDetails.module.scss";
import { updatePersonalDetails } from "../../../../features/booking";
import type { PersonalDetails as PersonalData } from "../../../../types";

interface user_props {
  personal: PersonalData;
  errors: Record<string, string>;
  dispatch: any;
  doUpdate: (
    field: keyof PersonalData,
    value: string,
    section: "personal",
  ) => void;
  complete_btn_click: () => void;
}

export const PersonalDetails = ({
  personal,
  errors,
  dispatch,
  doUpdate,
  complete_btn_click,
}: user_props) => (
  <section className={styles.section}>
    <div className={styles.sectionHeader}>Personal Details</div>
    <div className={styles.sectionBody}>
      <div className={styles.personalForm}>
        <div className={styles.fieldGroup}>
          <input
            placeholder="Email Address"
            value={personal.email}
            style={{ borderColor: errors.email ? "#e74c3c" : "#ddd" }}
            onChange={(e) => doUpdate("email", e.target.value, "personal")}
          />
          {errors.email && (
            <span className={styles.RequiredText}>Required</span>
          )}
        </div>

        <div className={styles.fieldGroup}>
          <input
            placeholder="Phone Number"
            value={personal.phone}
            maxLength={10}
            style={{ borderColor: errors.phone ? "#e74c3c" : "#ddd" }}
            onChange={(e) =>
              doUpdate("phone", e.target.value.replace(/\D/g, ""), "personal")
            }
          />
          {errors.phone && (
            <span className={styles.RequiredText}>Required</span>
          )}
        </div>

        <div className={styles.fieldGroup}>
          <input
            placeholder="Address"
            value={personal.address}
            style={{ borderColor: errors.address ? "#e74c3c" : "#ddd" }}
            onChange={(e) => doUpdate("address", e.target.value, "personal")}
          />
          {errors.address && (
            <span className={styles.RequiredText}>Required</span>
          )}
        </div>

        <div className={styles.fieldGroup}>
          <input
            placeholder="Zip Code"
            value={personal.zipCode}
            maxLength={6}
            style={{ borderColor: errors.zipCode ? "#e74c3c" : "#ddd" }}
            onChange={(e) =>
              doUpdate("zipCode", e.target.value.replace(/\D/g, ""), "personal")
            }
          />
          {errors.zipCode && (
            <span className={styles.RequiredText}>Required</span>
          )}
        </div>

        <div className={styles.terms}>
          <input
            type="checkbox"
            id="termsCheckbox"
            checked={personal.termsAccepted}
            onChange={(e) =>
              dispatch(
                updatePersonalDetails({ termsAccepted: e.target.checked }),
              )
            }
          />
          <label htmlFor="termsCheckbox">
            I read and agree to the terms & conditions
          </label>
        </div>

        <button
          className={styles.submitBtn}
          disabled={!personal.termsAccepted}
          onClick={complete_btn_click}
        >
          Complete Booking
        </button>
      </div>
    </div>
  </section>
);
