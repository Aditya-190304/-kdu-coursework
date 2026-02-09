import React, { useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
import type { Product } from '../../types/product.types';
import styling from './ProductCard.module.scss';
import { percentage } from '../../constants/product.constants';

interface ProdCardProps {
   readonly product: Product;
}

const ProductCard: React.FC<ProdCardProps> = ({ product }) => {
    const navigate = useNavigate();

    const discountPrice = useMemo(() => {
        const discountAmount = (product.price * product.discountPercentage) / percentage;
        return (product.price - discountAmount).toFixed(2);
    }, [product.price, product.discountPercentage]); 

    return (
        <div className={styling.productCard} onClick={() => navigate(`/product/${product.id}`)}>
            
            
            {product.discountPercentage > 0 && (
                <div className={styling.discount}>
                    -{Math.round(product.discountPercentage)}%
                </div>
            )}

            
            <div className={styling.rating}>{product.rating}</div>

            
            <div className={styling.imagewrapper}>
                <img src={product.thumbnail} alt={product.title} />
            </div>

            <div className={styling.content}>
                <span className={styling.brand}>{product.brand}</span>
                <h3 className={styling.title}>{product.title}</h3>
                
                <div className={styling.priceRow}>
                    <span className={styling.old}>${product.price}</span>
                    <span className={styling.new}>${discountPrice}</span>
                </div>
                <p className={styling.stock}>{product.stock} in stock</p>
            </div>
        </div>
    );
};

export default React.memo(ProductCard);