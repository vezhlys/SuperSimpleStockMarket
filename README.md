# Super simple stock market 
Super simple stock market application.

## Prerequisities
- JDK 1.8
- Maven 3

##Run
Just run from command line:
```
mvn install exec:java
```

##Usage
- help or h - show the help.
- trade or t - record a trade. format: trade <stock symbol> <buy|sell> <quantity> <price>. example: trade TEA buy 3 2.50
- show or s - show stock market status (All Share index, all stock tickers with current prices).
- quit or q - quit application.
