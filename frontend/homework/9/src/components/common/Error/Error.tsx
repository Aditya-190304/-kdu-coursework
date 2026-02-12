import styles from './Error.module.scss';
const ErrorComponent = ({ message, onRetry }: { message: string, onRetry?: () => void }) => 
    (
  <div className={styles.errBox} role="alert">
<p>{message}</p>
    {onRetry && <button onClick={onRetry}>Retry</button>}
  </div>
);
export default ErrorComponent;