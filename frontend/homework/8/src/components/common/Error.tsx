import React from 'react';
import styles from './Error.module.scss';

export const ErrorState: React.FC<{ message: string }> = ({ message }) => (
  <div className={styles.errorContainer}>
    <strong>Error:</strong> {message}
  </div>
);