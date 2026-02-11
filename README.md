# 🏛 E-Gram Panchayat – A Digital Governance System

A full-stack web application designed to digitize village-level governance services.  
This system enables citizens to access government services online and allows administrators to manage and monitor service requests efficiently.

---

## 🚀 Live Application

🔗 Frontend (Netlify):  
https://e-gram-panchayat.netlify.app/

🔗 Backend (Railway):  
https://e-grampanchayat-a-digital-governance-production.up.railway.app/

🔗 Swagger API Documentation:  
https://e-grampanchayat-a-digital-governance-production.up.railway.app/swagger-ui/index.html

🔗 H2 database console:  
https://e-grampanchayat-a-digital-governance-production.up.railway.app/h2-console/login.jsp?jsessionid=a877685dd4071241f73ffe8c98e434a6


---

# ▶️ How to Use the Application

## 🧑 Citizen Login

You can either:

- Register as a new user using the registration form  
OR  
- Use the demo citizen account:

**Email:** mahesh@gmail.com  
**Password:** mahesh@123  

After login, you can:
- Apply for certificates
- Track certificate status
- Raise complaints
- Track complaint status
- Pay water and property taxes
- Update your profile
- Download approved certificates

---

## 👨‍💼 Admin Login

Use the following admin credentials:

**Email:** admin@gmail.com  
**Password:** admin@123  

After login, admin can:
- View dashboard statistics
- Approve or reject certificate requests
- Manage complaints (In Progress / Resolved with remarks)
- View all registered citizens
- Monitor payment transactions

---

⚠️ Note: This project uses an H2 in-memory database for demonstration purposes.


## 🛠 Tech Stack

### Frontend
- React.js
- Axios
- Bootstrap / CSS
- React Router

### Backend
- Spring Boot
- Spring Security
- JWT Authentication
- Hibernate / JPA
- H2 Database
- Global Exception Handling

### Deployment
- Frontend: Netlify
- Backend: Railway

---

## 🔐 Authentication & Security

- Role-Based Authentication (Citizen & Admin)
- JWT Token-based Authorization
- Password Encryption using BCrypt
- Secure REST APIs
- Centralized Global Exception Handling

---


# 👤 Citizen Features

### 📜 Certificate Services
- Apply for Certificates (Birth, Income, Residence, etc.)
- View Submitted Certificate Forms
- Track Certificate Status (Pending / Approved / Rejected)
- Download Approved Certificates

### 📝 Complaint Management
- Raise Complaints
- Track Complaint Status (Pending / In Progress / Resolved)

### 💰 Tax Payment
- Pay Water Tax
- Pay Property Tax
- Secure Online Payment via Razorpay
- View Payment History

### 👤 Profile Management
- Update User Records
- View Personal Details

### 🚪 Logout
- Secure Logout with JWT token removal

---

# 👨‍💼 Admin Features

## 📊 Admin Dashboard
- View Statistics of:
  - Pending Certificate Requests
  - Complaint Status Overview
  - Payment Details
  - Total Registered Citizens

---

## 📜 Certificate Management
- View all Certificate Requests submitted by Citizens
- View Detailed Form Data
- Approve or Reject Requests
- Update Certificate Status

---

## 📝 Complaint Management
- View all Complaints
- Mark Status as "In Progress"
- Resolve Complaints
- Add Remarks while Resolving

---

## 👥 Citizen Management
- View All Registered Citizens
- Access Citizen Details

---

## 💳 Payment Management
- View All Payment Transactions
- Track Payment Details
- Monitor Tax Collections

---

# 🏗 System Architecture

Frontend (React)
        ↓
REST APIs (Spring Boot)
        ↓
Service Layer
        ↓
Repository Layer (JPA/Hibernate)
        ↓
H2 Database

---

# 🧪 API Documentation/Testing

All APIs are documented using Swagger UI.


---

# 💳 Payment Gateway

- Integrated Razorpay for secure online transactions
- Payment Verification handled at backend
- Stores transaction details in database

---

# 📁 Project Structure

com.egrampanchyat
├── controller
├── service
├── repository
├── dto
├── entity
├── exception
└── utils
        ↓
Repository Layer (JPA/Hibernate)
        ↓
H2 Database



---

# 🔮 Future Enhancements

- MySQL / PostgreSQL Integration
- Email & SMS Notifications
- File Upload & Document Verification
- Cloud Storage Integration
- Admin Analytics Dashboard
- Multi-language Support

---

# 👨‍💻 Developed By

Mahesh Kumar Jadhav  
Full Stack Java Developer  

---

# ⭐ Project Highlights

✔ Production Deployment  
✔ Role-Based Access Control  
✔ JWT Authentication  
✔ Razorpay Payment Integration  
✔ Certificate & Complaint Management  
✔ Tax Payment System  
✔ Admin Dashboard with Statistics  
✔ Clean Layered Architecture  
✔ Professional Exception Handling  
✔ Swagger API Documentation

