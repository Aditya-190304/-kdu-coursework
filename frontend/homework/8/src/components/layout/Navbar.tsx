import { useEffect, useState, useRef } from 'react';
import { NavLink, useNavigate } from 'react-router-dom'; // 1. Removed useLocation
import { useDebounce } from '../../hooks/useDebounce';
import { useProducts } from '../../context/ProductContext';
import styles from './Navbar.module.scss';

export const Navbar = () => {
  const [Text, setText] = useState('');
  const debouncedSearch = useDebounce(Text, 500);
  const { searchProducts, fetchAllProducts, clearSelectedProduct } = useProducts();
  
  const navigate = useNavigate();
  const inputRef = useRef<HTMLInputElement>(null);


  useEffect(() => {
    const controller = new AbortController();  
    const isTyping = document.activeElement === inputRef.current;

    if (debouncedSearch.trim()) {
      searchProducts(debouncedSearch, controller.signal);
      
      if (clearSelectedProduct) clearSelectedProduct(); 
      if (isTyping) {
          navigate('/'); 
      }
      
    } else {
      fetchAllProducts(controller.signal);
    }

    return () => controller.abort();
  }, [debouncedSearch]); 

  return (
    <nav className={styles.navbar} aria-label="Main Navigation">
      <NavLink to="/" className={styles.logo}>Product discovery</NavLink>

      <div className={styles.navActions}>
        <NavLink to="/"onClick={() => { setText(''); 
        if (clearSelectedProduct) clearSelectedProduct();
          }} 
          className={({ isActive }) => isActive ? `${styles.homeBtn} ${styles.active}` : styles.homeBtn}>
          Home
        </NavLink>

        <div className={styles.searchContainer} role="search">
          <input
            ref={inputRef} type="text"placeholder="search products"value={Text}onChange={(e) => setText(e.target.value)}className={styles.searchInput}aria-label="Search products"/>
          {Text && (
            <button onClick={() => setText('')} className={styles.clearBtn} aria-label="Clear search">
              &times;
            </button>
          )}
        </div>
      </div>
    </nav>
  );
};