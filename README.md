smart-shopper
=============

Future Work:

  - Consider splitting custom crawler configurations from tracked product configurations
  - Use strategy pattern to use different retrieval methods based on crawler type (FUTURE WORK)
  - 
  
Installation:

  1) Configure App.properties found in the configuration folder
    - Configure Username, Password, and Email
    - Optionally configure Name and CrawlInterval (default is every 2 minutes)
    	- Configure crawl interval in this format: "1h 30m" or "15m" (without quotes)
  
  2) Configure ProductConfig.xml to contain your own tracked products
    - Note: this file is read at every scheduled crawl interval which you set in step 1.
  
  3) Run the jar smart-shopper jar file

  4) You can view the log file called Smart-Shopper.log that gets created in the smart-shopper directory to track the app

  5) In order to close the app:
  	- In Windows, open command prompt and enter: jps -l
  		- This command will spit out the jar processes currently running on the machine
  		-  Look for the Process ID number for the smrt-shopper.jar, note this number down.
  	- In OS X, open up terminal and enter: jps -l:
  		- This command will spit out the jar processes currently running on the machine
  		-  Look for the Process ID number for the smrt-shopper.jar, note this number down.
  	- Run this command: kill <Process ID> (i.e. kill 4929)
  		- This command kills the process running the smart-shopper.jar
  	NOTE: Task Manager won't show smart-shopper.jar it will only show a java process but if you inspect it, then it will show details for smart-shopper.jar.
