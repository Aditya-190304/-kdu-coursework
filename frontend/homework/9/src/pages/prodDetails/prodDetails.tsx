import { useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../../store/hooks';
import { fetchById, cleanupSelection } from '../../store/slices/productSlice';
import { addToCart } from '../../store/slices/cartSlice';
import Loader from '../../components/common/Loader';
import ErrorComponent from '../../components/common/Error';
import { APP_ROUTES, DISCOUNT_DIVISOR } from '../../constants';
import styles from './prodDetails.module.scss';

export default function ProdDetails() {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const dispatch = useAppDispatch();
  const { selectedProduct: p, isLoadingDetail, error } = useAppSelector(s => s.products);

  useEffect(() => {
    if (!id) return;
    const promise = dispatch(fetchById(id));
    return () => { promise.abort(); dispatch(cleanupSelection()); };
  }, [id, dispatch]);

  if (isLoadingDetail) return <Loader />;
  if (error) 
    {
        return <ErrorComponent message={error} onRetry={() => id && dispatch(fetchById(id))} />;
    }
  if (!p) return null;

  const discounted = (p.price * (1 - p.discountPercentage / DISCOUNT_DIVISOR)).toFixed(2);

  return (
    <div className={styles.wrapper}>
      <button onClick={() => navigate(APP_ROUTES.HOME)} className={styles.backBtn}>&larr; back</button>
      <div className={styles.layout}>
        <div className={styles.images}>
          <img src={p.thumbnail} alt={`${p.title} main`} className={styles.mainImg} />
          <div className={styles.thumbs}>
            {p.images.map((img, i) => <img key={`${p.id}-${i}`} src={img} alt={`view ${i}`} />)}
          </div>
        </div>
        <div className={styles.info}>
          <h1>{p.title}</h1>
          <p className={styles.cat}>{p.category}</p>
          <div className={styles.pricing}>
            <del>${p.price}</del> <h2>${discounted}</h2>
          </div>
          <p>{p.description}</p>
          <button onClick={() => dispatch(addToCart(p))} className={styles.addBtn}>add to cart</button>
        </div>
      </div>
    </div>
  );
}