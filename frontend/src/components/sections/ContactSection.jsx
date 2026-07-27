import React, { useState } from 'react';
import { UIConstants } from '../../constants/UIConstants';
import { contactService } from '../../services/contactService';
import { usePortfolioStore } from '../../store/usePortfolioStore';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Interactive Contact Form section connected to POST /api/v1/contact.
 * Features client-side validation, rate limit handling, and toast feedback.
 */
export const ContactSection = () => {
  const { showToast } = usePortfolioStore();
  const [formData, setFormData] = useState({ name: '', email: '', subject: '', message: '' });
  const [submitting, setSubmitting] = useState(false);
  const [submitted, setSubmitted] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitting(true);

    try {
      await contactService.submitContact(formData);
      setSubmitted(true);
      showToast(UIConstants.CONTACT.SUCCESS_DESC, 'success');
      setFormData({ name: '', email: '', subject: '', message: '' });
    } catch (err) {
      showToast(err.message || UIConstants.CONTACT.ERROR_TITLE, 'error');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <section id="contact" className="py-20 relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Section Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <h2 className="section-title">{UIConstants.CONTACT.TITLE}</h2>
          <p className="section-subtitle">{UIConstants.CONTACT.SUBTITLE}</p>
        </div>

        <div className="max-w-2xl mx-auto">
          {submitted ? (
            <div className="glass-panel p-8 text-center border-emerald-500/50">
              <div className="w-16 h-16 bg-emerald-500/20 text-emerald-400 rounded-full flex items-center justify-center mx-auto mb-4 text-2xl">
                ✓
              </div>
              <h3 className="text-xl font-bold text-white mb-2">{UIConstants.CONTACT.SUCCESS_TITLE}</h3>
              <p className="text-slate-300 text-sm mb-6">{UIConstants.CONTACT.SUCCESS_DESC}</p>
              <button onClick={() => setSubmitted(false)} className="btn-secondary text-xs">
                Send Another Message
              </button>
            </div>
          ) : (
            <form onSubmit={handleSubmit} className="glass-panel p-6 sm:p-8 space-y-6">
              
              <div className="grid grid-cols-1 sm:grid-cols-2 gap-6">
                <div>
                  <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
                    {UIConstants.CONTACT.FORM_NAME_LABEL}
                  </label>
                  <input
                    type="text"
                    name="name"
                    required
                    minLength={2}
                    maxLength={100}
                    value={formData.name}
                    onChange={handleChange}
                    placeholder={UIConstants.CONTACT.FORM_NAME_PLACEHOLDER}
                    className="w-full bg-slate-900/90 border border-slate-700 rounded-lg px-4 py-3 text-sm text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500 transition-colors"
                  />
                </div>

                <div>
                  <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
                    {UIConstants.CONTACT.FORM_EMAIL_LABEL}
                  </label>
                  <input
                    type="email"
                    name="email"
                    required
                    maxLength={150}
                    value={formData.email}
                    onChange={handleChange}
                    placeholder={UIConstants.CONTACT.FORM_EMAIL_PLACEHOLDER}
                    className="w-full bg-slate-900/90 border border-slate-700 rounded-lg px-4 py-3 text-sm text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500 transition-colors"
                  />
                </div>
              </div>

              <div>
                <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
                  {UIConstants.CONTACT.FORM_SUBJECT_LABEL}
                </label>
                <input
                  type="text"
                  name="subject"
                  required
                  minLength={5}
                  maxLength={200}
                  value={formData.subject}
                  onChange={handleChange}
                  placeholder={UIConstants.CONTACT.FORM_SUBJECT_PLACEHOLDER}
                  className="w-full bg-slate-900/90 border border-slate-700 rounded-lg px-4 py-3 text-sm text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500 transition-colors"
                />
              </div>

              <div>
                <label className="block text-xs font-semibold text-slate-300 uppercase tracking-wider mb-2">
                  {UIConstants.CONTACT.FORM_MESSAGE_LABEL}
                </label>
                <textarea
                  name="message"
                  required
                  minLength={10}
                  maxLength={2000}
                  rows={5}
                  value={formData.message}
                  onChange={handleChange}
                  placeholder={UIConstants.CONTACT.FORM_MESSAGE_PLACEHOLDER}
                  className="w-full bg-slate-900/90 border border-slate-700 rounded-lg px-4 py-3 text-sm text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500 transition-colors resize-none"
                ></textarea>
              </div>

              <button
                type="submit"
                disabled={submitting}
                className="btn-primary w-full justify-center py-3.5 text-sm disabled:opacity-50"
              >
                {submitting ? UIConstants.CONTACT.SUBMITTING_BTN : UIConstants.CONTACT.SUBMIT_BTN}
              </button>

            </form>
          )}
        </div>

      </div>
    </section>
  );
};
