// Vehicle Rental System - Frontend JavaScript

// ===================== DOCUMENT READY =====================
document.addEventListener('DOMContentLoaded', function() {
    initializeEventListeners();
    setMinDates();
});

// ===================== INITIALIZE EVENT LISTENERS =====================
function initializeEventListeners() {
    const searchForm = document.getElementById('searchForm');
    const bookingForm = document.getElementById('bookingForm');
    const contactForm = document.getElementById('contactForm');

    if (searchForm) {
        searchForm.addEventListener('submit', handleSearchSubmit);
    }

    if (bookingForm) {
        bookingForm.addEventListener('submit', handleBookingSubmit);
        document.getElementById('pickupDateBooking').addEventListener('change', calculateCost);
        document.getElementById('returnDateBooking').addEventListener('change', calculateCost);
        document.getElementById('vehicleSelect').addEventListener('change', calculateCost);
        document.getElementById('additionalOptions').addEventListener('change', calculateCost);
    }

    if (contactForm) {
        contactForm.addEventListener('submit', handleContactSubmit);
    }

    // Smooth scrolling for navbar links
    document.querySelectorAll('.navbar-menu a').forEach(link => {
        link.addEventListener('click', handleNavbarClick);
    });

    // Book Now buttons
    document.querySelectorAll('.vehicle-card .btn-primary').forEach(btn => {
        btn.addEventListener('click', handleBookNowClick);
    });
}

// ===================== FORM HANDLERS =====================
function handleSearchSubmit(e) {
    e.preventDefault();
    
    const pickupDate = document.getElementById('pickupDate').value;
    const returnDate = document.getElementById('returnDate').value;
    const vehicleType = document.getElementById('vehicleType').value;

    if (!pickupDate || !returnDate) {
        showAlert('Please select both dates', 'error');
        return;
    }

    if (new Date(pickupDate) >= new Date(returnDate)) {
        showAlert('Return date must be after pickup date', 'error');
        return;
    }

    showAlert(`Searching for ${vehicleType || 'all'} vehicles from ${pickupDate} to ${returnDate}`, 'success');
    scrollToSection('vehicles');
}

function handleBookingSubmit(e) {
    e.preventDefault();

    const formData = {
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value,
        licenseNumber: document.getElementById('licenseNumber').value,
        licenseExpiry: document.getElementById('licenseExpiry').value,
        pickupDate: document.getElementById('pickupDateBooking').value,
        returnDate: document.getElementById('returnDateBooking').value,
        vehicle: document.getElementById('vehicleSelect').value,
        paymentMethod: document.getElementById('paymentMethod').value
    };

    // Validation
    if (!validateBookingForm(formData)) {
        return;
    }

    // Show confirmation
    const totalCost = getTotalCost();
    const confirmation = confirm(
        `Booking Summary:\n\n` +
        `Customer: ${formData.firstName} ${formData.lastName}\n` +
        `Email: ${formData.email}\n` +
        `Pickup: ${formData.pickupDate}\n` +
        `Return: ${formData.returnDate}\n` +
        `Vehicle: ${formData.vehicle}\n` +
        `Total Cost: $${totalCost.toFixed(2)}\n\n` +
        `Proceed with booking?`
    );

    if (confirmation) {
        // Simulate booking submission
        showAlert('Booking submitted successfully! Confirmation sent to your email.', 'success');
        document.getElementById('bookingForm').reset();
        calculateCost();
    }
}

function handleContactSubmit(e) {
    e.preventDefault();

    const name = document.getElementById('contactName').value;
    const email = document.getElementById('contactEmail').value;
    const message = document.getElementById('contactMessage').value;

    if (name && email && message) {
        showAlert('Thank you for your message! We will get back to you soon.', 'success');
        document.getElementById('contactForm').reset();
    }
}

