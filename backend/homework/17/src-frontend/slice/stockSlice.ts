import { createSlice, createAsyncThunk,type  PayloadAction } from '@reduxjs/toolkit';
import type { StockState, StockResponse } from '../types';

export const fetchStocks = createAsyncThunk<StockResponse, { username: string, page: number }>(
  'stocks/fetchStocks',
  async ({ username, page }) => {
    const response = await fetch(`http://localhost:8080/api/stocks?username=${username}&page=${page}&limit=10`);
    if (!response.ok) throw new Error('failed to fetch stocks');
    return response.json();
  }
);

export const fetchWatchlist = createAsyncThunk<StockResponse, { username: string, page: number }>(
  'stocks/fetchWatchlist',
  async ({ username, page }) => {
    const response = await fetch(`http://localhost:8080/api/watchlist?username=${username}&page=${page}&limit=10`);
    if (!response.ok) throw new Error('failed to fetch watchlist');
    return response.json();
  }
);

export const toggleWatchlistThunk = createAsyncThunk<{ stockId: string; isFavorite: boolean },{ username: string, stockId: string, isFavorite: boolean }>
  (
  'stocks/toggleWatchlist',
  async ({ username, stockId, isFavorite }) => {
    const endpoint = isFavorite ? '/api/watchlist/remove' : '/api/watchlist/add';
    const response = await fetch(`http://localhost:8080${endpoint}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, stockId }),
    });
    if (!response.ok) throw new Error('Action failed');
    return { stockId, isFavorite };
  }
);

const initialState: StockState = {
  exploreList: [],
  watchlist: [],
  activeTab: 'explore',
  currentPage: 1,
  totalPages: 1,
  loading: false,
};

const stockSlice = createSlice({
  name: 'stocks',
  initialState,
  reducers: {
    setActiveTab: (state, action: PayloadAction<'explore' | 'watchlist'>) => 
    {
      state.activeTab = action.payload;
      state.currentPage = 1;
    },
    setPage: (state, action: PayloadAction<number>) => {
      state.currentPage = action.payload;
    },
    resetDashboard: (state) => {
      state.activeTab = 'explore';
      state.currentPage = 1;
    }
  },
  extraReducers: (builder) => {
    builder
      .addCase(fetchStocks.pending, (state) => { state.loading = true; })
      .addCase(fetchStocks.fulfilled, (state, action) => {
        state.loading = false;
        state.exploreList = action.payload.stocks;
        state.totalPages = action.payload.totalPages;
      })
      .addCase(fetchWatchlist.pending, (state) => { state.loading = true; })
      .addCase(fetchWatchlist.fulfilled, (state, action) => {
        state.loading = false;
        state.watchlist = action.payload.stocks;
        state.totalPages = action.payload.totalPages;
      })
     builder.addCase(toggleWatchlistThunk.fulfilled, (state, action) => {
  const { stockId, isFavorite } = action.payload;

  
  const exploreStock = state.exploreList.find(s => s._id === stockId);
  if (exploreStock) {
    exploreStock.isFavorite = !isFavorite;
  }

  
  if (isFavorite) {
  
    state.watchlist = state.watchlist.filter(s => s._id !== stockId);
  } 
});
  },
});

export const { setActiveTab, setPage, resetDashboard } = stockSlice.actions;
export default stockSlice.reducer;