import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import RegPage from './pages/Reg-page/reg_page';
import Status_pg from './pages/Statuspage/Statuspage';


const App = () => {
  return (
    <Router>
      <div className="App_Container">
        <Routes>
          
          <Route path="/" element={<RegPage />} />
          <Route path="/Status/:id" element={<Status_pg />} />

          <Route path="*" element={<Navigate to="/" replace />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;