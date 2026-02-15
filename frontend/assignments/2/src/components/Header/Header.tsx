import styles from "./Header.module.scss";

export const Header = () => {
  return (
    <header className={styles.header}>
      <div className={styles.container}>
        <div className={styles.logo}>Cleanly</div>

        <div className={styles.support}>
          <span className={styles.phoneNumber}>800-710-8420</span>
        </div>
      </div>
    </header>
  );
};
