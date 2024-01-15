import { useLocation, useNavigate } from "react-router-dom";
import styles from "./Login.module.scss";
import { useAuth } from "../../App";
const Login = () => {
  let navigate = useNavigate();
  let location = useLocation();
  let auth = useAuth();

  let state = location.state as { from: Location };
  let from = state ? state.from.pathname : "/home";

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();

    let formData = new FormData(event.currentTarget);

    auth.signin(
      formData.get("loginUsername") as string,
      formData.get("password") as string,
      () => {
        navigate(from, { replace: true });
      }
    );
  }

  return (
    <form className={styles.formCentered} onSubmit={handleSubmit}>
      <div className={styles.formCentered__grouper}>
        <label htmlFor="loginUsername">Username</label>
        <input
          className={styles.formCentered__grouper}
          type="text"
          id="loginUsername"
          name="loginUsername"
          placeholder="Enter your username"
        />
      </div>
      <div className={styles.formCentered__grouper}>
        <label htmlFor="password">Password</label>
        <input
          type="password"
          id="password"
          name="password"
          placeholder="Enter your password"
        />
      </div>
      <button type="submit">Login</button>
    </form>
  );
};

export default Login;
