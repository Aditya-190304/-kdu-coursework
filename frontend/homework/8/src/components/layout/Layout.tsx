import { Outlet } from 'react-router-dom';
import { Navbar } from './Navbar';
import styles from './Layout.module.scss';

export const Layout = () => {
  return (
    <div>
      <Navbar />
      <main className={styles.mainContent}>
        <Outlet />
      </main>
    </div>
  );
};