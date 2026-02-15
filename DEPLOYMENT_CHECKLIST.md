# Deployment Checklist for Usage Tracker Research Study

## Pre-Deployment Phase

### Google Cloud Setup
- [ ] Created Google Cloud project
- [ ] Enabled Google Sheets API
- [ ] Created OAuth 2.0 credentials for Android
- [ ] Configured package name: `com.research.usagetracker`
- [ ] Added SHA-1 fingerprint
- [ ] Saved OAuth client ID

### Google Sheets Setup
- [ ] Created new Google Sheet for data collection
- [ ] Added column headers (Date, User Name, Anonymous ID, etc.)
- [ ] Copied Sheet ID
- [ ] Shared sheet with research team Google accounts
- [ ] Set appropriate access permissions

### App Configuration
- [ ] Updated Sheet ID in code (`AppPreferences.kt`)
- [ ] Configured Google OAuth credentials
- [ ] Updated researcher contact information
- [ ] Customized study duration if needed (default: 10 days)
- [ ] Customized reminder time if needed (default: 10 AM)

### App Building
- [ ] Opened project in Android Studio
- [ ] Resolved all dependencies
- [ ] Built successfully without errors
- [ ] Generated signed APK (release variant)
- [ ] Tested APK installation on device
- [ ] Verified app version number

### Testing Phase
- [ ] Installed app on test device
- [ ] Completed registration with test name
- [ ] Granted all required permissions
- [ ] Used phone normally for 24 hours
- [ ] Verified midnight data collection occurred
- [ ] Checked dashboard displays data correctly
- [ ] Tested manual sync to Google Sheets
- [ ] Confirmed data appears correctly in Sheet
- [ ] Tested daily reminder notification
- [ ] Verified app survives device reboot
- [ ] Tested with battery optimization enabled
- [ ] Checked app performance and battery usage

## IRB & Ethics

### Documentation
- [ ] Completed IRB application
- [ ] Obtained IRB approval (if required)
- [ ] Prepared informed consent form
- [ ] Created participant information sheet
- [ ] Drafted privacy policy
- [ ] Prepared data security documentation

### Consent Process
- [ ] Defined consent procedure
- [ ] Created consent form storage system
- [ ] Planned how to obtain signatures
- [ ] Prepared withdrawal procedure

## Participant Recruitment

### Materials Preparation
- [ ] Created participant recruitment flyer
- [ ] Wrote study description
- [ ] Prepared installation instructions
- [ ] Created troubleshooting guide
- [ ] Set up participant contact method (email/phone)

### Participant Communication
- [ ] Drafted recruitment email/message
- [ ] Prepared welcome email with APK link
- [ ] Created daily check-in template (optional)
- [ ] Drafted completion thank-you message
- [ ] Prepared payment instructions

### Screening
- [ ] Defined eligibility criteria
- [ ] Created screening questionnaire
- [ ] Determined sample size needed
- [ ] Planned for over-recruitment (~20% buffer)

## Distribution Phase

### App Distribution
- [ ] Chose distribution method (email, cloud storage, etc.)
- [ ] Uploaded APK to secure location
- [ ] Generated download link
- [ ] Tested download link works
- [ ] Prepared installation video/guide (optional)

### Participant Onboarding
- [ ] Sent consent forms to participants
- [ ] Collected signed consent forms
- [ ] Distributed APK download link
- [ ] Provided installation instructions
- [ ] Offered technical support for installation
- [ ] Verified successful installations

### Initial Setup Support
- [ ] Confirmed participants completed registration
- [ ] Verified permissions were granted
- [ ] Checked first sync occurred
- [ ] Addressed any technical issues

## Monitoring Phase

### Daily Monitoring
- [ ] Check Google Sheet for new data each day
- [ ] Verify all participants are syncing
- [ ] Look for anomalies or missing data
- [ ] Monitor for data quality issues
- [ ] Track participant progress (study day)

