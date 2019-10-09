# itzap-message
**itzap-message** micro-service project designed to send email messages. 
## Usage
itzap-message desinged to run in a Docker container along with [postfix](http://www.postfix.org/BASIC_CONFIGURATION_README.html) that is also running in a Docker. Using `docker-compose` **itzap-message** can be deployed anyware and ready to send emails. 
### How To Build
* Clone the following projects: 
	* `git clone git@github.com:avinokurov/itzap-parent.git`
	* `git clone git@github.com:avinokurov/itzap-common.git`
	* `git clone git@github.com:avinokurov/itzap-rxjava.git`
	* `git clone git@github.com:avinokurov/itzap-message.git`
* Build all projects
	* `cd itzap-parent && mvn clean install`
	* `cd ../itzap-common && mvn clean install`
	* `cd ../itzap-rxjava && mvn clean install`
	* `cd ../itzap-message && mvn clean install`
* Running
	* Before running set environment variables `export MAIL_FROM = mailer@test.com` to whater email address will be used to send messages from and `export ALLOWED_DOMAINS=test.com` domain used to send emails.
	* To run the **itzap-message** micro-service `docker-compose up`
* Testing
	* Once both Docker containers are running open [Postman](https://www.getpostman.com/downloads/)  and call micro-service API to send an email
	`POST http://localhost:8025/v1/itzap/message/email`
	**Body**
	```json
		{
			"messageId": "new-user",
			"subject": "test",
			"addresses": ["avinokurov@itzap.com"],
			"transport": "email"
		}
	```