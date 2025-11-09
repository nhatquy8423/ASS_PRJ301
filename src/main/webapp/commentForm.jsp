<%-- 
    Document   : commentForm
    Created on : Nov 9, 2025, 3:42:26 PM
    Author     : Trien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thêm bình luận</title>

        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

        <style>
            body {
                background-color: #f5f6fa;
            }

            .comment-box {
                background: #fff;
                border-radius: 15px;
                padding: 20px;
                box-shadow: 0 4px 12px rgba(0,0,0,0.05);
                width: 1350px;
               
                margin-left:40px;
            }

            .comment-input {
                border: none;
                background-color: #f1f3f5;
                border-radius: 10px;
                padding: 12px 15px;
                resize: none;
                width: 100%;
                transition: all 0.2s ease-in-out;
            }

            .comment-input:focus {
                background-color: #fff;
                border: 1px solid #007bff;
                box-shadow: 0 0 0 0.2rem rgba(0,123,255,0.15);
                outline: none;
            }

            .comment-toolbar {
                display: flex;
                align-items: center;
                gap: 10px;
                color: #6c757d;
                font-size: 1.1rem;
            }

            .comment-toolbar i {
                cursor: pointer;
                transition: 0.2s;
            }

            .comment-toolbar i:hover {
                color: #007bff;
            }

            .submit-btn {
                background-color: #ff6600;
                color: #fff;
                font-weight: 600;
                border: none;
                border-radius: 25px;
                padding: 8px 25px;
                transition: background-color 0.3s ease;
            }

            .submit-btn:hover {
                background-color: #e55d00;
            }

            .toolbar-section {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
            #buttonSubmit {
                background-color: #28a745;  /* xanh lá tươi */
                border: none;
                font-weight: 600;
                padding: 10px 22px;  /* ↑ tăng chiều cao, rộng vừa phải */
                font-size: 15px;
                transition: 0.3s;
            }

            #buttonSubmit:hover {
                background-color: #218838;  /* xanh đậm hơn khi hover */
            }
        </style>
    </head>

    <body>
        <div class="comment-box">






            <form action="addComment" method="post">
                <input type="hidden" name="productId" value="<%= request.getParameter("pro_id")%>">

                <div class="mb-3">
                    <label for="userName" class="form-label fw-semibold">Tên</label>
                    <input type="text" class="form-control" id="userName" name="userName" placeholder="Nhập tên của bạn" required>
                </div>

                <div class="mb-3">
                    <label for="content" class="form-label fw-semibold">Bình luận</label>
                    <textarea class="form-control" id="content" name="content" rows="3" placeholder="Viết bình luận của bạn..." required></textarea>
                </div>

                <div class="d-grid">
                    <button type="submit" class="btn rounded-pill text-white" id="buttonSubmit">
                        <i class="bi bi-send"></i> Gửi bình luận
                    </button>
                </div>
            </form>
        </div>
    </div>

    <div class="text-center mt-3">
        <a href="productDetail?id=<%= request.getParameter("id")%>" class="text-decoration-none">

        </a>
    </div>
</div>
</div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
</body>
</html>
