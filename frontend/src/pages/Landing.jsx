import Navbar from "../components/layout/Navbar";

const Landing = () => {
  return (
    <>
      <Navbar />
      <div className="flex flex-col items-center justify-center min-h-screen text-center">
        <h1 className="text-5xl font-bold mb-4">
          Exchange Skills. Grow Together.
        </h1>
        <p className="text-slate-400">
          Connect. Learn. Teach.
        </p>
      </div>
    </>
  );
};

export default Landing;