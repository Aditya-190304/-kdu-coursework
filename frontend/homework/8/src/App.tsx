import { BrowserRouter } from 'react-router-dom';
import { ProductProvider } from './context/ProductContext';
import { ErrorBoundary } from './components/common/ErrorBoundary';
import { AppRoutes } from './routes/AppRoutes';
import './App.css'; 

function App() {
  return (
    <ErrorBoundary>
      <BrowserRouter>
        <ProductProvider>
          <AppRoutes />
        </ProductProvider>
      </BrowserRouter>
    </ErrorBoundary>
  );
}

export default App;