import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

interface UserData {
  loginUsername: string; // Adjust the type based on your actual data structure
  // Add other properties as needed
}

const Home = () => {
  const navigate = useNavigate();
  const [userData, setUserData] = useState<UserData | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getUserData();
        setUserData(data);
      } catch (error: any) {
        console.error("Error fetching user data:", error.message);
      }
    };

    fetchData();
  }, []);

  const handleLogout = async () => {
    try {
      const response = await fetch("/logout", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        navigate("/");
      } else {
        const errorText = await response.text();
        console.error("Logout failed:", errorText);
      }
    } catch (error: any) {
      console.error("An error occurred during logout:", error.message);
    }
  };

  async function getUserData(): Promise<UserData> {
    try {
      const response = await fetch("/user/get", {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        const jsonData: UserData = await response.json();
        return jsonData;
      } else {
        const errorText = await response.text();
        console.error("Fetching user data failed:", errorText);
        throw new Error("Error fetching user data");
      }
    } catch (error: any) {
      console.error("An error during fetch:", error.message);
      throw new Error("Error fetching user data");
    }
  }

  return (
    <div>
      <button type="button" onClick={handleLogout}>
        Logout
      </button>
      {userData && <div>{userData.loginUsername}</div>}
    </div>
  );
};

export default Home;
