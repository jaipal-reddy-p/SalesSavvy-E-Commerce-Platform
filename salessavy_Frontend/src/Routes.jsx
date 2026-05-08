import React from "react";
import { Routes, Route } from "react-router-dom";
import LoginPage from "./LoginPage";
import RegistrationPage from "./RegistrationPage";
import ForgotPassword from "./ForgotPassword";
import OtpVerification from "./OtpVerification";
import ResetPassword from "./ResetPassword";
import CustomerHomePage from "./CustomerHomePage";
import CartPage from "./CartPage";
import OrdersPage from "./OrdersPage";
import CustomModal from "./CustomModal";
import AdminDashboard from "./AdminDashboard";
import AdminLogin from "./AdminLogin";

const AppRoutes = () => {
  return (
    <Routes>
      <Route path="/" element={<LoginPage />} />
      <Route path="/register" element={<RegistrationPage />} />
      {/* Add more routes here as your app grows */}{" "}
      <Route path="/forgot-password" element={<ForgotPassword />} />
      <Route path="/verify-otp" element={<OtpVerification />} />
      {/* Fixed path */}
      <Route path="/reset-password" element={<ResetPassword />} />
      <Route path="/customerhome" element={<CustomerHomePage />} />
      <Route path="/UserCartPage" element={<CartPage />} />
      <Route path="/orders" element={<OrdersPage />} />
      <Route path="/custommodal" element={<CustomModal />} />
      <Route path="/admindashboard" element={<AdminDashboard />} />
      <Route path="/admin" element={<AdminLogin />} />
    </Routes>
  );
};
export default AppRoutes;
