import * as React from "react";
import { Routes, Route, useLocation, Navigate, Outlet } from "react-router-dom";
import { AuthP } from "./Auth/AuthProvider";
import { AuthContextType } from "./Types/types";
import Home from "./Pages/Home/Home";
import Login from "./Pages/Login/Login";

export let AuthContext = React.createContext<AuthContextType>({
  isAuthenticated: false,
  signin: () => {},
  signout: () => {},
});
function AuthProvider({ children }: { children: React.ReactNode }) {
  let [isAuthenticated, setIsAuthenticated] = React.useState<boolean>(false);

  let signin = (
    loginUsername: string,
    password: string,
    callback: VoidFunction
  ) => {
    return AuthP.signin(loginUsername, password, () => {
      setIsAuthenticated(AuthP.isAuthenticated);
      callback();
    });
  };

  let signout = (callback: VoidFunction) => {
    return AuthP.signout(() => {
      setIsAuthenticated(false);
      callback();
    });
  };

  let value = { isAuthenticated, signin, signout };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}
export default function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route element={<Outlet />}>
          <Route path="/" element={<Login />} />
          <Route element={<RequireAuth />}>
            <Route path="/home" element={<Home />} />
          </Route>
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </AuthProvider>
  );
}

export function useAuth() {
  return React.useContext(AuthContext);
}

function RequireAuth() {
  let auth = useAuth();
  let location = useLocation();

  if (!auth.isAuthenticated) {
    return <Navigate to="/" state={{ from: location }} />;
  }
  return <Home />;
}

function NotFound() {
  return <h3>NotFound</h3>;
}
