// Thêm import jwt-decode (sau khi cài đặt: npm install jwt-decode)
import jwtDecode from 'jwt-decode';

// Utility function to handle API requests
const apiRequest = async (url, method = 'GET', body = null, token = null) => {
    const headers = {
        'Content-Type': 'application/json',
    };
    if (token) headers['Authorization'] = `Bearer ${token}`;

    const response = await fetch(url, {
        method,
        headers,
        body: body ? JSON.stringify(body) : null,
    });

    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }

    return response.json();
};

// Handle page routing
const navigateTo = (url) => {
    window.history.pushState({}, '', url);
    window.location.href = url;
};

// Main script
document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');

    // Lấy userId từ token
    let userId;
    if (token) {
        try {
            const decoded = jwtDecode(token);
            userId = decoded.id; // Giả sử token có trường "id" chứa userId
        } catch (error) {
            console.error('Error decoding token:', error);
            userId = null;
        }
    }

    // Redirect to login if not authenticated
    if (!token && !window.location.pathname.includes('login.html') && !window.location.pathname.includes('register.html')) {
        navigateTo('/login.html');
    }

    // Handle register form
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            const email = document.getElementById('email').value;
            const role = document.getElementById('role').value;

            try {
                await apiRequest('http://localhost:5000/api/users/register', 'POST', { username, password, email, role });
                alert('Registration successful! Please login.');
                navigateTo('/login.html');
            } catch (error) {
                console.error('Registration error:', error);
                alert('Registration failed. Please try again.');
            }
        });
    }

    // Handle login form
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;

            try {
                const data = await apiRequest('http://localhost:5000/api/users/login', 'POST', { username, password });
                localStorage.setItem('token', data.token);
                localStorage.setItem('role', data.role);
                navigateTo('/dashboard.html');
            } catch (error) {
                console.error('Login error:', error);
                alert('Login failed. Please check your credentials.');
            }
        });
    }

    // Handle logout
    const logoutBtn = document.getElementById('logoutBtn');
    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            localStorage.removeItem('token');
            localStorage.removeItem('role');
            navigateTo('/login.html');
        });
    }

    // Fetch pregnancy data for dashboard
    const pregnancyDataDiv = document.getElementById('pregnancyData');
    if (pregnancyDataDiv && userId) {
        apiRequest(`http://localhost:5000/api/pregnancydata/${userId}`, 'GET', null, token)
            .then(data => {
                if (data.length === 0) {
                    pregnancyDataDiv.innerHTML = '<p>No pregnancy data available.</p>';
                } else {
                    pregnancyDataDiv.innerHTML = data.map(item => `
                        <div class="border-b py-2">
                            <p><strong>Week:</strong> ${item.week}</p>
                            <p><strong>Weight:</strong> ${item.weight} kg</p>
                            <p><strong>Blood Pressure:</strong> ${item.bloodPressure}</p>
                            <p><strong>Nutrition:</strong> ${item.nutrition}</p>
                            <p><strong>Milestone:</strong> ${item.milestone}</p>
                        </div>
                    `).join('');
                }
            })
            .catch(error => {
                console.error('Error fetching pregnancy data:', error);
                pregnancyDataDiv.innerHTML = '<p>Error loading data. Please try again later.</p>';
            });
    }

    // Fetch notifications for dashboard
    const notificationsDiv = document.getElementById('notifications');
    if (notificationsDiv && userId) {
        apiRequest(`http://localhost:5000/api/notifications/${userId}`, 'GET', null, token)
            .then(data => {
                if (data.length === 0) {
                    notificationsDiv.innerHTML = '<p>No notifications available.</p>';
                } else {
                    notificationsDiv.innerHTML = data.map(item => `
                        <div class="border-b py-2">
                            <p><strong>Message:</strong> ${item.message}</p>
                            <p><strong>Date:</strong> ${new Date(item.date).toLocaleString()}</p>
                            <p><strong>Status:</strong> ${item.status ? 'Read' : 'Unread'}</p>
                        </div>
                    `).join('');
                }
            })
            .catch(error => {
                console.error('Error fetching notifications:', error);
                notificationsDiv.innerHTML = '<p>Error loading notifications. Please try again later.</p>';
            });
    }
});
