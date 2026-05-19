# Contact Us Form - Fix for 500 Error

## What Was Fixed

### 1. Service Dependency Issue
**Problem**: The servlet was trying to use a service user ("contactServiceUser") that wasn't configured in AEM, causing the 500 error.

**Solution**: Updated to use the request's own ResourceResolver instead:
- Modified `ContactUsService` interface to accept `ResourceResolver` as parameter
- Updated `ContactUsServiceImpl` to work with the provided resolver
- Updated `ContactUsServlet` to pass `request.getResourceResolver()` to the service

### 2. Better Error Handling
- Added logging (SLF4J) for debugging
- Added try-catch block in servlet
- Added service availability check
- Better exception handling in service implementation

### 3. Email Validation
- Fixed email regex pattern to be more strict: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$`

## Deployment Steps

### Step 1: Rebuild the Core Module
```bash
cd c:\AEM project\project\Sample\AEM\testproject\core
mvn clean install
```

### Step 2: Rebuild and Deploy the Entire Project
```bash
cd c:\AEM project\project\Sample\AEM\testproject
mvn clean install -PautoInstallPackage
```

### Step 3: Verify in AEM
1. Go to AEM Console: http://localhost:4502
2. Check OSGi Bundles (System > Status > Bundles)
3. Search for "contactus" - verify bundle is active
4. Check error logs: System > Status > Logs
5. Test the form on a page with the Contact Us component

## Testing the Fix

1. Add the Contact Us Form component to a test page
2. Fill out the form with valid data
3. Click "Send Message"
4. You should see success message: "Thank you! We will get back to you soon."
5. Contact data will be saved in `/content/contacts/` path in JCR

## Checking Submitted Data

In AEM:
1. Go to CRXDE Lite
2. Navigate to `/content/contacts/`
3. You'll see nodes like `contact-1234567890` with properties: firstName, lastName, email, phone, subject, message, createdAt, status

## Troubleshooting

### If still getting 500 error:
1. Check AEM error logs for detailed exception
2. Verify bundle is active in OSGi console
3. Clear browser cache (Ctrl+F5)
4. Try force-rebuilding: `mvn clean install -U`

### If you see the import statement error:
This is from another custom clientlib (not contact-us form).
Fix in that clientlib's JavaScript:
- Change from: `import { ... } from '...'`
- Change to: Regular JavaScript without import statements
- Or configure webpack to handle modules

## Modified Files

1. **ContactUsService.java** - Added ResourceResolver parameter
2. **ContactUsServiceImpl.java** - Removed service user dependency, added logging
3. **ContactUsServlet.java** - Added resource resolver passing, error handling, logging

## Files No Longer Need Configuration

- No need to create service user "contactServiceUser" anymore
- No need to configure service user mappings
- Form submission will work immediately after deployment
