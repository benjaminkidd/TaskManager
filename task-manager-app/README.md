# Task Manager App

This is a React project bootstrapped with Vite. Axios is used for all API requests.

## Getting Started

1. Install dependencies:
   ```sh
   npm install
   ```
2. Start the development server:
   ```sh
   npm run dev
   ```

## API Usage
- All HTTP requests are handled using Axios (see `src/api.js`).
- Update the `baseURL` in `src/api.js` to match your backend API.

## Project Structure
- `src/api.js`: Centralized Axios instance and API methods
- `src/App.jsx`: Main React component, fetches and displays tasks

---

For custom Copilot instructions, see `.github/copilot-instructions.md`.

# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.