// ===================== VALIDATION =====================
function validateBookingForm(data) {
    // Check for empty fields
    if (!data.firstName || !data.lastName || !data.email || !data.phone || 
        !data.licenseNumber || !data.licenseExpiry || !data.pickupDate || 
        !data.returnDate || !data.vehicle || !data.paymentMethod) {
        showAlert('Please fill in all required fields', 'error');
        return false;
    }

    // Email validation
    if (!isValidEmail(data.email)) {
        showAlert('Please enter a valid email address', 'error');
        return false;
    }

    // Date validation
    if (new Date(data.pickupDate) >= new Date(data.returnDate)) {
        showAlert('Return date must be after pickup date', 'error');
        return false;
    }

    // License expiry validation
    if (new Date(data.licenseExpiry) < new Date()) {
        showAlert('License has expired', 'error');
        return false;
    }

    return true;
}

function isValidEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// ===================== COST CALCULATION =====================
function calculateCost() {
    const pickupDate = document.getElementById('pickupDateBooking').value;
    const returnDate = document.getElementById('returnDateBooking').value;
    const vehicleSelect = document.getElementById('vehicleSelect').value;
    const additionalOptions = document.getElementById('additionalOptions').value;

    // Vehicle prices
    const vehiclePrices = {
        'toyota-camry': 50,
        'honda-crv': 70,
        'bmw-x5': 150,
        'tesla-model3': 80,
        'ford-transit': 90
    };

    // Additional option prices
    const optionPrices = {
        'gps': 10,
        'baby-seat': 5,
        'wifi': 3
    };

    if (!pickupDate || !returnDate || !vehicleSelect) {
        updateSummary(0, 0, 0, 0, 0);
        return;
    }

    // Calculate number of days
    const pickup = new Date(pickupDate);
    const returnD = new Date(returnDate);
    const days = Math.ceil((returnD - pickup) / (1000 * 60 * 60 * 24));

    if (days <= 0) {
        return;
    }

    // Calculate costs
    const dailyRate = vehiclePrices[vehicleSelect] || 50;
    const dailyOptionCost = optionPrices[additionalOptions] || 0;
    const subtotal = dailyRate * days;
    const additionalCost = dailyOptionCost * days;
    const tax = (subtotal + additionalCost) * 0.1;
    const total = subtotal + additionalCost + tax;

    updateSummary(days, subtotal, additionalCost, tax, total);
}

function updateSummary(days, subtotal, additionalCost, tax, total) {
    const summaryItems = document.querySelectorAll('.summary-item');
    
    // Update the summary display
    const vehicleSelect = document.getElementById('vehicleSelect');
    const vehicleText = vehicleSelect.options[vehicleSelect.selectedIndex].text;
    
    // You would need to add specific classes/ids to each summary item to update them properly
    // For now, updating the last summary items directly
    
    const summaryElements = document.querySelectorAll('.summary-item');
    if (summaryElements.length > 0) {
        // Update values
        summaryElements[3].innerHTML = `<span>Number of Days</span><span>${days}</span>`;
        summaryElements[4].innerHTML = `<span>Subtotal</span><span>$${subtotal.toFixed(2)}</span>`;
        summaryElements[6].innerHTML = `<span>Additional Options</span><span>$${additionalCost.toFixed(2)}</span>`;
        summaryElements[8].innerHTML = `<span>Tax (10%)</span><span>$${tax.toFixed(2)}</span>`;
        summaryElements[10].innerHTML = `<span>Total</span><span class="total-price">$${total.toFixed(2)}</span>`;
    }
}

