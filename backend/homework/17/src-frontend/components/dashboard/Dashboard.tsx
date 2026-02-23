import { useEffect } from "react";
import { useAppDispatch, useAppSelector } from "../../App/hooks";
import {
  fetchStocks,
  fetchWatchlist,
  setActiveTab,
  setPage,
} from "../../slice/stockSlice";
import Navbar from "../navbar/Navbar";
import StockTable from "../stocktable/StockTable";
import "./Dashboard.scss";

const Dashboard = () => {
  const dispatch = useAppDispatch();
  const selector = useAppSelector; 
  const username = selector((state) => state.user.name);
  const {activeTab,currentPage,totalPages,exploreList,watchlist,loading} = selector((state) => state.stocks);

  useEffect(() => {

    switch (activeTab) {
      case "explore":
        dispatch(fetchStocks({ username, page: currentPage }));
        break;
      case "watchlist":
        dispatch(fetchWatchlist({ username, page: currentPage }));
        break;
    }
  }, [activeTab, currentPage, username, dispatch]);
  const currentData = activeTab === "explore" ? exploreList : watchlist;

  return (
    <div className="dashboard-container">
      <Navbar />
      <main className="dashboard-content">
        <div className="tabs">
          <button className={activeTab === "explore" ? "active-tab" : ""} onClick={() => dispatch(setActiveTab("explore"))}>
            Explore
          </button>
          <button className={activeTab === "watchlist" ? "active-tab" : ""} onClick={() => dispatch(setActiveTab("watchlist"))} >
            My Watchlist
          </button>
        </div>

        {loading ? (<p className="loading-text">Loading...</p> ) :
        (
          <StockTable stocks={currentData} /> 
        )}

        <div className="pagination">
          <button disabled={currentPage <= 1} onClick={() => dispatch(setPage(currentPage - 1))}>
            Previous
          </button>
          <span className="page-info">
            Page {currentPage} of {totalPages === 0 ? 1 : totalPages}
          </span>
          <button disabled={currentPage >= totalPages || totalPages === 0} onClick={() => dispatch(setPage(currentPage + 1))}>
            Next
          </button>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;