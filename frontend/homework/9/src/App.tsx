import { createBrowserRouter, RouterProvider, Outlet } from 'react-router-dom';
import NavBar from './components/layout/navBar';
import Home from './pages/home';
import ProdDetails from './pages/prodDetails';
import Cart from './pages/cart';
import ErrorBoundary from './components/common/errorBoundary';
import { APP_ROUTES } from './constants';
import { useTheme } from './context/themeContext';
import './styles/global.scss'; 

const RootLayout = () => {
  const { theme } = useTheme();
  return (
    <div className={`app-container ${theme}`}>
      <NavBar />
      <main><Outlet /></main>
    </div>
  );
};

const router = createBrowserRouter([
  {
    path: APP_ROUTES.HOME,
    element: <RootLayout />,
    errorElement: <ErrorBoundary><div style={{padding:'2rem'}}><h2>Router Error</h2></div></ErrorBoundary>,
    children: [
      { index: true, element: <Home /> },
      { path: APP_ROUTES.PRODUCT_DETAILS, element: <ProdDetails /> },
      { path: APP_ROUTES.CART, element: <Cart /> },
    ],
  },
]);

export default function App() {
  return <RouterProvider router={router} />;
}