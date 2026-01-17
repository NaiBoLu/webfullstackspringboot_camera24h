"use client";
import React, { useState, useEffect } from "react";

const BackToTop: React.FC = () => {
  const [isVisible, setIsVisible] = useState<boolean>(false);

  useEffect(() => {
    const toggleVisibility = () => {
      // 1. Kiểm tra vị trí cuộn từ mọi nguồn có thể
      const winScroll = window.scrollY; // Cuộn của cửa sổ
      const docScroll = document.documentElement.scrollTop; // Cuộn của thẻ html
      const bodyScroll = document.body.scrollTop; // Cuộn của thẻ body

      // 2. Lấy giá trị lớn nhất tìm được
      const scrolled = Math.max(winScroll, docScroll, bodyScroll);

      console.log("Thực tế đang cuộn được:", scrolled);

      if (scrolled > 300) {
        setIsVisible(true);
      } else {
        setIsVisible(false);
      }
    };

    // 3. QUAN TRỌNG: Thêm tham số 'true' (Capture phase)
    // để bắt sự kiện cuộn kể cả khi nó xảy ra trong các div bọc ngoài (Container)
    window.addEventListener("scroll", toggleVisibility, true);

    return () => window.removeEventListener("scroll", toggleVisibility, true);
  }, []);

  const scrollToTop = () => {
    // 1. Cuộn cửa sổ chính
    window.scrollTo({ top: 0, behavior: "smooth" });

    // 2. Cuộn thẻ html và body
    document.documentElement.scrollTo({ top: 0, behavior: "smooth" });
    document.body.scrollTo({ top: 0, behavior: "smooth" });

    // 3. Tìm và cuộn các container phổ biến (thường là thủ phạm trong Next.js/Bootstrap)
    const containers = document.querySelectorAll(
      "main, .wrapper, #__next, .content-container"
    );
    containers.forEach((container) => {
      container.scrollTo({ top: 0, behavior: "smooth" });
    });
  };

  const buttonStyle: React.CSSProperties = {
    position: "fixed",
    bottom: "40px",
    right: "40px",
    zIndex: 999999, // Tăng z-index cực cao để không bị thằng nào đè
    backgroundColor: "transparent",
    border: "none",
    outline: "none",
    padding: 0,
    cursor: "pointer",
    transition: "all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275)", // Hiệu ứng mượt hơn
    opacity: isVisible ? 1 : 0,
    visibility: isVisible ? "visible" : "hidden",
    // transform: isVisible ? "translateY(0)" : "translateY(50px)", // Trượt từ dưới lên
  };

  const imageStyle: React.CSSProperties = {
    width: "60px",
    height: "60px",
    display: "block",
    filter: "drop-shadow(0px 4px 8px rgba(0,0,0,0.5))",
  };

  return (
    <>
      {/* Hiệu ứng rung rinh khi hover */}
      <style>{`
       @keyframes shake-entire-button {
          { transform: scale(1, 1); }
          30% { transform: scale(1.25, 0.75); }
          40% { transform: scale(0.75, 1.25); }
          50% { transform: scale(1.15, 0.85); }
          65% { transform: scale(0.95, 1.05); }
          75% { transform: scale(1.05, 0.95); }
          100% { transform: scale(1, 1); }
        }
        .btn-shake-all {
          /* Điều khiển trạng thái ẩn hiện bằng CSS class thay vì inline */
          transform: ${isVisible ? "translateY(0)" : "translateY(50px)"};
          transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
        }

        .btn-shake-all:hover {
          /* Rung nhẹ toàn bộ button */
          animation: shake-entire-button 0.8s linear infinite;
        }

        .btn-shake-all:active img,
        .btn-shake-all:hover img {
          /* Kết hợp icon hơi phóng to ra một chút khi hover */
          transform: scale(1.05);
          transition: transform 0.2s ease;
        }

        .btn-shake-all:active img {
          /* Khi nhấn vào, scale nhỏ lại nhưng phải giữ nguyên translateY(0) */
          transform: translateY(0) scale(0.85) !important;
          transform: scale(0.8) !important; /* Nhấn vào thì thu nhỏ hẳn lại tạo cảm giác đàn hồi */
          /* Giảm thời gian transition để nó lún xuống ngay lập tức (cảm giác nhạy) */
          transition: transform 0.1s ease-out !important;
        }
      `}</style>

      <button
        onClick={scrollToTop}
        style={buttonStyle}
        className="btn-shake-all"
        title="Quay lại đầu trang"
      >
        <img src="/up-arrow.png" alt="Back to top" style={imageStyle} />
      </button>
    </>
  );
};

export default BackToTop;
