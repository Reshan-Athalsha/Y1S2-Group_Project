<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Add Review — Wedding Planner</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">
            <i class="bi bi-heart-fill text-danger me-2"></i>Wedding Planner
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mainNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/"><i class="bi bi-house-door me-1"></i>Home</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/users"><i class="bi bi-people me-1"></i>Users</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/vendors"><i class="bi bi-shop me-1"></i>Vendors</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/bookings"><i class="bi bi-calendar-check me-1"></i>Bookings</a></li>
                <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/reviews"><i class="bi bi-star me-1"></i>Reviews</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Form -->
<div class="container my-5">
    <div class="form-section mx-auto" style="max-width: 600px;">
        <h2><i class="bi bi-star me-2"></i>Write a Review</h2>
        <form action="${pageContext.request.contextPath}/reviews" method="post">
            
            <div class="mb-3">
                <label for="userId" class="form-label">User ID</label>
                <input type="number" class="form-control" id="userId" name="userId" required />
            </div>

            <div class="mb-3">
                <label for="vendorId" class="form-label">Vendor ID</label>
                <input type="number" class="form-control" id="vendorId" name="vendorId" required />
            </div>

            <div class="mb-3">
                <label for="rating" class="form-label">Rating (1 to 5 Stars)</label>
                <select class="form-select" id="rating" name="rating" required>
                    <option value="" disabled selected>Select rating...</option>
                    <option value="5">5 - Excellent</option>
                    <option value="4">4 - Very Good</option>
                    <option value="3">3 - Good</option>
                    <option value="2">2 - Fair</option>
                    <option value="1">1 - Poor</option>
                </select>
            </div>

            <div class="mb-4">
                <label for="comment" class="form-label">Comment</label>
                <textarea class="form-control" id="comment" name="comment" rows="4" required></textarea>
            </div>

            <div class="d-flex justify-content-between">
                <a href="${pageContext.request.contextPath}/reviews" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left me-1"></i>Cancel
                </a>
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-save me-1"></i>Submit Review
                </button>
            </div>
        </form>
    </div>
</div>

<footer class="bg-dark text-white text-center py-3 mt-auto">
    <small class="text-secondary">Wedding Planner &amp; Vendor Booking System &copy; 2026</small>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
