### Installation

First install [Vagrant](https://www.vagrantup.com/downloads.html) and [Virtual Box](https://www.virtualbox.org/wiki/Downloads).

Then, in the env-builder folder, you can run the following:

    # Start up the virtual machine:
    $ vagrant up

    # Stop the virtual machine:
    $ vagrant halt

Once it has started up you can connect to the PostgresSQL using the following: 

      Host: localhost
      Port: 15432
      Database: barganha
      Username: barganha
      Password: barganha

### What does it do?

It creates a virtual server running Ubuntu 14.04 with the latest version of PostgreSQL installed. 
It also edits the PostgreSQL configuration files to allow network access and creates a database for the application to use.


