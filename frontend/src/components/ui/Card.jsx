const Card = ({ children, className = "" }) => {
  return (
    <div
      className={`bg-[#111827] border border-slate-800 rounded-xl p-6 shadow-sm hover:border-slate-700 transition ${className}`}
    >
      {children}
    </div>
  );
};

export default Card;