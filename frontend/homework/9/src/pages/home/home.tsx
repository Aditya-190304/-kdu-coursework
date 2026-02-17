import { useEffect } from 'react';
import { useAppDispatch, useAppSelector } from '../../store/hooks';
import { loadAll } from '../../store/slices/productSlice';
import ProductCard from '../../components/features/productCard/productCard';
import Loader from '../../components/common/Loader';
import ErrorComponent from '../../components/common/Error';
import styles from './home.module.scss';

export default function Home() {
  const dispatch = useAppDispatch();
  const { items, isLoadingList, isLoadingSearch, error, searchQuery } = useAppSelector(s => s.products);

  useEffect(() => 
    {
    if (items.length === 0 && !searchQuery) 
        {
      const promise = dispatch(loadAll());
      return () => promise.abort();
    }
  }, [dispatch, items.length, searchQuery]);

  if (isLoadingList || isLoadingSearch) return <Loader />;
  if (error) return <ErrorComponent message={error} onRetry={() => dispatch(loadAll())} />;
  
  if (items.length === 0) return <div className={styles.empty}><h2>no products found</h2><p>try a different search term.</p></div>;

  return (
    <div className={styles.homeContainer}>
      <h2>{searchQuery ? `results for "${searchQuery}"` : "all products"}</h2>
      <div className={styles.grid}>
        {items.map(p => <ProductCard key={p.id} prodData={p} />)}
      </div>
    </div>
  );
}