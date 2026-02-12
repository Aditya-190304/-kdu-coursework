import { useEffect, useState, useCallback } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../../../store/hooks';
import { performSearch, loadAll, resetSearch, setQuery } from '../../../store/slices/productSlice';
import { APP_ROUTES, DEBOUNCE_DELAY_MS } from '../../../constants';
import styles from './navBar.module.scss';

const NavBar = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const cartCount = useAppSelector(state => state.cart.totalCount);
  const currentQuery = useAppSelector(state => state.products.searchQuery);
  const [localSearch, setLocalSearch] = useState(currentQuery);

  useEffect(() => {
    const handler = setTimeout(() => 
        {
      if (localSearch.trim()) {
        dispatch(setQuery(localSearch));
        dispatch(performSearch(localSearch));
        navigate(APP_ROUTES.HOME);
      } 
      else if (currentQuery !== '') 
        {
        dispatch(resetSearch());
        dispatch(loadAll());
      }
    }, DEBOUNCE_DELAY_MS);
    
    return () => clearTimeout(handler);

  }, [localSearch, dispatch, navigate, currentQuery]);

  const handleClear = useCallback(() => 
    {
    setLocalSearch(''); dispatch(resetSearch()); dispatch(loadAll());
  }, [dispatch]);

  return (
    <nav className={styles.navBar} aria-label="mainnavigation">
      <Link to={APP_ROUTES.HOME} className={styles.brand}>Product Discovery</Link>
      <div className={styles.searchWrap}>
        <input type="text" placeholder="Search..." value={localSearch} onChange={(e) => setLocalSearch(e.target.value)} />
        {localSearch && <button onClick={handleClear}>X</button>}
      </div>
      <Link to={APP_ROUTES.CART} className={styles.cartLink}>Cart <span className={styles.badge}>{cartCount}</span></Link>
    </nav>
  );
};
export default NavBar;