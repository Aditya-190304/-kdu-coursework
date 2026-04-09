import type{Product , ProductResponse} from '../types/product.types';

const BASE_URL = 'https://dummyjson.com/products';

export const productAPI={
    getLIsts:async(signal?: AbortSignal):Promise<ProductResponse>=>{
        const res=await fetch(`${BASE_URL}?limit=100`,{signal});
        if(!res.ok){
            throw new Error(`failed to fetch products: ${res.status} ${res.statusText}`);
        }
        return res.json();
    },

    search:async(query:string,signal?: AbortSignal):Promise<ProductResponse>=>{
        const res=await fetch(`${BASE_URL}/search?q=${encodeURIComponent(query)}`,{signal});
        if(!res.ok){
            throw new Error(`failed to find Products ${res.status} ${res.statusText}`);
        }
        return res.json();
    },
    getDetailsid:async(id:number,signal?: AbortSignal):Promise<Product>=>{
        const res=await fetch(`${BASE_URL}/${id}`,{signal});
        if(!res.ok){
            throw new Error(`failed to fetch product with id ${id}: ${res.status} ${res.statusText}`);
        }
        return res.json();
    },
}
