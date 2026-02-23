import { useAppDispatch } from "../../App/hooks";
import { resetDashboard } from "../../slice/stockSlice";
import "./Navbar.scss"; 

const Navbar = () => {
  const dispatch = useAppDispatch();

  return (
    <nav className="navbar">
      <div className="navbar-left" onClick={() => dispatch(resetDashboard())}>
        <h2>📈 StockApp</h2>
      </div>
      <div className="navbar-right">
        <button className="nav-btn" disabled>
          Summarizer
        </button>
        <button className="nav-btn" disabled>
          My Portfolio
        </button>
      </div>
    </nav>
  );
};

export default Navbar;