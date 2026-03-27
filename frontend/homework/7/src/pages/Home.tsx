import React, { useEffect, useState, useCallback } from 'react';
import type { Product, ApiResponse } from '../types/product.types';
import { API_URL } from '../constants/product.constants';
import ProductCard from '../components/product/ProductCard';
import styling from './Home.module.scss';

const home: React.FC = () => {
    const [products, setProducts] = useState<readonly Product[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string|null>(null);


    const fetchProducts = useCallback(async ()=>{
        try{
            setLoading(true);
            const response=await fetch(API_URL);
            if(!response.ok) throw new Error("failed to fetch products");
            const data:ApiResponse=await response.json();
            setProducts([...data.products]);
        }catch(err){
            setError((err as Error).message);
        }finally{
            setLoading(false);
        }
    },[]);

    useEffect(()=>{
        fetchProducts();
    },[fetchProducts]);

if(loading)return <div className={styling.loading}>Loading...</div>;
if(error)return <div className={styling.error}>{error}</div>;

return (
    <main className={styling.container}>
        <h1 className={styling.title}>All Products</h1>
        
        <div className={styling.grid}>
            {products.map(product => (
                <ProductCard key={product.id} product={product}/>
            ))}
        </div>
    </main>
);
};

export default home;
