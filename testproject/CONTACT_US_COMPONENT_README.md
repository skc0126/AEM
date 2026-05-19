# Contact Us Form Component - Implementation Guide

## Overview
A complete, production-ready Contact Us form component for AEM with 6 essential fields, backend validation, form submission handling, and responsive design with modern CSS styling.

## Component Structure

### Backend Modules (Java)

#### 1. **ContactUsFormModel.java**
- **Location**: `core/src/main/java/com/testproject/core/models/`
- **Purpose**: Sling Model for data binding
- **Fields**: firstName, lastName, email, phone, subject, message

#### 2. **ContactUsService.java** (Interface)
- **Location**: `core/src/main/java/com/testproject/core/services/`
- **Purpose**: Service interface for contact form submission
- **Method**: `boolean saveContactUs(...)`

#### 3. **ContactUsServiceImpl.java** (Implementation)
- **Location**: `core/src/main/java/com/testproject/core/services/impl/`
- **Purpose**: Implementation of ContactUsService
- **Features**:
  - Saves form data to JCR repository at `/content/contacts`
  - Auto-creates contact nodes with unique IDs
  - Stores submission timestamp and status
  - Uses service user for secure data access

#### 4. **ContactUsServlet.java**
- **Location**: `core/src/main/java/com/testproject/core/servlets/`
- **Purpose**: REST endpoint for form submission
- **Endpoint**: `/bin/contactus` (POST)
- **Features**:
  - Validates all required fields
  - Email format validation using regex
  - JSON response with success/error messages
  - HTTP status codes (200 success, 400 validation error, 500 server error)

### Frontend Components (UI)

#### 5. **Component Definition (.content.xml)**
- **Location**: `ui.apps/src/main/content/jcr_root/apps/testproject/components/content/contact-us/`
- **Purpose**: AEM component metadata
- **Properties**: Title, description, component group

#### 6. **Component Template (contact-us.html)**
- **Location**: Same as above
- **Purpose**: HTL template rendering the form
- **Form Fields**:
  1. **First Name** (Required) - Text input with placeholder
  2. **Last Name** (Required) - Text input with placeholder
  3. **Email** (Required) - Email input with validation
  4. **Phone** (Optional) - Tel input
  5. **Subject** (Required) - Text input
  6. **Message** (Required) - Textarea with multiple rows

- **Features**:
  - Error message placeholders for each field
  - Submit and Reset buttons
  - Response message div for feedback
  - Clientlib integration

#### 7. **Dialog (.content.xml)**
- **Location**: `contact-us/_cq_dialog/`
- **Purpose**: Author dialog for component configuration
- **Configurable Fields**:
  - Form Title
  - Form Description
  - Submit Button Text

### Frontend Styling & Scripts

#### 8. **Clientlib Configuration (.content.xml)**
- **Location**: `contact-us/clientlib-contact-us/`
- **Category**: `testproject.contactus.form`
- **Dependencies**: `cq.jquery`

#### 9. **CSS (contact-us-form.css)**
- **Features**:
  - Modern gradient background
  - Two-column responsive layout
  - Smooth transitions and animations
  - Error state styling with red borders
  - Success/Error/Loading message styling
  - Spinner animation for loading state
  - Fully responsive (desktop, tablet, mobile)
  - Touch-friendly on mobile (16px font size for inputs)

#### 10. **JavaScript (contact-us-form.js)**
- **Features**:
  - Form validation (real-time on blur)
  - Field-specific validation rules:
    - Names: 2+ chars, letters/spaces/hyphens/apostrophes only
    - Email: Valid email format
    - Phone: Optional, numeric with special characters
    - Subject: 3+ characters
    - Message: 10+ characters
  - AJAX form submission without page reload
  - Loading spinner during submission
  - Auto-scroll to response messages
  - Error message display for each field
  - Success message with form reset
  - Smooth UX with focus states

## Form Fields (6 Essential Fields)

| Field | Type | Required | Validation |
|-------|------|----------|------------|
| First Name | Text | Yes | 2+ chars, letters only |
| Last Name | Text | Yes | 2+ chars, letters only |
| Email | Email | Yes | Valid email format |
| Phone | Tel | No | Numeric with special chars |
| Subject | Text | Yes | 3+ characters |
| Message | Textarea | Yes | 10+ characters |

