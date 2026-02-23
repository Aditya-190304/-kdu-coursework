import { useEffect } from "react";
import { useAppDispatch, useAppSelector } from "./App/hooks";
import { setUsername } from "./slice/userSlice";
import Dashboard from "./components/dashboard/Dashboard";

function App() {
  const dispatch = useAppDispatch();
  const username = useAppSelector((state) => state.user.name);

  useEffect(() => {
    if (!username) {
      let enteredName = "";
      while (!enteredName || enteredName.trim() === "") {
        enteredName =
          window.prompt("enter your username:") || "";
      }
      dispatch(setUsername(enteredName.trim()));
    }
  }, [username, dispatch]);

  if (!username) return <div>Loading...</div>;

  return (
    <div className="app-container">
      <Dashboard />
    </div>
  );
}

export default App;
