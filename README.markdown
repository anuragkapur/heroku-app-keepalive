Heroku-App-KeepAlive
====================

###TL;DR
An app (hack) that prevents an heroku app from sleeping by periodically send a ping request over HTTP.

###Introduction
By default, if your heroku app uses a single dyno (which comes for free), it sleeps after 1 hour of inactivity. This
results in the first call on a sleeping app having a high response time.

This project is an app (hack) to prevent a heroku app from sleeping when it uses a single Dyno. This
only works if you have a "web" heroku app which exposes a HTTP endpoint that can be invoked periodically. It is recommended
to use an endpoint using a [safe HTTP method](http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html) to request
periodically.

###Configuration
Configure a comma separated list of urls in [src/resource/app.properties](src/main/resource/app.properties). There are two
sets of urls that can be configured

* Urls to HTTP GET (safe method)
* Urls to HTTP POST (Ex. to run periodic maintenance tasks on a database)
