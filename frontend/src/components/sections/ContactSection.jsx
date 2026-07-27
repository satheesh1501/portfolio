import React, { useState } from 'react';
import { contactService } from '../../services/contactService';
import { usePortfolioStore } from '../../store/usePortfolioStore';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 3.2.0
 * 
 * @description ContactSection with LeetCode profile card integration.
 */
export const ContactSection = () => {
  const [formData, setFormData] = useState({ name: '', email: '', subject: '', message: '' });
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);
  const { showToast } = usePortfolioStore();

  const validate = () => {
    const newErrors = {};

    if (!formData.name.trim()) {
      newErrors.name = 'Name is required.';
    } else if (formData.name.length > 100) {
      newErrors.name = 'Name cannot exceed 100 characters.';
    } else if (!/^[a-zA-Z\s'\-]+$/.test(formData.name)) {
      newErrors.name = 'Name can only contain letters, spaces, and hyphens.';
    }

    if (!formData.email.trim()) {
      newErrors.email = 'Email is required.';
    } else if (formData.email.length > 100) {
      newErrors.email = 'Email cannot exceed 100 characters.';
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = 'Please enter a valid email address (e.g. name@domain.com).';
    }

    if (!formData.subject.trim()) {
      newErrors.subject = 'Subject is required.';
    } else if (formData.subject.length > 150) {
      newErrors.subject = 'Subject cannot exceed 150 characters.';
    } else if (!/^[a-zA-Z0-9\s.,!?'"\-]+$/.test(formData.subject)) {
      newErrors.subject = 'Subject contains invalid special characters.';
    }

    if (!formData.message.trim()) {
      newErrors.message = 'Message is required.';
    } else if (formData.message.trim().length < 10) {
      newErrors.message = 'Message must be at least 10 characters long.';
    } else if (formData.message.length > 1000) {
      newErrors.message = 'Message cannot exceed 1000 characters.';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (field, value) => {
    setFormData((prev) => ({ ...prev, [field]: value }));
    if (errors[field]) {
      setErrors((prev) => ({ ...prev, [field]: null }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validate()) return;

    setLoading(true);
    try {
      await contactService.submitContact(formData);
      showToast('Your message has been sent to Satheesh! He will reach out to you soon.', 'success');
      setFormData({ name: '', email: '', subject: '', message: '' });
      setErrors({});
    } catch (err) {
      const errorMsg = err.response?.data?.error || err.message || '';
      if (errorMsg.includes('submitted a message recently') || err.response?.status === 400 || err.response?.status === 429) {
        showToast('You have submitted a message recently. Please wait a couple of minutes before submitting again. Thank you for understanding!', 'warning');
      } else {
        showToast(errorMsg || 'Failed to send message. Please try again.', 'error');
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <section className="contact-section" id="contact">
      <div className="contact-wrap">
        <span className="eyebrow">Get in touch</span>
        <h2 className="contact-title">Let's Connect</h2>
        <p className="contact-sub">Open to new engineering opportunities, SaaS discussions, and technical conversations.</p>

        <div className="contact-grid">

          {/* LEFT: Info Cards */}
          <div className="info-col">
            <div className="info-card">
              <div className="info-icon">✉</div>
              <div>
                <div className="info-label">Email</div>
                <div className="info-value">psatheeshkumar89@gmail.com</div>
              </div>
            </div>

            <a className="info-card" href="https://www.linkedin.com/in/satheesh-kumar-p-24ab5427b/" target="_blank" rel="noopener noreferrer" style={{ textDecoration: 'none' }}>
              <div className="info-icon">🔗</div>
              <div>
                <div className="info-label">LinkedIn</div>
                <div className="info-value">linkedin.com/in/satheesh-kumar-p-24ab5427b</div>
              </div>
            </a>

            <a className="info-card" href="https://github.com/satheesh1501" target="_blank" rel="noopener noreferrer" style={{ textDecoration: 'none' }}>
              <div className="info-icon">💻</div>
              <div>
                <div className="info-label">GitHub</div>
                <div className="info-value">github.com/satheesh1501</div>
              </div>
            </a>

            <a className="info-card" href="https://leetcode.com/u/satheeshkumar1501/" target="_blank" rel="noopener noreferrer" style={{ textDecoration: 'none' }}>
              <div className="info-icon">🧩</div>
              <div>
                <div className="info-label">LeetCode (138+ Solved)</div>
                <div className="info-value">leetcode.com/u/satheeshkumar1501</div>
              </div>
            </a>

            <div className="info-card">
              <div className="info-icon">📍</div>
              <div>
                <div className="info-label">Location</div>
                <div className="info-value">Aruppukottai, Tamil Nadu</div>
              </div>
            </div>
          </div>

          {/* RIGHT: Form */}
          <div className="form-card">
            <form onSubmit={handleSubmit} noValidate>
              
              <div className="form-row">
                <div style={{ flex: 1 }}>
                  <input
                    type="text"
                    className={`field ${errors.name ? 'field-error' : ''}`}
                    maxLength={100}
                    placeholder="Your Name"
                    value={formData.name}
                    onChange={(e) => handleChange('name', e.target.value)}
                  />
                  {errors.name && <span className="error-text">{errors.name}</span>}
                </div>

                <div style={{ flex: 1 }}>
                  <input
                    type="email"
                    className={`field ${errors.email ? 'field-error' : ''}`}
                    maxLength={100}
                    placeholder="Your Email"
                    value={formData.email}
                    onChange={(e) => handleChange('email', e.target.value)}
                  />
                  {errors.email && <span className="error-text">{errors.email}</span>}
                </div>
              </div>

              <div style={{ marginBottom: errors.subject ? '8px' : '0' }}>
                <input
                  type="text"
                  className={`field subject-field ${errors.subject ? 'field-error' : ''}`}
                  maxLength={150}
                  placeholder="Subject"
                  value={formData.subject}
                  onChange={(e) => handleChange('subject', e.target.value)}
                />
                {errors.subject && <span className="error-text" style={{ marginTop: '-12px', marginBottom: '12px' }}>{errors.subject}</span>}
              </div>

              <div>
                <textarea
                  className={`field ${errors.message ? 'field-error' : ''}`}
                  maxLength={1000}
                  placeholder="Your Message (minimum 10 characters)"
                  value={formData.message}
                  onChange={(e) => handleChange('message', e.target.value)}
                ></textarea>
                {errors.message && <span className="error-text" style={{ marginTop: '-16px', marginBottom: '16px' }}>{errors.message}</span>}
              </div>

              <button type="submit" className="send-btn" disabled={loading}>
                {loading ? 'Sending Message...' : 'Send Message →'}
              </button>

            </form>
          </div>

        </div>
      </div>
    </section>
  );
};
