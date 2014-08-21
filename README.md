smart-shopper
=============

Future Work:

  - Consider splitting custom crawler configurations from tracked product configurations
  - Use strategy pattern to use different retrieval methods based on crawler type (FUTURE WORK)
  - 
  
Installation:

  1) Configure App.properties found in the configuration folder
    - Configure Username, Password, and Email
    - Optionally configure Name and CrawlInterval
  
  2) Configure ProductConfig.xml to contain your own tracked products
    - Note: this file is read at every scheduled crawl interval.
  
  3) Run the jar smart-shopper jar file
