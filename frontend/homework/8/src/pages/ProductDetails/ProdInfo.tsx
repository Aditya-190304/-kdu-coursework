import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useProducts } from '../../context/ProductContext';
import { calculateDiscountPrice } from '../../utils/priceHelpers';
import { Loader } from '../../components/common/loader';
import { ErrorState } from '../../components/common/Error';
import styles from './ProdInfo.module.scss';

export const ProductInfo = () => {
  const { id } = useParams<{ id: string }>();
  const { fetchProductById, selectedProduct, isLoading, error, clearSelectedProduct } = useProducts();

  useEffect(() => {
    if (id) {
      clearSelectedProduct();
      const controller = new AbortController();
      fetchProductById(id, controller.signal);
      return () => controller.abort();
    }
  }, [id, fetchProductById, clearSelectedProduct]);

  if (isLoading) return <Loader />;
    if (error) return <ErrorState message={error} />;
  if (!selectedProduct) return null;

   const discountedPrice = calculateDiscountPrice(selectedProduct.price, selectedProduct.discountPercentage);
  

  return (
    <div className={styles.wrapper}>
        <div className={styles.container}>
        <div className={styles.gallery}>
             <img src={selectedProduct.images[0]} alt={`${selectedProduct.title}`} className={styles.mainImage} />
          {selectedProduct.images.length > 1 && (
         <div className={styles.thumbnails}>
                {selectedProduct.images.map((img, index) => (<img key={`${selectedProduct.id}-img-${index}`} src={img} alt={`${selectedProduct.title} view ${index + 1}`} className={styles.thumb}/>))}
            </div>
          )}
        </div>

        <div className={styles.info}>
          <span className={styles.tag}>{selectedProduct.category}</span>
          <h1 className={styles.title}>{selectedProduct.title}</h1>
         <p className={styles.brand}>Brand: {selectedProduct.brand}</p>
           <p className={styles.desc}>{selectedProduct.description}</p>
          
          <div className={styles.priceBlock}>
            <span className={styles.discounted}>${discountedPrice}</span>
            {selectedProduct.discountPercentage > 0 && (
              <>
                <span className={styles.original}>${selectedProduct.price}</span>
                <span className={styles.discount}>{selectedProduct.discountPercentage}% OFF</span>
              </>
            )}
          </div>

          <div className={styles.extrainfo}>
            <div className={styles.rating}><strong>Rating:</strong> ★ {selectedProduct.rating} / 5</div>
            <div className={styles.stock}><strong>Stock:</strong> {selectedProduct.stock} units</div>
          </div>
        </div>
      </div>
    </div>
  );
};