import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    loginUsername: "",
    password: "",
  });

  async function handleLogin() {
    try {
      const response = await fetch("/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      const responseIsOk = response.ok;

      if (responseIsOk) {
        const responseData = await response.text() as string;
        console.log("Login successful:", responseData);

        navigate("/home");
      } else {
        const errorText = await response.text();
        console.error("Login failed:", errorText);
      }
    } catch (error: any) {
      console.error("An error occurred during login:", error.message);
    }
  }




  const handleInputChange = (e: any): void => {
    const { id, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [id]: value,
    }));
  };

  return (
    <form>
      <label htmlFor="loginUsername">Username</label>
      <input
        type="text"
        id="loginUsername"
        placeholder="Enter your username"
        value={formData.loginUsername}
        onChange={handleInputChange}
      />

      <label htmlFor="password">Password</label>
      <input
        type="password"
        id="password"
        placeholder="Enter your password"
        value={formData.password}
        onChange={handleInputChange}
      />

      <button type="button" onClick={handleLogin}>
        Login
      </button>
    </form>
  );
};

export default Login;
