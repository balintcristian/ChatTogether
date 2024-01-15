import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/login": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
      "/logout": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
      "/conversations": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
      "/user": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
      "/messages": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
      "/chat": {
        target: "ws://localhost:8080",
        changeOrigin: true,
        secure: false,
        ws: true,
      },
    },
  },
});
