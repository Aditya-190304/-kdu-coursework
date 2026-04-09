import { useProducts } from '../../context/ProductContext';
import { ProductCard } from '../../components/product/ProductCard';
import { Loader } from '../../components/common/loader';
import { ErrorState } from '../../components/common/Error';
import styles from './Home.module.scss';

export const Home = () => {
  const { products, isLoading, error } = useProducts();

  if (isLoading) return <Loader />;
  if (error) return <ErrorState message={error} />;
  
  if (!isLoading && products.length === 0) {
    return (
      <div className={styles.noresults}>
        <h3>no product</h3>
        <p>adjust your search query</p>
      </div>
    );
  }

  return (
    <div className={styles.grid}>
      {products.map((product) => (
        <ProductCard key={product.id} product={product} />
      ))}
    </div>
  );
};