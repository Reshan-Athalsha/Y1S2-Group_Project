<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Vendor Management — Wedding Planner</title>
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

<!-- Content -->
<div class="container my-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="bi bi-shop-window me-2"></i>All Vendors</h2>
        <a href="${pageContext.request.contextPath}/vendors?action=add" class="btn btn-primary">
            <i class="bi bi-plus-circle me-1"></i>Add Vendor
        </a>
    </div>

    <c:choose>
        <c:when test="${empty vendors}">
            <div class="alert alert-info">
                <i class="bi bi-info-circle me-1"></i>No vendors found. Click <strong>Add Vendor</strong> to register one.
            </div>
        </c:when>
        <c:otherwise>
            <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-4">
                <c:forEach var="vendor" items="${vendors}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body">
                                <div class="d-flex justify-content-between align-items-start">
                                    <h5 class="card-title text-primary">${vendor.businessName}</h5>
                                    <span class="badge bg-secondary">${vendor.category}</span>
                                </div>
                                <p class="card-text mt-2"><small class="text-muted"><i class="bi bi-geo-alt-fill me-1"></i>${vendor.location}</small></p>
                                <p class="card-text">${vendor.description}</p>
                                <hr>
                                <p class="card-text mb-1"><i class="bi bi-envelope me-1"></i>${vendor.contactEmail}</p>
                                <p class="card-text mb-1"><i class="bi bi-telephone me-1"></i>${vendor.phone}</p>
                                <p class="card-text text-warning fw-bold mb-0">
                                    <i class="bi bi-star-fill me-1"></i>${vendor.rating} / 5.0
                                </p>
                            </div>
                            <div class="card-footer bg-white border-top-0 d-flex justify-content-end">
                                <a href="${pageContext.request.contextPath}/vendors?action=edit&id=${vendor.id}" class="btn btn-sm btn-outline-warning me-2" title="Edit">
                                    <i class="bi bi-pencil"></i> Edit
                                </a>
                                <a href="${pageContext.request.contextPath}/vendors?action=delete&id=${vendor.id}" class="btn btn-sm btn-outline-danger" title="Delete" onclick="return confirm('Are you sure you want to delete this vendor?');">
                                    <i class="bi bi-trash"></i> Delete
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<footer class="bg-dark text-white text-center py-3 mt-auto">
    <small class="text-secondary">Wedding Planner &amp; Vendor Booking System &copy; 2026</small>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
