<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Edit Vendor — Wedding Planner</title>
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
                <li class="nav-item"><a class="nav-link active" href="${pageContext.request.contextPath}/vendors"><i class="bi bi-shop me-1"></i>Vendors</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/bookings"><i class="bi bi-calendar-check me-1"></i>Bookings</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/reviews"><i class="bi bi-star me-1"></i>Reviews</a></li>
            </ul>
        </div>
    </div>
</nav>

<!-- Form -->
<div class="container my-5">
    <div class="form-section mx-auto" style="max-width: 600px;">
        <h2><i class="bi bi-pencil-square me-2"></i>Edit Vendor</h2>
        <form action="${pageContext.request.contextPath}/vendors?action=update" method="post">
            
            <input type="hidden" name="id" value="${vendor.id}" />

            <div class="mb-3">
                <label for="businessName" class="form-label">Business Name</label>
                <input type="text" class="form-control" id="businessName" name="businessName" value="${vendor.businessName}" required />
            </div>

            <div class="mb-3">
                <label for="category" class="form-label">Category</label>
                <select class="form-select" id="category" name="category" required>
                    <option value="Venue" ${vendor.category == 'Venue' ? 'selected' : ''}>Venue</option>
                    <option value="Catering" ${vendor.category == 'Catering' ? 'selected' : ''}>Catering</option>
                    <option value="Photography" ${vendor.category == 'Photography' ? 'selected' : ''}>Photography</option>
                    <option value="Florist" ${vendor.category == 'Florist' ? 'selected' : ''}>Florist</option>
                    <option value="Music/DJ" ${vendor.category == 'Music/DJ' ? 'selected' : ''}>Music/DJ</option>
                    <option value="Decoration" ${vendor.category == 'Decoration' ? 'selected' : ''}>Decoration</option>
                    <option value="Bridal Wear" ${vendor.category == 'Bridal Wear' ? 'selected' : ''}>Bridal Wear</option>
                    <option value="Other" ${vendor.category == 'Other' ? 'selected' : ''}>Other</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="contactEmail" class="form-label">Contact Email</label>
                <input type="email" class="form-control" id="contactEmail" name="contactEmail" value="${vendor.contactEmail}" required />
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label">Phone</label>
                <input type="text" class="form-control" id="phone" name="phone" value="${vendor.phone}" required />
            </div>
            
            <div class="mb-3">
                <label for="location" class="form-label">Location</label>
                <input type="text" class="form-control" id="location" name="location" value="${vendor.location}" required />
            </div>

            <div class="mb-3">
                <label for="description" class="form-label">Description</label>
                <textarea class="form-control" id="description" name="description" rows="3" required>${vendor.description}</textarea>
            </div>

            <div class="mb-4">
                <label for="rating" class="form-label">Rating (0.0 to 5.0)</label>
                <input type="number" step="0.1" min="0" max="5" class="form-control" id="rating" name="rating" value="${vendor.rating}" required />
            </div>

            <div class="d-flex justify-content-between">
                <a href="${pageContext.request.contextPath}/vendors" class="btn btn-outline-secondary">
                    <i class="bi bi-arrow-left me-1"></i>Cancel
                </a>
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-check-circle me-1"></i>Update Vendor
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
