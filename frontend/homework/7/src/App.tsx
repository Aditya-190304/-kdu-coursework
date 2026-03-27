import { BrowserRouter as Router, Routes, Route, NavLink } from "react-router-dom";
import Home from "./pages/Home";
import ProductDetails from "./pages/ProductDetails";
import { Routes as AppRoutes } from "./constants/product.constants";

function App() {
  return (
    <Router>
      <header className="nav-header">
        <span className="logo"> Product Discovery</span>
        <nav>
          <NavLink to={AppRoutes.Home} className={({isActive}) => isActive ? 'active' : ''}>
            Home
          </NavLink>
        </nav>
      </header>

      <Routes>
        <Route path={AppRoutes.Home} element={<Home />} />
        <Route path={AppRoutes.ProductDetails} element={<ProductDetails />} />
      </Routes>
    </Router>
  );
}

export default App;