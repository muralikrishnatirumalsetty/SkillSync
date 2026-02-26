const Button = ({ children, className = "", ...props }) => {
  return (
    <button
      {...props}
      className={`px-5 py-2.5 rounded-lg text-sm font-medium bg-slate-200 text-slate-900 hover:bg-white transition ${className}`}
    >
      {children}
    </button>
  );
};

export default Button;