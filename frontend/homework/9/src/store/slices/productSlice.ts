import { createSlice, createAsyncThunk,type  PayloadAction } from '@reduxjs/toolkit';
import type { Product } from '../../types';
import { fetchProductList, search, getProductDetails } from '../../api/products';

export const loadAll = createAsyncThunk(
    'products/loadAll', async (_, { signal }) => await fetchProductList(signal)
);


export const performSearch = createAsyncThunk(
    'products/performSearch', async (q: string, { signal }) => await search(q, signal)
);


export const fetchById = createAsyncThunk(
    'products/fetchById', async (id: string, { signal }) => await getProductDetails(id, signal)
);

interface ProductState {
  items: Product[];
  selectedProduct: Product | null;
  searchQuery: string;
  isLoadingList: boolean;
  isLoadingDetail: boolean;
  isLoadingSearch: boolean;
  error: string | null;
}

const initialState: ProductState = {
  items: [], selectedProduct: null, searchQuery: '',
  isLoadingList: false, isLoadingDetail: false, isLoadingSearch: false, error: null,
};

const productSlice = createSlice({
  name: 'products',
  initialState,
  reducers: {
    
    setQuery: (state, action: PayloadAction<string>) => 
        { 
            state.searchQuery = action.payload; 
        },
    
    
    resetSearch: (state) => { 
        state.searchQuery = ''; 
        state.items = []; 
    },
    
    
    cleanupSelection: (state) => { state.selectedProduct = null; }
  },
  extraReducers: (builder) => {
    
    builder.addCase(loadAll.pending, (state) => { 
        state.isLoadingList = true; 
        state.error = null; 
    }
    )
      .addCase(
        loadAll.fulfilled, (state, action) => {
             state.isLoadingList = false;
              state.items = action.payload.products;
             }
            )
      .addCase(loadAll.rejected, (state, action) => 
        {
             if (action.error.name !== 'AbortError') 
                {
                     state.isLoadingList = false; state.error = action.error.message || 'Error'; 
                    }
                }
            );
    
    
    builder.addCase(fetchById.pending, (state) => {
         state.isLoadingDetail = true;
          state.error = null;
           state.selectedProduct = null;
         }
        )
      .addCase(fetchById.fulfilled, (state, action) => { 
        state.isLoadingDetail = false; 
        state.selectedProduct = action.payload; 
    }
)
      .addCase(fetchById.rejected, (state, action) => { 
        if (action.error.name !== 'AbortError') 
            {
                 state.isLoadingDetail = false; 
                 state.error = action.error.message || 'Error';
                 }
                }
            );
    
    
    builder.addCase(performSearch.pending, (state) => { 
        state.isLoadingSearch = true; state.error = null; 
    }
)
      .addCase(performSearch.fulfilled, (state, action) => { 
        state.isLoadingSearch = false; 
        state.items = action.payload.products; 
    }
);
  },
});


export const { setQuery, resetSearch, cleanupSelection } = productSlice.actions;
export default productSlice.reducer;