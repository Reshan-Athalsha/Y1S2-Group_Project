<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Wedding Planner &amp; Vendor Booking System</title>

    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet" />
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css"
          rel="stylesheet" />
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" />
</head>
<body>

<!-- ═══════════════════ NAVIGATION BAR ═══════════════════════ -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
    <div class="container">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/">
            <i class="bi bi-heart-fill text-danger me-2"></i>Wedding Planner
        </a>
        <button class="navbar-toggler" type="button"
                data-bs-toggle="collapse" data-bs-target="#mainNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="mainNav">
            <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/">
                        <i class="bi bi-house-door me-1"></i>Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/users">
                        <i class="bi bi-people me-1"></i>User Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/vendors">
                        <i class="bi bi-shop me-1"></i>Vendor Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/bookings">
                        <i class="bi bi-calendar-check me-1"></i>Booking Management
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/reviews">
                        <i class="bi bi-star me-1"></i>Reviews
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- ═══════════════════ HERO SECTION ═════════════════════════ -->
<header class="hero-section text-white text-center d-flex align-items-center">
    <div class="container">
        <h1 class="display-3 fw-bold mb-3">Plan Your Perfect Day</h1>
        <p class="lead mb-4">
            Browse vendors, book services, and manage every detail of your
            dream wedding — all in one place.
        </p>
        <a href="${pageContext.request.contextPath}/vendors"
           class="btn btn-lg btn-outline-light me-2">
            <i class="bi bi-search me-1"></i>Browse Vendors
        </a>
        <a href="${pageContext.request.contextPath}/bookings"
           class="btn btn-lg btn-danger">
            <i class="bi bi-calendar-plus me-1"></i>Make a Booking
        </a>
    </div>
</header>

<!-- ═══════════════════ FEATURE CARDS ════════════════════════ -->
<section class="py-5 bg-light">
    <div class="container">
        <h2 class="text-center mb-5">System Modules</h2>
        <div class="row g-4">

            <!-- User Management -->
            <div class="col-md-6 col-lg-3">
                <div class="card h-100 shadow-sm border-0 text-center">
                    <div class="card-body">
                        <i class="bi bi-people-fill fs-1 text-primary mb-3 d-block"></i>
                        <h5 class="card-title">User Management</h5>
                        <p class="card-text text-muted">
                            Register, login, and manage user profiles.
                        </p>
                        <a href="${pageContext.request.contextPath}/users"
                           class="btn btn-outline-primary btn-sm">Go &rarr;</a>
                    </div>
                </div>
            </div>

            <!-- Vendor Management -->
            <div class="col-md-6 col-lg-3">
                <div class="card h-100 shadow-sm border-0 text-center">
                    <div class="card-body">
                        <i class="bi bi-shop-window fs-1 text-success mb-3 d-block"></i>
                        <h5 class="card-title">Vendor Management</h5>
                        <p class="card-text text-muted">
                            Add, edit, and browse wedding vendors &amp; services.
                        </p>
                        <a href="${pageContext.request.contextPath}/vendors"
                           class="btn btn-outline-success btn-sm">Go &rarr;</a>
                    </div>
                </div>
            </div>

            <!-- Booking Management -->
            <div class="col-md-6 col-lg-3">
                <div class="card h-100 shadow-sm border-0 text-center">
                    <div class="card-body">
                        <i class="bi bi-calendar-check-fill fs-1 text-warning mb-3 d-block"></i>
                        <h5 class="card-title">Booking Management</h5>
                        <p class="card-text text-muted">
                            Create and track bookings with your chosen vendors.
                        </p>
                        <a href="${pageContext.request.contextPath}/bookings"
                           class="btn btn-outline-warning btn-sm">Go &rarr;</a>
                    </div>
                </div>
            </div>

            <!-- Reviews -->
            <div class="col-md-6 col-lg-3">
                <div class="card h-100 shadow-sm border-0 text-center">
                    <div class="card-body">
                        <i class="bi bi-star-fill fs-1 text-danger mb-3 d-block"></i>
                        <h5 class="card-title">Reviews</h5>
                        <p class="card-text text-muted">
                            Rate vendors and read feedback from other couples.
                        </p>
                        <a href="${pageContext.request.contextPath}/reviews"
                           class="btn btn-outline-danger btn-sm">Go &rarr;</a>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>

<!-- ═══════════════════ FOOTER ════════════════════════════════ -->
<footer class="bg-dark text-white text-center py-4">
    <div class="container">
        <p class="mb-1">
            <i class="bi bi-heart-fill text-danger"></i>
            Wedding Planner &amp; Vendor Booking System
        </p>
        <small class="text-secondary">
            Y1S2 University Project &copy; 2026 &mdash; Built with Java Servlets, JSP &amp; File I/O
        </small>
    </div>
</footer>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
