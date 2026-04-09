import { Link } from 'react-router-dom';
import type { Product } from '../../types/product.types';
import { calculateDiscountPrice } from '../../utils/priceHelpers';
import styles from './ProductCard.module.scss';

export const ProductCard = ({ product }: { product: Product }) => {
  const discountedPrice = calculateDiscountPrice(product.price, product.discountPercentage);

  return (
    <Link to={`/product/${product.id}`} className={styles.card} aria-label={`View details for ${product.title}`}>
      <img src={product.thumbnail} alt={product.title} className={styles.image} loading="lazy" />
      <h3 className={styles.title}>{product.title}</h3>
      <p className={styles.brand}>{product.brand}</p>
      <div className={styles.priceContainer}>
        <span className={styles.price}>${discountedPrice}</span>
        {product.discountPercentage > 0 && (
          <>
            <span className={styles.originalPrice}>${product.price}</span>
            <span className={styles.discountBadge}>-{product.discountPercentage}%</span>
          </>
        )}
      </div>
      <div className={styles.rating}><span>★ {product.rating}</span></div>
    </Link>
  );
};