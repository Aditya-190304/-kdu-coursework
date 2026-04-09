import { createContext, useContext, useState, type ReactNode, useCallback } from 'react';
import type { Product } from '../types/product.types';
import  {productAPI} from '../api/client';

interface ProductContextType {
  products: Product[];
  selectedProduct: Product | null;
  isLoading: boolean;
  error: string | null;
  fetchAllProducts: (signal?: AbortSignal) => Promise<void>;
  searchProducts: (query: string, signal?: AbortSignal) => Promise<void>;
  fetchProductById: (id: string, signal?: AbortSignal) => Promise<void>;
  clearSelectedProduct: () => void;
}

const ProductContext = createContext<ProductContextType | undefined>(undefined);

export const ProductProvider = ({ children }: { children: ReactNode }) => {
  const [products, setProducts] = useState<Product[]>([]);

  const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);

  const [isLoading, setIsLoading] = useState(false);

  const [error, setError] = useState<string | null>(null);

  const fetchAllProducts = useCallback(async (signal?: AbortSignal) => {
    setIsLoading(true);
    setError(null);
    try {
      const data = await productAPI.getLIsts(signal);
      setProducts(data.products);

    } catch (err) {
      if (err instanceof Error && err.name !== 'AbortError') setError(err.message);
    } finally {

      if (!signal?.aborted) setIsLoading(false);
    }
  }, []);

  const searchProducts = useCallback(async (query: string, signal?: AbortSignal) => {
    setIsLoading(true);
    setError(null);
    try {
      const data = await productAPI.search(query, signal);

      setProducts(data.products);
    } catch (err) {
        
      if (err instanceof Error && err.name !== 'AbortError') setError(err.message);
    } finally {
      if (!signal?.aborted) setIsLoading(false);
    }
  }, []);

  const fetchProductById = useCallback(async (id: string, signal?: AbortSignal) => {
    setIsLoading(true);
    setError(null);
    try {
      const product = await productAPI.getDetailsid(Number(id), signal);
      setSelectedProduct(product);
    } catch (err) {
      if (err instanceof Error && err.name !== 'AbortError') setError(err.message);
    } finally {
      if (!signal?.aborted) setIsLoading(false);
    }
  }, []);

  const clearSelectedProduct = useCallback(() => {
    setSelectedProduct(null);
    setError(null);
  }, []);

  return (
    <ProductContext.Provider value={{ products, selectedProduct, isLoading, error, fetchAllProducts, searchProducts, fetchProductById, clearSelectedProduct }}>
      {children}
    </ProductContext.Provider>
  );
};

export const useProducts = () => {
  const context = useContext(ProductContext);
  if (!context) throw new Error('useProducts must be used within a Productprovider');
  return context;
};