import { describe, it, expect } from 'vitest';

// Pure validation logic extracted for unit testing
const validateContactForm = (formData) => {
  const errors = {};

  if (!formData.name.trim()) {
    errors.name = 'Name is required.';
  } else if (formData.name.length > 100) {
    errors.name = 'Name cannot exceed 100 characters.';
  } else if (!/^[a-zA-Z\s'\-]+$/.test(formData.name)) {
    errors.name = 'Name can only contain letters, spaces, and hyphens.';
  }

  if (!formData.email.trim()) {
    errors.email = 'Email is required.';
  } else if (formData.email.length > 100) {
    errors.email = 'Email cannot exceed 100 characters.';
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
    errors.email = 'Please enter a valid email address (e.g. name@domain.com).';
  }

  if (!formData.subject.trim()) {
    errors.subject = 'Subject is required.';
  } else if (formData.subject.length > 150) {
    errors.subject = 'Subject cannot exceed 150 characters.';
  } else if (!/^[a-zA-Z0-9\s.,!?'"\-]+$/.test(formData.subject)) {
    errors.subject = 'Subject contains invalid special characters.';
  }

  if (!formData.message.trim()) {
    errors.message = 'Message is required.';
  } else if (formData.message.trim().length < 10) {
    errors.message = 'Message must be at least 10 characters long.';
  } else if (formData.message.length > 1000) {
    errors.message = 'Message cannot exceed 1000 characters.';
  }

  return { isValid: Object.keys(errors).length === 0, errors };
};

describe('Contact Form Validation Rules (Positive & Negative)', () => {

  it('TC-VAL-01 [Positive]: Should pass validation for clean input', () => {
    const validData = {
      name: 'Satheesh Kumar',
      email: 'satheesh@example.com',
      subject: 'Job Opportunity',
      message: 'Hello Satheesh, we loved your microservices portfolio project!'
    };

    const result = validateContactForm(validData);
    expect(result.isValid).toBe(true);
    expect(result.errors).toEqual({});
  });

  it('TC-VAL-02 [Negative]: Should catch missing required fields', () => {
    const emptyData = { name: '', email: '', subject: '', message: '' };
    const result = validateContactForm(emptyData);

    expect(result.isValid).toBe(false);
    expect(result.errors.name).toBe('Name is required.');
    expect(result.errors.email).toBe('Email is required.');
    expect(result.errors.subject).toBe('Subject is required.');
    expect(result.errors.message).toBe('Message is required.');
  });

  it('TC-VAL-03 [Negative]: Should catch invalid email format without @ or TLD', () => {
    const invalidEmail = {
      name: 'John Doe',
      email: 'invalid-email-address',
      subject: 'Inquiry',
      message: 'Valid length message for testing.'
    };

    const result = validateContactForm(invalidEmail);
    expect(result.isValid).toBe(false);
    expect(result.errors.email).toBe('Please enter a valid email address (e.g. name@domain.com).');
  });

  it('TC-VAL-04 [Negative]: Should catch malicious special characters in subject', () => {
    const maliciousSubject = {
      name: 'John Doe',
      email: 'john@example.com',
      subject: '<script>alert("xss")</script>',
      message: 'Valid length message for testing.'
    };

    const result = validateContactForm(maliciousSubject);
    expect(result.isValid).toBe(false);
    expect(result.errors.subject).toBe('Subject contains invalid special characters.');
  });

  it('TC-VAL-05 [Negative]: Should catch short message (< 10 chars)', () => {
    const shortMsg = {
      name: 'John Doe',
      email: 'john@example.com',
      subject: 'Inquiry',
      message: 'Too short'
    };

    const result = validateContactForm(shortMsg);
    expect(result.isValid).toBe(false);
    expect(result.errors.message).toBe('Message must be at least 10 characters long.');
  });
});
