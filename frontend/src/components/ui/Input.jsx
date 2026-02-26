const Input = ({ className = "", ...props }) => {
  return (
    <input
      {...props}
      className={`w-full px-4 py-3 bg-[#0f172a] border border-slate-800 rounded-lg text-sm text-slate-200 placeholder-slate-500 focus:outline-none focus:ring-1 focus:ring-slate-500 focus:border-slate-500 transition ${className}`}
    />
  );
};

export default Input;