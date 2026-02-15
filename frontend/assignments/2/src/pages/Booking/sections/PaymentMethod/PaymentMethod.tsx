import { VisaIcon, LockIcon } from "../../../../components/Icons";
import styles from "./PaymentMethod.module.scss";
import type { CreditCardDetails } from "../../../../types";

interface props_pay {
  payment: CreditCardDetails;
  errors: Record<string, string>;
  change_data: (
    field: keyof CreditCardDetails,
    value: string,
    section: "payment",
  ) => void;
}

export const PaymentMethod = ({ payment, errors, change_data }: props_pay) => (
  <section className={styles.section}>
    <div className={styles.sectionHeader}>Payment Method</div>
    <div className={styles.sectionBody}>
      <div className={styles.sslBadge}>
        <LockIcon />
        <div>
          <strong>256 bit Secure</strong>
          <br />
          <small>SSL Encryption</small>
        </div>
      </div>

      <div className={styles.subHeader}>Credit Card details</div>
      <div className={styles.creditCardForm}>
        <div className={styles.cardInputWrapper}>
          <input
            placeholder="Card number"
            value={payment.cardNumber}
            maxLength={16}
            style={{ borderColor: errors.cardNumber ? "#e74c3c" : "#ddd" }}
            onChange={(e) =>
              change_data(
                "cardNumber",
                e.target.value.replace(/\D/g, ""),
                "payment",
              )
            }
          />
          <div className={styles.cardLogo}>
            <VisaIcon />
          </div>
        </div>
        {errors.cardNumber && (
          <span className={styles.RequiredText}>Required</span>
        )}

        <div className={styles.row}>
          <div style={{ flex: 1 }}>
            <input
              placeholder="MM/YY"
              value={payment.expiry}
              maxLength={5}
              style={{
                width: "100%",
                borderColor: errors.expiry ? "#e74c3c" : "#ddd",
              }}
              onChange={(e) => change_data("expiry", e.target.value, "payment")}
            />
            {errors.expiry && (
              <span className={styles.RequiredText}>Required</span>
            )}
          </div>
          <div style={{ flex: 1 }}>
            <input
              placeholder="CVV"
              value={payment.cvv}
              maxLength={4}
              style={{
                width: "100%",
                borderColor: errors.cvv ? "#e74c3c" : "#ddd",
              }}
              onChange={(e) =>
                change_data("cvv", e.target.value.replace(/\D/g, ""), "payment")
              }
            />
            {errors.cvv && (
              <span className={styles.RequiredText}>Required</span>
            )}
          </div>
        </div>
        <div>
          <input
            placeholder="Name as on Card"
            value={payment.nameOnCard}
            style={{
              width: "100%",
              borderColor: errors.nameOnCard ? "#e74c3c" : "#ddd",
            }}
            onChange={(e) =>
              change_data("nameOnCard", e.target.value, "payment")
            }
          />
          {errors.nameOnCard && (
            <span className={styles.RequiredText}>Required</span>
          )}
        </div>
      </div>
    </div>
  </section>
);
