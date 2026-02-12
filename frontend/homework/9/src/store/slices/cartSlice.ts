import { createSlice,type  PayloadAction } from '@reduxjs/toolkit';
import type { Product, CartItem } from '../../types';

interface CartState { 
    items: CartItem[]; 
    totalCount: number; 
    cartValue: number; 
} 
const initialState: CartState = { 
    items: [], 
    totalCount: 0, 
    cartValue: 0 
};

const cartSlice = createSlice(
    {
  name: 'cart',
  initialState,
  reducers: {
    addToCart: (state, action: PayloadAction<Product>) =>
         {
      const existing = state.items.find(i => i.id === action.payload.id);
      if (existing) existing.quantity += 1;
      else state.items.push({ ...action.payload, quantity: 1 });
      state.totalCount += 1;
      state.cartValue += action.payload.price;
    },
    removeItem: (state, action: PayloadAction<number>) => {
      const existing = state.items.find(i => i.id === action.payload);
      if (existing) {
        state.totalCount -= existing.quantity;
        state.cartValue -= (existing.price * existing.quantity);
        state.items = state.items.filter(i => i.id !== action.payload);
      }
    },
    modQty: (state, action: PayloadAction<{ id: number; q: number }>) => {
      const existing = state.items.find(i => i.id === action.payload.id);
      if (existing && action.payload.q > 0) {
        const diff = action.payload.q - existing.quantity;
        existing.quantity = action.payload.q;
        state.totalCount += diff;
        state.cartValue += (existing.price * diff);
      }
    },
    emptyCart: (state) => {
      state.items = [];
      state.totalCount = 0;
      state.cartValue = 0;
    }
  },
}
);

export const { addToCart, removeItem, modQty, emptyCart } = cartSlice.actions;
export default cartSlice.reducer;