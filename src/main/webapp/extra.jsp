<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Library Management</title>
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link rel="stylesheet" href="static/css/mystyle.css">
        <style>
            .hero {
                background-image: url('${pageContext.request.contextPath}/static/bookimg/A 4k Ultra HD Wallpaper Of Heart Shaped Bookshelf Filled With.jpeg');
                background-size: cover;
                background-position: center;
                color: white;
                text-align: center;
                padding: 12rem 0;
            }

        </style>
    </head>
    <body>

        <section class="hero">
            <div class="hero-content">
                <h1>Welcome to the Digital Library</h1>
                <p>Your gateway to knowledge and endless adventures in reading</p>
                <a href="login.jsp" class="cta-btn">Browse Books</a>
            </div>
        </section>

        <!--        <section class="search-bar">
                    <div class="container">
                        <input type="text" id="search" class="search-input" placeholder="Search for books by title, author, or genre...">
                        <button class="search-btn"><i class="fas fa-search"></i> Search</button>
                    </div>
                </section>-->


        <section id="books" class="books-section">
            <div class="container">
                <h2 class="section-title">Featured Books</h2>
                <div class="book-grid">

                    <div class="book-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Brown and Blue Wizard Fantasy Novel Book Cover.jpg" alt="The Great Gatsby" class="book-img">
                        <div class="book-info">
                            <h3 class="book-title">The Young Wizard</h3>
                            <p class="book-author">by Aaron Leob</p>
                            <p class="book-desc">A timeless classic about love, obsession, and the American Dream.</p>
                            <a href="#" class="book-action">View Details</a>
                        </div>
                    </div>

                    <div class="book-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Purple And Green Romance Novel Book Cover.jpg" alt="1984" class="book-img">
                        <div class="book-info">
                            <h3 class="book-title">Stay With Me</h3>
                            <p class="book-author">by George Orwell</p>
                            <p class="book-desc">A dystopian masterpiece exploring totalitarianism and surveillance.</p>
                            <a href="#" class="book-action">View Details</a>
                        </div>
                    </div>

                    <div class="book-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Festive Illustration Christmas Market Book Cover.jpg" alt="To Kill a Mockingbird" class="book-img">
                        <div class="book-info">
                            <h3 class="book-title">Festive Hearts</h3>
                            <p class="book-author">by Harper Lee</p>
                            <p class="book-desc">A profound tale about racial injustice and moral growth in the Deep South.</p>
                            <a href="#" class="book-action">View Details</a>
                        </div>
                    </div>

                    <div class="book-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Black and Blue Modern Fantasy Dragon Novel Book Cover.jpg" alt="Moby Dick" class="book-img">
                        <div class="book-info">
                            <h3 class="book-title">Conquest of Flames</h3>
                            <p class="book-author">by Herman Melville</p>
                            <p class="book-desc">The epic story of Captain Ahab?s pursuit of the elusive white whale.</p>
                            <a href="#" class="book-action">View Details</a>
                        </div>
                    </div>

                    <div class="book-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Autumn Theme Book Cover.jpg" alt="Moby Dick" class="book-img">
                        <div class="book-info">
                            <h3 class="book-title">Falling For You</h3>
                            <p class="book-author">by Herman Melville</p>
                            <p class="book-desc">The epic story of Captain Ahab?s pursuit of the elusive white whale.</p>
                            <a href="#" class="book-action">View Details</a>
                        </div>
                    </div>

                    <div class="book-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/CookBook.jpg" alt="Moby Dick" class="book-img">
                        <div class="book-info">
                            <h3 class="book-title">Cookbook</h3>
                            <p class="book-author">by Herman Melville</p>
                            <p class="book-desc">The epic story of Captain Ahab?s pursuit of the elusive white whale.</p>
                            <a href="#" class="book-action">View Details</a>
                        </div>
                    </div>

                    <div class="book-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Purple Cute Cartoon Fairy Winter Stories Book Cover.jpg" alt="Moby Dick" class="book-img">
                        <div class="book-info">
                            <h3 class="book-title">Winter Stories</h3>
                            <p class="book-author">by Herman Melville</p>
                            <p class="book-desc">The epic story of Captain Ahab?s pursuit of the elusive white whale.</p>
                            <a href="#" class="book-action">View Details</a>
                        </div>
                    </div>

                    <div class="book-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Brown Vintage Magic Mirror Winter Fantasy Book Cover.jpg" alt="Moby Dick" class="book-img">
                        <div class="book-info">
                            <h3 class="book-title">The Mirror of Destiny</h3>
                            <p class="book-author">by Herman Melville</p>
                            <p class="book-desc">The epic story of Captain Ahab?s pursuit of the elusive white whale.</p>
                            <a href="#" class="book-action">View Details</a>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <section class="categories-section">
            <div class="container">
                <h2 class="section-title">Browse by Categories</h2>
                <div class="categories-grid">
                    <div class="category-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Black and Blue Modern Fantasy Dragon Novel Book Cover.jpg" alt="Fiction" class="category-img">
                        <h3 class="category-title">Fiction</h3>
                    </div>
                    <div class="category-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Beige And Yellow Mystery Novel Book Cover.jpg" alt="Non-Fiction" class="category-img">
                        <h3 class="category-title">Non-Fiction</h3>
                    </div>
                    <div class="category-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/Dark Blue illustrative Space Travel Book Cover.jpg" alt="Science" class="category-img">
                        <h3 class="category-title">Science</h3>
                    </div>
                    <div class="category-card">
                        <img src="${pageContext.request.contextPath}/static/bookimg/White Gray Moody Mystery Wattpad Book Cover.jpg" alt="Mystery" class="category-img">
                        <h3 class="category-title">Mystery</h3>
                    </div>
                </div>
            </div>
        </section>

        <!--     Footer Section 
            <footer class="footer">
                <div class="container">
                    <p>&copy; 2024 Library Management System | All Rights Reserved</p>
                    <div class="social-links">
                        <a href="#" class="social-link"><i class="fab fa-facebook"></i></a>
                        <a href="#" class="social-link"><i class="fab fa-twitter"></i></a>
                        <a href="#" class="social-link"><i class="fab fa-instagram"></i></a>
                    </div>
                </div>
            </footer>-->

        <!-- Scripts -->
        <script src="scripts.js"></script>
    </body>
</html>
