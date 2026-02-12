import type  { ProductsResponse, Product } from '../types';

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'https://dummyjson.com/products';


export const fetchProductList = async (signal?: AbortSignal): Promise<ProductsResponse> => {
  const response = await fetch(BASE_URL, { signal });
  if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
  return response.json();
};


export const search = async (q: string, abortSignal?: AbortSignal): Promise<ProductsResponse> => {
  const response = await fetch(`${BASE_URL}/search?q=${encodeURIComponent(q)}`, { signal: abortSignal });
  if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
  return response.json();
};


export const getProductDetails = async (id: number | string, signal?: AbortSignal): Promise<Product> => {
  const response = await fetch(`${BASE_URL}/${id}`, { signal });
  if (!response.ok) throw new Error(`HTTP Error: ${response.status}`);
  return response.json();
};