# React 18 + Vite — Complete Interview Study Guide

## 1. What is React?
React is a declarative, component-based UI library developed by Facebook for building user interfaces.

## 2. React 18 New Features
- **Concurrent Rendering**: Allows React to pause, resume, or abandon rendering tasks to keep the UI responsive.
- **Automatic Batching**: Groups multiple state updates into a single re-render for better performance.
- **useTransition and useDeferredValue**: Hooks to mark non-urgent state updates.
- **Suspense improvements**: Full support for data fetching and SSR.
- **createRoot**: Replaces `ReactDOM.render` to enable concurrent features.

## 3. Core React Concepts
- **Components**: Functional vs class (always use functional with hooks in modern React).
- **JSX**: Syntax extension that looks like HTML but compiles to JavaScript `React.createElement`.
- **Props vs State**: Props are passed top-down (read-only), state is managed internally by the component.
- **Virtual DOM and reconciliation**: An in-memory representation of the UI. React diffs it with the real DOM and updates only what changed.
- **Keys in lists**: Help React identify which items have changed, been added, or removed.
- **Controlled vs uncontrolled components**: Controlled inputs have their value driven by React state; uncontrolled use DOM refs.

## 4. React Hooks (complete list with use cases)
- `useState`: Manage local state.
- `useEffect`: Perform side effects (fetching data, DOM manipulation, subscriptions) and manage lifecycle.
- `useContext`: Consume values from the Context API without prop drilling.
- `useRef`: Access DOM elements directly or store mutable values that don't trigger re-renders.
- `useMemo`: Memoize expensive computations.
- `useCallback`: Memoize function references to prevent unnecessary re-renders of child components.
- `useReducer`: Manage complex state logic (similar to Redux).
- `useId`: Generate unique IDs for accessibility attributes.
- **Custom hooks**: Extract and reuse stateful logic across components (e.g., `useWindowSize`).

## 5. React Router DOM
- `BrowserRouter`, `Routes`, `Route`: Core components for routing.
- `useNavigate`: Programmatic navigation.
- `useParams`: Access dynamic URL parameters.
- `useLocation`: Access the current URL location object.
- `Link` vs `NavLink`: `NavLink` knows whether or not it is "active".

## 6. Vite vs Create React App
- **Vite**: Uses native ESM, lightning-fast Hot Module Replacement (HMR), smaller bundles, uses esbuild under the hood.
- **Create React App (CRA)**: Webpack-based, slower builds, officially deprecated by the React team.
- **Why Vite**: It is the modern standard for blazing fast frontend tooling.

## 7. Performance Optimization
- `React.memo`: Prevent re-rendering of components if props haven't changed.
- `useMemo` and `useCallback`: Prevent unnecessary recalculations and re-creations of functions.
- **Code splitting**: Using `React.lazy` + `Suspense` to load bundles only when needed.
- **Image lazy loading**: Deferring loading of off-screen images.

## 8. How used in Portfolio
- **Vite project setup**: Fast development server and optimized build.
- **React Router**: For section navigation and handling 404s.
- `useEffect` for scroll-spy: Highlights the active section in the navbar based on scroll position.
- `useRef` for animation triggers: Observes when elements enter the viewport.

## 9. Top 15 Interview Q&A
1. **What is the Virtual DOM?** An in-memory copy of the DOM used to optimize updates.
2. **What are React hooks? Name all the built-in hooks.** Functions that let you hook into React state and lifecycle. (useState, useEffect, useContext, etc.)
3. **What is the difference between useEffect and useLayoutEffect?** useEffect runs asynchronously after render; useLayoutEffect runs synchronously after DOM mutations but before paint.
4. **When would you use useCallback vs useMemo?** useCallback memoizes a function; useMemo memoizes the result of a function.
5. **What is the difference between controlled and uncontrolled components?** Controlled form data is handled by React state; uncontrolled is handled by the DOM (using refs).
6. **What is reconciliation in React?** The process of syncing the Virtual DOM with the real DOM using a diffing algorithm.
7. **What is React.memo and when do you use it?** A higher-order component that memoizes the rendered output of a functional component.
8. **What are keys in React lists and why are they important?** They give elements a stable identity, optimizing list rendering.
9. **What is the Context API?** A way to pass data deeply through the component tree without prop drilling.
10. **What is Vite and why is it better than CRA?** A modern build tool offering significantly faster startup and HMR than webpack-based CRA.
11. **What are React 18 concurrent features?** Features that allow React to interrupt rendering to prioritize user interactions (e.g., useTransition).
12. **What is code splitting?** Splitting the app bundle into smaller chunks loaded on demand.
13. **What is the difference between useRef and useState?** Updates to useRef do not trigger a re-render; useState updates do.
14. **What is a custom hook?** A JavaScript function starting with "use" that can call other hooks to share reusable logic.
15. **What are the rules of hooks?** Only call hooks at the top level, and only from React function components or custom hooks.
