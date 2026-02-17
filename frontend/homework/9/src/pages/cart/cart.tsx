import { useAppDispatch, useAppSelector } from '../../store/hooks';
import { removeItem, modQty } from '../../store/slices/cartSlice';
import { useNavigate } from 'react-router-dom';
import { APP_ROUTES } from '../../constants';
import styles from './cart.module.scss';

export default function Cart() {
  const { items, cartValue } = useAppSelector(s => s.cart);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  if (items.length === 0) return <div className={styles.empty}><h2>Cart is empty</h2><button onClick={() => navigate(APP_ROUTES.HOME)}>continue shopping</button></div>;

  return (
    <div className={styles.cartLayout}>
      <div className={styles.itemsList}>
        {items.map(item => (
          <div key={item.id} className={styles.cartItem}>
            <img src={item.thumbnail} alt={item.title} />
            <div className={styles.details}>
              <h3>{item.title}</h3>
              <p>${item.price}</p>
              <div className={styles.actions}>
                <button onClick={() => dispatch(modQty({ id: item.id, q: item.quantity - 1 }))}>-</button>
                <span>{item.quantity}</span>
                <button onClick={() => dispatch(modQty({ id: item.id, q: item.quantity + 1 }))}>+</button>
                <button onClick={() => dispatch(removeItem(item.id))} className={styles.remove}>remove</button>
              </div>
            </div>
            <div className={styles.total}>${(item.price * item.quantity).toFixed(2)}</div>
          </div>
        ))}
      </div>
      <div className={styles.summary}>
        <h3>order summary</h3>
        <p>total: <strong>${cartValue.toFixed(2)}</strong></p>
        <button onClick={() => navigate(APP_ROUTES.HOME)} className={styles.shopBtn}>continue shopping</button>
      </div>
    </div>
  );
}