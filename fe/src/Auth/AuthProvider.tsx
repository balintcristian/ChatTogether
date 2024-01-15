const AuthP = {
  isAuthenticated: false,
  signin(loginUsername: string, password: string, callback: VoidFunction) {
    fetch("/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ loginUsername, password }),
    })
      .then((response) => {
        if (response.status.toString().match("200")) {
          AuthP.isAuthenticated = true;
          callback();
        } else {
          console.error("Error:", response.statusText);
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  },
  signout(callback: VoidFunction) {
    fetch("/logout", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => {
        if (response.ok) {
          AuthP.isAuthenticated = false;
          callback();
        } else {
          console.error("Error:", response.statusText);
        }
      })
      .catch((error) => {
        console.error("Error:", error);
      });
  },
};
export { AuthP };
