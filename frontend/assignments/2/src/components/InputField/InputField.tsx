import styles from "./InputField.module.scss";

export const InputField = ({ label, error, ...props }: any) => (
  <div className={styles.container}>
    {label && <label>{label}</label>}
    <input
      className={`${styles.input} ${error ? styles.inputError : ""}`}
      {...props}
    />
    {error && <span className={styles.error}>{error}</span>}
  </div>
);
