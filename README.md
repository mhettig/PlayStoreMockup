# PlayStoreMockup

Back in 2013 I developed a mockup of Googles Play Store for research purposes. The [paper](http://dl.acm.org/citation.cfm?id=2556978) I contributed to was then puplished on the [CHI 2014](http://chi2014.acm.org/) - the premier international conference of Human-Computer Interaction.

This mockup is based on Googles Plays Store version 4.1.10. Take a look at the screenshots to see the similarity to the original store. 

Due to possible copyright issues there is just one (demo) app inside this repository and you "only" see the non-research version without our improvements mentioned in the paper.

There are several possible improvements. Check the issues here on github and the //TODOs in the code for some ideas. Feel free to contribute :)


### Screenshots

![screen1](https://cloud.githubusercontent.com/assets/2598617/4063039/a9917226-2e04-11e4-94a2-53883e2ff037.png)

![screen2](https://cloud.githubusercontent.com/assets/2598617/4063043/ae1e4e4a-2e04-11e4-8147-eec327a2e03c.png)

![screen3](https://cloud.githubusercontent.com/assets/2598617/4063045/b11ed27c-2e04-11e4-9521-a494d21ff6c6.png)


### How to add new apps
* Add a new json file under: asstes/apps/"category name"/"app.json" (name it as you wish - e.g. "helloworld.json").
  * for possible category names see res/values/strings.xml section: <!-- Categories: Apps -->
* Add the app icon (png), pattern: appname_ic.png (e.g. "helloworld_ic.png").
* Add screenshots for the app (jpg), pattern: appname_sc1.jpg (e.g. "helloworld_sc1.jpg", "helloworld_sc2.jpg", ...).
* Json syntax example:
```
{
    "name": "Helle World",
    "fileSize": 1100, //in KB
    "isRelevant": true, //if false this app will just show up in the listing but is not clickable (so you'll see no details)
    "downloadAmount": 5000000,
    "version": "3.8.1",
    "description": "Simple Hello World App",
    "vendor": "Vendor",
    "rating5": 30004, //amount of 5 star rating
    "rating4": 10513,
    "rating3": 3336,
    "rating2": 996,
    "rating1": 1360,
    "price": "",
    "permissions": [ // set of permissions to show before the app will be installed (this sample contains all possible permissions)
		"NETWORK", // Full Internet access
		"WIFI",		// View Wi-Fi state, view network state
		"STORAGE", // Modify/delete SD card contents
		"PHONE",   // Read phone state and identity
		"ACCOUNTS",	// Find accounts on the device, read Google service configuration
		"LOCATION",  // Coarse (network-based) location, fine (GPS) location
		"CONTACTS",  // Read contact data, read your own contact card, write contact data
		"CALL_LOG",  // Read call log, write call log
		"PICTURES",  // Record audio, take pictures
		"APPS"		// Retrieve running applications
        ],
    "comments": [
        {
        	"title": "comment title",
            "comment": "What a nice App!",
            "rating": 5,
            "date": "7/11/2013"				
        },
        {
        	"title": "Awesome",
            "comment": "Just awesome.",
            "rating": 5,
            "date": "7/10/2013"
        }
    ]
}
```
