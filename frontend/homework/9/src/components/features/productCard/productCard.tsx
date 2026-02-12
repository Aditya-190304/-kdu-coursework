import { memo } from 'react';
import { Link } from 'react-router-dom';
import type { Product } from '../../../types';
import { DISCOUNT_DIVISOR } from '../../../constants';
import styles from './productCard.module.scss';

const ProductCard = memo(({ prodData }: { prodData: Product }) => {const discounted = (prodData.price * (1 - prodData.discountPercentage / DISCOUNT_DIVISOR)).toFixed(2);
  return (
    <div className={styles.card}>
      <span className={styles.discount}>-{prodData.discountPercentage}%</span>
      <img src={prodData.thumbnail} alt={prodData.title} />
      <div className={styles.info}>
        <h3>{prodData.title}</h3>
        <p><del>${prodData.price}</del> <strong>${discounted}</strong></p>
      </div>
      <Link to={`/product/${prodData.id}`} className={styles.stretchedLink} aria-label={`View ${prodData.title}`} />
    </div>
  );
});
export default ProductCard;