## Form Submission Flow

1. **User submits form** → JavaScript validates all fields
2. **Client-side validation** → Checks format and required fields
3. **AJAX POST request** → Sends to `/bin/contactus` servlet
4. **Server validation** → ContactUsServlet validates inputs
5. **Data storage** → ContactUsServiceImpl saves to JCR
6. **JSON response** → Returns success/error message
7. **User feedback** → Displays message with auto-scroll

## Data Storage

- **Location**: `/content/contacts/`
- **Node naming**: `contact-{timestamp}` (e.g., `contact-1684756234567`)
- **Node properties**:
  - firstName
  - lastName
  - email
  - phone
  - subject
  - message
  - createdAt (timestamp)
  - status (default: "new")

## Setup Instructions

### 1. Build the Module
```bash
cd c:\AEM project\project\Sample\AEM\testproject
mvn clean install -PautoInstallPackage
```

### 2. Create Service User (in AEM Admin Console)
Create a service user named `contactServiceUser` with JCR rights to `/content/contacts`:
- Path: `/etc/cloudservices`
- Add in `core/src/main/java/com/testproject/core/config/` if not exists

### 3. Add Component to Page
- Open page in author mode
- Drag "Contact Us Form" component from component sidebar
- Form will appear with default styling

### 4. Configure Component (Optional)
- Edit component dialog to customize:
  - Form title
  - Form description
  - Submit button text

## Responsive Breakpoints

- **Desktop**: Two-column form layout (max-width: 700px container)
- **Tablet**: Single column layout (≤768px)
- **Mobile**: Full width with single column (≤480px)

## Browser Compatibility

- Chrome/Edge (latest)
- Firefox (latest)
- Safari (latest)
- Mobile browsers (iOS Safari, Chrome Mobile)

## Security Features

- CSRF protection via Sling framework
- Server-side validation of all inputs
- Email format validation
- Service user permissions for data access
- HTTP status codes for error handling

## Performance Optimization

- Minified CSS and JS
- Clientlib caching
- AJAX prevents full page reload
- Lazy loading of form scripts

## Future Enhancements

Consider adding:
- Email notification on form submission
- File attachment support
- Captcha for spam prevention
- Multi-step form wizard
- Form analytics tracking
- Integration with CRM systems
- Workflow triggers for approvals

## Support & Troubleshooting

### Form not appearing:
- Check if component is added to page template
- Verify clientlib is properly configured
- Check browser console for JavaScript errors

### Form submission failing:
- Verify servlet path `/bin/contactus` is accessible
- Check service user permissions
- Review AEM error logs
- Ensure JCR `/content/contacts` path exists

### Styling issues:
- Clear browser cache
- Verify CSS file is in clientlib
- Check for conflicting CSS rules

## Files Created

1. `core/src/main/java/com/testproject/core/models/ContactUsFormModel.java`
2. `core/src/main/java/com/testproject/core/services/ContactUsService.java`
3. `core/src/main/java/com/testproject/core/services/impl/ContactUsServiceImpl.java`
4. `core/src/main/java/com/testproject/core/servlets/ContactUsServlet.java`
5. `ui.apps/src/main/content/jcr_root/apps/testproject/components/content/contact-us/.content.xml`
6. `ui.apps/src/main/content/jcr_root/apps/testproject/components/content/contact-us/contact-us.html`
7. `ui.apps/src/main/content/jcr_root/apps/testproject/components/content/contact-us/_cq_dialog/.content.xml`
8. `ui.apps/src/main/content/jcr_root/apps/testproject/components/content/contact-us/clientlib-contact-us/.content.xml`
9. `ui.apps/src/main/content/jcr_root/apps/testproject/components/content/contact-us/clientlib-contact-us/css/contact-us-form.css`
10. `ui.apps/src/main/content/jcr_root/apps/testproject/components/content/contact-us/clientlib-contact-us/js/contact-us-form.js`
11. `ui.apps/src/main/content/jcr_root/apps/testproject/components/content/contact-us/clientlib-contact-us/css.txt`
12. `ui.apps/src/main/content/jcr_root/apps/testproject/components/content/contact-us/clientlib-contact-us/js.txt`
