export interface Stock {
  _id: string;
  stock_name: string;
  symbol: string;
  base_price: number;
  isFavorite: boolean; 
}

export interface StockResponse {
  stocks: Stock[];
  totalPages: number;
}

export interface UserState {
  name: string;
}

export interface StockState {
  exploreList: Stock[];
  watchlist: Stock[];
  activeTab: 'explore' | 'watchlist';
  currentPage: number;
  totalPages: number;
  loading: boolean;
}