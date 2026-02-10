import { Routes, Route } from 'react-router-dom';
import { Layout } from '../components/layout/Layout';
import { Home } from '../pages/Home/Home';
import { ProductInfo } from '../pages/ProductDetails/ProdInfo';

export const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route index element={<Home />} />
        <Route path="product/:id" element={<ProductInfo />} />
      </Route>
    </Routes>
  );
};