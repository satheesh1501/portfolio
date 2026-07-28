import React from 'react';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description InterestsSection matching WireFrame/interests-section.html 100%.
 */
export const InterestsSection = () => {
  const interests = [
    {
      id: 1,
      icon: "🧩",
      title: "Problem Solving / DSA / LeetCode",
      subtitle: "Algorithmic Thinking & Logic",
      description: "Practicing Data Structures & Algorithms, solving algorithmic challenges on LeetCode, and optimizing time/space complexity.",
      tag: "Core Engineering"
    },
    {
      id: 2,
      icon: "🚀",
      title: "Building SaaS Products",
      subtitle: "Architecting End-to-End Solutions",
      description: "Hands-on development of Civil Platform (Civil SaaS) and building scalable full-stack microservice products from scratch with friends.",
      tag: "Entrepreneurial"
    },
    {
      id: 3,
      icon: "📺",
      title: "Watching Tech Talks",
      subtitle: "Continuous Learning & Trends",
      description: "Staying up-to-date with modern system architecture by watching SpringOne, Google I/O, AWS re:Invent, and high-scalability tech talks.",
      tag: "Industry Knowledge"
    },
    {
      id: 4,
      icon: "🏕️",
      title: "Trekking, Fishing & Travelling",
      subtitle: "Outdoor Balance & Exploration",
      description: "Unplugging outdoors through nature treks, quiet fishing trips, and exploring new places to recharge and maintain balance.",
      tag: "Active Lifestyle"
    }
  ];

  return (
    <section className="interests-section" id="interests">
      <div className="interests-wrap">
        <span className="eyebrow">Beyond the code</span>
        <h2 className="interests-title">Passions & Personal Interests</h2>
        <p className="interests-sub">
          What keeps me inspired, curious, and balanced outside of daily software
          engineering.
        </p>

        <div className="interests-grid">
          {interests.map((item) => (
            <div key={item.id} className="interest-card">
              <div className="interest-icon">{item.icon}</div>
              <h3 className="interest-title">{item.title}</h3>
              <div className="interest-subtitle">{item.subtitle}</div>
              <p className="interest-desc">{item.description}</p>
              <span className="interest-tag">{item.tag}</span>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};