### Participant Support
- [ ] Respond to technical questions within 24 hours
- [ ] Send reminders to participants with missing data
- [ ] Troubleshoot permission issues
- [ ] Help with sync problems
- [ ] Address battery/performance concerns

### Data Management
- [ ] Backup Google Sheet regularly
- [ ] Monitor data storage limits
- [ ] Check for duplicate entries
- [ ] Verify data integrity
- [ ] Document any data issues

## Mid-Study Check-ins

### Progress Review (Day 5)
- [ ] Check completion rates
- [ ] Identify participants falling behind
- [ ] Send encouragement messages
- [ ] Remind about payment requirements
- [ ] Address any concerns

## Post-Study Phase

### Data Collection Completion
- [ ] Verify all participants completed 10 days
- [ ] Check for complete data sets
- [ ] Identify any missing data
- [ ] Download final data backup
- [ ] Export data to analysis format (CSV)

### Data Cleaning
- [ ] Remove test entries
- [ ] Check for outliers or errors
- [ ] Verify anonymous ID assignments
- [ ] Create analysis-ready dataset
- [ ] Document any data exclusions

### Participant Completion
- [ ] Count completed days per participant
- [ ] Verify eligibility for payment
- [ ] Send thank-you messages
- [ ] Request payment information
- [ ] Process payments
- [ ] Confirm participants received payment

### Data Privacy
- [ ] Create anonymized dataset (remove names)
- [ ] Store original data securely
- [ ] Set data retention schedule
- [ ] Document data handling procedures
- [ ] Plan for data destruction after retention period

## Analysis Phase

### Data Preparation
- [ ] Import data to analysis software (R/Python/SPSS)
- [ ] Create analysis scripts
- [ ] Run descriptive statistics
- [ ] Check data distributions
- [ ] Identify any data quality issues

### Documentation
- [ ] Document data collection process
- [ ] Note any protocol deviations
- [ ] Record participant feedback
- [ ] Save all technical documentation
- [ ] Archive consent forms

## Reporting Phase

### Results Preparation
- [ ] Complete statistical analyses
- [ ] Create visualizations
- [ ] Write results section
- [ ] Prepare tables and figures
- [ ] Check for participant anonymity in all outputs

### Dissemination
- [ ] Share results with participants (optional)
- [ ] Prepare conference presentation
- [ ] Draft manuscript
- [ ] Submit for publication
- [ ] Archive final dataset

## Long-term Management

### Data Retention
- [ ] Store data according to institutional policy
- [ ] Maintain consent forms per regulations
- [ ] Set calendar reminder for data destruction
- [ ] Document data storage location

### Follow-up (if applicable)
- [ ] Plan for follow-up study
- [ ] Maintain participant contact information (with consent)
- [ ] Send updates about published results

## Emergency Procedures

### Technical Issues
- [ ] Have backup APK ready
- [ ] Know how to export Sheet data manually
- [ ] Have contact for Google Cloud support
- [ ] Prepare for server downtime

### Ethical Issues
- [ ] Know IRB contact information
- [ ] Have procedure for adverse events
- [ ] Plan for unexpected data breaches
- [ ] Prepare for participant complaints

### Data Loss Prevention
- [ ] Daily automatic backups enabled
- [ ] Manual backup schedule (weekly)
- [ ] Offline copy of data
- [ ] Multiple team members have access

---

## Quick Reference

**Study Duration**: 10 days  
**Sample Size**: [Your N]  
**Compensation**: $[Amount]  
**IRB #**: [Number]  
**Start Date**: [Date]  
**Expected End Date**: [Date]  

**Key Contacts**:
- PI: [Name] - [Email] - [Phone]
- Tech Support: [Email]
- IRB Office: [Email] - [Phone]

**Important Links**:
- Google Sheet: [Link]
- APK Download: [Link]
- Cloud Console: [Link]

---

## Notes

_Use this space for study-specific notes, deviations, or important information_

---

**Checklist Version**: 1.0  
**Last Updated**: February 2026  
**Reviewed By**: _____________  
**Date**: _____________
