FIPS-Datastore
==============

Project Overview
----------------

This repository is part of the project "An Extensible Framework for Indoor Positioning on Mobile Devices", which is the master thesis that I did in 2011-2012 at the Vrije Universiteit Brussel to achieve my "Master in Applied Computer Science". My promotor for this master thesis was [Prof. Dr. Beat Signer](http://www.beatsigner.com/). The thesis document can be found [here](https://www.dropbox.com/s/j0xehv5qodxh3id/Van%20Rossem%20-%202012%20-%20A%20FrameWork%20for%20Indoor%20Positioning%20on%20Mobile%20Devices.pdf).

The entire project is divided into several repositories:

* [FIPS-Datastore](https://github.com/wvrossem/FIPS-Datastore)
* [FIPS-Server](https://github.com/wvrossem/FIPS-Server)
* [FIPS-Tool](https://github.com/wvrossem/FIPS-Tool)
* [FIPS-Android-Offline](https://github.com/wvrossem/FIPS-Android-Offline)
* [FIPS-Android-Online](https://github.com/wvrossem/FIPS-Android-Online)

Datastore
---------

This project is used for storing the data that is used in an indoor positioning system. It uses [Datanucleus](http://www.datanucleus.org/) to support a wide range of data stores.

The first thing that needs to be done is to configure the `datanucleus.properties` file. The sample properties file in `config/` can be used for a MySQL database.

After this is done we can compile and enhance our classes:

```
$ ant enhance
```

The schema for our database can then be created by doing: 

```
$ ant createschema
```

The datastore should then be ready to be used by the [FIPS-Tool](https://github.com/wvrossem/FIPS-Tool) to upload data.

License
-------

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.



