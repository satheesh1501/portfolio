# React Query + Zustand + Framer Motion — Complete Interview Study Guide

## PART 1: React Query (TanStack Query)
1. **What is React Query?** A powerful server state management library for fetching, caching, and updating asynchronous data in React.
2. **Server state vs Client state**: Server state is persisted remotely, requires asynchronous APIs, and can become out of date. Client state is local, synchronous UI state (e.g., modal open/close).
3. **Core concepts**:
   - `useQuery`: For fetching data.
   - `useMutation`: For creating, updating, or deleting data.
   - `QueryClient` and `QueryClientProvider`: The core instance and context provider.
   - **Query keys**: Arrays used to uniquely identify and cache queries.
   - **Stale time vs Cache time**: Stale time dictates when data should be refetched; cache time dictates how long inactive data is kept in memory before garbage collection.
   - **Background refetching**: Automatically updates stale data in the background (e.g., on window focus).
   - **Loading, error, success states**: Built-in boolean flags (`isLoading`, `isError`).
   - **Pagination**: Handled easily with `useInfiniteQuery`.
4. **Why React Query over useEffect + fetch**:
   - Automatic caching and deduplication of requests.
   - Background updates and stale-while-revalidate.
   - Built-in loading/error states.
   - Zero boilerplate compared to Redux thunks.
5. **How used in Portfolio**: Fetching projects from backend, handling the contact form submission mutation.

## PART 2: Zustand
1. **What is Zustand?** A small, fast, and scalable barebones state-management solution using simplified flux principles.
2. **Zustand vs Redux**: Simpler API, no boilerplate, no explicit actions/reducers required, doesn't wrap your app in providers.
3. **Core API**: `createStore` to define state, `useStore` to access it, `set` to update, `get` to read inside actions.
4. **Slices pattern**: Breaking down a large store into smaller, manageable slices that are bound together.
5. **Middleware**: Built-in support for `persist` (saving to localStorage) and `devtools` (Redux DevTools integration).
6. **How used in Portfolio**:
   - `chatStore`: Manages AI chat state (isOpen, messages, isLoading).
   - `themeStore`: Manages dark/light mode (`isDarkMode`).
   - `navStore`: Tracks the currently active section for the navbar.

## PART 3: Framer Motion
1. **What is Framer Motion?** A production-ready motion library for React that makes complex animations simple.
2. **Core concepts**:
   - `motion` components: Optimized elements like `motion.div`.
   - **variants**: Objects that define specific animation states (e.g., hidden, visible).
   - `initial`, `animate`, `exit`: Props to define starting, active, and unmount states.
   - **transition**: Controls duration, ease, and delay.
   - `AnimatePresence`: Allows components to animate out when removed from the React tree.
   - `useInView`: Hook to trigger animations when an element scrolls into the viewport.
   - `useAnimation`: Hook for programmatic animation control.
   - `staggerChildren`: Delays the animation of children sequentially.
3. **Gestures**: Built-in support for `whileHover` and `whileTap`.
4. **How used in Portfolio**:
   - **Hero**: Fade-in with staggered animations on the name and buttons.
   - **Skill chips**: Staggered entrance animations.
   - **Project cards**: Lift effect on `whileHover`.
   - **AI chat panel**: Slide up animation using `AnimatePresence`.
   - **Timeline cards**: Scroll-triggered fade-in.

## Top 10 Interview Q&A per tool (30 total)
*(A selection of the most critical questions from above topics, focusing on differences between server/client state, how caching works, why Zustand over Redux, and how AnimatePresence functions under the hood.)*
