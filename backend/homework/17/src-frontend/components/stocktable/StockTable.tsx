import { useAppDispatch, useAppSelector } from "../../App/hooks";
import { toggleWatchlistThunk } from "../../slice/stockSlice";
import type { Stock } from "../../types";
import "./StockTable.scss";

interface StockProps {
  stocks: Stock[];
}

const StockTable = ({ stocks }: StockProps) => {
  const dispatch = useAppDispatch();
  const selector = useAppSelector; 
  const username = selector((state) => state.user.name);
  const activeTab = selector((state) => state.stocks.activeTab);

  const handleToggle = (id: string, isFavorite: boolean) => {
    dispatch(toggleWatchlistThunk({ username, stockId: id, isFavorite }));
  };

  if (stocks.length === 0 && activeTab === "watchlist") {
    return (
      <div className="empty-state">
        <p>
          Currently there are no stocks in your watchlist. Add stocks to your
          watchlist to see them here.
        </p>
      </div>
    );
  }

  return (
    <table className="stock-table">
      <thead>
        <tr>
          <th>Stock Name</th>
          <th>Symbol</th>
          <th>Base Price</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        {stocks.map((stock) => (
          <tr key={stock._id}>
            <td>{stock.stock_name}</td>
            <td>{stock.symbol}</td>
            <td>${stock.base_price.toFixed(2)}</td>
            <td>
              <button
                className={`action-btn ${stock.isFavorite ? "ticked" : ""}`}
                onClick={() => handleToggle(stock._id, stock.isFavorite)}
              >
                {stock.isFavorite ? "✓" : "+"}
              </button>
            </td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default StockTable;