function getTotalCost() {
    const pickupDate = document.getElementById('pickupDateBooking').value;
    const returnDate = document.getElementById('returnDateBooking').value;
    const vehicleSelect = document.getElementById('vehicleSelect').value;
    const additionalOptions = document.getElementById('additionalOptions').value;

    const vehiclePrices = {
        'toyota-camry': 50,
        'honda-crv': 70,
        'bmw-x5': 150,
        'tesla-model3': 80,
        'ford-transit': 90
    };

    const optionPrices = {
        'gps': 10,
        'baby-seat': 5,
        'wifi': 3
    };

    if (!pickupDate || !returnDate || !vehicleSelect) {
        return 0;
    }

    const pickup = new Date(pickupDate);
    const returnD = new Date(returnDate);
    const days = Math.ceil((returnD - pickup) / (1000 * 60 * 60 * 24));

    if (days <= 0) {
        return 0;
    }

    const dailyRate = vehiclePrices[vehicleSelect] || 50;
    const dailyOptionCost = optionPrices[additionalOptions] || 0;
    const subtotal = dailyRate * days;
    const additionalCost = dailyOptionCost * days;
    const tax = (subtotal + additionalCost) * 0.1;
    
    return subtotal + additionalCost + tax;
}

// ===================== NAVIGATION =====================
function handleNavbarClick(e) {
    const href = e.target.getAttribute('href');
    if (href && href.startsWith('#')) {
        e.preventDefault();
        
        // Update active state
        document.querySelectorAll('.navbar-menu a').forEach(link => {
            link.classList.remove('active');
        });
        e.target.classList.add('active');

        scrollToSection(href.substring(1));
    }
}

function scrollToSection(sectionId) {
    const section = document.getElementById(sectionId);
    if (section) {
        section.scrollIntoView({ behavior: 'smooth', block: 'start' });
    }
}

function handleBookNowClick(e) {
    e.preventDefault();
    
    // Scroll to booking section
    const bookingSection = document.querySelector('.booking-section');
    if (bookingSection) {
        bookingSection.scrollIntoView({ behavior: 'smooth', block: 'start' });
        
        // Focus on vehicle select
        setTimeout(() => {
            document.getElementById('vehicleSelect').focus();
        }, 500);
    }
}

// ===================== UTILITY FUNCTIONS =====================
function setMinDates() {
    const today = new Date().toISOString().split('T')[0];
    
    // Set minimum pickup date to today
    document.getElementById('pickupDate').setAttribute('min', today);
    document.getElementById('pickupDateBooking').setAttribute('min', today);
    
    // Set minimum license expiry to today
    document.getElementById('licenseExpiry').setAttribute('min', today);
}

function showAlert(message, type = 'info') {
    // Create alert element
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;
    alertDiv.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        background-color: ${type === 'success' ? '#16a34a' : type === 'error' ? '#dc2626' : '#2563eb'};
        color: white;
        border-radius: 6px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        z-index: 1000;
        animation: slideIn 0.3s ease-in-out;
    `;

    document.body.appendChild(alertDiv);

    // Remove after 3 seconds
    setTimeout(() => {
        alertDiv.style.animation = 'slideOut 0.3s ease-in-out';
        setTimeout(() => alertDiv.remove(), 300);
    }, 3000);
}

// ===================== ANIMATIONS =====================
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from {
            transform: translateX(400px);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }

    @keyframes slideOut {
        from {
            transform: translateX(0);
            opacity: 1;
        }
        to {
            transform: translateX(400px);
            opacity: 0;
        }
    }
`;
document.head.appendChild(style);

// ===================== VEHICLE FILTER FUNCTIONALITY =====================
function filterVehiclesByType() {
    const vehicleType = document.getElementById('vehicleType').value;
    const vehicleCards = document.querySelectorAll('.vehicle-card');

    vehicleCards.forEach(card => {
        const type = card.getAttribute('data-type');
        
        if (!vehicleType || type === vehicleType) {
            card.style.display = 'block';
            card.style.animation = 'fadeIn 0.3s ease-in-out';
        } else {
            card.style.display = 'none';
        }
    });
}

// ===================== CONSOLE LOGS FOR DEBUGGING =====================
console.log('Vehicle Rental System loaded successfully');
console.log('Available functions: calculateCost(), validateBookingForm(), showAlert()');
