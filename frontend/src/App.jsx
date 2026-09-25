function App() {
  const appName = "SpendWise";
  const totalSpent = 2650;

  return (
    <div>
      <h1>Welcome to {appName}</h1>
      <p>You have spent ₹{totalSpent} this month.</p>
    </div>
  );
}

export default App;