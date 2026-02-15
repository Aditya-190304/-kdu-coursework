import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ErrorBoundary from './components/ErrorBoundary/ErrorBoundary';
import { Layout } from './components/Layout/Layout';
import { Booking } from './pages/Booking/Booking';
import { Confirmation } from './pages/Confirmation/Confirmation';

export const App = () => (
  <ErrorBoundary>
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}> 
          <Route path="/" element={<Booking />} />
          <Route path="/confirmation" element={<Confirmation />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </ErrorBoundary>
);