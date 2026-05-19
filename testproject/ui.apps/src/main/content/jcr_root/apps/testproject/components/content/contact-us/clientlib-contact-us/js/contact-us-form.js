/**
 * Contact Us Form Handler
 * Manages form submission, validation, and response handling
 */

(function() {
    'use strict';

    // Initialize when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initContactUsForm);
    } else {
        initContactUsForm();
    }

    /**
     * Initialize the Contact Us form
     */
    function initContactUsForm() {
        const form = document.getElementById('contactUsForm');
        const responseDiv = document.getElementById('formResponse');

        if (!form || !responseDiv) {
            console.warn('Contact Us form elements not found');
            return;
        }

        // Add form submit handler
        form.addEventListener('submit', handleFormSubmit);

        // Add real-time validation on blur
        form.querySelectorAll('input, textarea').forEach(field => {
            field.addEventListener('blur', function() {
                validateField(this);
            });

            // Clear error on focus
            field.addEventListener('focus', function() {
                clearFieldError(this);
            });
        });

        // Add input event for better UX
        form.querySelectorAll('input, textarea').forEach(field => {
            field.addEventListener('input', function() {
                if (this.classList.contains('error')) {
                    validateField(this);
                }
            });
        });
    }

    /**
     * Handle form submission
     */
    function handleFormSubmit(e) {
        e.preventDefault();

        const form = e.target;
        const responseDiv = document.getElementById('formResponse');

        // Clear previous response
        responseDiv.className = 'form-response';
        responseDiv.innerHTML = '';
        responseDiv.classList.remove('show');

        // Validate all fields
        let isValid = true;
        form.querySelectorAll('input, textarea').forEach(field => {
            if (!validateField(field)) {
                isValid = false;
            }
        });

        if (!isValid) {
            showResponse('Please fill in all required fields correctly.', 'error', responseDiv);
            return;
        }

        // Show loading message
        showResponse('<span class="spinner"></span>Submitting your message...', 'loading', responseDiv);

        // Collect form data
        const formData = new FormData(form);

        // Submit form via AJAX
        fetch(form.action, {
            method: 'POST',
            body: formData,
            headers: {
                'Accept': 'application/json'
            }
        })
        .then(response => {
            // Parse response
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            // Check for success
            if (data.success) {
                showResponse(data.message, 'success', responseDiv);
                form.reset();
                // Clear any error states
                form.querySelectorAll('.error').forEach(field => {
                    field.classList.remove('error');
                });
                // Scroll to response
                scrollToResponse(responseDiv);
            } else {
                showResponse(data.message || 'Failed to submit form. Please try again.', 'error', responseDiv);
            }
        })
        .catch(error => {
            console.error('Form submission error:', error);
            showResponse('An error occurred while submitting the form. Please try again later.', 'error', responseDiv);
            scrollToResponse(responseDiv);
        });
    }

    /**
     * Validate individual field
     */
    function validateField(field) {
        const fieldName = field.name;
        const fieldValue = field.value.trim();
        let isValid = true;

        // Clear previous error
        clearFieldError(field);

        // Check if field is required and empty
        if (field.hasAttribute('required') && fieldValue === '') {
            showFieldError(field, 'This field is required');
            return false;
        }

        // Skip further validation for empty non-required fields
        if (!field.hasAttribute('required') && fieldValue === '') {
            return true;
        }

        // Field-specific validation
        switch(fieldName) {
            case 'firstName':
            case 'lastName':
                if (fieldValue.length < 2) {
                    showFieldError(field, 'Must be at least 2 characters');
                    isValid = false;
                }
                if (!/^[a-zA-Z\s'-]+$/.test(fieldValue)) {
                    showFieldError(field, 'Only letters, spaces, hyphens and apostrophes allowed');
                    isValid = false;
                }
                break;

            case 'email':
                if (!isValidEmail(fieldValue)) {
                    showFieldError(field, 'Please enter a valid email address');
                    isValid = false;
                }
                break;

            case 'phone':
                if (fieldValue && !/^[\d\s\-\+\(\)]+$/.test(fieldValue)) {
                    showFieldError(field, 'Please enter a valid phone number');
                    isValid = false;
                }
                break;

            case 'subject':
                if (fieldValue.length < 3) {
                    showFieldError(field, 'Subject must be at least 3 characters');
                    isValid = false;
                }
                break;

            case 'message':
                if (fieldValue.length < 10) {
                    showFieldError(field, 'Message must be at least 10 characters');
                    isValid = false;
                }
                break;
        }

        return isValid;
    }

    /**
     * Show field error
     */
    function showFieldError(field, message) {
        field.classList.add('error');
        const errorElement = document.getElementById(field.name + '-error');
        if (errorElement) {
            errorElement.textContent = message;
            errorElement.classList.add('show');
        }
    }

    /**
     * Clear field error
     */
    function clearFieldError(field) {
        field.classList.remove('error');
        const errorElement = document.getElementById(field.name + '-error');
        if (errorElement) {
            errorElement.textContent = '';
            errorElement.classList.remove('show');
        }
    }

    /**
     * Show response message
     */
    function showResponse(message, type, responseDiv) {
        responseDiv.className = 'form-response show ' + type;
        responseDiv.innerHTML = message;
    }

    /**
     * Scroll to response message
     */
    function scrollToResponse(element) {
        setTimeout(function() {
            element.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
        }, 100);
    }

    /**
     * Validate email format
     */
    function isValidEmail(email) {
        return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
    }

})();
