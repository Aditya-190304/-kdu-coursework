import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Provider as ReduxProvider } from 'react-redux';
import { store } from './store';
import { ThemeProvider } from './context/themeContext';
import App from './App';
import ErrorBoundary from './components/common/errorBoundary/errorBoundary';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ReduxProvider store={store}>
      <ThemeProvider>
        <ErrorBoundary>
          <App />
        </ErrorBoundary>
      </ThemeProvider>
    </ReduxProvider>
  </StrictMode>
);