import styles from './Loader.module.scss';
const Loader = () => (
  <div className={styles.spinnerWrap} role="status" aria-live="polite">
    <div className={styles.spinner}></div>
    <span className="sr-only" style={{display:'none'}}>loading...</span>
  </div>
);
export default Loader;